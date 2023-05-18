package com.example.chatapp.models;


public class ChatMessage {
    public String senderId, receiverId, message, dateTime, orderId;

    public ChatMessage(String senderId, String receiverId, String message, String dateTime, String orderId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.dateTime = dateTime;
        this.orderId = orderId;
    }

    public ChatMessage() {
    }
}
