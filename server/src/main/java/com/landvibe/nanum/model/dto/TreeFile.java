package com.landvibe.nanum.model.dto;

import java.util.ArrayList;
import java.util.List;

public class TreeFile {

    private String text;
    private Type type;

    public enum Type{
        DIRECTORY,FILE
    }

    public TreeFile(String text, Type type) {
        this.text = text;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
