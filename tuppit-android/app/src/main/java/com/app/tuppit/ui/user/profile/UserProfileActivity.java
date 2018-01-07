package com.app.tuppit.ui.user.profile;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.tuppit.R;

public class UserProfileActivity extends AppCompatActivity {

    private FragmentTabHost mTabHost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        getData();
        setDataToScreen();
        setFunctionality();

    }


    /*
    *
    * Obtiene datos necesarios
    *
    * */

    String userName;
    String chefScore;
    String customerScore;
    Drawable avatarImage;
    //Bundles que con info que se pasa por parametro a las tabs
    Bundle bundleUser;
    Bundle bundleChef;
    Bundle bundleCustomer;
    Bundle bundleRank;

    void getData(){
        //TODO obtener datos del intent
        //TODO obtener info del usuario mediante query a DB


        //datos de prueba
        userName = "David Suarez";
        chefScore = "8/10";
        customerScore = "9/10";
        avatarImage = ContextCompat.getDrawable(this.getApplicationContext(),R.drawable.avatar);

        String description = "Me encanta la cocina, he realizado varios cursos en Madrid. Soy un gran fan de la cocina de Arguiñano. Me encanta cocinar asados y estofados.";
        String [] preferences = {"Tradicional","Asiatico", "Italiano"};
        String prueba = "Texto de prueba";

        bundleUser = new Bundle();
        bundleUser.putString("description",description);
        bundleUser.putStringArray("preferences",preferences);

        bundleChef = new Bundle();
        bundleChef.putString("prueba",prueba);

        bundleCustomer = new Bundle();
        bundleCustomer.putString("prueba",prueba);

        bundleRank = new Bundle();
        bundleRank.putString("prueba",prueba);

    }


    /*
    *
    * Anade datos a los elementos de la pantalla
    *
    * */
    void setDataToScreen(){

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView user_name = (TextView) findViewById(R.id.user_name);
        user_name.setText(userName);

        TextView chef_score = (TextView) findViewById(R.id.chef_score);
        chef_score.setText(chefScore);

        TextView customer_score = (TextView) findViewById(R.id.customer_score);
        customer_score.setText(customerScore);

        ImageView iv = (ImageView) findViewById(R.id.avatar_image);
        iv.setImageDrawable(avatarImage);
        iv.requestFocus();

    }


    /*
    *
    * Anade funcionalidad a los elementos de la pantalla
    *
    * */
    void setFunctionality(){


        //setea los iconos de las tabs
        //TODO cambiar el tamano de los iconos, tirar de values o calcular el tamano al vuelo
        Drawable user_icon = ContextCompat.getDrawable(this.getApplicationContext(),R.drawable.user);
        Bitmap bitmap = ((BitmapDrawable) user_icon).getBitmap();
        user_icon = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 75, 75, true));

        Drawable chef_icon = ContextCompat.getDrawable(this.getApplicationContext(),R.drawable.cooking);
        bitmap = ((BitmapDrawable) chef_icon).getBitmap();
        chef_icon = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 75, 75, true));

        Drawable customer_icon = ContextCompat.getDrawable(this.getApplicationContext(),R.drawable.food);
        bitmap = ((BitmapDrawable) customer_icon).getBitmap();
        customer_icon = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 75, 75, true));

        Drawable rank_icon = ContextCompat.getDrawable(this.getApplicationContext(),R.drawable.fashion);
        bitmap = ((BitmapDrawable) rank_icon).getBitmap();
        rank_icon = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 75, 75, true));

        //inicializa las tabs de fragments
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        mTabHost.addTab(
                mTabHost.newTabSpec("user").setIndicator(null,user_icon ),
                UserProfileFragmentTab.class, bundleUser);
        mTabHost.addTab(
                mTabHost.newTabSpec("chef").setIndicator(null,chef_icon),
                UserProfileFragmentTab.class, bundleChef);
        mTabHost.addTab(
                mTabHost.newTabSpec("customer").setIndicator(null,customer_icon),
                UserProfileFragmentTab.class, bundleCustomer);
        mTabHost.addTab(
                mTabHost.newTabSpec("rank").setIndicator(null,rank_icon),
                UserProfileFragmentTab.class, bundleRank);

        //quita el focus a las tabs para que no aparezcan en el centro de la pantalla al lanzarse
        mTabHost.setFocusable(false);

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
}
