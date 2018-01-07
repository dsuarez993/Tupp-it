package com.app.tuppit.ui.user.signup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.app.tuppit.R;
import com.app.tuppit.api.ServiceAPI;
import com.app.tuppit.api.ServiceAPIHelper;
import com.app.tuppit.api.model.User;
import com.app.tuppit.ui.food.list.FoodListActivity;
import com.app.tuppit.utils.FormValidation;
import com.google.firebase.iid.FirebaseInstanceId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserSignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);

        setDataToScreen();
        setFunctionality();

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
    * Anade datos a los elementos de la pantalla
    *
    * */

    EditText et_displayname;
    EditText et_email;
    EditText et_password;
    EditText et_repassword;
    Button b_signup;
    TextView tv_login;

    private void setDataToScreen(){

        //Actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_displayname = (EditText) findViewById(R.id.et_displayname);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        et_repassword = (EditText) findViewById(R.id.et_repassword);
        b_signup = (Button) findViewById(R.id.b_signup);
        tv_login = (TextView) findViewById(R.id.tv_login);

    }


    /*
    *
    * Anade funcionalidad a los elementos de la pantalla
    *
    * */
    private void setFunctionality(){

        if (b_signup != null) {
            b_signup.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {

                    switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_DOWN:
                            b_signup.setBackgroundColor(getResources().getColor(R.color.colorSecondaryAccent));

                            break;

                        case MotionEvent.ACTION_UP:
                            b_signup.setBackgroundColor(getResources().getColor(R.color.colorTertiary));

                            String displayname = "";
                            String e_mail = "";
                            String password = "";
                            String repassword = "";

                            displayname = et_displayname.getText().toString();
                            e_mail = et_email.getText().toString();
                            password = et_password.getText().toString();
                            repassword = et_password.getText().toString();

                            Log.i("name",displayname);
                            Log.i("e_mail",e_mail);
                            Log.i("password",password);
                            Log.i("repassword",repassword);

                            if (!displayname.equals("") &&
                                    !e_mail.equals("") &&
                                    !password.equals("") &&
                                    !repassword.equals("") &&
                                    FormValidation.isNameValid(displayname) &&
                                    FormValidation.isEmailValid(e_mail) &&
                                    FormValidation.isPasswordValid(password) &&
                                    FormValidation.arePasswordsEquals(password,repassword)) {

                                createUser(displayname,e_mail,password);
                                //TODO login on server


                            } else {

                                et_displayname.setText("");
                                et_email.setText("");
                                et_password.setText("");
                                et_repassword.setText("");

                                //TODO show error popup

                            }

                            break;

                        default:
                            break;
                    }

                    return true;

                }
            });
        }

        if (tv_login != null) {
            tv_login.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {

                    switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_DOWN:
                            tv_login.setTextColor(getResources().getColor(R.color.colorLightText));

                            break;

                        case MotionEvent.ACTION_UP:
                            tv_login.setTextColor(getResources().getColor(R.color.colorMediumText));
                            onBackPressed();

                            break;

                        default:
                            break;
                    }

                    return true;

                }
            });
        }

    }

    /*
    *
    *  API call to create user
    *
     */
    private String tokenId;

    private void createUser (String displayName, String e_mail, String password){


        Log.i("API Call", "createUser");

        tokenId = FirebaseInstanceId.getInstance().getToken();

        // Getting a JSON object
        ServiceAPI api = ServiceAPIHelper.getRestClient();
        Call<User> callObject = api.createUser(e_mail, displayName, password, "", "", tokenId);

        //TODO show loading popup

        callObject.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful())
                {
                    try {
                        User user = response.body();
                        Log.i("User Call", "" + user.data.email);

                        SharedPreferences settings = getSharedPreferences("PREFERENCES", 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("user", user.data.id);
                        editor.putString("tokenId", tokenId);
                        editor.commit();

                        com.app.tuppit.notifications.MyFirebaseTokenManager.updateTokenId(user.data.id,tokenId);

                        //TODO close loading popup

                        Intent intent = new Intent(getApplicationContext(), FoodListActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();

                    } catch (Exception e) {
                        Log.d("onResponse", "There is an error");
                        e.printStackTrace();

                        //TODO show error popup
                    }

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("User Call", t.getLocalizedMessage());

                //TODO show error popup
            }
        });


    }


}
