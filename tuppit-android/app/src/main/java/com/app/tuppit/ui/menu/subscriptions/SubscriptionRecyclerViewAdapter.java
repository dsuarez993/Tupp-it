package com.app.tuppit.ui.menu.subscriptions;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.tuppit.R;
import com.app.tuppit.model.SubscriptionListItem;
import com.app.tuppit.ui.user.profile.UserProfileActivity;

import java.util.ArrayList;

/**
 * Created by David on 5/9/16.
 */
public class SubscriptionRecyclerViewAdapter extends RecyclerView.Adapter<SubscriptionRecyclerViewAdapter.RecyclerViewHolder> {

    private ArrayList<SubscriptionListItem> data;
    public Context context;

    public SubscriptionRecyclerViewAdapter(ArrayList<SubscriptionListItem> data, Context context) {

        this.data = data;
        this.context = context;
    }

    public static class RecyclerViewHolder
            extends RecyclerView.ViewHolder {

        private TextView userName;

        private Context context;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            userName = (TextView)itemView.findViewById(R.id.tv_userName);
            //TODO Anadir imagen de perfil de usuario

        }

        public void bindTestObject(SubscriptionListItem t, Context c) {

            userName.setText(t.getUser().getName());

        }

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.subscription_list_item, viewGroup, false);

        RecyclerViewHolder tvh = new RecyclerViewHolder(itemView);

        return tvh;
    }


    @Override
    public void onBindViewHolder(final RecyclerViewHolder viewHolder, int pos) {
        SubscriptionListItem item = data.get(pos);

        viewHolder.bindTestObject(item,context);

        final SubscriptionListItem auxItem = item;

        viewHolder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_DOWN:
                        viewHolder.itemView.setAlpha(0.5F);

                        break;

                    case MotionEvent.ACTION_UP:
                        viewHolder.itemView.setAlpha(1F);

                        Intent intent = new Intent(context, UserProfileActivity.class);
                        intent.putExtra("user", "prueba");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
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