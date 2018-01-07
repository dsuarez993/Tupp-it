package com.app.tuppit.ui.user.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.app.tuppit.R;
import com.app.tuppit.api.ServiceAPI;
import com.app.tuppit.api.ServiceAPIHelper;
import com.app.tuppit.api.model.User;
import com.app.tuppit.utils.FormValidation;
import com.app.tuppit.ui.food.list.FoodListActivity;
import com.app.tuppit.ui.user.signup.UserSignupActivity;
import com.google.firebase.iid.FirebaseInstanceId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        getData();
        setDataToScreen();
        setFunctionality();
    }


    /*
    *
    * Obtiene datos necesarios
    *
    * */
    private void getData(){
        //TODO comprobar si el usuario esta logeado


    }


    /*
    *
    * Anade datos a los elementos de la pantalla
    *
    * */

    EditText et_email;
    EditText et_password;
    Button b_login;
    TextView tv_signup;

    private void setDataToScreen(){

        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        b_login = (Button) findViewById(R.id.b_login);
        tv_signup = (TextView) findViewById(R.id.tv_signup);

    }


    /*
    *
    * Anade funcionalidad a los elementos de la pantalla
    *
    * */
    private void setFunctionality(){

        if (b_login != null) {
            b_login.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {

                    switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_DOWN:
                            b_login.setBackgroundColor(getResources().getColor(R.color.colorSecondaryAccent));

                            break;

                        case MotionEvent.ACTION_UP:
                            b_login.setBackgroundColor(getResources().getColor(R.color.colorTertiary));

                            String e_mail = "";
                            String password = "";

                            e_mail = et_email.getText().toString();
                            password = et_password.getText().toString();

                            Log.i("e_mail",e_mail);
                            Log.i("password",password);

                            if ((!e_mail.equals("") &&
                                    !password.equals("")) &&
                                    FormValidation.isEmailValid(e_mail) &&
                                    FormValidation.isPasswordValid(password)) {

                                //login user
                                loginUser(e_mail,password);


                            } else {

                                et_email.setText("");
                                et_password.setText("");

                                //TODO mostrar mensaje de error

                            }

                            break;

                        default:
                            break;
                    }

                    return true;

                }
            });
        }

        if (tv_signup != null) {
            tv_signup.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {

                    switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_DOWN:
                            tv_signup.setTextColor(getResources().getColor(R.color.colorLightText));

                            break;

                        case MotionEvent.ACTION_UP:
                            tv_signup.setTextColor(getResources().getColor(R.color.colorMediumText));

                                Intent intent = new Intent(getApplicationContext(), UserSignupActivity.class);
                                startActivity(intent);

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
    *  API call to login user
    *
   */
    private void loginUser (String e_mail, String password){

        Log.i("User Call","loginUser");

        // Getting a JSON object
        ServiceAPI api = ServiceAPIHelper.getRestClient();
        Call<User> callObject = api.loginUser(e_mail, password);

        //TODO show loading popup

        callObject.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {

                    try {
                        User user = response.body();

                        SharedPreferences settings = getSharedPreferences("PREFERENCES", 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("user", user.data.id);

                        String tokenId = FirebaseInstanceId.getInstance().getToken();
                        editor.putString("fb_token", tokenId);

                        editor.commit();

                        com.app.tuppit.notifications.MyFirebaseTokenManager.updateTokenId(user.data.id,tokenId);

                        //TODO close loading popup


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
