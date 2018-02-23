package com.landvibe.nanum.handler;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Delta {
    String action;
    List<String> lines;
    Position start;
    Position end;
}
