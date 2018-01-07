package com.app.tuppit.utils.custom_ui_elements;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by david on 4/3/17.
 */

/*
*
* Button editado para cambiar la fuente por una externa
*
* */
public class MyCustomButton extends Button {

    public MyCustomButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyCustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyCustomButton(Context context) {
        super(context);
        init();
    }

    private void init() {
        if(!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/varela_round_regular.ttf");
            setTypeface(tf);
        }
    }

}
