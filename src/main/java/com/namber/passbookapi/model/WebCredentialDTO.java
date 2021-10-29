package com.namber.passbookapi.model;

import lombok.Data;

@Data
public class WebCredentialDTO {
    private String credGroupName;
    private String credName;
    private String parentCredName;
    private String credWebServer;
    private String credUserName;
    private String credPassword;
}
