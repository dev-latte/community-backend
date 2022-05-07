package com.commu.back.communitybackend.config.auth.dto;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String screenName;
    private String displayName;
    private String picture;

    private static final long serialVersionUID = 1L;

    public SessionUser(String screenName, String displayName, String picture) {
        this.screenName = screenName;
        this.displayName = displayName;
        this.picture = picture;
    }
}
