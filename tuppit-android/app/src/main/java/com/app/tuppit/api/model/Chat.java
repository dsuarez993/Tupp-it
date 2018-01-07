package com.app.tuppit.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by david on 21/5/17.
 */

public class Chat {

    @SerializedName("chat")
    @Expose
    public ChatObject data;


    public class ChatObject {

        @SerializedName("_id")
        @Expose
        public String chatId;

        @SerializedName("foodId")
        @Expose
        public String foodId;

        @SerializedName("users")
        @Expose
        public List<ChatUserObject> users;

        @SerializedName("messages")
        @Expose
        public List<ChatMessageObject> messages;

    }

    public class ChatUserObject{

        @SerializedName("userId")
        @Expose
        public String userId;

    }

    public class ChatMessageObject{

        @SerializedName("userId")
        @Expose
        public String userId;

        @SerializedName("message")
        @Expose
        public String text;

        @SerializedName("sendedDate")
        @Expose
        public String sentDate;

    }
}
