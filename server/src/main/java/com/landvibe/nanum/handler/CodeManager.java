package com.landvibe.nanum.handler;

import com.landvibe.nanum.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CodeManager {

    @Autowired
    private SimpMessagingTemplate template;

    private static HashMap<String,CodeRoom> roomList = new HashMap<>();
    private static int userNumber = 1;

    public void handleJoinMessage(JoinMessage joinMessage, SimpMessageHeaderAccessor headerAccessor){
        Map<String,Object> sessionAttributes = headerAccessor.getSessionAttributes();
        User user = (User) sessionAttributes.get("user");
        String filename = joinMessage.getFilename();

        if("JOIN".equals(joinMessage.getType())){
            if (user == null) {
                user = new User();
                user.setUsername("guest" + userNumber);
                sessionAttributes.put("user",user);
                joinMessage.setSender(user.getUsername());
                userNumber++;
            } else {
                joinMessage.setSender(user.getUsername());
            }
            joinMessage.setSync(addUser(joinMessage.getFilename(),joinMessage.getPath(),user));
            sessionAttributes.put("file",roomList.get(filename));
        }else if("LEAVE".equals(joinMessage.getType())){
            joinMessage.setSync(removeUser(joinMessage.getFilename(),user));
        }

    }


    public void handleDisconnection(SimpMessageHeaderAccessor headerAccessor){
        User user = (User) headerAccessor.getSessionAttributes().get("user");
        CodeRoom file = (CodeRoom)headerAccessor.getSessionAttributes().get("file");

        if(file != null){
            JoinMessage joinMessage = new JoinMessage();
            joinMessage.setSender(user.getUsername());
            joinMessage.setFilename(file.getFilename());
            joinMessage.setPath(file.getPath());
            joinMessage.setType("LEAVE");
            System.out.println(file.getFilename());
            joinMessage.setSync(removeUser(file.getFilename(),user));
            sendLeaveMessage(joinMessage);
        }
    }

    private void sendLeaveMessage(JoinMessage joinMessage){
        this.template.convertAndSend("/code", joinMessage);
    }

    public boolean addUser(String filename,String path,User user){
        CodeRoom codeRoom = roomList.get(filename);
        if(codeRoom == null){
            codeRoom = new CodeRoom();
            codeRoom.addUser(user);
            codeRoom.setFilename(filename);
            codeRoom.setPath(path);
            roomList.put(filename,codeRoom);
            return true;
        }
        codeRoom.addUser(user);
        return false;
    }

    public boolean removeUser(String filename,User user){
        CodeRoom codeRoom = roomList.get(filename);
        if(codeRoom == null){
            return false;
        }
        if(codeRoom.removeUser(user) == 0){
            roomList.remove(filename);
            return true;
        }
        return false;

    }

    public List<CodeRoom> getRooms(){
        return new ArrayList<>(roomList.values());
    }



}
