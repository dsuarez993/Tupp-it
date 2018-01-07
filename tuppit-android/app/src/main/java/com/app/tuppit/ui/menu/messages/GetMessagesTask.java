package com.app.tuppit.ui.menu.messages;

import android.os.AsyncTask;


import com.app.tuppit.api.ServiceAPI;
import com.app.tuppit.api.ServiceAPIHelper;
import com.app.tuppit.api.model.User;
import com.app.tuppit.model.MessageListItem;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;

/**
 * Created by david on 18/10/17.
 */

public class GetMessagesTask extends AsyncTask<String, Integer, ArrayList<MessageListItem>> {

    @Override
    protected ArrayList<MessageListItem> doInBackground(String... params) {

        ArrayList<MessageListItem> messageListAux = new ArrayList<>();

        String myUserId = params[0];//TODO userId

        String chatId, foodId, userId, userName, userAvatar="";
        // Getting a JSON object
        ServiceAPI api = ServiceAPIHelper.getRestClient();
        Call<User> callMyUser = api.getUser(myUserId);
        try {
            User myUser= callMyUser.execute().body();

            for(User.UserChatObject c : myUser.data.chats){
                chatId=c.chatId;
                foodId=c.foodId;
                userId=c.userId;
                Call<User> callOtherUser = api.getUser(userId);
                User otherUser = callOtherUser.execute().body();
                userName = otherUser.data.displayName;
                userAvatar = otherUser.data.avatar;

                messageListAux.add(new MessageListItem(chatId,userId,foodId,userName,userAvatar,"",""));
            }

        } catch (IOException e) {
            e.printStackTrace();

        }

        return messageListAux;

    }
}
