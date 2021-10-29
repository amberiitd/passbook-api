package com.namber.passbookapi.model;

import lombok.Data;

import java.sql.Date;

@Data
public class CredOverview {
    private String credName;
    private String parentCredName;
    private String credGroupName;
    private String credWebServer;
    private String lastModified;

}
