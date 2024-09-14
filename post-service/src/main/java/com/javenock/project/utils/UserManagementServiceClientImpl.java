package com.javenock.project.utils;

import com.javenock.project.model.vo.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserManagementServiceClientImpl {

//    private static final Logger log = LoggerFactory.getLogger(UserManagementServiceClientImpl.class);
//
//    @Autowired
//    private UserManagementServiceClient userManagementServiceClient;
//
//    @HystrixCommand(fallbackMethod = "fetchInstitutionFallback")
//    public Optional<User> fetchUser(String username) {
//        log.info("===========name here===={}", username);
//        return Optional.of(userManagementServiceClient.fetchUserByUsername(username));
//    }
//
//    public Optional<User> fetchInstitutionFallback(String username) {
//        return Optional.empty();
//    }

}
