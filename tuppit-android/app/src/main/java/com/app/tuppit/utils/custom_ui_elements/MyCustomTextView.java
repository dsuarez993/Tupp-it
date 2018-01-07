package com.app.tuppit.utils.custom_ui_elements;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by David on 25/8/16.
 */

/*
*
* TextView editado para cambiar la fuente por una externa
*
* */
public class MyCustomTextView extends TextView {

    public MyCustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyCustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyCustomTextView(Context context) {
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
