package com.example.webservice.domains.users.services.beans;

import com.example.webservice.commons.PageAttr;
import com.example.webservice.commons.utils.NetworkUtil;
import com.example.webservice.commons.utils.PasswordUtil;
import com.example.webservice.commons.utils.SessionIdentifierGenerator;
import com.example.webservice.config.security.SecurityConfig;
import com.example.webservice.domains.common.services.MailService;
import com.example.webservice.domains.common.services.SmsService;
import com.example.webservice.domains.users.models.entities.AcValidationToken;
import com.example.webservice.domains.users.models.entities.Role;
import com.example.webservice.domains.users.models.entities.User;
import com.example.webservice.domains.users.repositories.UserRepository;
import com.example.webservice.domains.users.services.AcValidationTokenService;
import com.example.webservice.domains.users.services.RoleService;
import com.example.webservice.domains.users.services.UserService;
import com.example.webservice.exceptions.exists.UserAlreadyExistsException;
import com.example.webservice.exceptions.forbidden.ForbiddenException;
import com.example.webservice.exceptions.invalid.InvalidException;
import com.example.webservice.exceptions.invalid.UserInvalidException;
import com.example.webservice.exceptions.notfound.NotFoundException;
import com.example.webservice.exceptions.notfound.UserNotFoundException;
import com.example.webservice.exceptions.nullpointer.NullPasswordException;
import com.example.webservice.exceptions.unknown.UnknownException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final AcValidationTokenService acValidationTokenService;
    private final MailService mailService;
    private final RoleService roleService;
    private final RegistrationAttemptService registrationAttemptService;
    private final SmsService smsService;

    @Value("${applicationName}")
    private String applicationName;
    @Value("${baseUrlApi}")
    private String baseUrlApi;
    @Value("${admin.phone1}")
    private String adminPhone1;
    @Value("${admin.phone2}")
    private String adminPhone2;
    @Value("${token.validity}")
    private String tokenValidity;

    @Autowired
    public UserServiceImpl(UserRepository userRepo, AcValidationTokenService acValidationTokenService, MailService mailService, RoleService roleService, RegistrationAttemptService registrationAttemptService, SmsService smsService) {
        this.userRepo = userRepo;
        this.acValidationTokenService = acValidationTokenService;
        this.mailService = mailService;
        this.roleService = roleService;
        this.registrationAttemptService = registrationAttemptService;
        this.smsService = smsService;
    }

    @Override
    public User findByUsername(String username) throws UserNotFoundException {
        if (username == null) throw new UserNotFoundException("Username can not be null!");
        return this.userRepo.findByUsername(username);
    }

    @Override
    public User findByPhone(String phoneNumber) throws InvalidException {
        if (phoneNumber == null) throw new InvalidException("Phone number can not be null!");
        return this.userRepo.findByPhone(phoneNumber);
    }

    @Override
    public User findByEmail(String email) throws UserNotFoundException {
        if (email == null) throw new UserNotFoundException("Email can not be null!");
        return this.userRepo.findByPhone(email);
    }

    @Override
    public User findByUsernameOrPhone(String usernameOrPhone) throws UserNotFoundException {
        User user = this.userRepo.findByUsername(usernameOrPhone);
        if (user == null)
            user = this.userRepo.findByPhone(usernameOrPhone);
        if (user == null)
            throw new UserNotFoundException("Could not find user with username or email " + usernameOrPhone);
        return user;
    }

    @Override
    public Page<User> searchUser(String query, int page, int size) {
        return this.userRepo.searchByNameOrUsername(query, PageAttr.getPageRequest(page, size));
    }

    @Override
    public Page<User> findAll(int page) {
        if (page < 0) page = 0;
        return this.userRepo.findAll(PageAttr.getPageRequest(page));
    }

    @Override
    public Page<User> findByRole(String role, int page) {
        return this.userRepo.findByRolesName(role, PageAttr.getPageRequest(page));
    }

    @Override
    public List<User> findByRole(String role) {
        return this.userRepo.findByRolesName(role);
    }

    @Override
    public User findOne(Long id) throws UserNotFoundException {
        if (id == null) throw new UserNotFoundException("User id can not be null!");
        return this.userRepo.findById(id).orElse(null);
    }

    @Override
    public User save(User user) throws UserAlreadyExistsException, UserInvalidException, NullPasswordException, NotFoundException {
        if (!this.isValid(user)) throw new UserInvalidException("User invalid");

        // check if user already exists
        if (user.getId() == null && this.exists(user))
            throw new UserAlreadyExistsException("User already exists with this email or username");
        if (user.getPhone() == null)
            throw new UserInvalidException("Phone number can not be empty!");
        if (user.getPassword() == null || user.getPassword().length() < 6)
            throw new UserInvalidException("Password length must be at least 6 or more!");

        // set Roles
        Role role = this.roleService.find("User").orElseThrow(() -> new NotFoundException("Could not find role with name $name"));
        user.grantRole(role);

        // Execute only when user is being registered
        if (user.getId() == null) {
            // Encrypt passwprd
            user.setPassword(PasswordUtil.encryptPassword(user.getPassword(), PasswordUtil.EncType.BCRYPT_ENCODER, null));

            // flood control
            String ip = NetworkUtil.getClientIP();
            if (this.registrationAttemptService.isBlocked(ip))
                throw new UserInvalidException("Maximum limit exceed!");
            this.registrationAttemptService.registrationSuccess(ip);
        }
//        // sent otp for new user
//        boolean newUser = user.getId() == null;
//        user = this.userRepo.save(user);
//        if (newUser) try {
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTimeInMillis(System.currentTimeMillis() + 120000);
//            this.requireAccountValidationByOTP(user.getPhoneNumber(), calendar.getTime());
//        } catch (UserNotFoundException e) {
//            e.printStackTrace();
//        }
        return this.userRepo.save(user);
    }

    private boolean isValid(User user) {
        return user != null && user.getPassword() != null;
    }

    @Override
    public boolean exists(User user) {
        if (user == null) throw new IllegalArgumentException("user can't be null");
        return this.userRepo.findByUsername(user.getUsername()) != null
                || this.userRepo.findByPhone(user.getPhone()) != null;
    }

    @Override
    public User getAuthentication(String username, String password) throws UserNotFoundException, NullPasswordException {
        User user = this.findByUsernameOrPhone(username);
        if (PasswordUtil.matches(user.getPassword(), password))
            return user;
        return null;
    }


    @Override
    public boolean requireAccountValidationByOTP(String phone, Date tokenValidUntil) throws InvalidException, UserAlreadyExistsException, ForbiddenException {
        if (phone == null) throw new IllegalArgumentException("Phone invalid!");
        User user = this.findByPhone(phone);
        if (user != null) throw new UserAlreadyExistsException("User already registered with this phone number!");
        if (!this.acValidationTokenService.canGetOTP(phone))
            throw new ForbiddenException("Already sent an OTP. Please try agin in two minutes!");
        AcValidationToken acValidationToken = new AcValidationToken();
        acValidationToken.setToken(String.valueOf(SessionIdentifierGenerator.generateOTP()));
        acValidationToken.setTokenValid(true);
        acValidationToken.setUsername(phone);
        acValidationToken.setTokenValidUntil(tokenValidUntil);
        // save acvalidationtoken
        acValidationToken = this.acValidationTokenService.save(acValidationToken);
        AcValidationToken finalAcValidationToken = acValidationToken;
        new Thread(() -> {
            try {
                Thread.sleep(2 * 60 * 1000);
                finalAcValidationToken.setTokenValid(false);
                acValidationTokenService.save(finalAcValidationToken);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // build confirmation link
        String tokenMessage = "Your " + this.applicationName + " token is: " + acValidationToken.getToken();
        // send link by sms
        return this.smsService.sendSms(phone, tokenMessage);
    }

    @Override
    public void requireAccountValidationByEmail(String email, String validationUrl) throws UserNotFoundException {
        if (email == null) throw new IllegalArgumentException("Email invalid!");
        User user = this.findByEmail(email);
        SessionIdentifierGenerator sessionIdentifierGenerator = new SessionIdentifierGenerator();
        AcValidationToken acValidationToken = new AcValidationToken();
        acValidationToken.setToken(sessionIdentifierGenerator.nextSessionId());
        acValidationToken.setTokenValid(true);
        acValidationToken.setUser(user);
        // save acvalidationtoken
        acValidationToken = this.acValidationTokenService.save(acValidationToken);
        if (validationUrl == null) {
            this.mailService.sendEmail(user.getEmail(), "ShareMyRevenue verification token", "Your verification token is: " + acValidationToken.getToken());
            return;
        }
        // build confirmation link
        String confirmationLink = baseUrlApi.trim() + validationUrl + "?token=" + acValidationToken.getToken() + "&enabled=true";
        // send link by email
        this.mailService.sendEmail(user.getEmail(), "Please verify you ShareMyRevenue account", "Please verify your email by clicking this link " + confirmationLink);
    }

    @Override
    @Transactional
    public User resetPassword(String username, String token, String password) throws NullPasswordException, UserAlreadyExistsException, UserInvalidException, ForbiddenException, NotFoundException {
        if (password.length() < 6)
            throw new ForbiddenException("Password length should be at least 6");
        AcValidationToken acValidationToken = this.acValidationTokenService.findByToken(token);
        User user = acValidationToken.getUser();
        if (username == null || !username.equals(user.getUsername()))
            throw new ForbiddenException("You are not authorized to do this action!");
        user.setPassword(PasswordUtil.encryptPassword(password, PasswordUtil.EncType.BCRYPT_ENCODER, null));
        acValidationToken.setTokenValid(false);
        acValidationToken.setReason("Password Reset");
        user = this.save(user);
        acValidationToken.setUser(user);
        this.acValidationTokenService.save(acValidationToken);
        return user;
    }

    @Override
    public Page<User> findUsersIn(List<Long> userIds, int page) {
        return this.userRepo.findByIdIn(userIds, PageAttr.getPageRequest(page));
    }

    @Override
    public User changePassword(Long id, String currentPassword, String newPassword) throws NullPasswordException, UserNotFoundException, InvalidException, ForbiddenException {
        User user = this.findOne(id);
        if (user == null) throw new UserNotFoundException("Could not find user with id " + id);

        if (!PasswordUtil.matches(user.getPassword(), currentPassword))
            throw new ForbiddenException("Password doesn't match");

        if (newPassword.length() < 6) throw new InvalidException("Password invalid");
        user.setPassword(PasswordUtil.encryptPassword(newPassword, PasswordUtil.EncType.BCRYPT_ENCODER, null));
        user = this.userRepo.save(user);
        return user;
    }

    @Override
    public User setPassword(Long id, String newPassword) throws NullPasswordException, UserNotFoundException, InvalidException, ForbiddenException {
        User currentUser = SecurityConfig.getCurrentUser();
        if (currentUser == null || !currentUser.isAdmin())
            throw new ForbiddenException("You are not authorised to do this action.");

        User user = this.findOne(id);
        if (user == null) throw new UserNotFoundException("Could not find user with id " + id);

        if (newPassword.length() < 6) throw new InvalidException("Password invalid");
        user.setPassword(PasswordUtil.encryptPassword(newPassword, PasswordUtil.EncType.BCRYPT_ENCODER, null));
        user = this.userRepo.save(user);
        return user;
    }

    @Override
    public void handlePasswordResetRequest(String username) throws UserNotFoundException, ForbiddenException, UnknownException {
        User user = this.findByUsername(username);
        if (this.acValidationTokenService.isLimitExceeded(user))
            throw new ForbiddenException("Limit exceeded!");

        int otp = SessionIdentifierGenerator.generateOTP();
        String message = "Your " + this.applicationName + " OTP is: " + otp;
        boolean success = this.smsService.sendSms(user.getUsername(), message);
        // save validation token
        if (!success) throw new UnknownException("Could not send SMS");

        AcValidationToken resetToken = new AcValidationToken();
        resetToken.setUser(user);
        resetToken.setToken(String.valueOf(otp));
        resetToken.setTokenValid(true);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis() + Integer.parseInt(this.tokenValidity));
        resetToken.setTokenValidUntil(calendar.getTime());

        this.acValidationTokenService.save(resetToken);
    }

    @Override
    public User setRoles(Long id, List<Long> roleIds) throws NotFoundException, UserAlreadyExistsException, NullPasswordException, UserInvalidException {
        User user = this.findOne(id);
        boolean isAdmin = user.isAdmin(); // check if user is admin
        List<Role> roles = this.roleService.findByIds(roleIds);
        user.setRoles(roles);
        if (isAdmin) {// set admin role explicitly after clearing roles
            Role role = this.roleService.find(Role.ERole.Admin.toString()).orElseThrow(() -> new NotFoundException("Could not find role with name " + Role.ERole.Admin.toString()));
            user.getRoles().add(role);
        }
        return this.save(user);
    }


}
