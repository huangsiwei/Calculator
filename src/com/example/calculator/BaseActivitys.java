package com.example.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

/**
 * Created by huangsiwei on 14-8-24.
 */
public class BaseActivitys extends Activity {
    private Animation shakeAction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.shakeAction = new TranslateAnimation(-3, 3, 0, 0);
        this.shakeAction.setDuration(100);
        this.shakeAction.setRepeatCount(5);
    }

    protected void setRequired(View view, String... error) {
        view.startAnimation(shakeAction);
        view.setFocusable(true);
        view.requestFocus();//设置光标为选择状态
        view.setFocusableInTouchMode(true);
        if (view instanceof EditText) {
            ((EditText) view).setError(error[0]);
        }
    }

}