package com.app.tuppit.ui.food.details;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tuppit.R;
import com.app.tuppit.ui.chat.ChatActivity;

public class FoodDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        getData();
        setDataOnScreen();
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
    * Obtiene datos necesarios
    *
    * */
    private String title;
    private String price;
    private String image;
    private String foodId;
    private String userId;

    private void getData(){

        getIntentExtras(getIntent());

    }


    /*
    *
    * Anade datos a los elementos de la pantalla
    *
    * */
    private void setDataOnScreen(){

        //Actionbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set Collapsing Toolbar layout to the screen
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        // Set title of Detail page
        collapsingToolbar.setTitle(title);


        TextView tv_price = (TextView) findViewById(R.id.tv_price);
        tv_price.setText(price);

        TextView tv_description = (TextView) findViewById(R.id.tv_description);
        tv_description.setText("Texto1");


        TextView placeLocation =  (TextView) findViewById(R.id.tv_location);
        placeLocation.setText("Texto2");

        ImageView iv_food_image = (ImageView) findViewById(R.id.iv_food_image);

        byte[] imageBytes = Base64.decode(image, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        iv_food_image.setImageBitmap(decodedImage);

        //ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPageAndroid);
        //FoodImagesAdapter adapterView = new FoodImagesAdapter(this,images);
        //mViewPager.setAdapter(adapterView);

        //viewPagerBehaviour(mViewPager);

    }


    /*
    *
    * Anade funcionalidad a los elementos de la pantalla
    *
    * */
    private void setFunctionality(){


        final Button b_chat = (Button) findViewById(R.id.b_chat);

        if (b_chat != null) {
            b_chat.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {

                    switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_DOWN:
                            b_chat.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_accent_shape));

                            break;

                        case MotionEvent.ACTION_UP:
                            b_chat.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_shape));


                            Intent intent = new Intent(getApplicationContext(), ChatActivity.class);

                            intent.putExtra("foodId", foodId);
                            intent.putExtra("chatId", "");
                            intent.putExtra("userId", userId);

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
    * Anade comportamiento al slider de imagenes e introduce los puntos
    *
    * */

    /*
    private int dotsCount;
    private LinearLayout dotsLayout;
    private static TextView dotsText[];

    private void viewPagerBehaviour(ViewPager viewPager){

        //TODO cambiar los colores por los que tengamos en @colors y el tamaño por el que tengamos en @values

        dotsLayout = (LinearLayout)findViewById(R.id.dotsLayout);
        //here we count the number of images we have to know how many dots we need
        dotsCount = viewPager.getAdapter().getCount();

        //here we create the dots
        //as you can see the dots are nothing but "."  of large size
        dotsText = new TextView[dotsCount];

        //here we set the dots
        for (int i = 0; i < dotsCount; i++) {
            dotsText[i] = new TextView(this);
            dotsText[i].setText(".");
            dotsText[i].setTextSize(45);
            dotsText[i].setTypeface(null, Typeface.BOLD);
            dotsText[i].setTextColor(android.graphics.Color.GRAY);
            dotsLayout.addView(dotsText[i]);
        }

        FoodDetailsActivity.dotsText[0].setTextColor(Color.WHITE);

        //when we scroll the images we have to set the dot that corresponds to the image to White and the others
        //will be Gray
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotsCount; i++) {
                    FoodDetailsActivity.dotsText[i].setTextColor(Color.GRAY);
                }

                FoodDetailsActivity.dotsText[position].setTextColor(Color.WHITE);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });
    }
    */

    /*
    *
    * Funcion que obtiene los extras del intent y los almacena
    *
    * */
    private boolean getIntentExtras(Intent intent){

        if (getIntent().hasExtra("title"))
            title = getIntent().getStringExtra("title");
        else
            throw new IllegalArgumentException("Activity cannot find  extras " + "title");

        if (getIntent().hasExtra("price"))
            price = getIntent().getStringExtra("price");
        else
            throw new IllegalArgumentException("Activity cannot find  extras " + "price");

        if (getIntent().hasExtra("image"))
            image = getIntent().getStringExtra("image");
        else
            throw new IllegalArgumentException("Activity cannot find  extras " + "image");

        if (getIntent().hasExtra("foodId"))
            foodId = getIntent().getStringExtra("foodId");
        else
            throw new IllegalArgumentException("Activity cannot find  extras " + "foodId");

        if (getIntent().hasExtra("userId"))
            userId = getIntent().getStringExtra("userId");
        else
            throw new IllegalArgumentException("Activity cannot find  extras " + "userId");

        return true;
    }


}
