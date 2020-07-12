package com.example.app.domains.profiles.services;

import com.example.app.domains.profiles.models.entities.Profile;
import com.example.common.exceptions.exists.UserAlreadyExistsException;
import com.example.common.exceptions.forbidden.ForbiddenException;
import com.example.common.exceptions.invalid.UserInvalidException;
import com.example.common.exceptions.notfound.NotFoundException;
import com.example.common.exceptions.notfound.ProfileNotFoundException;
import com.example.common.exceptions.nullpointer.NullPasswordException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProfileService {
    Profile save(Profile profile, String username) throws NotFoundException, UserAlreadyExistsException, NullPasswordException, UserInvalidException;

    Profile getProfileByUserId(Long id) throws ProfileNotFoundException, ForbiddenException;

    Profile getOneProfile(Long id) throws ProfileNotFoundException, ForbiddenException;

    Profile getProfileByUsername(String username) throws ProfileNotFoundException, ForbiddenException;

    List<Profile> getAllProfile();

    Page<Profile> getAllProfilePaginated(int pageNumber);

    Long countProfile();

    void delete(Long id) throws ProfileNotFoundException;

    boolean isExists(Long id);

}
