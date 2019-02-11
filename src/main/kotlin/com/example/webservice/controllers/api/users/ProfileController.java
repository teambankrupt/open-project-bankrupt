package com.example.webservice.controllers.api.users;

import com.example.webservice.commons.utils.ImageValidator;
import com.example.webservice.entities.Profile;
import com.example.webservice.exceptions.exists.UserAlreadyExistsException;
import com.example.webservice.exceptions.forbidden.ForbiddenException;
import com.example.webservice.exceptions.invalid.ImageInvalidException;
import com.example.webservice.exceptions.invalid.UserInvalidException;
import com.example.webservice.exceptions.notfound.NotFoundException;
import com.example.webservice.exceptions.notfound.ProfileNotFoundException;
import com.example.webservice.exceptions.nullpointer.NullPasswordException;
import com.example.webservice.services.ProfileService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("")
    private ResponseEntity getAllProfile(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        Page profilePage = this.profileService.getAllProfilePaginated(page);
        return ResponseEntity.ok(profilePage);
    }

//    @PostMapping("")
//    private ResponseEntity<Profile> saveProfile(@RequestBody Profile profile,
//                                                @CurrentUser User user) throws ProfileNotFoundException, UserAlreadyExistsException, NullPasswordException, UserInvalidException, UserNotFoundException {
//        profile = this.profileService.saveProfile(profile, user.getUsername());
//        return ResponseEntity.ok(profile);
//    }

    // GET PROFILE
//    @GetMapping("/{id}")
//    private ResponseEntity<Profile> getOneProfile(@PathVariable("id") Long id) throws ProfileNotFoundException, ForbiddenException {
//        return ResponseEntity.ok(this.profileService.getOneProfile(id));
//    }

    @GetMapping("/user/{username}")
    private ResponseEntity<Profile> getProfile(@PathVariable("username") String username) throws ProfileNotFoundException, ForbiddenException {
        return ResponseEntity.ok(this.profileService.getProfileByUsername(username));
    }


    // UPDATE PROFILE
    @PutMapping("/user/{username}")
    private ResponseEntity<Profile> updateProfile(@RequestBody Profile profile,
                                                     @PathVariable("username") String username) throws NotFoundException, UserAlreadyExistsException, NullPasswordException, UserInvalidException, ForbiddenException {
        // include existing profile picture when updating
        if (profile == null) return ResponseEntity.badRequest().build();
        profile = this.profileService.save(profile, username);
        return ResponseEntity.ok(profile);
    }

    // DELETE

    @DeleteMapping("/{id}")
    private ResponseEntity deleteProfile(@PathVariable("id") Long id) throws ProfileNotFoundException {
        this.profileService.delete(id);
        return ResponseEntity.ok().build();
    }

    // PROFILE PICTURE
    @PostMapping("/profilePic/{username}")
    private ResponseEntity updateProfilePic(@PathVariable("username") String username,
                                            @RequestParam("file") MultipartFile file) throws IOException, NotFoundException, ImageInvalidException, UserAlreadyExistsException, NullPasswordException, UserInvalidException {
        if (!ImageValidator.isImageValid(file))
            throw new ImageInvalidException("Invalid Image");
        return ResponseEntity.ok(this.profileService.setProfileImage(username, file));
    }

    @GetMapping("/profilePic/{username}")
    private ResponseEntity getProfilePic(@PathVariable("username") String username) throws ProfileNotFoundException, ForbiddenException, IOException {
        Profile profile = this.profileService.getProfileByUsername(username);
        File file = new File(profile.getProfilePicturePath());
        if (!file.exists()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(FileUtils.readFileToByteArray(file));
    }

    @GetMapping("/profilePic/user/{userId}")
    private ResponseEntity getProfilePhotoByUserId(@PathVariable("userId") Long userId) throws ProfileNotFoundException, ForbiddenException, IOException {
        Profile profile = this.profileService.getProfileByUserId(userId);
        File file = new File(profile.getProfilePicturePath());
        if (!file.exists()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(FileUtils.readFileToByteArray(file));
    }

}
