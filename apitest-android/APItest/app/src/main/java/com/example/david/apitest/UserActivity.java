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
import com.example.david.apitest.model.User;
import com.example.david.apitest.utils.SimpleSHA1;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity implements View.OnClickListener{

    Button b_create, b_update, b_delete, b_login, b_get;
    EditText et_email, et_displayName, et_password, et_avatar, et_description, et_id;
    String email, displayName, password, avatar, description, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        b_create = (Button) findViewById(R.id.b_create);
        b_update = (Button) findViewById(R.id.b_update);
        b_delete = (Button) findViewById(R.id.b_delete);
        b_login = (Button) findViewById(R.id.b_login);
        b_get = (Button) findViewById(R.id.b_get);

        b_create.setOnClickListener(this);
        b_update.setOnClickListener(this);
        b_delete.setOnClickListener(this);
        b_login.setOnClickListener(this);
        b_get.setOnClickListener(this);

        et_email = (EditText) findViewById(R.id.et_email);
        et_displayName = (EditText) findViewById(R.id.et_displayName);
        et_password = (EditText) findViewById(R.id.et_password);
        et_avatar = (EditText) findViewById(R.id.et_avatar);
        et_description = (EditText) findViewById(R.id.et_description);
        et_id = (EditText) findViewById(R.id.et_id);
    }


    @Override
    public void onClick(View view) {


        getTextFields();

        if (view.equals(b_create)){
            createUser();

        }else if (view.equals(b_update)){
            updateUser();

        }else if (view.equals(b_delete)){
            deleteUser();

        }else if (view.equals(b_login)){
            loginUser();

        }else if (view.equals(b_get)){
            getUser();

        }
    }

    private void getTextFields(){
        email = et_email.getText().toString();
        displayName = et_displayName.getText().toString();
        try {
            password = SimpleSHA1.SHA1(et_password.getText().toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        avatar = et_avatar.getText().toString();
        description = et_description.getText().toString();
        id = et_id.getText().toString();
    }

    private void createUser (){
        if(!email.equals("") && !displayName.equals("") && !password.equals("") &&
                !avatar.equals("") && !description.equals("")){

            Log.i("API Call", "createUser");

            // Getting a JSON object
            ServiceAPI api = ServiceAPIHelper.getRestClient();
            Call<User> callObject = api.createUser(email, displayName, password, avatar, description);

            callObject.enqueue(new Callback<User>() {

                Intent i = new Intent(getApplicationContext(), ResultActivity.class);

                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful())
                    {
                        try {
                            User user = response.body();
                            Log.i("User Call", "" + user.data.email);

                            i.putExtra("result",user.data.id);
                            startActivity(i);


                        } catch (Exception e) {
                            Log.d("onResponse", "There is an error");
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.e("User Call", t.getLocalizedMessage());

                    i.putExtra("result",t.getMessage());
                    startActivity(i);
                }
            });

        }
    }

    private void updateUser (){
        if((!email.equals("") || !displayName.equals("") || !password.equals("") ||
                !avatar.equals("") || !description.equals("")) && !id.equals("")){

            //TODO API CALL
            Log.i("API Call", "updateUser");

            // Getting a JSON object
            ServiceAPI api = ServiceAPIHelper.getRestClient();
            Call<User> callObject = api.updateUser(id, email, displayName, password, avatar, description);
            callObject.enqueue(new Callback<User>() {

                Intent i = new Intent(getApplicationContext(), ResultActivity.class);

                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {

                        try {
                            User user = response.body();
                            Log.i("User Call", "" + user.data.email);

                            Intent i = new Intent(getApplicationContext(), ResultActivity.class);
                            i.putExtra("result",user.data.id);
                            startActivity(i);

                        } catch (Exception e) {
                            Log.d("onResponse", "There is an error");
                            e.printStackTrace();
                        }


                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.e("User Call", t.getLocalizedMessage());

                    i.putExtra("result",t.getMessage());
                    startActivity(i);
                }
            });

        }
    }

    private void deleteUser (){
        if(!id.equals("")) {

            Log.i("API Call", "deleteUser");

            // Getting a JSON object
            ServiceAPI api = ServiceAPIHelper.getRestClient();
            Call<User> callObject = api.deleteUser(id);
            callObject.enqueue(new Callback<User>() {

                Intent i = new Intent(getApplicationContext(), ResultActivity.class);

                @Override
                public void onResponse(Call<User> call, Response<User> response) {
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
                public void onFailure(Call<User> call, Throwable t) {
                    Log.e("User Call", t.getLocalizedMessage());

                    i.putExtra("result",t.getMessage());
                    startActivity(i);
                }
            });
        }
    }

    private void loginUser (){
        if(!email.equals("") && !password.equals("")){

            Log.i("User Call","loginUser");

            // Getting a JSON object
            ServiceAPI api = ServiceAPIHelper.getRestClient();
            Call<User> callObject = api.loginUser(email, password);
            callObject.enqueue(new Callback<User>() {

                Intent i = new Intent(getApplicationContext(), ResultActivity.class);

                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {

                        try {
                            User user = response.body();
                            Log.i("User Call", "" + user.data.email);

                            Intent i = new Intent(getApplicationContext(), ResultActivity.class);
                            i.putExtra("result",user.data.id);
                            startActivity(i);

                        } catch (Exception e) {
                            Log.d("onResponse", "There is an error");
                            e.printStackTrace();
                        }


                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.e("User Call", t.getLocalizedMessage());

                    i.putExtra("result",t.getMessage());
                    startActivity(i);
                }
            });

        }
    }

    private void getUser (){
        if(!id.equals("")) {

            Log.i("API Call", "getUser");

            // Getting a JSON object
            ServiceAPI api = ServiceAPIHelper.getRestClient();
            Call<User> callObject = api.getUser(id);
            callObject.enqueue(new Callback<User>() {

                Intent i = new Intent(getApplicationContext(), ResultActivity.class);

                @Override
                public void onResponse(Call<User> call, Response<User> response) {

                    if (response.isSuccessful()) {

                        try {
                            User user = response.body();
                            Log.i("User Call", "" + user.data.email);

                            i.putExtra("result",user.data.id);
                            startActivity(i);

                        } catch (Exception e) {
                            Log.d("onResponse", "There is an error");
                            e.printStackTrace();
                        }


                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.e("User Call", t.getLocalizedMessage());

                    i.putExtra("result",t.getMessage());
                    startActivity(i);
                }
            });
        }
    }
}
