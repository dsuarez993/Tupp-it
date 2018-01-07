package com.app.tuppit.ui.food.upload;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.app.tuppit.R;
import com.app.tuppit.api.ServiceAPI;
import com.app.tuppit.api.ServiceAPIHelper;
import com.app.tuppit.api.model.Food;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by david on 4/3/17.
 */

public class FoodUploadLoadingActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_upload_loading);

        getData();
        setDataToScreen();
        setFunctionality();

    }

    /*
    *
    * Obtiene datos necesarios
    *
    * */
    private String food_name;
    private float food_price;
    private float food_latitude;
    private float food_longitude;
    private String food_description;
    private String food_image;
    private String userId;

    private void getData() {

        getIntentExtras(getIntent());

        SharedPreferences settings = getSharedPreferences("PREFERENCES", 0);
        userId = settings.getString("user", "");

        //TODO get longitude and latitude
        food_longitude = 0.0F;
        food_latitude = 0.0F;



    }

    private RelativeLayout rl_uploading_wrapper;

    private void setDataToScreen() {
        rl_uploading_wrapper = (RelativeLayout) findViewById(R.id.rl_uploading_wrapper);
    }

    private void setFunctionality() {

        new Thread(new Runnable() {
            public void run() {

                //TODO subir plato al servidor
                createFood(food_name, food_image, food_description, food_price, food_latitude, food_longitude, userId);

                final String result = "ok";

                runOnUiThread(new Runnable() {
                    public void run() {

                        rl_uploading_wrapper.setVisibility(View.INVISIBLE);

                        Intent i = new Intent(getApplicationContext(), FoodUploadResultActivity.class);
                        i.putExtra("result", result);

                        //TODO error si falla la subida
                        startActivityForResult(i, 3);
                    }
                });
            }
        }).start();
    }

    /*
    *
    * Funcion que obtiene los extras del intent y los almacena
    *
    * */
    private boolean getIntentExtras(Intent intent) {

        if (getIntent().hasExtra("food_name"))
            food_name = getIntent().getStringExtra("food_name");
        else
            throw new IllegalArgumentException("Activity cannot find  extras " + "food_name");

        if (getIntent().hasExtra("food_price"))
            food_price = getIntent().getFloatExtra("food_price",0);
        else
            throw new IllegalArgumentException("Activity cannot find  extras " + "food_price");

        if (getIntent().hasExtra("food_description"))
            food_description = getIntent().getStringExtra("food_description");
        else
            throw new IllegalArgumentException("Activity cannot find  extras " + "food_description");
        if (getIntent().hasExtra("food_image"))
            food_image = getIntent().getStringExtra("food_image");
        else
            throw new IllegalArgumentException("Activity cannot find  extras " + "food_image");

        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 3 && resultCode == RESULT_OK) {

            String result = data.getStringExtra("result");

            Intent data2 = new Intent();
            data2.putExtra("result", result);
            setResult(RESULT_OK, data2);

            finish();

        }
    }

    @Override
    public void onBackPressed() {
    }


    /*
    *
    * API call create food
    *
     */
    private void createFood (String title, String image, String description, Float price, Float latitude, Float longitude, String userId){


        Log.i("API Call", "createFood");

        // Getting a JSON object
        ServiceAPI api = ServiceAPIHelper.getRestClient();
        Call<Food> callObject = api.createFood(title, image, description, price, latitude, longitude, userId);

        callObject.enqueue(new Callback<Food>() {

            @Override
            public void onResponse(Call<Food> call, Response<Food> response) {
                if (response.isSuccessful())
                {
                    try {
                        Food food = response.body();
                        Log.i("User Call", "" + food.data.id);


                    } catch (Exception e) {
                        Log.d("onResponse", "There is an error");
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<Food> call, Throwable t) {
                Log.e("User Call", t.getLocalizedMessage());


            }
        });


    }
}
