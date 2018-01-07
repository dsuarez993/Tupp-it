package com.app.tuppit.ui.food.upload;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.app.tuppit.R;
import com.app.tuppit.utils.MyImageEncoder;

public class FoodUploadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_upload);

        setDataToScreen();
        setFunctionality();

    }

    /*
    *
    * Anade datos a los elementos de la pantalla
    *
    * */
    private ImageButton ib_close;
    private Button b_upload;
    private EditText et_food_name;
    private EditText et_food_price;
    private EditText et_food_description;
    private ImageView iv_food_image;
    private RelativeLayout rl_food_image_wrapper;

    private void setDataToScreen(){

        ib_close = (ImageButton) findViewById(R.id.ib_close);
        b_upload = (Button) findViewById(R.id.b_upload);
        et_food_name = (EditText) findViewById(R.id.et_displayname);
        et_food_price = (EditText) findViewById(R.id.et_food_price);
        et_food_description = (EditText) findViewById(R.id.et_food_description);
        iv_food_image = (ImageView) findViewById(R.id.iv_food_image);
        rl_food_image_wrapper = (RelativeLayout) findViewById(R.id.rl_food_image_wrapper);

    }

    /*
    *
    * Anade funcionalidad a los elementos de la pantalla
    *
    * */

    private String food_name;
    private float food_price;
    private String food_description;
    private String food_image;

    private void setFunctionality(){

        //funcionalidad del close button
        ib_close.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_DOWN:
                        ib_close.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.close_accent));

                        break;

                    case MotionEvent.ACTION_UP:
                        ib_close.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.close));
                        finish();
                        break;

                    default:
                        break;
                }
                return true;
            }

        });

        //funcionalidad del boton de subir plato
        b_upload.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_DOWN:
                        b_upload.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_accent_shape));

                        break;

                    case MotionEvent.ACTION_UP:
                        b_upload.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_shape));

                        food_name = et_food_name.getText().toString();
                        food_price = Float.parseFloat(et_food_price.getText().toString());
                        food_description = et_food_description.getText().toString();
                        food_image="";

                        if(!food_name.equals("")
                                && food_price>0F
                                && !food_description.equals("")
                                && !food_description.equals("")
                                && photo_bitmap!=null){

                            food_image = MyImageEncoder.encodeTobase64(photo_bitmap);
                            Intent i = new Intent(getApplicationContext(),FoodUploadLoadingActivity.class);
                            i.putExtra("food_name",food_name);
                            i.putExtra("food_price",food_price);
                            i.putExtra("food_description",food_description);
                            i.putExtra("food_image",food_image);

                            startActivityForResult(i, 2);
                        }

                        break;

                    default:
                        break;
                }

                return true;

            }
        });

        //funcionalidad del boton de añadir foto
        iv_food_image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_DOWN:
                        rl_food_image_wrapper.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.image_shape_accent));

                        if(photo_bitmap==null){
                            iv_food_image.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.add_photo_accent));
                        }

                        break;

                    case MotionEvent.ACTION_UP:
                        rl_food_image_wrapper.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.image_shape));

                        if(photo_bitmap==null){
                            iv_food_image.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.add_photo));
                        }

                        dispatchTakePictureIntent();

                        break;

                    default:
                        break;
                }

                return true;

            }
        });

    }


    /*
    *
    * Función del dispath de la camara
    *
    * */
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    /*
    *
    * Función Activity Result que se ejecuta tras realizar la foto
    *
    * */

    private Bitmap photo_bitmap = null;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            photo_bitmap = (Bitmap) extras.get("data");
            iv_food_image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv_food_image.setImageBitmap(photo_bitmap);
            if(et_food_name.getText().toString().equals("")){
                et_food_name.requestFocus();
            }
        }else if (requestCode == 2 && resultCode == RESULT_OK) {
            Log.i("Results","UploadResult");
            String result= data.getStringExtra("result");
            Log.i("Results","result::"+result+"::");

            if(result.equals("ok")){
                Intent data2 = new Intent();
                data2.putExtra("food_name", food_name);
                data2.putExtra("food_price", food_price);
                data2.putExtra("food_description", food_description);
                data2.putExtra("food_image", food_image);

                setResult(RESULT_OK, data2);

                finish();
            }


        }
    }

}
