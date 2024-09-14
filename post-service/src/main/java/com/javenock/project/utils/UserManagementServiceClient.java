package com.javenock.project.utils;

import com.javenock.project.model.vo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("user-management-service")
public interface UserManagementServiceClient {

    @RequestMapping(method = RequestMethod.GET, value = "/auth/internal/user")
    User fetchUserByUsername(@RequestParam("username") String username);
}
