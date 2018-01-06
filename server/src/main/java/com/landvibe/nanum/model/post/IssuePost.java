package com.landvibe.nanum.model.post;

import com.landvibe.nanum.model.User;

import javax.persistence.*;

@Entity
public class IssuePost extends Post {

    public IssuePost () {
        super();
    }

    public IssuePost (String title, String content, User user) {
        super(title, content, user);
    }

    @Override
    public String toString() {
        return "IssutPost: " + super.toString();
    }
}
