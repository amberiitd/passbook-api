package com.namber.passbookapi.service;

import com.namber.passbookapi.dao.WebCredRepo;
import com.namber.passbookapi.model.CredOverview;
import com.namber.passbookapi.model.WebCredential;
import com.namber.passbookapi.model.WebCredentialDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class DefaultDataService {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private WebCredRepo credRepo;

    @Autowired
    private PasswordEncoder encoder;

    public List<String> getCredGroups(String principal){
        List<String> groups = new ArrayList<>();
        this.credRepo.findByUsername(principal).forEach(cred -> {
            if(!groups.contains(cred.getCredGroupName())) {
                groups.add(cred.getCredGroupName());
            }
        });
        return groups;
    }

    public List<CredOverview> getCredOverview(String user, String groupName, String nameParam) {
        List<CredOverview> overviews = new ArrayList<>();
        List<WebCredential> creds;
        if (nameParam != null && !nameParam.isEmpty()){
            creds = this.credRepo.getCredByParentName(user, groupName, nameParam);
        }else{
            creds = this.credRepo.getCredByGroupName(user, groupName);
        }
        if(creds != null) {
            creds.forEach(cred -> {
                overviews.add(mapper.map(cred, CredOverview.class));
            });
        }

        return overviews;
    }

    public WebCredential getCredDetail(String user, String groupName, String credName) {
        List<WebCredential> creds = this.credRepo.getCredByName(user, groupName, credName);
        if (!creds.isEmpty()){
            return creds.get(0);
        }
        return null;
    }

    public void saveCred(String user, WebCredentialDTO credentialDTO) throws Exception{
        WebCredential credential = mapper.map(credentialDTO, WebCredential.class);
        credential.setCredPassword(encoder.encode(credential.getCredPassword()));
        credential.setCredId(UUID.randomUUID().toString());
        credential.setUsername(user);
        credential.setLastModified(new Date().toString());

        WebCredential savedCred = getCredDetail(credential.getUsername(), credential.getCredGroupName(), credential.getCredName());

        if( savedCred == null) {
            this.credRepo.save(credential);
        }else{
            savedCred.setParentCredName(savedCred.getCredName());
            savedCred.setCredName(savedCred.getCredName()+ UUID.randomUUID().toString());
            this.credRepo.save(savedCred);
            this.credRepo.save(credential);

            throw new Exception("Credential by name ["+ credential.getCredName()+"] updated");
        }
    }
}
