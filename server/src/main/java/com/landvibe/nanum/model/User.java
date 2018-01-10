package com.landvibe.nanum.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class User {

    @Id
    @Column(name = "user_id")
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

    @JsonIgnore
    public String getSnsId(){
        return this.snsId;
    }
}