package com.example.calculator;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        basicDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //TODO:增加是否登录的Flag
        boolean isUser = true;
        initListView(isUser);
        mDrawerToggle = new ActionBarDrawerToggle(this, basicDrawerLayout, R.drawable.ic_launcher, R.string.drawer_open, R.string.drawer_close) {
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
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }


    private void initListView(boolean isUser) {
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        ArrayList<HashMap<String, Object>> menuItemList = new ArrayList<HashMap<String, Object>>();

        int[] visitorMenuIconList = new int[]{

        };

        String[] visitorMenuItemNameList = new String[]{

        };

        int[] menuItemIconList = new int[]{};

        String[] menuItemNameList = new String[]{};
        if (isUser) {
            menuItemNameList = getResources().getStringArray(R.array.userMenuItemName);
            menuItemIconList = new int[]{
                    R.drawable.calculate,
                    R.drawable.filter,
                    R.drawable.recommend,
                    R.drawable.star
            };
        } else {

        }


        for (int i = 0; i < 4; i++) {
            HashMap<String, Object> menuItem = new HashMap<String, Object>();
            menuItem.put("icon", menuItemIconList[i]);
            menuItem.put("name", menuItemNameList[i]);
            menuItemList.add(menuItem);
        }


        SimpleAdapter menuSimpleAdapter = new SimpleAdapter(this, menuItemList, R.layout.menu_item, new String[]{"icon", "name"}, new int[]{R.id.menuItemIcon, R.id.menuItemName});
        mDrawerList.setAdapter(menuSimpleAdapter);

//        mPlanetTitles = getResources().getStringArray(R.array.city);

        // Set the adapter for the list view
//        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
//                R.layout.main, mPlanetTitles));
        // Set the list's click listener
//        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                // Highlight the selected item, update the title, and close the
//                // drawer
//                mDrawerList.setItemChecked(position, true);
//                setTitle(mPlanetTitles[position]);
//                basicDrawerLayout.closeDrawer(mDrawerList);
//            }
//        });
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
