package com.landvibe.nanum.handler;

import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CodeHandler {
    @MessageMapping("/hello")
    @SendTo("/topic")
    public CodeMessage broadCasting(@Payload CodeMessage codeMessage) throws Exception{
        return codeMessage;
    }

}

