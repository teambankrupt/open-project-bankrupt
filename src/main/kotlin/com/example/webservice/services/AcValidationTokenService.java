package com.example.webservice.services;


import com.example.webservice.entities.AcValidationToken;
import com.example.webservice.entities.User;
import com.example.webservice.exceptions.forbidden.ForbiddenException;

public interface AcValidationTokenService {
    AcValidationToken save(AcValidationToken acValidationToken);
    AcValidationToken findOne(Long id);
    AcValidationToken findByToken(String token) throws ForbiddenException;
    void delete(Long id);
    boolean isTokenValid(String token) throws ForbiddenException;
    boolean isLimitExceeded(User user);

    boolean canGetOTP(String phone);
}
