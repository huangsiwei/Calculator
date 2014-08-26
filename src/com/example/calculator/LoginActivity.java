package com.example.calculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.avos.avoscloud.*;

import java.io.BufferedReader;

/**
 * Created by huangsiwei on 14-8-20.
 */
public class LoginActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        AVOSCloud.initialize(this, "0uc74h6qurtg3653d3kqorrjpf8kqgnlay4dgqbna7nq6036", "bcmur3lsa8uv6cmpsjy1h380respd9mtvd5qgondpaav9uxa");
        AVAnalytics.trackAppOpened(getIntent());

        Button register = (Button) findViewById(R.id.register);
        final Button login = (Button) findViewById(R.id.login);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText email = (EditText) findViewById(R.id.email);
                EditText passWord = (EditText) findViewById(R.id.passWord);
                String emailString = email.getText().toString();
                String passWordString = passWord.getText().toString();
                AVUser avUser = new AVUser();
                avUser.setUsername(emailString);
                avUser.setEmail(emailString);
                avUser.setPassword(passWordString);
                avUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            Intent intent = new Intent();
                            intent.setClass(LoginActivity.this, MyActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
//                            Toast.makeText();
                        }
                    }
                });
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}