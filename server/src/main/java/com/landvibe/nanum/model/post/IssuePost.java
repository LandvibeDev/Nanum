package com.landvibe.nanum.model.post;

import com.landvibe.nanum.model.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "issue")
public class IssuePost extends Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User creator;

    public IssuePost () {
        super();
    }

    public IssuePost (String title, String content, User user) {
        super(title, content, user);
    }
}
