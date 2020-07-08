package com.example.application.domains.users.services;


import com.example.application.domains.users.models.entities.AcValidationToken;
import com.example.application.domains.users.models.entities.User;
import com.example.common.exceptions.forbidden.ForbiddenException;

public interface AcValidationTokenService {
    AcValidationToken save(AcValidationToken acValidationToken);
    AcValidationToken findOne(Long id);
    AcValidationToken findByToken(String token) throws ForbiddenException;
    void delete(Long id);
    boolean isTokenValid(String token) throws ForbiddenException;
    boolean isLimitExceeded(User user);

    boolean canGetOTP(String username);
}
