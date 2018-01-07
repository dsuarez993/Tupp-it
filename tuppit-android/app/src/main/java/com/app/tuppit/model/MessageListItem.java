package com.app.tuppit.model;

/**
 * Created by David on 4/9/16.
 */
public class MessageListItem {

    private String chatId;
    private String userId;
    private String foodId;
    private String userName;
    private String userAvatar;
    private String sentDate;
    private String lastMessage;

    public MessageListItem(String chatId, String userId, String foodId, String userName, String userAvatar, String sentDate, String lastMessage) {
        this.chatId = chatId;
        this.userId = userId;
        this.foodId = foodId;
        this.userName = userName;
        this.userAvatar = userAvatar;
        this.sentDate = sentDate;//950050590
        this.lastMessage = lastMessage;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getSentDate() {
        return sentDate;
    }

    public void setSentDate(String sentDate) {
        this.sentDate = sentDate;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}