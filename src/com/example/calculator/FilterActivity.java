package com.example.calculator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.avos.avoscloud.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by huangsiwei on 14-9-27.
 */
public class FilterActivity extends Activity {

    private Spinner investmentMoneySpinner;
    private Spinner investmentTimeSpinner;
    private Spinner profitTypeSpinner;
    private Spinner localCitySpinner;
    private Button issuerButton;
    private Button queryButton;
    private ArrayAdapter<CharSequence> investmentMoneyAdapter;
    private ArrayAdapter<CharSequence> investmentTimeAdapter;
    private ArrayAdapter<CharSequence> preservationTypeAdapter;
    private ArrayAdapter<CharSequence> localCityAdapter;
    private boolean[] selected;
    private SharedPreferences issuerPreferences;

    //查询参数
    private int maxInvestmentMoney;
    private int maxInvestmentTime;
    private String profitType;
    private String preservationType;
    private String localCity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AVOSCloud.initialize(this, "543eanv3de6r242jk51cwzln3dqe04nuj0b87n3z05nqqcgj", "mg0hbw40y1et96af4y9ppanu1etzn0y33aohiw4t5fk02emr");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter);

        investmentMoneySpinner = (Spinner) findViewById(R.id.investmentMoney);
        investmentTimeSpinner = (Spinner) findViewById(R.id.investmentTime);
        profitTypeSpinner = (Spinner) findViewById(R.id.profitType);
        localCitySpinner = (Spinner) findViewById(R.id.localCity);
        issuerButton = (Button) findViewById(R.id.issuer);
        queryButton = (Button) findViewById(R.id.query);
        issuerPreferences = getSharedPreferences("issuerChecked", MODE_PRIVATE);

        investmentMoneyAdapter = ArrayAdapter.createFromResource(this, R.array.investmentMoneyList, android.R.layout.simple_spinner_item);
        investmentTimeAdapter = ArrayAdapter.createFromResource(this, R.array.investmentTimeList, android.R.layout.simple_spinner_item);
        preservationTypeAdapter = ArrayAdapter.createFromResource(this, R.array.preservationTypeList, android.R.layout.simple_spinner_item);
        localCityAdapter = ArrayAdapter.createFromResource(this, R.array.cityList, android.R.layout.simple_spinner_item);

        investmentMoneyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        investmentMoneySpinner.setAdapter(investmentMoneyAdapter);
        investmentTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        investmentTimeSpinner.setAdapter(investmentTimeAdapter);
        preservationTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        profitTypeSpinner.setAdapter(preservationTypeAdapter);
        localCityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        localCitySpinner.setAdapter(localCityAdapter);

        investmentMoneySpinner.setVisibility(View.VISIBLE);
        investmentTimeSpinner.setVisibility(View.VISIBLE);
        profitTypeSpinner.setVisibility(View.VISIBLE);
        localCitySpinner.setVisibility(View.VISIBLE);

        issuerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] issuerList = getResources().getStringArray(R.array.issuerList);
                int issuerNum = issuerList.length;
                selected = new boolean[issuerNum];
                AlertDialog.Builder builder = new AlertDialog.Builder(FilterActivity.this);
                builder.setTitle("请选择银行");
                Set issuerSavedSet = issuerPreferences.getStringSet("issuer", new HashSet<String>());
                for (int i = 0; i < issuerNum; i++) {
                    if (issuerSavedSet.contains(issuerList[i])) {
                        selected[i] = true;
                    }
                }
                builder.setMultiChoiceItems(R.array.issuerList, selected, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        selected[i] = b;
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences.Editor editor = issuerPreferences.edit();
                        String issuerKey = "issuer";
                        Set<String> issuerSet = new HashSet();
                        for (int j = 0; j < selected.length; j++) {
                            if (selected[j] == true) {
                                issuerSet.add(getResources().getStringArray(R.array.issuerList)[j]);
                            }
                        }
                        editor.putStringSet(issuerKey, issuerSet);
                        editor.commit();
                    }
                });
                builder.show();
            }
        });

        investmentMoneySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        maxInvestmentMoney = 5;
                        break;
                    case 1:
                        maxInvestmentMoney = 10;
                        break;
                    case 2:
                        maxInvestmentMoney = 50;
                        break;
                    case 3:
                        maxInvestmentMoney = -1;
                        break;
                    default:
                        maxInvestmentMoney = 5;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        investmentTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        maxInvestmentTime = 93;
                        break;
                    case 1:
                        maxInvestmentTime = 183;
                        break;
                    case 2:
                        maxInvestmentTime = 365;
                        break;
                    case 3:
                        maxInvestmentTime = -1;
                        break;
                    default:
                        maxInvestmentTime = 93;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        profitTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        profitType = "固定收益";
                        preservationType = "保本";
                        break;
                    case 1:
                        profitType = "浮动收益";
                        preservationType = "保本";
                        break;
                    case 2:
                        profitType = "浮动收益";
                        preservationType = "非保本";
                        break;
                    default:
                        profitType = "固定收益";
                        preservationType = "保本";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        localCitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            String[] cityList = getResources().getStringArray(R.array.cityList);

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                localCity = cityList[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                localCity = cityList[0];
            }
        });
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(FilterActivity.this, FilterResultActivity.class);
                intent.putExtra("profitType", profitType);
                intent.putExtra("preservationType", preservationType);
                intent.putExtra("minAmountOfInvestment", maxInvestmentMoney);
                intent.putExtra("investmentTime", maxInvestmentTime);
                intent.putExtra("localCity", localCity);
                intent.putExtra("limit", 20);
                ArrayList<String> issuerArray = new ArrayList<String>(issuerPreferences.getStringSet("issuer", new HashSet<String>()));
                intent.putStringArrayListExtra("issuerArray", issuerArray);
                FilterActivity.this.startActivity(intent);
            }
        });
    }
}