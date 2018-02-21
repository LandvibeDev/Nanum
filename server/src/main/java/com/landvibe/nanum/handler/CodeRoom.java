package com.landvibe.nanum.handler;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.landvibe.nanum.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class CodeRoom {

    @JsonIgnore
    public ArrayList<User> users = new ArrayList<>();
    private String filename;
    private String path;
    private long projectId;

    public int addUser(User user){
        users.add(user);
        return users.size();
    }

    public int removeUser(User user){
        int size = users.size();
        for(int i=0;i<size;i++){
            if(users.get(i).getUsername().equals(user.getUsername())){
                users.remove(i);
                break;
            }
        }
        return users.size();
    }


}
