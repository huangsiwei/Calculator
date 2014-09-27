package com.example.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyActivity extends BaseActivitys {
    /**
     * Called when the activity is first created.
     */

    private DrawerLayout basicDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;
    private String[] menuItemNameList;
    private boolean isUser;
    private AVUser currentUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AVOSCloud.initialize(this, "543eanv3de6r242jk51cwzln3dqe04nuj0b87n3z05nqqcgj", "mg0hbw40y1et96af4y9ppanu1etzn0y33aohiw4t5fk02emr");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        currentUser = AVUser.getCurrentUser();
        isUser = false;
        if (currentUser != null) {
            isUser = true;
        } else {
            isUser = false;
        }
        basicDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        initListView(isUser);
        mDrawerToggle = new ActionBarDrawerToggle(this, basicDrawerLayout, R.drawable.calculate, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View view) {
                invalidateOptionsMenu();
            }
        };
        basicDrawerLayout.setDrawerListener(mDrawerToggle);
        getActionBar().setHomeButtonEnabled(true);

        /*
        理财产品收益率计算功能
        */

        final Button calculateBtn = (Button) findViewById(R.id.calculate);
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
                    if (nullValid(editText)) {
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


//        Button resetBtn = (Button) findViewById(R.id.reset);
//        resetBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EditText investment_amount = (EditText) findViewById(R.id.investment_amount);
//                EditText annualized_rate = (EditText) findViewById(R.id.annualized_rate);
//                EditText investment_days = (EditText) findViewById(R.id.investment_days);
//                investment_amount.setText("");
//                annualized_rate.setText("");
//                investment_days.setText("");
//            }
//        });
//
//        ImageButton menuCalculate = (ImageButton) findViewById(R.id.menuCalculate);
//        menuCalculate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("DDD", "clicked!!!");
////                basicDrawerLayout.closeDrawers();
//            }
//        });
//
//        ImageButton menuRecommend = (ImageButton) findViewById(R.id.menuRecommend);
//        menuRecommend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                basicDrawerLayout.closeDrawers();
//                Intent intent = new Intent();
//                intent.setClass(MyActivity.this, RecommendActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.about:
                String tmp = "about";
                tmp = tmp + "!!!";
                break;
            case R.id.logOut:
                AVUser.logOut();
                currentUser = AVUser.getCurrentUser();
                Intent intent = new Intent(MyActivity.this, MyActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if (currentUser != null) {
            inflater.inflate(R.menu.main_activity_user_actions, menu);
        } else {
            inflater.inflate(R.menu.main_activity_vistor_actions, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }


    private void initListView(final boolean isUser) {
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        ArrayList<HashMap<String, Object>> menuItemList = new ArrayList<HashMap<String, Object>>();

        int[] menuItemIconList;

        menuItemNameList = new String[]{};
        if (isUser) {
            menuItemNameList = getResources().getStringArray(R.array.userMenuItemName);
            menuItemIconList = new int[]{
                    R.drawable.calculate,
                    R.drawable.filter,
                    R.drawable.recommend,
                    R.drawable.star
            };
        } else {
            menuItemNameList = getResources().getStringArray(R.array.visitorMenuItemName);
            menuItemIconList = new int[]{
                    R.drawable.email,
                    R.drawable.qq,
                    R.drawable.weibo,
                    R.drawable.user
            };
        }

        for (int i = 0; i < menuItemNameList.length; i++) {
            HashMap<String, Object> menuItem = new HashMap<String, Object>();
            menuItem.put("icon", menuItemIconList[i]);
            menuItem.put("name", menuItemNameList[i]);
            menuItemList.add(menuItem);
        }


        SimpleAdapter menuSimpleAdapter = new SimpleAdapter(this, menuItemList, R.layout.menu_item, new String[]{"icon", "name"}, new int[]{R.id.menuItemIcon, R.id.menuItemName});
        mDrawerList.setAdapter(menuSimpleAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // Highlight the selected item, update the title, and close the
                // drawer
                mDrawerList.setItemChecked(position, true);
                setTitle(menuItemNameList[position]);
                Intent intent = new Intent();
                if (isUser == false) {
                    switch (position) {

                        case 0:
                            intent = new Intent(MyActivity.this, RegisterActivity.class);
                            break;
                        //TODO:QQ登录待完成
                        case 1:
                            intent = new Intent(MyActivity.this, LoginActivity.class);
                            break;
                        //TODO:WeiBo登录待完成
                        case 2:
                            intent = new Intent(MyActivity.this, LoginActivity.class);
                            break;
                        case 3:
                            intent = new Intent(MyActivity.this, LoginActivity.class);
                            break;

                    }
                } else {
                    switch (position) {

                        case 0:
                            intent = new Intent(MyActivity.this, MyActivity.class);
                            break;
                        //TODO:筛选器待完成
                        case 1:
                            intent = new Intent(MyActivity.this, FilterActivity.class);
                            break;
                        case 2:
                            intent = new Intent(MyActivity.this, RecommendActivity.class);
                            break;
                        //TODO:我的关注待完成
//                        case 3:
//                            intent = new Intent(MyActivity.this, );
//                            break;

                    }

                }

                startActivity(intent);

                basicDrawerLayout.closeDrawer(mDrawerList);
            }
        });
    }

}
