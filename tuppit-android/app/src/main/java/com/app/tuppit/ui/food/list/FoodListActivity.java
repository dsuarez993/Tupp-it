package com.app.tuppit.ui.food.list;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.tuppit.R;
import com.app.tuppit.api.ServiceAPI;
import com.app.tuppit.api.ServiceAPIHelper;

import com.app.tuppit.api.model.Food;
import com.app.tuppit.model.FoodUI;
import com.app.tuppit.model.GPSLocation;
import com.app.tuppit.ui.food.details.FoodDetailsActivity;
import com.app.tuppit.ui.food.upload.FoodUploadActivity;
import com.app.tuppit.ui.menu.help.HelpActivity;
import com.app.tuppit.ui.menu.invitefriends.InviteFriendsActivity;
import com.app.tuppit.ui.menu.messages.MessagesActivity;
import com.app.tuppit.ui.menu.notifications.NotificationsActivity;
import com.app.tuppit.ui.menu.subscriptions.SubscriptionsActivity;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        setMenu();
        getData();
        setDataToScreen();
        setFunctionality();

    }


    /*
    *
    * Obtiene datos necesarios
    *
    * */
    private ArrayList<FoodUI> foodList;
    private GPSLocation location;
    private int fromPos = 0;

    private String myUserId;

    private void getData(){

        if(location==null)
            location = new GPSLocation(0,0);

        if(foodList==null)
            foodList = new ArrayList<>();


        fromPos = foodList.size();

        SharedPreferences settings = getSharedPreferences("PREFERENCES", 0);
        myUserId = settings.getString("user", "");
    }


    /*
    *
    * Anade datos a los elementos de la pantalla
    *
    * */
    private RecyclerView recView;
    private FoodRecyclerViewAdapter adapter;
    private LinearLayoutManager mLayoutManager;
    private FloatingActionButton fab_addfood;
    private RelativeLayout rl_progressBar;

    private void setDataToScreen(){

        // initialize RecyclerView
        recView = (RecyclerView) findViewById(R.id.food_list);
        //recView.setHasFixedSize(true);

        adapter = new FoodRecyclerViewAdapter(foodList, getApplicationContext());

        recView.setAdapter(adapter);

        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recView.setLayoutManager(mLayoutManager);

        fab_addfood = (FloatingActionButton) findViewById(R.id.fab_addfood);

        rl_progressBar = (RelativeLayout) findViewById(R.id.rl_progressBar);
    }


    /*
    *
    * Anade funcionalidad a los elementos de la pantalla
    *
    * */
    private boolean loading = true;
    private int pastVisibleItems, visibleItemCount, totalItemCount;
    private boolean fab_isShow = true;

    private int page = 0;

    private void setFunctionality(){

        //refresca los datos al hacer scroll y muestra o esconde el fab
        recView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            // check for scroll down
            if(dy > 0) {

                if(fab_isShow){
                    fab_addfood.hide();
                    fab_isShow=false;
                }

                visibleItemCount = mLayoutManager.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();

                if (loading) {

                    if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {

                        loading = false;
                        rl_progressBar.setVisibility(View.VISIBLE);
                        //anade elementos en la lista
                        getFoodList();

                    }
                }
            }else{
                if(!fab_isShow){
                    fab_addfood.show();
                    fab_isShow=true;
                }
            }
            }
        });

        //funcionalidad del fab
        fab_addfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), FoodUploadActivity.class);
                startActivityForResult(i,4);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        page=0;
        foodList.clear();
        adapter.notifyDataSetChanged();
        getFoodList();
    }


