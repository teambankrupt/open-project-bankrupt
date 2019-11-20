package com.example.webservice.domains.users.services;

import com.example.webservice.domains.users.models.entities.Profile;
import com.example.webservice.exceptions.exists.UserAlreadyExistsException;
import com.example.webservice.exceptions.forbidden.ForbiddenException;
import com.example.webservice.exceptions.invalid.UserInvalidException;
import com.example.webservice.exceptions.notfound.NotFoundException;
import com.example.webservice.exceptions.notfound.ProfileNotFoundException;
import com.example.webservice.exceptions.nullpointer.NullPasswordException;
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
