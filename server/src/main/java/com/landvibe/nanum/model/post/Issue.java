package com.landvibe.nanum.model.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.landvibe.nanum.model.Project;
import com.landvibe.nanum.model.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "issue")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Issue extends Post {

    @Id
    @Column(name = "issue_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User creator;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    public Issue() {
        super();
    }

    public Issue(String title, String content, User user) {
        super(title, content, user);
    }
}
