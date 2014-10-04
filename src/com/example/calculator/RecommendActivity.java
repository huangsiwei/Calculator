package com.example.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.avos.avoscloud.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by huangsiwei on 14-9-1.
 */
public class RecommendActivity extends Activity {

    private Spinner sortBySpinner;
    private ArrayAdapter<CharSequence> typeListAdapter;
    private String sortByType;


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        AVOSCloud.initialize(this, "543eanv3de6r242jk51cwzln3dqe04nuj0b87n3z05nqqcgj", "mg0hbw40y1et96af4y9ppanu1etzn0y33aohiw4t5fk02emr");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend);

        sortBySpinner = (Spinner) findViewById(R.id.sortBy);
        typeListAdapter = ArrayAdapter.createFromResource(this, R.array.sortType, android.R.layout.simple_spinner_item);
        typeListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortBySpinner.setAdapter(typeListAdapter);
        sortBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        sortByType = "minAmountOfInvestment"; //expectedYield
                        fetchRecommendFPList(sortByType);
                        break;
                    case 1:
                        sortByType = "investmentTime";
                        fetchRecommendFPList(sortByType);
                        break;
                    case 2:
                        sortByType = "expectedYield";
                        fetchRecommendFPList(sortByType);
                        break;
                    default:
                        sortByType = "expectedYield";
                        fetchRecommendFPList(sortByType);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sortBySpinner.setVisibility(View.VISIBLE);
        fetchRecommendFPList(sortByType);
    }

    public void fetchRecommendFPList(String sortByType) {
        AVQuery<AVObject> query = new AVQuery<AVObject>("FinancialProductInfo");
        query.whereEqualTo("preservationType", "保本");
        query.whereLessThanOrEqualTo("minAmountOfInvestment", 50);
        query.orderByDescending(sortByType);
        query.whereLessThanOrEqualTo("investmentTime", 360);
        query.setLimit(10);
        query.findInBackground(new FindCallback<AVObject>() {
            public void done(List<AVObject> avObjects, AVException e) {
                ArrayList<HashMap<String, String>> recommendResultList = new ArrayList<HashMap<String, String>>();
                if (e == null) {
                    for (int i = 0; i < avObjects.size(); i++) {
                        HashMap<String, String> dataMap = new HashMap<String, String>();
                        AVObject avObject = avObjects.get(i);
                        String fPName = avObject.getString("productName");
                        String fPIssuer = avObject.getString("issuer");
                        Number fPMinAmountOfInvestment = avObject.getNumber("minAmountOfInvestment");
                        Number fPInvestmentTime = avObject.getNumber("investmentTime");
                        Number fPExpectedYield = avObject.getNumber("expectedYield");
                        dataMap.put("fPName", fPName);
                        dataMap.put("fPIssuer", fPIssuer);
                        dataMap.put("fPMinAmountOfInvestment", String.valueOf(fPMinAmountOfInvestment));
                        dataMap.put("fPInvestmentTime", String.valueOf(fPInvestmentTime));
                        dataMap.put("fPExpectedYield", String.valueOf(fPExpectedYield));
                        recommendResultList.add(dataMap);
                    }
                    ListView recommendResultListView = (ListView) findViewById(R.id.recommendResultListView);
                    SimpleAdapter simpleAdapter = new SimpleAdapter(RecommendActivity.this, recommendResultList, R.layout.query_result,
                            new String[]{"fPName", "fPIssuer", "fPMinAmountOfInvestment", "fPInvestmentTime", "fPExpectedYield"},
                            new int[]{R.id.fPName, R.id.fPIssuer, R.id.fPMinAmountOfInvestment, R.id.fPInvestmentTime, R.id.fPExpectedYield});
                    recommendResultListView.setAdapter(simpleAdapter);
                    Log.d("成功", "查询到" + avObjects.size() + " 条符合条件的数据");
                    System.out.println("!!!!!");
                } else {
                    Log.d("失败", "查询错误: " + e.getMessage());
                }
            }
        });
    }

}