/*
    *
    * Funcion que realiza peticion de datos al server segun la posicion GPS,
    * el numero de elementos que ya hay en la lista, y el numero de resultados esperados,
    * devuelve una lista de elementos FoodUI
    *
    * */
    /*private ArrayList<FoodUI> getFoodListFromServer(GPSLocation location, int fromPos, int nResults){

        //TODO Hacer query real al servidor y obtener los datos con AsyncTask

        ArrayList<FoodUI> foodListAux = new ArrayList<>();

        Food.FoodList foodList = getFoodList();

        if(foodList!=null){

            for (Food.FoodObject food : foodList.foodList) {
                foodListAux.add(new FoodUI(food.title, "", food.price+"€", 0, 0, false, food.image));

            }
        }


        //Rellenar con datos de prueba
        for(int i=fromPos; i<fromPos+nResults; i++) {

            if (i % 2 == 0)
                foodListAux.add(new FoodUI("Tacos Mexicanos " + i, "Subtítulo item " + i, "" + (i * 12 + 1) + "€", 3.0, i + 2, true, new int[] {R.drawable.tacos,R.drawable.burger}));

            else
                foodListAux.add(new FoodUI("Hamburguesa  " + i, "Subtítulo item " + i, "" + (i * 12 + 1) + "€", 3.0, i + 2, false, new int[] {R.drawable.burger,R.drawable.tacos}));
        }



        return foodListAux;
    }*/

    /*
    *
    * LLamada al API para obtener lista de platos
    *
    **/
    private void getFoodList (){

        Log.i("FoodUI Call","getFoodList");

        // Getting a JSON object

        ServiceAPI api = ServiceAPIHelper.getRestClient();
        Call<Food.FoodList> callObject = api.getFoodList(page);
        callObject.enqueue(new Callback<Food.FoodList>() {

            @Override
            public void onResponse(Call<Food.FoodList> call, Response<Food.FoodList> response) {
                if (response.isSuccessful()) {

                    try {
                        Food.FoodList foodListResult = response.body();
                        Log.i("FoodUI Call", "" + foodListResult.foodList.get(0).title);


                        for (Food.FoodObject food : foodListResult.foodList) {

                            if(!food.userId.equals(myUserId)){
                                foodList.add(new FoodUI(food.id, food.userId, food.title, "", food.price+"€", 0, 0, false, food.image));
                                adapter.notifyDataSetChanged();
                            }

                        }

                        loading = true;
                        page++;
                        fromPos = foodList.size();
                        rl_progressBar.setVisibility(View.GONE);


                    } catch (Exception e) {
                        Log.d("onResponse", "There is an error");
                        e.printStackTrace();
                        rl_progressBar.setVisibility(View.GONE);
                    }


                }
            }

            @Override
            public void onFailure(Call<Food.FoodList> call, Throwable t) {
                Log.e("FoodUI Call", t.getLocalizedMessage());
                rl_progressBar.setVisibility(View.GONE);

            }
        });

    }


    /*
     *
     * Funciones del Menú
     *
     */
    private void setMenu(){
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

        switch (id){
            case R.id.nav_messages:
                intent = new Intent(getApplicationContext(), MessagesActivity.class);
                break;
            case R.id.nav_notifications:
                intent = new Intent(getApplicationContext(), NotificationsActivity.class);
                break;
            case R.id.nav_subscriptions:
                intent = new Intent(getApplicationContext(), SubscriptionsActivity.class);
                break;
            case R.id.nav_invite_friends:
                intent = new Intent(getApplicationContext(), InviteFriendsActivity.class);
                break;
            case R.id.nav_help:
                intent = new Intent(getApplicationContext(), HelpActivity.class);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        if(intent!=null)
            startActivity(intent);

        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 4 && resultCode == RESULT_OK) {

            String food_name = data.getStringExtra("food_name");
            String food_price = data.getStringExtra("food_price");
            String food_description = data.getStringExtra("food_description");
            String food_image = data.getStringExtra("food_image");

            Intent i = new Intent(getApplicationContext(), FoodDetailsActivity.class);

            i.putExtra("title",food_name);
            i.putExtra("price",food_price);
            i.putExtra("description",food_description);
            i.putExtra("image",food_image);

            //TODO arreglar esto
            i.putExtra("foodId","");
            i.putExtra("userId","");

            //TODO cambiar esto
            i.putExtra("images",new int[] {R.drawable.tacos,R.drawable.burger});

            startActivity(i);
        }
    }
}
