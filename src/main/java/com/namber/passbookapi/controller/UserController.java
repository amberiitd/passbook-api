package com.namber.passbookapi.controller;

import com.namber.passbookapi.model.UserDTO;
import com.namber.passbookapi.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @GetMapping("/user")
    public UserDTO getUser(){
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.userDetailsService.getUserDTO(user.getUsername());
    }

    @PostMapping("/user-register")
    public ResponseEntity registerUser(@RequestBody UserDTO user){
        try{
            this.userDetailsService.registerUser(user);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.OK);
    }
}
