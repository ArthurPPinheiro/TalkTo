package com.example.talkto;

public class Message {
    private String senderName;
    private String messageText;
    private String messageTimestamp;
    private boolean sentByUser;
    private String userId;
    private String image;

    public Message(String senderName, String messageText, String messageTimestamp, boolean sentByUser, String userId, String image) {
        this.senderName = senderName;
        this.messageText = messageText;
        this.sentByUser = sentByUser;
        this.messageTimestamp = messageTimestamp;
        this.userId = userId;
        this.image = image;
    }

    public String getSenderName() { return senderName; }

    public String getMessageText() {
        return messageText;
    }

    public String getMessageTimestamp() {
        return messageTimestamp;
    }

    public String getImage() {
        return image;
    }

    public boolean isSentByUser() {
        return sentByUser;
    }
}
