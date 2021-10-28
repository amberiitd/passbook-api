package com.namber.passbookapi.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class WebCredential {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String credId;

    private String username;
    private String credGroupName;

    private String credName;
    private String parentCredName;

    private String credWebServer;
    private String credUserName;
    private String credPassword;
    private String lastModified;

}
