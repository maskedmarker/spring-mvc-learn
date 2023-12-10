package org.example.learn.spring.web.sse.model;

import org.springframework.core.style.ToStringCreator;

import java.util.StringJoiner;

public class ClientMessage {

    private String messageId;

    private String messageType;

    private String messageContent;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ClientMessage.class.getSimpleName() + "[", "]")
                .add("messageId='" + messageId + "'")
                .add("messageType='" + messageType + "'")
                .add("messageContent='" + messageContent + "'")
                .toString();
    }
}
