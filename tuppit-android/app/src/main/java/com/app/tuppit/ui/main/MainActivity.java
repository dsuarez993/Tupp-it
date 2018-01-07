package com.app.tuppit.ui.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.app.tuppit.R;
import com.app.tuppit.ui.food.list.FoodListActivity;
import com.app.tuppit.ui.food.upload.FoodUploadActivity;
import com.app.tuppit.ui.menu.help.HelpActivity;
import com.app.tuppit.ui.menu.invitefriends.InviteFriendsActivity;
import com.app.tuppit.ui.menu.messages.MessagesActivity;
import com.app.tuppit.ui.menu.notifications.NotificationsActivity;
import com.app.tuppit.ui.menu.subscriptions.SubscriptionsActivity;
import com.app.tuppit.ui.user.profile.UserProfileActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO comprobar si el usuario esta logeado
        //TODO si no lo esta, lanzar activity de logueo

        setContentView(R.layout.activity_main);

        //Menu
        setMenu();

        //Funcionalidades
        setFunctionality();

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        resetScreen();
    }


    /*
    *
    * Anade funcionalidad al los elementos de la actividad
    *
    * */

    RelativeLayout rlTop = null;
    RelativeLayout rlBottom = null;

    private void setFunctionality(){

        rlTop = (RelativeLayout) findViewById(R.id.rlTop);
        rlBottom = (RelativeLayout) findViewById(R.id.rlBottom);

        rlTop.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                onMotionEventFunctionality(1,rlTop, motionEvent, getResources().getColor(R.color.colorPrimary),
                        getResources().getColor(R.color.colorPrimaryAccent),
                        new Intent(getApplicationContext(), FoodListActivity.class));

                return true;

            }
        });

        rlBottom.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                onMotionEventFunctionality(2,rlBottom,motionEvent,getResources().getColor(R.color.colorSecondary),
                        getResources().getColor(R.color.colorSecondaryAccent),
                        new Intent(getApplicationContext(), FoodUploadActivity.class));

                return true;
            }
        });
    }

    /*
    *
    * Anade funcionalidad a cada MotionEvent del Layout
    *
    * */
    private int selected = 0;

    private void onMotionEventFunctionality(int rlId, RelativeLayout rl, MotionEvent motionEvent, int color, int accentColor, Intent nextActivity){

        if(selected == 0 || selected == rlId){
            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
                case MotionEvent.ACTION_DOWN:

                    selected = rlId;
                    rl.setBackgroundColor(accentColor);
                    break;

                case MotionEvent.ACTION_UP:

                    selected = 0;
                    rl.setBackgroundColor(color);
                    startActivity(nextActivity);
                    break;

                case MotionEvent.ACTION_CANCEL:
                    selected = 0;
                    rl.setBackgroundColor(color);

                default:
                    break;
            }
        }
    }

    /*
    *
    * Reinicia los elementos de la actividad
    *
    * */
    private void resetScreen(){
        selected = 0;
        rlTop.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        rlBottom.setBackgroundColor(getResources().getColor(R.color.colorSecondary));
    }


    /*
     *
     * Funciones del Menú
     *
     */

    private void setMenu(){
        //Menu
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Intent intent = null;

        if (id == R.id.nav_messages) {
            intent = new Intent(getApplicationContext(), MessagesActivity.class);

        } else if (id == R.id.nav_notifications) {
            intent = new Intent(getApplicationContext(), NotificationsActivity.class);

        } else if (id == R.id.nav_subscriptions) {
            intent = new Intent(getApplicationContext(), SubscriptionsActivity.class);

        } else if (id == R.id.nav_invite_friends) {
            intent = new Intent(getApplicationContext(), InviteFriendsActivity.class);

        } else if (id == R.id.nav_help) {
            //intent = new Intent(getApplicationContext(), HelpActivity.class);
            intent = new Intent(getApplicationContext(), UserProfileActivity.class);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        if(intent!=null)
            startActivity(intent);

        return true;
    }
}
