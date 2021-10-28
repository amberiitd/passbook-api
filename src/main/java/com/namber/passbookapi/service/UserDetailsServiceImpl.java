package com.namber.passbookapi.service;

import com.namber.passbookapi.dao.UserRepo;
import com.namber.passbookapi.model.User;
import com.namber.passbookapi.model.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = this.userRepo.findByUsername(username);
        if(users != null && !users.isEmpty()){
            User user = users.get(0);
            List<GrantedAuthority> authorities = getGrantedAuthorities(user.getAuthorities());
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        }
        return null;
    }

    public UserDTO getUserDTO(String username){
        List<User> users = this.userRepo.findByUsername(username);
        if(users != null && !users.isEmpty()){
            User user = users.get(0);
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(user.getUsername());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setScope(Arrays.asList(user.getAuthorities().split(",")));
            return userDTO;
        }

        return null;
    }

    private List<GrantedAuthority> getGrantedAuthorities(String authorities) {
        List<GrantedAuthority> auths = new ArrayList<>();
        for ( String authStr :authorities.split(",")){
            auths.add(new SimpleGrantedAuthority(authStr));
        }

        return auths;
    }

    public void registerUser(UserDTO userDTO) throws Exception{
        if (getUserDTO(userDTO.getUsername()) == null) {
            User user = mapper.map(userDTO, User.class);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setUserId(UUID.randomUUID().toString());
            user.setAuthorities("USER");
            this.userRepo.save(user);
        }else{
            throw new Exception("Username already exist");
        }
    }
}
