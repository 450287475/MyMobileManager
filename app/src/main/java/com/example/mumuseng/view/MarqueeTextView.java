package com.example.mumuseng.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Mumuseng on 2016/3/25.
 */
public class MarqueeTextView extends TextView {
    public MarqueeTextView(Context context) {
        super(context);
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
