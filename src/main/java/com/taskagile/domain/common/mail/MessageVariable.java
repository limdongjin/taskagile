package com.taskagile.domain.common.mail;

public class MessageVariable {
    private String key;
    private Object value;

    public MessageVariable(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public static MessageVariable from(String key, Object value){
        return new MessageVariable(key, value);
    }
}
