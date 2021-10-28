package com.namber.passbookapi.model;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private List<String> scope;
}
