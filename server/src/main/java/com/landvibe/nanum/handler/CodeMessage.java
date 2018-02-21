package com.landvibe.nanum.handler;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CodeMessage {
    private MessageType type;
    private String content;
    private Delta delta;
    private String sender;
    private String source;

    public enum MessageType {
        JOIN,
        LEAVE
    }


}