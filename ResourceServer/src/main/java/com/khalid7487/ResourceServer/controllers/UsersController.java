package com.khalid7487.ResourceServer.controllers;


import com.khalid7487.ResourceServer.response.UserRest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.env.Environment;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    Environment env;


    @GetMapping("/status/check")
    public  String status(){
        return "working on port: " + env.getProperty("local.server.port");
    }

    //@Secured("ROLE_developer")
    @PreAuthorize("hasRole('developer') or #id == #jwt.subject")
    @DeleteMapping(path = "/{id}")
    public String delete(@PathVariable String id , @AuthenticationPrincipal Jwt jwt){
        return "Delete user with id " + id + " and JWT subject "+ jwt.getSubject();
    }

    @PostAuthorize("returnObject.userId == #jwt.subject")
    @GetMapping(path = "/{id}")
    public UserRest getUser(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
        return new UserRest("khalid", "Hossen","6eefa5b8-4423-4c0f-b2ba-897309550424");
    }
}
