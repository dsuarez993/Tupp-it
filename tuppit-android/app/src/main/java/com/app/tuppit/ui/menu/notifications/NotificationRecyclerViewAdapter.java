package com.app.tuppit.ui.menu.notifications;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.tuppit.R;
import com.app.tuppit.model.NotificationListItem;
import com.app.tuppit.utils.MyDate;

import java.util.ArrayList;

/**
 * Created by David on 5/9/16.
 */
public class NotificationRecyclerViewAdapter extends RecyclerView.Adapter<NotificationRecyclerViewAdapter.RecyclerViewHolder> {

    private ArrayList<NotificationListItem> data;
    public Context context;

    public NotificationRecyclerViewAdapter(ArrayList<NotificationListItem> data, Context context) {

        this.data = data;
        this.context = context;
    }

    public static class RecyclerViewHolder
            extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView date;
        private TextView contain;

        private Context context;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            title = (TextView)itemView.findViewById(R.id.tv_userName);
            date = (TextView)itemView.findViewById(R.id.tv_date);
            contain = (TextView)itemView.findViewById(R.id.tv_contain);

        }

        public void bindTestObject(NotificationListItem t, Context c) {

            title.setText(t.getTitle());
            contain.setText(t.getContain());

            if(MyDate.getCurrentDate().equals(t.getDate()))
                date.setText(t.getTime());
            else
                date.setText(t.getDate());

        }

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.notification_list_item, viewGroup, false);

        RecyclerViewHolder tvh = new RecyclerViewHolder(itemView);

        return tvh;
    }


    @Override
    public void onBindViewHolder(final RecyclerViewHolder viewHolder, int pos) {
        NotificationListItem item = data.get(pos);

        viewHolder.bindTestObject(item,context);

        final NotificationListItem auxItem = item;

        viewHolder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_DOWN:
                        viewHolder.itemView.setAlpha(0.5F);

                        break;

                    case MotionEvent.ACTION_UP:
                        viewHolder.itemView.setAlpha(1F);

                        /*Intent intent = new Intent(context, ChatActivity.class);
                        //TODO Mostrar pop-up con el contenido completo de la notificacion
                        intent.putExtra("user", "prueba");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);*/
                        break;

                    case MotionEvent.ACTION_CANCEL:
                        viewHolder.itemView.setAlpha(1F);
                        break;

                    default:
                        break;
                }

                return true;

            }
        });

    }



    @Override
    public int getItemCount() {
        return data.size();
    }


}