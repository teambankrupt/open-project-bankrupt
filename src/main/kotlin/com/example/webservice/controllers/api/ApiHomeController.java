package com.example.webservice.controllers.api;

import com.example.webservice.commons.utils.DateUtil;
import com.example.webservice.config.security.SecurityConfig;
import com.example.webservice.config.security.TokenService;
import com.example.webservice.entities.AcValidationToken;
import com.example.webservice.entities.User;
import com.example.webservice.entities.annotations.CurrentUser;
import com.example.webservice.entities.firebase.NotificationData;
import com.example.webservice.entities.firebase.PushNotification;
import com.example.webservice.exceptions.exists.UserAlreadyExistsException;
import com.example.webservice.exceptions.forbidden.ForbiddenException;
import com.example.webservice.exceptions.invalid.InvalidException;
import com.example.webservice.exceptions.invalid.UserInvalidException;
import com.example.webservice.exceptions.notfound.UserNotFoundException;
import com.example.webservice.exceptions.nullpointer.NullPasswordException;
import com.example.webservice.exceptions.unknown.UnknownException;
import com.example.webservice.services.AcValidationTokenService;
import com.example.webservice.services.NotificationService;
import com.example.webservice.services.SmsService;
import com.example.webservice.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.Date;

@RestController
@RequestMapping("/api/v1")
public class ApiHomeController {

    private final UserService userService;
    private final AcValidationTokenService acValidationTokenService;
    private final NotificationService notificationService;
    private final TokenService tokenService;
    private final SmsService smsService;

    @Value("${baseUrl}")
    private String baseUrl;

    @Autowired
    public ApiHomeController(UserService userService, AcValidationTokenService acValidationTokenService, NotificationService notificationService, TokenService tokenService, SmsService smsService) {
        this.userService = userService;
        this.acValidationTokenService = acValidationTokenService;
        this.notificationService = notificationService;
        this.tokenService = tokenService;
        this.smsService = smsService;
    }

    @GetMapping("")
    private ResponseEntity index() {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/swagger-ui.html"));
        return new ResponseEntity(headers, HttpStatus.TEMPORARY_REDIRECT);
    }

    @PostMapping("/register")
    private ResponseEntity register(@RequestBody User user, BindingResult bindingResult,
                                    @RequestParam(value = "sendPassword", defaultValue = "false") Boolean sendPassword,
                                    @CurrentUser User currentUser) throws UserAlreadyExistsException, InvalidException, NullPasswordException, JsonProcessingException, UnknownException {
        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().build();
        String tempRawPassword = user.getPassword();
        user = this.userService.save(user);

        // Notify admin for new registration
        this.notifyAdmin(user);

        // send sms to landlords with their password when field employee adds them
        if (currentUser != null && !currentUser.isAdmin() && sendPassword) {
            String message = "Dear User, your " + baseUrl + " credentials are - Username: " + user.getUsername() + " and Password: " + tempRawPassword;
            this.smsService.sendSms(user.getUsername(), message);
        }

        return ResponseEntity.ok("OTP sent!");
    }

    private void notifyAdmin(User user) throws UnknownException, InvalidException, JsonProcessingException {
        NotificationData data = new NotificationData();
        data.setTitle("New Registration -:- " + user.getName());
        String description = "Username: " + user.getUsername() + ", On: " + DateUtil.getReadableDateTime(new Date());
        String brief = description.substring(0, Math.min(description.length(), 100));
        data.setMessage(brief);
        data.setType(PushNotification.Type.ADMIN_NOTIFICATIONS.getValue());

        PushNotification notification = new PushNotification(null, data);
        notification.setTo("/topics/adminnotifications");
        this.notificationService.sendNotification(notification);
    }

    @PostMapping("/changePassword")
    private ResponseEntity changePassword(@RequestParam("currentPassword") String currentPassword,
                                          @RequestParam("newPassword") String newPassword,
                                          @CurrentUser User currentUser) throws UserNotFoundException, NullPasswordException, ForbiddenException, InvalidException {
        this.userService.changePassword(currentUser.getId(), currentPassword, newPassword);
        return ResponseEntity.ok().build();
    }

    // Password reset
    @GetMapping("/resetPassword")
    @ResponseStatus(HttpStatus.OK)
    private void requestResetPassword(@RequestParam("username") String username) throws UserNotFoundException, ForbiddenException, UnknownException {
        this.userService.handlePasswordResetRequest(username);
    }

    @PostMapping("/resetPassword")
    @ResponseStatus(HttpStatus.OK)
    private void resetPassword(@RequestParam("username") String username,
                               @RequestParam("token") String token,
                               @RequestParam("password") String password) throws UserNotFoundException, ForbiddenException, UserAlreadyExistsException, NullPasswordException, UserInvalidException {
        this.userService.resetPassword(username, token, password);
    }


    // Verify email when registration
    @PostMapping("/register/verify")
    @Transactional
    ResponseEntity verifyRegistration(@RequestParam("token") String token) throws Exception, NullPasswordException, UserAlreadyExistsException, UserInvalidException, ForbiddenException {
        if (!this.acValidationTokenService.isTokenValid(token))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Token Invalid!");
        AcValidationToken acValidationToken = this.acValidationTokenService.findByToken(token);
        if (acValidationToken == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Token doesn't exist!");
        User user = acValidationToken.getUser();
        user.setAccountNonExpired(true);
        user = this.userService.save(user);

        acValidationToken.setTokenValid(false);
        acValidationToken.setReason("Registration/Otp Confirmation");
        this.acValidationTokenService.save(acValidationToken);

        SecurityConfig.updateAuthentication(user);

        return ResponseEntity.ok(tokenService.createAccessToken(user));
    }


}
