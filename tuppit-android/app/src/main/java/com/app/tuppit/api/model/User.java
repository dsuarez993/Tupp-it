package com.app.tuppit.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by david on 14/5/17.
 */

public class User {

    @SerializedName("user")
    @Expose
    public UserObject data;


    public class UserObject {

        @SerializedName("email")
        @Expose
        public String email;

        @SerializedName("displayName")
        @Expose
        public String displayName;

        @SerializedName("password")
        @Expose
        public String password;

        @SerializedName("avatar")
        @Expose
        public String avatar;

        @SerializedName("description")
        @Expose
        public String description;

        @SerializedName("tokenId")
        @Expose
        public String tokenId;

        @SerializedName("_id")
        @Expose
        public String id;

        @SerializedName("foods")
        @Expose
        public List<UserFoodObject> foods;

        @SerializedName("chats")
        @Expose
        public List<UserChatObject> chats;

    }


    public class UserChatObject {

        @SerializedName("chatId")
        @Expose
        public String chatId;

        @SerializedName("userId")
        @Expose
        public String userId;

        @SerializedName("foodId")
        @Expose
        public String foodId;
    }


    public class UserFoodObject {

        @SerializedName("foodId")
        @Expose
        public String foodId;

    }
}


