package com.example.calculator;

import android.app.Activity;
import android.content.Intent;
import android.opengl.ETC1;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.widget.DrawerLayout;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

public class MyActivity extends BaseActivitys {
    /**
     * Called when the activity is first created.
     */

    private DrawerLayout mDrawerLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        Button calculateBtn = (Button) findViewById(R.id.calculate);
        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Boolean dataValidationFlag = true;
                List<EditText> editTextList = new ArrayList<EditText>();

                EditText investment_amount = (EditText) findViewById(R.id.investment_amount);
                EditText annualized_rate = (EditText) findViewById(R.id.annualized_rate);
                EditText investment_days = (EditText) findViewById(R.id.investment_days);

                editTextList.add(investment_amount);
                editTextList.add(annualized_rate);
                editTextList.add(investment_days);
                for (EditText editText : editTextList) {
                    if (vaild(editText)) {
                        Log.e("ddd", "不为空");
                    } else {
                        dataValidationFlag = false;
                        Log.e("ddd", "为空");
                    }
                }

                if (dataValidationFlag) {
                    RadioGroup base_days = (RadioGroup) findViewById(R.id.base_days);
                    float investmentAmount = Float.parseFloat(investment_amount.getText().toString());
                    float annualizedRate = Float.parseFloat(annualized_rate.getText().toString()) / 100;
                    int investmentDays = Integer.parseInt(investment_days.getText().toString());
                    int baseDay = 0;
                    int checkedRadioButtonId = base_days.getCheckedRadioButtonId();
                    if (checkedRadioButtonId == -1) {

                    } else if (checkedRadioButtonId == R.id.yearTypeOne) {
                        baseDay = 365;
                    }
                    if (checkedRadioButtonId == R.id.yearTypeTwo) {
                        baseDay = 360;
                    }

                    InterestRateCal interestRateCal = new InterestRateCal();
                    float earnedMoney = interestRateCal.countEarning(investmentAmount, annualizedRate, investmentDays, baseDay);
                    String earnedMoneyString = Float.toString(earnedMoney);
                    String allMoneyString = Float.toString(earnedMoney + investmentAmount);
                    TextView moneyEarned = (TextView) findViewById(R.id.moneyEarned);
                    moneyEarned.setText("到期收益为:" + earnedMoneyString + "元");
                    TextView allMoney = (TextView) findViewById(R.id.allMoney);
                    allMoney.setText("到期本利和为:" + allMoneyString + "元");
                    Log.e("ddd", "end!");
                }
            }
        });

        Button resetBtn = (Button) findViewById(R.id.reset);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText investment_amount = (EditText) findViewById(R.id.investment_amount);
                EditText annualized_rate = (EditText) findViewById(R.id.annualized_rate);
                EditText investment_days = (EditText) findViewById(R.id.investment_days);
                investment_amount.setText("");
                annualized_rate.setText("");
                investment_days.setText("");
            }
        });

        ImageButton menuCalculate = (ImageButton) findViewById(R.id.menuCalculate);
        menuCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DDD", "clicked!!!");
                mDrawerLayout.closeDrawers();
            }
        });

        ImageButton menuRecommend = (ImageButton) findViewById(R.id.menuRecommend);
        menuRecommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawers();
                Intent intent = new Intent();
                intent.setClass(MyActivity.this, RecommendActivity.class);
                startActivity(intent);
            }
        });
    }


    private boolean vaild(EditText editText) {

        String requiredStr = editText.getText().toString();
        if (requiredStr.equals("")) {
            this.setRequired(editText, "请填写此处");
            return false;
        }
        return true;
    }
}
