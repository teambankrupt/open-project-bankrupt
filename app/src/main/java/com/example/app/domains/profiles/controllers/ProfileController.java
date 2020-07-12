package com.example.app.domains.profiles.controllers;

import com.example.app.domains.profiles.models.entities.Profile;
import com.example.app.domains.profiles.services.ProfileService;
import com.example.common.exceptions.exists.UserAlreadyExistsException;
import com.example.common.exceptions.forbidden.ForbiddenException;
import com.example.common.exceptions.invalid.UserInvalidException;
import com.example.common.exceptions.notfound.NotFoundException;
import com.example.common.exceptions.notfound.ProfileNotFoundException;
import com.example.common.exceptions.nullpointer.NullPasswordException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profiles")
@Api(tags = "Profiles", description = "Create/update user profile")
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


}
