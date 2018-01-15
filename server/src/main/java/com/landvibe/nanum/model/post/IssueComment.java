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
@Table(name = "issue_comment")
public class IssueComment extends Post {

    @Id
    @Column(name = "issue_comment_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User creator;

    public IssueComment() {
        super();
    }

    public IssueComment(String content) {
        super(content);
    }

}
