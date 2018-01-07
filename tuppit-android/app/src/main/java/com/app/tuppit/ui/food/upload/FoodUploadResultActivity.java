package com.app.tuppit.ui.food.upload;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.app.tuppit.R;

/**
 * Created by david on 4/3/17.
 */

public class FoodUploadResultActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getData();
        setDataToScreen();
        setFunctionality();

    }

    /*
    *
    * Obtiene datos necesarios
    *
    * */
    private String result;

    private void getData(){

        getIntentExtras(getIntent());

    }


    private Button b_result;

    private void setDataToScreen(){

        if(result.equals("ok")) {
            setContentView(R.layout.activity_food_upload_result_ok);
            b_result = (Button) findViewById(R.id.b_result_ok);

        }else {
            setContentView(R.layout.activity_food_upload_result_error);
            b_result = (Button) findViewById(R.id.b_result_error);
        }


    }

    private void setFunctionality() {

        b_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closePopup();
            }
        });

    }

    /*
    *
    * Funcion que obtiene los extras del intent y los almacena
    *
    * */
    private boolean getIntentExtras(Intent intent){

        if (getIntent().hasExtra("result"))
            result = getIntent().getStringExtra("result");
        else
            throw new IllegalArgumentException("Activity cannot find  extras " + "result");

        return true;
    }

    @Override
    public void onBackPressed() {
        closePopup();
    }

    private void closePopup(){
        Intent data = new Intent();

        data.putExtra("result",result);
        setResult(RESULT_OK, data);

        finish();
    }
}
