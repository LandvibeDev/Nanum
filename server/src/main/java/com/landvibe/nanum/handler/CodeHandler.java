package com.landvibe.nanum.handler;

import com.landvibe.nanum.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CodeHandler {

    private CodeManager codeManager;

    @Autowired
    public CodeHandler(CodeManager codeManager){
        this.codeManager = codeManager;
    }

    @MessageMapping("/join")
    @SendTo("/code")
    public JoinMessage joinCode(@Payload JoinMessage joinMessage, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        codeManager.handleJoinMessage(joinMessage,headerAccessor);
        return joinMessage;
    }

    @MessageMapping("/{fileName}")
    @SendTo("/code/{fileName}")
    public CodeMessage broadCasting(@Payload CodeMessage codeMessage, @DestinationVariable String fileName) throws Exception {
        return codeMessage;
    }

}

