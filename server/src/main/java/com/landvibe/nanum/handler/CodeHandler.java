package com.landvibe.nanum.handler;

import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;


@RestController
public class CodeHandler {
    @MessageMapping("/join")
    @SendTo("/code")
    public JoinMessage joinCode(@Payload JoinMessage joinMessage) throws Exception{

        return joinMessage;
    }

    @MessageMapping("/{fileName}")
    @SendTo("/code/{fileName}")
    public CodeMessage broadCasting(@Payload CodeMessage codeMessage, @DestinationVariable String fileName) throws Exception{
        return codeMessage;
    }
}

