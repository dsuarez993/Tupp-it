package com.example.david.apitest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.david.apitest.api.ServiceAPI;
import com.example.david.apitest.api.ServiceAPIHelper;
import com.example.david.apitest.model.Chat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener{

    Button b_create, b_delete, b_getChat, b_sendMessage;
    EditText et_chatId, et_foodId, et_user1Id, et_user2Id, et_message;
    String chatId, foodId, user1Id, user2Id, message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        b_create = (Button) findViewById(R.id.b_create);
        b_delete = (Button) findViewById(R.id.b_delete);
        b_getChat = (Button) findViewById(R.id.b_getChat);
        b_sendMessage = (Button) findViewById(R.id.b_sendMessage);

        b_create.setOnClickListener(this);
        b_delete.setOnClickListener(this);
        b_getChat.setOnClickListener(this);
        b_sendMessage.setOnClickListener(this);

        et_chatId = (EditText) findViewById(R.id.et_chatId);
        et_foodId = (EditText) findViewById(R.id.et_foodId);
        et_user1Id = (EditText) findViewById(R.id.et_user1Id);
        et_user2Id = (EditText) findViewById(R.id.et_user2Id);
        et_message = (EditText) findViewById(R.id.et_message);
    }


    @Override
    public void onClick(View view) {


        getTextFields();

        if (view.equals(b_create)){
            createChat();

        }else if (view.equals(b_delete)){
            deleteChat();

        }else if (view.equals(b_getChat)){
            getChat();

        }else if (view.equals(b_sendMessage)){
            sendMessage();

        }
    }

    private void getTextFields(){

        chatId = et_chatId.getText().toString();
        foodId =et_foodId.getText().toString();
        user1Id = et_user1Id.getText().toString();
        user2Id = et_user2Id.getText().toString();
        message = et_message.getText().toString();

    }

    private void createChat (){
        if(!foodId.equals("") && !user1Id.equals("") && !user2Id.equals("")){

            Log.i("API Call", "createChat");

            Log.i("CreateChat", "user1Id::"+user1Id);
            Log.i("CreateChat", "user2Id::"+user2Id);
            Log.i("CreateChat", "foodId::"+foodId);
            Log.i("CreateChat","ChatId generado::"+chatId);

            // Getting a JSON object
            ServiceAPI api = ServiceAPIHelper.getRestClient();
            Call<Chat> callObject = api.createChat(foodId,user1Id,"a",user2Id,"b");

            callObject.enqueue(new Callback<Chat>() {

                Intent i = new Intent(getApplicationContext(), ResultActivity.class);

                @Override
                public void onResponse(Call<Chat> call, Response<Chat> response) {
                    if (response.isSuccessful())
                    {
                        try {
                            Chat chat = response.body();
                            Log.i("User Call", "" + chat.data.chatId);

                            i.putExtra("result",chat.data.chatId);
                            startActivity(i);


                        } catch (Exception e) {
                            Log.d("onResponse", "There is an error");
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<Chat> call, Throwable t) {
                    Log.e("User Call", t.getLocalizedMessage());

                    i.putExtra("result",t.getMessage());
                    startActivity(i);
                }
            });

        }
    }

    private void deleteChat(){
        if(!chatId.equals("")) {

            Log.i("API Call", "deleteUser");

            // Getting a JSON object
            ServiceAPI api = ServiceAPIHelper.getRestClient();
            Call<Chat> callObject = api.deleteChat(chatId);
            callObject.enqueue(new Callback<Chat>() {

                Intent i = new Intent(getApplicationContext(), ResultActivity.class);

                @Override
                public void onResponse(Call<Chat> call, Response<Chat> response) {
                    if (response.isSuccessful()) {

                        try {
                            response.body();
                            Intent i = new Intent(getApplicationContext(), ResultActivity.class);
                            i.putExtra("result",response.message());
                            startActivity(i);

                        } catch (Exception e) {
                            Log.d("onResponse", "There is an error");
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<Chat> call, Throwable t) {
                    Log.e("Chat Call", t.getLocalizedMessage());

                    i.putExtra("result",t.getMessage());
                    startActivity(i);
                }
            });
        }
    }

    private void getChat(){
        if(!chatId.equals("")) {
            Log.i("Chat Call", "getChat");

            // Getting a JSON object

            ServiceAPI api = ServiceAPIHelper.getRestClient();
            Call<Chat> callObject = api.getChat(chatId);
            callObject.enqueue(new Callback<Chat>() {

                Intent i = new Intent(getApplicationContext(), ResultActivity.class);


                @Override
                public void onResponse(Call<Chat> call, Response<Chat> response) {
                    if (response.isSuccessful()) {

                        try {
                            Chat chat = response.body();
                            Log.i("Food Call", "" + chat.data.chatId);

                            Intent i = new Intent(getApplicationContext(), ResultActivity.class);
                            i.putExtra("result", chat.data.chatId);
                            startActivity(i);

                        } catch (Exception e) {
                            Log.d("onResponse", "There is an error");
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<Chat> call, Throwable t) {
                    Log.e("Food Call", t.getLocalizedMessage());

                    i.putExtra("result", t.getMessage());
                    startActivity(i);
                }
            });
        }
    }

    private void sendMessage () {

        if (!chatId.equals("") && !user1Id.equals("") && !message.equals("")) {
            Log.i("Chat Call", "sendMessage");

            // Getting a JSON object

            ServiceAPI api = ServiceAPIHelper.getRestClient();
            Call<Chat> callObject = api.sendChatMessage(chatId, user1Id, message);
            callObject.enqueue(new Callback<Chat>() {

                Intent i = new Intent(getApplicationContext(), ResultActivity.class);

                @Override
                public void onResponse(Call<Chat> call, Response<Chat> response) {
                    if (response.isSuccessful()) {

                        try {
                            response.body();

                            i.putExtra("result",response.message());
                            startActivity(i);


                        } catch (Exception e) {
                            Log.d("onResponse", "There is an error");
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<Chat> call, Throwable t) {
                    Log.e("Chat Call", t.getLocalizedMessage());

                    i.putExtra("result",t.getMessage());
                    startActivity(i);
                }
            });
        }
    }

}
