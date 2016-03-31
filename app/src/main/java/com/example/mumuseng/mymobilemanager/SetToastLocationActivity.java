package com.example.mumuseng.mymobilemanager;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mumuseng.application.MyApplication;

public class SetToastLocationActivity extends ActionBarActivity {

    private LinearLayout ll_SetToastLocation_message;
    private LinearLayout.LayoutParams layoutParams;
    private int ll_width;
    private int ll_height;
    private int height;
    private int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_toast_location);
        ll_SetToastLocation_message = (LinearLayout) findViewById(R.id.ll_SetToastLocation_message);
        layoutParams = (LinearLayout.LayoutParams) ll_SetToastLocation_message.getLayoutParams();
        layoutParams.gravity= Gravity.LEFT|Gravity.TOP;
        float toastx = MyApplication.sp.getFloat("toastx", 200);
        float toasty = MyApplication.sp.getFloat("toasty", 200);
        layoutParams.leftMargin= (int) toastx;
        layoutParams.topMargin= (int) toasty;
        final WindowManager systemService = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display defaultDisplay = systemService.getDefaultDisplay();
        width = defaultDisplay.getWidth();
        height = defaultDisplay.getHeight();

        ll_SetToastLocation_message.setLayoutParams(layoutParams);
        ll_SetToastLocation_message.setOnTouchListener(new View.OnTouchListener() {

            private float top;
            private float left;
            private float endy;
            private float endx;
            private float starty;
            private float startx;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println(event.getAction());
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startx = event.getRawX();
                        starty = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:

                        endx = event.getRawX();
                        endy = event.getRawY();
                        left = ll_SetToastLocation_message.getX();
                        top = ll_SetToastLocation_message.getY();
                        float dx = endx - startx;
                        float dy = endy - starty;
                        ll_width = ll_SetToastLocation_message.getWidth();
                        ll_height = ll_SetToastLocation_message.getHeight();
                        System.out.println((endy - event.getX()));
                        if (left + dx < 0 || top + dy < 0 || (left + ll_width + dx) > width || (top + ll_height + dy) > height) {
                            break;
                        }
                        ll_SetToastLocation_message.layout((int) (left + dx), (int) (top + dy), (int) (left + ll_width + dx), (int) (top + ll_height + dy));
                        startx = endx;
                        starty = endy;
                        break;
                    case MotionEvent.ACTION_UP:
                        MyApplication.editor.putFloat("toastx", ll_SetToastLocation_message.getX());
                        MyApplication.editor.putFloat("toasty", ll_SetToastLocation_message.getY());
                        MyApplication.editor.commit();
                        break;
                }
                return false;
            }
        });
        ll_SetToastLocation_message.setOnClickListener(new View.OnClickListener() {
            boolean flag=true;
            long time=0;
            @Override
            public void onClick(View v) {

                //第一次点击
                if(flag){
                    time = System.currentTimeMillis();
                    flag=false;
                }else {
                    if(System.currentTimeMillis()-time<500){
                        System.out.println("双击");
                        MyApplication.editor.putFloat("toastx", 200);
                        MyApplication.editor.putFloat("toasty", 300);
                        MyApplication.editor.commit();
                        ll_SetToastLocation_message.layout(200,300,(int)(200+ll_width),(int)(300+ll_height));
                        flag=true;
                    }else {
                        time = System.currentTimeMillis();
                    }
                }
            }
        });
    }

}
