package com.example.calculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.avos.avoscloud.*;

/**
 * Created by huangsiwei on 14-8-20.
 */
public class LoginActivity extends BaseActivitys {

    private EditText userNameEditText;
    private EditText userPassWordEditText;
    private Button loginBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        AVOSCloud.initialize(this, "543eanv3de6r242jk51cwzln3dqe04nuj0b87n3z05nqqcgj", "mg0hbw40y1et96af4y9ppanu1etzn0y33aohiw4t5fk02emr");
        AVAnalytics.trackAppOpened(getIntent());
        userNameEditText = (EditText) findViewById(R.id.userName);
        userPassWordEditText = (EditText) findViewById(R.id.userPassWord);
        loginBtn = (Button) findViewById(R.id.login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nullValid(userNameEditText);
                passWordValid(userPassWordEditText);
                String userNameStr = userNameEditText.getText().toString();
                String userPassWordStr = userPassWordEditText.getText().toString();
                AVUser.logInInBackground(userNameStr, userPassWordStr, new LogInCallback<AVUser>() {
                    @Override
                    public void done(AVUser avUser, AVException e) {
                        if (avUser != null) {
                            Intent intent = new Intent(LoginActivity.this, MyActivity.class);
                            startActivity(intent);
                        } else {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}