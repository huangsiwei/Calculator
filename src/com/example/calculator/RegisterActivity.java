package com.example.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.avos.avoscloud.*;

/**
 * Created by huangsiwei on 14-9-21.
 */
public class RegisterActivity extends BaseActivitys {
    private EditText userNameEditText;
    private EditText userEmailEditText;
    private EditText userPassWordEditText;
    private Button finishBtn;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        AVOSCloud.initialize(this, "543eanv3de6r242jk51cwzln3dqe04nuj0b87n3z05nqqcgj", "mg0hbw40y1et96af4y9ppanu1etzn0y33aohiw4t5fk02emr");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        userNameEditText = (EditText) findViewById(R.id.userName);
        userEmailEditText = (EditText) findViewById(R.id.userEmail);
        userPassWordEditText = (EditText) findViewById(R.id.userPassWord);
        //TODO:注册时可选手机号码等信息
        finishBtn = (Button) findViewById(R.id.finish);

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userNameEditText = (EditText) findViewById(R.id.userName);
                userEmailEditText = (EditText) findViewById(R.id.userEmail);
                userPassWordEditText = (EditText) findViewById(R.id.userPassWord);
                nullValid(userNameEditText);
                emailValid(userEmailEditText);
                passWordValid(userPassWordEditText);
                String userNameStr = userNameEditText.getText().toString();
                String userEmailStr = userEmailEditText.getText().toString();
                String userPassWordStr = userPassWordEditText.getText().toString();

                AVUser user = new AVUser();
                user.setUsername(userNameStr);
                user.setEmail(userEmailStr);
                user.setPassword(userPassWordStr);

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            Toast.makeText(getApplicationContext(), "恭喜您注册成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, MyActivity.class);
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