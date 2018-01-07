package com.app.tuppit.model;

import java.util.Random;

/**
 * Created by David on 3/9/16.
 */
public class ChatMessage {

    public String userId;
    public String text;
    public String sentDate;

    public ChatMessage(String userId, String text, String sendedDate) {
        this.userId = userId;
        this.text = text;
        this.sentDate = sendedDate;
    }

    /*public String body, sender, receiver, senderName;
    public String date, time;
    public String msgid;
    public boolean isMine;// Did I send the message.

    public ChatMessage(String body, String sender, String receiver, String senderName, String date, String time, String msgid, boolean isMine) {
        this.body = body;
        this.sender = sender;
        this.receiver = receiver;
        this.senderName = senderName;
        this.date = date;
        this.time = time;
        this.msgid = msgid;
        this.isMine = isMine;
    }

    public static String setMessageID() {

        return "-" + String.format("%02d", new Random().nextInt(100));

    }*/


}
