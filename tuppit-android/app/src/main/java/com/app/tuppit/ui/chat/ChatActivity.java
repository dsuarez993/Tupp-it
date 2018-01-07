package com.app.tuppit.ui.chat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.app.tuppit.R;
import com.app.tuppit.api.ServiceAPI;
import com.app.tuppit.api.ServiceAPIHelper;
import com.app.tuppit.api.model.Chat;
import com.app.tuppit.api.model.User;
import com.app.tuppit.model.ChatMessage;
import com.app.tuppit.ui.user.login.UserLoginActivity;
import com.app.tuppit.utils.MyDate;
import com.app.tuppit.utils.MyTime;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    //TODO Modificar esta actividad y su layout


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        getData();
        setDataOnScreen();
        setFunctionality();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);

    }

    /*
    *
    * Funcionalidad de los botones del actionbar
    *
    * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //al pulsar el boton back superior
                onBackPressed();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
    *
    * Obtiene datos necesarios
    *
    * */
    private String myUserId;
    private String userId;
    private String foodId;
    private String user1;
    private String user2;
    private String chatId;

    public static ArrayList<ChatMessage> chatList;

    private void getData(){

        getIntentExtras(getIntent());
        SharedPreferences settings = getSharedPreferences("PREFERENCES", 0);
        myUserId = settings.getString("user", "");

        if(chatId.equals(""))
            getChatId();

    }

    /*
    *
    * Anade datos a los elementos de la pantalla
    *
    * */
    private ListView msgListView;
    private EditText et_msg;

    private void setDataOnScreen(){

        //Actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_msg = (EditText) findViewById(R.id.messageEditText);
        msgListView = (ListView) findViewById(R.id.msgListView);

        chatList = new ArrayList <ChatMessage>();
    }


    /*
    *
    * Anade funcionalidad a los elementos de la pantalla
    *
    * */
    public static ChatAdapter chatAdapter;

    private void setFunctionality(){
        ImageButton sendButton = (ImageButton) findViewById(R.id.sendMessageButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                sendTextMessage(v);
            }
        });

        // ----Set autoscroll of listview when a new message arrives----//
        msgListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        msgListView.setStackFromBottom(true);

        chatAdapter = new ChatAdapter(this, chatList, myUserId);
        msgListView.setAdapter(chatAdapter);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("messageReceived"));

        getChatMessages(chatId);
    }



    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String mChatId = intent.getStringExtra("chatId");
            String message = intent.getStringExtra("message");
            String fromUserId = intent.getStringExtra("fromUserId");
            String sentDate = intent.getStringExtra("sentDate");
            // do something here.

            if(mChatId.equals(chatId)){
                final ChatMessage chatMessage = new ChatMessage(fromUserId, message,sentDate);

                chatAdapter.add(chatMessage);
                chatAdapter.notifyDataSetChanged();

            }

        }
    };


    @Override
    public void onSaveInstanceState(Bundle outState) {
    }

    public void sendTextMessage(View v) {
        String message = et_msg.getEditableText().toString();

        if (!message.equalsIgnoreCase("")) {

            final ChatMessage chatMessage = new ChatMessage(myUserId, message,"");

            et_msg.setText("");
            chatAdapter.add(chatMessage);
            chatAdapter.notifyDataSetChanged();

            ServiceAPI api = ServiceAPIHelper.getRestClient();
            Call<Chat> callObject = api.sendChatMessage(chatId, myUserId, userId, message);
            callObject.enqueue(new Callback<Chat>() {

                @Override
                public void onResponse(Call<Chat> call, Response<Chat> response) {
                    if (response.isSuccessful()) {

                        try {
                            response.body();

                        } catch (Exception e) {
                            Log.d("onResponse", "There is an error");
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<Chat> call, Throwable t) {
                    Log.e("Chat Call", t.getLocalizedMessage());

                }
            });
        }
    }


    /*
    *
    * Funcion que obtiene los extras del intent y los almacena
    *
    * */
    private boolean getIntentExtras(Intent intent){

        if (getIntent().hasExtra("foodId"))
            foodId = getIntent().getStringExtra("foodId");
        else
            throw new IllegalArgumentException("Activity cannot find  extras " + "foodId");

        if (getIntent().hasExtra("chatId"))
            chatId = getIntent().getStringExtra("chatId");
        else
            throw new IllegalArgumentException("Activity cannot find  extras " + "foodId");

        if (getIntent().hasExtra("userId"))
            userId = getIntent().getStringExtra("userId");
        else
            throw new IllegalArgumentException("Activity cannot find  extras " + "userId");

        return true;
    }

    /*
    *
    * Funcion que obtiene los extras del intent y los almacena
    *
    * */
    private void getChatInfo(){



    }

    private void getChatId (){

        Log.i("API Call", "getUser");

        // Getting a JSON object
        ServiceAPI api = ServiceAPIHelper.getRestClient();
        Call<User> callObject = api.getUser(myUserId);
        callObject.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.isSuccessful()) {

                    try {
                        User user = response.body();
                        Log.i("User Call", "" + user.data.email);
                        //getChatMessages();
                        //chatId ="";
                        chatId = findChatId(userId,user);

                        if(chatId.equals("")){
                            createChat(foodId,myUserId,userId);
                        }else{
                            getChatMessages(chatId);
                        }

                    } catch (Exception e) {
                        Log.d("onResponse", "There is an error");
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("User Call", t.getLocalizedMessage());


            }
        });

    }

    private String findChatId(String userId, User userObject){

        String chatId="";
        for (User.UserChatObject uc: userObject.data.chats) {
            if(uc.userId.equals(userId) && uc.foodId.equals(foodId)){
                chatId=uc.chatId;
            }
        }

        return chatId;

    }

    private void getChatMessages(String chatId){

        this.chatList.clear();

        // Getting a JSON object
        ServiceAPI api = ServiceAPIHelper.getRestClient();
        Call<Chat> callObject = api.getChat(chatId);
        callObject.enqueue(new Callback<Chat>() {

            @Override
            public void onResponse(Call<Chat> call, Response<Chat> response) {

                if (response.isSuccessful()) {

                    try {
                        Chat chat = response.body();
                        Log.i("Chat Call", "" + chat.data);

                        for(Chat.ChatMessageObject m : chat.data.messages){

                            chatAdapter.add(new ChatMessage(m.userId, m.text, m.sentDate));

                        }

                        chatAdapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        Log.d("onResponse", "There is an error");
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onFailure(Call<Chat> call, Throwable t) {
                Log.e("Chat Call", t.getLocalizedMessage());


            }
        });

    }

    private void createChat(String foodId, String myUserId,String userId){
        // Getting a JSON object
        ServiceAPI api = ServiceAPIHelper.getRestClient();
        Call<Chat> callObject = api.createChat(foodId, myUserId, userId);
        callObject.enqueue(new Callback<Chat>() {

            @Override
            public void onResponse(Call<Chat> call, Response<Chat> response) {

                if (response.isSuccessful()) {

                    try {
                        Chat chat = response.body();
                        Log.i("Chat Call", "" + chat.data);

                        chatId=chat.data.chatId;

                    } catch (Exception e) {
                        Log.d("onResponse", "There is an error");
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onFailure(Call<Chat> call, Throwable t) {
                Log.e("Chat Call", t.getLocalizedMessage());


            }
        });
    }


}
