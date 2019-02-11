package com.example.webservice.services.impl;

import com.example.webservice.commons.PageAttr;
import com.example.webservice.commons.utils.Validator;
import com.example.webservice.config.security.SecurityConfig;
import com.example.webservice.entities.Profile;
import com.example.webservice.entities.User;
import com.example.webservice.entities.pojo.UploadProperties;
import com.example.webservice.exceptions.exists.UserAlreadyExistsException;
import com.example.webservice.exceptions.forbidden.ForbiddenException;
import com.example.webservice.exceptions.invalid.UserInvalidException;
import com.example.webservice.exceptions.notfound.NotFoundException;
import com.example.webservice.exceptions.notfound.ProfileNotFoundException;
import com.example.webservice.exceptions.nullpointer.NullPasswordException;
import com.example.webservice.repositories.ProfileRepository;
import com.example.webservice.services.FileUploadService;
import com.example.webservice.services.ProfileService;
import com.example.webservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService{

    private final ProfileRepository profileRepository;
    private final UserService userService;
    private final FileUploadService fileUploadService;

    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository, UserService userService, FileUploadService fileUploadService) {
        this.profileRepository = profileRepository;
        this.userService = userService;
        this.fileUploadService = fileUploadService;
    }

    @Override
    public Profile save(Profile profile, String username) throws NotFoundException, UserAlreadyExistsException, NullPasswordException, UserInvalidException {
        if (profile == null || username == null)
            throw new IllegalArgumentException("Profile or username can not be null");
        User user = this.userService.findByUsernameOrPhone(username);
        profile.setUser(user);
        Profile exProfile = this.profileRepository.findByUserUsername(username);
        if (exProfile != null) {
            profile.setId(exProfile.getId());
            if (Validator.nullOrEmpty(profile.getProfilePicturePath()))
                profile.setProfilePicturePath(exProfile.getProfilePicturePath());
        }
        if (profile.getAddress() == null ||
                profile.getAddress().getArea() == null
                || profile.getAddress().getPoliceStation() == null
                || profile.getAddress().getDistrict() == null)
            throw new NotFoundException("Address doean\'t meet requred informations");

        // update name of user
        user.setName(profile.getName());
        this.userService.save(user);

        SecurityConfig.updateAuthentication(profile.getUser());
        return this.profileRepository.save(profile);
    }

    @Override
    public Profile getProfileByUserId(Long id) throws ProfileNotFoundException, ForbiddenException {
        Profile profile= this.profileRepository.findByUserId(id);
        if (profile==null) throw new ProfileNotFoundException("Could not find profile for user id: "+id);
        return profile;
    }

    @Override
    public Profile getOneProfile(Long id) throws ProfileNotFoundException, ForbiddenException {
        if (id == null || !this.isExists(id))
            throw new ProfileNotFoundException("Profile could not found.");
        Profile profile = this.profileRepository.findOne(id);
        if (profile == null)
            throw new ProfileNotFoundException("Profile could not be found.");
        if (!profile.hasAuthorizedAccess()) throw new ForbiddenException("You can not access this profile.");
        return profile;
    }

    @Override
    public Profile getProfileByUsername(String username) throws ProfileNotFoundException, ForbiddenException {
        Profile profile = this.profileRepository.findByUserUsername(username);
        if(profile == null)
            throw new ProfileNotFoundException("Profile could not be found.");
        if (!profile.hasAuthorizedAccess()) throw new ForbiddenException("You can not access this profile.");
        return profile;
    }

    @Override
    public List<Profile> getAllProfile() {
        return this.profileRepository.findAll();
    }

    @Override
    public Page<Profile> getAllProfilePaginated(int pageNumber) {
        return this.profileRepository.findAll(PageAttr.getPageRequest(pageNumber));
    }

    @Override
    public Long countProfile() {
        return this.profileRepository.count();
    }

    @Override
    public void delete(Long id) throws ProfileNotFoundException {
        if (id == null || !this.isExists(id))
            throw new ProfileNotFoundException("Profile could not found.");
        this.profileRepository.delete(id);
    }

    @Override
    public boolean isExists(Long id) {
        return this.profileRepository.exists(id);
    }

    @Override
    public boolean setProfileImage(String username, MultipartFile image) throws IOException, NotFoundException, UserAlreadyExistsException, NullPasswordException, UserInvalidException {
        if (username == null)
            throw new ProfileNotFoundException("Profile could not found.");
        Profile profile = this.profileRepository.findByUserUsername(username);
        if (profile==null)
            throw new ProfileNotFoundException("Profile could not found.");
        UploadProperties properties = this.fileUploadService.uploadFile(image, UploadProperties.NameSpaces.USERS.getValue(), profile.getUser().getUsername() + "/profile", true);
        profile.setProfilePicturePath(properties.getFilePath());
        this.save(profile, profile.getUser().getUsername());
        return true;
    }


}
