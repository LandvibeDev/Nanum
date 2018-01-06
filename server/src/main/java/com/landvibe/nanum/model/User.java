package com.landvibe.nanum.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Calendar;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String username;

    @Column
    private String email;

    @Column
    private String nickname;

    @Column
    private String profileImage;

    @Column
    private String tel;

    @Column
    private String snsId;

    @Column
    private String snsType;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Calendar createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Calendar updatedAt;

    public User() {
        this.createdAt = Calendar.getInstance();
        this.updatedAt = this.createdAt;
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.createdAt = Calendar.getInstance();
        this.updatedAt = this.createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSnsId() {
        return snsId;
    }

    public void setSnsId(String snsId) {
        this.snsId = snsId;
    }

    public String getSnsType() {
        return snsType;
    }

    public void setSnsType(String snsType) {
        this.snsType = snsType;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", tel='" + tel + '\'' +
                ", snsId='" + snsId + '\'' +
                ", snsType='" + snsType + '\'' +
                '}';
    }
}
