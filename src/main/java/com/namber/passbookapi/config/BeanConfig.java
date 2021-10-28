package com.namber.passbookapi.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class BeanConfig {

    @Bean
    public ModelMapper mapper(){
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        Map<String, PasswordEncoder> encoderMap = new HashMap<>();
        encoderMap.put("noop", NoOpPasswordEncoder.getInstance());
        return new DelegatingPasswordEncoder("noop", encoderMap);
    }
}
