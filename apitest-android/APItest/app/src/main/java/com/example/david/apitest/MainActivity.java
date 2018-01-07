package com.example.david.apitest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button b_user, b_food, b_chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b_user = (Button) findViewById(R.id.b_user);
        b_food = (Button) findViewById(R.id.b_food);
        b_chat = (Button) findViewById(R.id.b_chat);

        b_user.setOnClickListener(this);
        b_food.setOnClickListener(this);
        b_chat.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        Intent intent = null;
        Log.i("MainActivity", ""+view);

        if (view.equals(b_user)){
            intent = new Intent(this, UserActivity.class);

        }else if (view.equals(b_food)){
            intent = new Intent(this, FoodActivity.class);

        }else if (view.equals(b_chat)){
            intent = new Intent(this, ChatActivity.class);

        }

        if(intent!=null)
            startActivity(intent);
    }

}
