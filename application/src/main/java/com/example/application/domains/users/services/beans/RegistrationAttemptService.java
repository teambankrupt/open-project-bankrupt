//package com.example.application.domains.users.services.beans;
//
//import com.google.common.cache.CacheBuilder;
//import com.google.common.cache.CacheLoader;
//import com.google.common.cache.LoadingCache;
//import org.springframework.stereotype.Service;
//
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.TimeUnit;
//
//@Service
//public class RegistrationAttemptService {
//    private final int MAX_ATTEMPT = 1;
//    private LoadingCache<String, Integer> attemptsCache;
//
//    public RegistrationAttemptService() {
//        super();
//        attemptsCache = CacheBuilder.newBuilder().
//                expireAfterWrite(1, TimeUnit.MINUTES).build(new CacheLoader<String, Integer>() {
//            public Integer load(String key) {
//                return 0;
//            }
//        });
//    }
//
//    public void registrationSuccess(String key) {
//        int attempts;
//        try {
//            attempts = attemptsCache.get(key);
//        } catch (ExecutionException e) {
//            attempts = 0;
//        }
//        attempts++;
//        attemptsCache.put(key, attempts);
//    }
//
//    public boolean isBlocked(String key) {
//        try {
//            return attemptsCache.get(key) >= MAX_ATTEMPT;
//        } catch (ExecutionException e) {
//            return false;
//        }
//    }
//}
