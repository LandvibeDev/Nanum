package com.landvibe.nanum.model.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.landvibe.nanum.model.Project;
import com.landvibe.nanum.model.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "issue_comment_id")
    private Collection<IssueComment> issueComments;

    public Issue() {
        super();
        issueComments = new ArrayList<IssueComment>();
    }

    public Issue(String title, String content, User user) {
        super(title, content, user);
    }
}
