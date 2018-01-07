package com.app.tuppit.ui.food.details;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by David on 31/8/16.
 */

/*
*
* Adapter del slider de imagenes
*
* */
public class FoodImagesAdapter extends PagerAdapter {

    Context context;

    //TODO cambiar sliderImagesId por: array de imagenes? array de ids en array de imagenes en cache?
    private int[] imagesArray;//array con id de recurso de las images

    public FoodImagesAdapter(Context mContext, int[] sliderImagesId) {
        this.context = mContext;
        this.imagesArray = sliderImagesId;
    }

    @Override
    public int getCount() {
        return imagesArray.length;
    }


    @Override
    public boolean isViewFromObject(View v, Object obj) {
        return v == obj;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int i) {
        ImageView mImageView = new ImageView(context);
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mImageView.setImageResource(imagesArray[i]);
        container.addView(mImageView, 0);
        return mImageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int i, Object obj) {
        container.removeView((ImageView) obj);
    }
}
