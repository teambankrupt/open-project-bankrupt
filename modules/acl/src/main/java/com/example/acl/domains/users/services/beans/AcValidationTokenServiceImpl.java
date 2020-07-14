package com.example.acl.domains.users.services.beans;

import com.example.common.utils.DateUtil;
import com.example.acl.domains.users.models.entities.AcValidationToken;
import com.example.auth.entities.User;
import com.example.acl.domains.users.repositories.AcValidationTokenRepository;
import com.example.acl.domains.users.services.AcValidationTokenService;
import com.example.common.exceptions.forbidden.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AcValidationTokenServiceImpl implements AcValidationTokenService {
    private final AcValidationTokenRepository tokenRepo;

    @Autowired
    public AcValidationTokenServiceImpl(AcValidationTokenRepository tokenRepo) {
        this.tokenRepo = tokenRepo;
    }

    @Override
    public AcValidationToken save(AcValidationToken acValidationToken) {
        return this.tokenRepo.save(acValidationToken);
    }

    @Override
    public AcValidationToken findOne(Long id) {
        return this.tokenRepo.findById(id).orElse(null);
    }

    @Override
    public AcValidationToken findByToken(String token) throws ForbiddenException {
        if (token == null) throw new ForbiddenException("Invalid Token");
        AcValidationToken tokenEntity = this.tokenRepo.findFirstByTokenOrderByIdDesc(token);
        if (tokenEntity == null || !tokenEntity.isTokenValid()) throw new ForbiddenException("Invalid Token");
        return tokenEntity;
    }

    @Override
    public void delete(Long id) {
        this.tokenRepo.deleteById(id);
    }

    @Override
    public boolean isTokenValid(String token) throws ForbiddenException {
        if (token == null || token.isEmpty()) return false;
        AcValidationToken acValidationToken = this.findByToken(token);
        return acValidationToken != null && acValidationToken.isTokenValid();
    }

    @Override
    public boolean isLimitExceeded(User user) {
        if (user == null) return true;
        Date date = new Date();
        Date fromDate = DateUtil.getDayStart(date);
        Date toDate = DateUtil.getDayEnd(date);
        return this.tokenRepo.count(user.getId(), fromDate, toDate) >= 3;
    }

    @Override
    public boolean canGetOTP(String username) {
        AcValidationToken token = this.tokenRepo.findFirstByUsernameOrderByIdDesc(username);
        return token == null || !token.isTokenValid() || new Date().after(token.getTokenValidUntil());
    }
}
