package com.example.calculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.avos.avoscloud.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by huangsiwei on 14-10-1.
 */
public class FilterResultActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AVOSCloud.initialize(this, "543eanv3de6r242jk51cwzln3dqe04nuj0b87n3z05nqqcgj", "mg0hbw40y1et96af4y9ppanu1etzn0y33aohiw4t5fk02emr");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_result);

        Intent intent = getIntent();
        String profitType = intent.getStringExtra("profitType");
        String preservationType = intent.getStringExtra("preservationType");
        String localCity = intent.getStringExtra("localCity");
        List issuerList = intent.getStringArrayListExtra("issuerArray");
        int maxInvestmentMoney = intent.getIntExtra("minAmountOfInvestment", 0);
        int maxInvestmentTime = intent.getIntExtra("investmentTime", 0);
        fetchFilterResults(profitType, preservationType, localCity, issuerList, maxInvestmentMoney, maxInvestmentTime);

    }

    public void fetchFilterResults(String profitType, String preservationType, String localCity, List issuerList, int maxInvestmentMoney, int maxInvestmentTime) {
        AVQuery<AVObject> avQuery = new AVQuery<AVObject>("FinancialProductInfo");
        avQuery.whereEqualTo("profitType", profitType);
        avQuery.whereEqualTo("city", localCity);
        avQuery.whereEqualTo("preservationType", preservationType);
        if (issuerList != null){
            avQuery.whereContainedIn("issuer", issuerList);
        }
        if (maxInvestmentMoney > 0) {
            avQuery.whereLessThanOrEqualTo("minAmountOfInvestment", maxInvestmentMoney);
        }
        if (maxInvestmentTime > 0) {
            avQuery.whereLessThanOrEqualTo("investmentTime", maxInvestmentTime);
        }
        avQuery.setLimit(20);
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> avObjects, AVException e) {
                ArrayList<HashMap<String, String>> filterResultList = new ArrayList<HashMap<String, String>>();
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
                        filterResultList.add(dataMap);
                    }
                    ListView filterResultListView = (ListView) findViewById(R.id.filterResultListView);
                    SimpleAdapter simpleAdapter = new SimpleAdapter(FilterResultActivity.this, filterResultList, R.layout.query_result, new String[]{"fPName", "fPIssuer", "fPMinAmountOfInvestment", "fPInvestmentTime", "fPExpectedYield"},
                            new int[]{R.id.fPName, R.id.fPIssuer, R.id.fPMinAmountOfInvestment, R.id.fPInvestmentTime, R.id.fPExpectedYield});
                    filterResultListView.setAdapter(simpleAdapter);
                    Log.d("成功", "查询到" + avObjects.size() + " 条符合条件的数据");
                } else {
                    Log.d("失败", "查询错误: " + e.getMessage());
                }
            }
        });
    }
}