package com.app.tuppit.ui.food.list;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;

import com.app.tuppit.R;
import com.app.tuppit.model.FoodUI;
import com.app.tuppit.ui.food.details.FoodDetailsActivity;


/**
 * Created by David on 25/8/16.
 */
public class FoodRecyclerViewAdapter extends RecyclerView.Adapter<FoodRecyclerViewAdapter.RecyclerViewHolder> {

    private ArrayList<FoodUI> data;
    public Context context;

    public FoodRecyclerViewAdapter(ArrayList<FoodUI> data, Context context) {

        this.data = data;
        this.context = context;
    }

    public static class RecyclerViewHolder
            extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView location;
        private TextView price;
        private TextView timeleft;
        private ProgressBar progressBar;

        private ImageView followed;

        private ImageView image;
        private Context context;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            title = (TextView)itemView.findViewById(R.id.food_title);
            location = (TextView)itemView.findViewById(R.id.food_location);
            price = (TextView)itemView.findViewById(R.id.food_price);
//            timeleft = (TextView)itemView.findViewById(R.id.tv_timeleft);
//            progressBar = (ProgressBar) itemView.findViewById(R.id.pb_valoration);
//            followed = (ImageView)itemView.findViewById(R.id.iv_followed);
            image = (ImageView)itemView.findViewById(R.id.food_image);


        }

        public void bindTestObject(FoodUI t, Context c) {
            title.setText(t.getTitle());
            location.setText(t.getLocation());
            price.setText(t.getPrice());
//            timeleft.setText(t.getTimeleft() + " h");
//            progressBar.setProgress((int)t.getValoration());
//            int visibility = (t.isFollowed())?View.VISIBLE:View.INVISIBLE;
//            followed.setVisibility(visibility);

            byte[] imageBytes = Base64.decode(t.getImage(), Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            image.setImageBitmap(decodedImage);


        }

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.food_list_item, viewGroup, false);

        RecyclerViewHolder tvh = new RecyclerViewHolder(itemView);


        return tvh;
    }


    @Override
    public void onBindViewHolder(final RecyclerViewHolder viewHolder, int pos) {
        FoodUI item = data.get(pos);

        viewHolder.bindTestObject(item,context);

        final FoodUI auxItem = item;

        viewHolder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_DOWN:
                        viewHolder.itemView.setAlpha(0.5F);

                        break;

                    case MotionEvent.ACTION_UP:
                        viewHolder.itemView.setAlpha(1F);

                        Intent intent = new Intent(context, FoodDetailsActivity.class);
                        intent.putExtra("title", auxItem.getTitle());
                        intent.putExtra("image", auxItem.getImage());
                        intent.putExtra("price", auxItem.getPrice());
                        intent.putExtra("location",auxItem.getLocation());
                        intent.putExtra("foodId",auxItem.getId());
                        intent.putExtra("userId", auxItem.getUserId());
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

        setAnimation(viewHolder.itemView, pos);

    }

    private int lastPosition = -1;
    /**
     * Here is the key method to apply the animation
     */
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


}
