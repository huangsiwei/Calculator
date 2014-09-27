package com.example.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by huangsiwei on 14-8-24.
 */
public class BaseActivitys extends Activity {
    private Animation shakeAction;
    private Pattern pattern;
    private Matcher matcher;
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

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

    protected boolean emailValid(EditText editText) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        String requiredStr = editText.getText().toString();
        if (requiredStr.equals("")) {
            this.setRequired(editText, "请填写此处");
            return false;
        }
        if (!pattern.matcher(requiredStr).matches()) {
            this.setRequired(editText, "请输入正确的邮箱地址");
            return false;
        }
        return true;
    }

    protected boolean nullValid(EditText editText) {
        String requiredStr = editText.getText().toString();
        if (requiredStr.equals("")) {
            this.setRequired(editText, "请填写此处");
            return false;
        }
        return true;
    }

    protected boolean passWordValid(EditText editText) {
        String requiredStr = editText.getText().toString();
        if (requiredStr.equals("")) {
            this.setRequired(editText, "请填写此处");
            return false;
        }
        if (requiredStr.length() < 6) {
            this.setRequired(editText, "密码最少为6位");
            return false;
        }
        return true;

    }
}