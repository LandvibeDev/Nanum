package com.landvibe.nanum.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.persistence.Entity;

@Data
public class GoogleUser extends User {

    public GoogleUser() {
        super();
        super.setSnsType("google");
    }

    @Override
    @JsonProperty("name")
    public void setUsername(String username) {
        super.setUsername(username);
    }

    @Override
    @JsonProperty("email")
    public void setEmail(String email) {
        super.setEmail(email);
    }

    @JsonProperty("id")
    public void setSnsId(String snsId) {
        super.setSnsId(snsId);
    }

    @Override
    @JsonProperty("picture")
    public void setProfileImage(String profileImage) {
        super.setProfileImage(profileImage);
    }
}