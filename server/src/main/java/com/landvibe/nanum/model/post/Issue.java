package com.landvibe.nanum.model.post;

import com.landvibe.nanum.model.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@Table(name = "issue")
public class Issue extends Post {

    @Id
    @Column(name = "issue_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long issueId;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User creator;

    public Issue() {
        super();
    }

    public Issue(String title, String content, User user) {
        super(title, content, user);
    }
}
