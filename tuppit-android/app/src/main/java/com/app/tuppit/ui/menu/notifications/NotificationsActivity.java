package com.app.tuppit.ui.menu.notifications;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.app.tuppit.R;
import com.app.tuppit.model.NotificationListItem;
import com.app.tuppit.utils.MyDate;
import com.app.tuppit.utils.MyDividerItemDecoration;

import java.util.ArrayList;

public class NotificationsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        //TODO anadir funcionalidad sobre las notificaciones (borrar)

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
    private ArrayList<NotificationListItem> notificationList;

    private void getData(){

        if(notificationList ==null)
            notificationList = new ArrayList<>();

        notificationList.addAll(getNotificationList());

    }


    /*
    *
    * Anade datos a los elementos de la pantalla
    *
    * */
    private RecyclerView recView;
    private NotificationRecyclerViewAdapter adapter;
    private LinearLayoutManager mLayoutManager;

    private void setDataToScreen(){

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // initialize RecyclerView
        recView = (RecyclerView) findViewById(R.id.notification_list);
        //recView.setHasFixedSize(true);

        adapter = new NotificationRecyclerViewAdapter(notificationList, getApplicationContext());

        recView.setAdapter(adapter);

        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recView.setLayoutManager(mLayoutManager);
        recView.addItemDecoration(new MyDividerItemDecoration(getApplicationContext(), MyDividerItemDecoration.VERTICAL_LIST));
    }


    /*
    *
    * Funcion que realiza peticion de datos al server o bd interna
    * devuelve una lista de conversaciones abiertas.
    *
    * */
    private ArrayList<NotificationListItem> getNotificationList(){

        //TODO Hacer query real al servidor o bd interna y obtener los datos con AsyncTask

        ArrayList<NotificationListItem> notificationListAux = new ArrayList<>();

        //Rellenar con datos de prueba
        notificationListAux.add(new NotificationListItem("0", MyDate.getCurrentDate(),"01:00","Notificación 1","Contenido 1..."));
        notificationListAux.add(new NotificationListItem("1", "02/09/2016","03:00","Notificación 2","Contenido 2..."));
        notificationListAux.add(new NotificationListItem("2", MyDate.getCurrentDate(),"05:00","Notificación 3","Contenido 3..."));
        notificationListAux.add(new NotificationListItem("3", "04/09/2016","04:00","Notificación 4","Contenido 4..."));

        return notificationListAux;

    }

}
