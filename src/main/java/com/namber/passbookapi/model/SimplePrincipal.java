package com.namber.passbookapi.model;

import lombok.Data;

import java.util.Collection;

@Data
public class SimplePrincipal extends Object{
    private String username;
    private Collection<Object> authorities;
}
