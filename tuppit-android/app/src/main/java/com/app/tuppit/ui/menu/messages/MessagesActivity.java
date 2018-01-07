package com.app.tuppit.ui.menu.messages;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.app.tuppit.R;
import com.app.tuppit.api.ServiceAPI;
import com.app.tuppit.api.ServiceAPIHelper;
import com.app.tuppit.model.MessageListItem;
import com.app.tuppit.api.model.User;
import com.app.tuppit.utils.MyDate;
import com.app.tuppit.utils.MyDividerItemDecoration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessagesActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        //TODO anadir funcionalidad sobre las conversaciones (borrar)

        setDataToScreen();
        getData();
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
    private ArrayList<MessageListItem> messageList;

    private void getData(){

        messageList.addAll(getMessageListFromServer());
        adapter.notifyDataSetChanged();

    }


    /*
    *
    * Anade datos a los elementos de la pantalla
    *
    * */
    private RecyclerView recView;
    private MessageRecyclerViewAdapter adapter;
    private LinearLayoutManager mLayoutManager;

    private void setDataToScreen(){

        //Actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // initialize RecyclerView
        recView = (RecyclerView) findViewById(R.id.message_list);
        //recView.setHasFixedSize(true);

        if(messageList==null)
            messageList = new ArrayList<>();
        adapter = new MessageRecyclerViewAdapter(messageList, getApplicationContext());

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
    private ArrayList<MessageListItem> getMessageListFromServer(){

        //TODO Hacer query real al servidor o bd interna y obtener los datos con AsyncTask

        SharedPreferences settings = getSharedPreferences("PREFERENCES", 0);
        String myUserId = settings.getString("user", "");

        ArrayList<MessageListItem> messageListAux = null;
        try {
            messageListAux = new GetMessagesTask().execute(myUserId).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return messageListAux;
    }


}
