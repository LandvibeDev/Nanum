package com.landvibe.nanum.handler;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class JoinMessage {

    private String filename;
    private String path;
    private String sender;
    private String content;
    private String source;
    private boolean sync;
    private String type;

}
