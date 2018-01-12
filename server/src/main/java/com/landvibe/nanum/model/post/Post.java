package com.landvibe.nanum.model.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.landvibe.nanum.model.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import sun.util.resources.cldr.naq.CalendarData_naq_NA;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

//@Entity
//@Getter
//@Setter
//@ToString
//@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@MappedSuperclass
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String title;

    @Column
    private String content;

//    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
//    @JoinColumn(name = "user_id")
//    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Calendar createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Calendar updatedAt;

    public Post() {
        this.createdAt = Calendar.getInstance();
        this.updatedAt = this.createdAt;
    }

    public Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.createdAt = Calendar.getInstance();
        this.updatedAt = this.createdAt;
    }
}