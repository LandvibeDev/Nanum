package com.landvibe.nanum.handler;

import com.landvibe.nanum.model.User;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.WebSocketSession;

import javax.servlet.http.HttpSession;
import java.util.Map;


@RestController
public class CodeHandler {
    @MessageMapping("/join")
    @SendTo("/code")
    public JoinMessage joinCode(@Payload JoinMessage joinMessage, SimpMessageHeaderAccessor headerAccessor) throws Exception{
        User user = (User)headerAccessor.getSessionAttributes().get("user");

         if(user == null){
             joinMessage.setSender("guest");
         }else{
             joinMessage.setSender(user.getUsername());
         }

        return joinMessage;
    }

    @MessageMapping("/{fileName}")
    @SendTo("/code/{fileName}")
    public CodeMessage broadCasting(@Payload CodeMessage codeMessage, @DestinationVariable String fileName) throws Exception{

        return codeMessage;
    }
}

