package com.namber.passbookapi.controller;

import com.namber.passbookapi.model.CredOverview;
import com.namber.passbookapi.model.WebCredential;
import com.namber.passbookapi.model.WebCredentialDTO;
import com.namber.passbookapi.service.DefaultDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/pass")
public class DataController {
    @Autowired
    private DefaultDataService dataService;

    @GetMapping("/groups")
    public List<String> getCredGroup() throws Exception{
        String user = getPrincipal();
        if (user != null){
            return this.dataService.getCredGroups(user);
        }else{
            throw new Exception("NOT AUTHORIZED");
        }
    }

    @GetMapping("/creds")
    public List<CredOverview> getCredOverview(@RequestParam String groupName) throws Exception{
        String user = getPrincipal();
        if (user != null){
            return this.dataService.getCredOverview(user, groupName, null);
        }else{
            throw new Exception("NOT AUTHORIZED");
        }
    }

    @GetMapping("/cred-history")
    public List<CredOverview> getCredOverview(@RequestParam String groupName, @RequestParam String credParentName) throws Exception{
        String user = getPrincipal();
        if (user != null){
            return this.dataService.getCredOverview(user, groupName, credParentName);
        }else{
            throw new Exception("NOT AUTHORIZED");
        }
    }

    @GetMapping("/cred-details")
    public WebCredential getCredHistory(@RequestParam String groupName, @RequestParam String credName) throws Exception{
        String user = getPrincipal();
        if (user != null){
            return this.dataService.getCredDetail(user, groupName, credName);
        }else{
            throw new Exception("NOT AUTHORIZED");
        }
    }

    @PostMapping("/cred-register")
    public ResponseEntity getCredDetail(@RequestBody WebCredentialDTO credentialDTO) throws Exception{
        String msg ="success";
        try {
            String user = getPrincipal();
            if (user != null){
                this.dataService.saveCred(user, credentialDTO);
            }else{
                msg = "user unauthorized";
            }
        }catch (Exception e){
            msg = e.getMessage();
        }
        return  new ResponseEntity(msg, HttpStatus.OK);

    }

    private String getPrincipal(){
        UserDetails user= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null && user.getUsername()!= null) {
            return user.getUsername();
        }

        return "namber";
    }
}
