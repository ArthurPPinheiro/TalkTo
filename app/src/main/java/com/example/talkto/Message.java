package com.example.talkto;

public class Message {
    private String senderName;
    private String messageText;
    private String messageTimestamp;
    private boolean sentByUser;

    public Message(String senderName, String messageText, String messageTimestamp, boolean sentByUser) {
        this.senderName = senderName;
        this.messageText = messageText;
        this.sentByUser = sentByUser;
        this.messageTimestamp = messageTimestamp;
    }

    public String getSenderName() { return senderName; }

    public String getMessageText() {
        return messageText;
    }

    public String getMessageTimestamp() {
        return messageTimestamp;
    }

    public boolean isSentByUser() {
        return sentByUser;
    }
}
