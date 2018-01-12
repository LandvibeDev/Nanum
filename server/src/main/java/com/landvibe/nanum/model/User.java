package com.landvibe.nanum.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Set;

@Entity
@Setter
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

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

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "users")
    private Set<Project> projects;

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