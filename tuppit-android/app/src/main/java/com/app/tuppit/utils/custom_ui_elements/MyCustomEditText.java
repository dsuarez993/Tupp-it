package com.app.tuppit.utils.custom_ui_elements;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by david on 4/3/17.
 */

/*
*
* EditText editado para cambiar la fuente por una externa
*
* */
public class MyCustomEditText extends EditText{


    public MyCustomEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyCustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyCustomEditText(Context context) {
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

