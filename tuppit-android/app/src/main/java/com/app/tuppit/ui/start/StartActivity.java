package com.app.tuppit.ui.start;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.app.tuppit.ui.food.list.FoodListActivity;
import com.app.tuppit.ui.main.MainActivity;
import com.app.tuppit.ui.user.login.UserLoginActivity;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getData();
        setFunctionality();

    }

    /*
    *
    * Obtiene datos necesarios
    *
    * */
    private String user;
    private void getData(){
        //TODO comprobar si el usuario esta logeado
        SharedPreferences settings = getSharedPreferences("PREFERENCES", 0);
        user = settings.getString("user", "");

    }


    /*
    *
    * Anade funcionalidad al los elementos de la actividad
    *
    * */
    private void setFunctionality() {

        Intent intent = null;
        if(!user.equals(""))
            intent = new Intent(getApplicationContext(), FoodListActivity.class);
        else
            intent = new Intent(getApplicationContext(), UserLoginActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    }
}