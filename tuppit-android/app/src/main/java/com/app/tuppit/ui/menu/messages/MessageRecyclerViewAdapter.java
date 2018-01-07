package com.app.tuppit.ui.menu.messages;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.tuppit.R;
import com.app.tuppit.model.MessageListItem;
import com.app.tuppit.utils.MyDate;
import com.app.tuppit.ui.chat.ChatActivity;

import java.util.ArrayList;

/**
 * Created by David on 5/9/16.
 */
public class MessageRecyclerViewAdapter extends RecyclerView.Adapter<MessageRecyclerViewAdapter.RecyclerViewHolder> {

    private ArrayList<MessageListItem> data;
    public Context context;

    public MessageRecyclerViewAdapter(ArrayList<MessageListItem> data, Context context) {

        this.data = data;
        this.context = context;
    }

    public static class RecyclerViewHolder
            extends RecyclerView.ViewHolder {

        private TextView userName;
        private TextView date;
        private TextView lastMessage;

        private Context context;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            userName = (TextView)itemView.findViewById(R.id.tv_userName);
            date = (TextView)itemView.findViewById(R.id.tv_date);
            lastMessage = (TextView)itemView.findViewById(R.id.tv_lastMessage);

        }

        public void bindTestObject(MessageListItem t, Context c) {

            userName.setText(t.getUserName());
            //lastMessage.setText(t.getLastMessage());
            lastMessage.setText(t.getFoodId());
            date.setText(t.getSentDate());

            //TODO meter la imagen del usuario

        }

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.message_list_item, viewGroup, false);

        RecyclerViewHolder tvh = new RecyclerViewHolder(itemView);

        return tvh;
    }


    @Override
    public void onBindViewHolder(final RecyclerViewHolder viewHolder, int pos) {
        MessageListItem item = data.get(pos);

        viewHolder.bindTestObject(item,context);

        final MessageListItem auxItem = item;

        viewHolder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_DOWN:
                        viewHolder.itemView.setAlpha(0.5F);

                        break;

                    case MotionEvent.ACTION_UP:
                        viewHolder.itemView.setAlpha(1F);

                        Intent intent = new Intent(context, ChatActivity.class);
                        //TODO extras del intent
                        intent.putExtra("userId", auxItem.getUserId());
                        intent.putExtra("foodId", auxItem.getFoodId());
                        intent.putExtra("chatId", auxItem.getChatId());
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