package com.example.kajza.kuharica.Font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class ProbaFont extends TextView {

    public ProbaFont(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public ProbaFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProbaFont(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/hoeflerText.ttf");
        setTypeface(tf);
    }

}
