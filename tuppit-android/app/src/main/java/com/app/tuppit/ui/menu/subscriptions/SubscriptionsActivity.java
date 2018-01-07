package com.app.tuppit.ui.menu.subscriptions;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.app.tuppit.R;
import com.app.tuppit.model.SubscriptionListItem;
import com.app.tuppit.model.User;
import com.app.tuppit.utils.MyDividerItemDecoration;

import java.util.ArrayList;

public class SubscriptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriptions);

        //TODO anadir funcionalidad sobre las subcripciones (borrar)
        //TODO anadir informacion sobre los chefs (comida a la venta?, puntuacion de chef, posicion en el ranking)
        getData();
        setDataToScreen();
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
    private ArrayList<SubscriptionListItem> subscriptionList;

    private void getData(){

        if(subscriptionList ==null)
            subscriptionList = new ArrayList<>();

        subscriptionList.addAll(getSubscriptionList());

    }


    /*
    *
    * Anade datos a los elementos de la pantalla
    *
    * */
    private RecyclerView recView;
    private SubscriptionRecyclerViewAdapter adapter;
    private LinearLayoutManager mLayoutManager;

    private void setDataToScreen(){

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // initialize RecyclerView
        recView = (RecyclerView) findViewById(R.id.subscription_list);
        //recView.setHasFixedSize(true);

        adapter = new SubscriptionRecyclerViewAdapter(subscriptionList, getApplicationContext());

        recView.setAdapter(adapter);

        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recView.setLayoutManager(mLayoutManager);
        recView.addItemDecoration(new MyDividerItemDecoration(getApplicationContext(), MyDividerItemDecoration.VERTICAL_LIST));
    }


    /*
    *
    * Funcion que realiza peticion de datos al server o bd interna
    * devuelve una lista de subcripciones a chefs.
    *
    * */
    private ArrayList<SubscriptionListItem> getSubscriptionList(){

        //TODO Hacer query real al servidor o bd interna y obtener los datos con AsyncTask

        ArrayList<SubscriptionListItem> subscriptionListAux = new ArrayList<>();

        //Rellenar con datos de prueba
        subscriptionListAux.add(new SubscriptionListItem("0", new User("0","","Juan E.",0)));
        subscriptionListAux.add(new SubscriptionListItem("1", new User("1","","David S.",1)));
        subscriptionListAux.add(new SubscriptionListItem("2", new User("2","","Paco S.",2)));
        subscriptionListAux.add(new SubscriptionListItem("3", new User("3","","Adrian O.",3)));

        return subscriptionListAux;

    }

}


