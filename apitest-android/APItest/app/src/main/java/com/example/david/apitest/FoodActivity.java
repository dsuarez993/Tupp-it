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
import com.example.david.apitest.model.Food;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodActivity extends AppCompatActivity implements View.OnClickListener{

    Button b_create, b_update, b_delete, b_getById, b_getList;
    EditText et_title, et_image, et_price, et_userId, et_latitude, et_longitude, et_id;
    String title, image, userId, date, status, id;
    Float price, latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        b_create = (Button) findViewById(R.id.b_create);
        b_update = (Button) findViewById(R.id.b_update);
        b_delete = (Button) findViewById(R.id.b_delete);
        b_getById = (Button) findViewById(R.id.b_getById);
        b_getList = (Button) findViewById(R.id.b_getList);

        b_create.setOnClickListener(this);
        b_update.setOnClickListener(this);
        b_delete.setOnClickListener(this);
        b_getById.setOnClickListener(this);
        b_getList.setOnClickListener(this);

        et_title = (EditText) findViewById(R.id.et_title);
        et_image = (EditText) findViewById(R.id.et_image);
        et_price = (EditText) findViewById(R.id.et_price);
        et_userId = (EditText) findViewById(R.id.et_userId);
        et_latitude = (EditText) findViewById(R.id.et_latitude);
        et_longitude = (EditText) findViewById(R.id.et_longitude);
        et_id = (EditText) findViewById(R.id.et_id);
    }


    @Override
    public void onClick(View view) {


        getTextFields();

        if (view.equals(b_create)){
            createFood();

        }else if (view.equals(b_update)){
            updateFood();

        }else if (view.equals(b_delete)){
            deleteFood();

        }else if (view.equals(b_getById)){
            getFood();

        }else if (view.equals(b_getList)){
            getFoodList();

        }
    }

    private void getTextFields(){

        id = et_id.getText().toString();
        title =et_title.getText().toString();
        image = et_image.getText().toString();
        try{
            price = Float.parseFloat(et_price.getText().toString());
        }
        catch(Exception e){
            price =0.0F;
        }

        userId = et_userId.getText().toString();
        try{
            latitude = Float.parseFloat(et_latitude.getText().toString());
        }
        catch(Exception e){
            latitude =0.0F;
        }
        try{
            longitude = Float.parseFloat(et_longitude.getText().toString());
        }
        catch(Exception e){
            longitude =0.0F;
        }

    }

    private void createFood (){
        if(!title.equals("") && !image.equals("") && price!=0.0F &&
                !userId.equals("") && latitude!=0.0F && longitude!=0.0F){

            Log.i("API Call", "createFood");

            // Getting a JSON object
            ServiceAPI api = ServiceAPIHelper.getRestClient();
            Call<Food> callObject = api.createFood(title, image, price, latitude, longitude, userId);

            callObject.enqueue(new Callback<Food>() {

                Intent i = new Intent(getApplicationContext(), ResultActivity.class);

                @Override
                public void onResponse(Call<Food> call, Response<Food> response) {
                    if (response.isSuccessful())
                    {
                        try {
                            Food food = response.body();
                            Log.i("User Call", "" + food.data.id);

                            i.putExtra("result",food.data.id);
                            startActivity(i);


                        } catch (Exception e) {
                            Log.d("onResponse", "There is an error");
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<Food> call, Throwable t) {
                    Log.e("User Call", t.getLocalizedMessage());

                    i.putExtra("result",t.getMessage());
                    startActivity(i);
                }
            });

        }
    }

    private void updateFood (){
        if((!title.equals("") || !image.equals("") || price!=0.0F ||
                !userId.equals("") || latitude!=0.0F || longitude!=0.0F) && !id.equals("")){

            Log.i("API Call", "updateFood");

            // Getting a JSON object
            ServiceAPI api = ServiceAPIHelper.getRestClient();
            Call<Food> callObject = api.updateFood(id, title, image, price, latitude, longitude, userId);
            callObject.enqueue(new Callback<Food>() {

                Intent i = new Intent(getApplicationContext(), ResultActivity.class);

                @Override
                public void onResponse(Call<Food> call, Response<Food> response) {
                    if (response.isSuccessful()) {

                        try {
                            Food food = response.body();
                            Log.i("User Call", "" + food.data.title);

                            Intent i = new Intent(getApplicationContext(), ResultActivity.class);
                            i.putExtra("result",food.data.id);
                            startActivity(i);

                        } catch (Exception e) {
                            Log.d("onResponse", "There is an error");
                            e.printStackTrace();
                        }


                    }
                }

                @Override
                public void onFailure(Call<Food> call, Throwable t) {
                    Log.e("User Call", t.getLocalizedMessage());

                    i.putExtra("result",t.getMessage());
                    startActivity(i);
                }
            });

        }
    }

    private void deleteFood (){
        if(!id.equals("")) {

            Log.i("API Call", "deleteUser");

            // Getting a JSON object
            ServiceAPI api = ServiceAPIHelper.getRestClient();
            Call<Food> callObject = api.deleteFood(id);
            callObject.enqueue(new Callback<Food>() {

                Intent i = new Intent(getApplicationContext(), ResultActivity.class);

                @Override
                public void onResponse(Call<Food> call, Response<Food> response) {
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
                public void onFailure(Call<Food> call, Throwable t) {
                    Log.e("User Call", t.getLocalizedMessage());

                    i.putExtra("result",t.getMessage());
                    startActivity(i);
                }
            });
        }
    }


    private void getFood (){
        if(!id.equals("")) {

            Log.i("API Call", "getUser");

            // Getting a JSON object
            ServiceAPI api = ServiceAPIHelper.getRestClient();
            Call<Food> callObject = api.getFood(id);
            callObject.enqueue(new Callback<Food>() {

                Intent i = new Intent(getApplicationContext(), ResultActivity.class);

                @Override
                public void onResponse(Call<Food> call, Response<Food> response) {



                    if (response.isSuccessful()) {

                        try {
                            Food food = response.body();
                            Log.i("User Call", "" + food.data.title);


                            i.putExtra("result",food.data.title);
                            startActivity(i);

                        } catch (Exception e) {
                            Log.d("onResponse", "There is an error");
                            e.printStackTrace();
                        }


                    }
                }

                @Override
                public void onFailure(Call<Food> call, Throwable t) {
                    Log.e("User Call", t.getLocalizedMessage());

                    i.putExtra("result",t.getMessage());
                    startActivity(i);
                }
            });
        }
    }

    private void getFoodList (){

        Log.i("Food Call","getFoodList");

        // Getting a JSON object

        ServiceAPI api = ServiceAPIHelper.getRestClient();
        Call<Food.FoodList> callObject = api.getFoodList();
        callObject.enqueue(new Callback<Food.FoodList>() {

            Intent i = new Intent(getApplicationContext(), ResultActivity.class);


            @Override
            public void onResponse(Call<Food.FoodList> call, Response<Food.FoodList> response) {
                if (response.isSuccessful()) {

                    try {
                        Food.FoodList foodList = response.body();
                        Log.i("Food Call", "" + foodList.foodList.get(0).title);

                        Intent i = new Intent(getApplicationContext(), ResultActivity.class);
                        i.putExtra("result",foodList.foodList.get(0).id);
                        startActivity(i);

                    } catch (Exception e) {
                        Log.d("onResponse", "There is an error");
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onFailure(Call<Food.FoodList> call, Throwable t) {
                Log.e("Food Call", t.getLocalizedMessage());

                i.putExtra("result",t.getMessage());
                startActivity(i);
            }
        });

    }

}
