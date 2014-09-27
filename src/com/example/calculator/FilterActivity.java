package com.example.calculator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

/**
 * Created by huangsiwei on 14-9-27.
 */
public class FilterActivity extends Activity {

    private Spinner investmentMoneySpinner;
    private Spinner investmentTimeSpinner;
    private Spinner preservationTypeSpinner;
    private Button issuerButton;
    private ArrayAdapter<CharSequence> investmentMoneyAdpter;
    private ArrayAdapter<CharSequence> investmentTimeAdpter;
    private ArrayAdapter<CharSequence> preservationTypeAdpter;
    private boolean[] selected;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter);

        investmentMoneySpinner = (Spinner) findViewById(R.id.investmentMoney);
        investmentTimeSpinner = (Spinner) findViewById(R.id.investmentTime);
        preservationTypeSpinner = (Spinner) findViewById(R.id.preservationType);
        issuerButton = (Button) findViewById(R.id.issuer);

        investmentMoneyAdpter = ArrayAdapter.createFromResource(this, R.array.investmentMoneyList, android.R.layout.simple_spinner_item);
        investmentTimeAdpter = ArrayAdapter.createFromResource(this, R.array.investmentTimeList, android.R.layout.simple_spinner_item);
        preservationTypeAdpter = ArrayAdapter.createFromResource(this, R.array.preservationTypeList, android.R.layout.simple_spinner_item);

        investmentMoneyAdpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        investmentMoneySpinner.setAdapter(investmentMoneyAdpter);
        investmentTimeAdpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        investmentTimeSpinner.setAdapter(investmentTimeAdpter);
        preservationTypeAdpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        preservationTypeSpinner.setAdapter(preservationTypeAdpter);

        investmentMoneySpinner.setVisibility(View.VISIBLE);
        investmentTimeSpinner.setVisibility(View.VISIBLE);
        preservationTypeSpinner.setVisibility(View.VISIBLE);

        issuerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int issuerNum = getResources().getStringArray(R.array.issuerList).length;
                selected = new boolean[issuerNum];
                AlertDialog.Builder builder = new AlertDialog.Builder(FilterActivity.this);
                builder.setTitle("请选择银行");
                builder.setMultiChoiceItems(R.array.issuerList, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        selected[i] = b;
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String selectedStr = "";
                        for (int j = 0; j < selected.length; j++) {
                            if (selected[j] == true) {
                                selectedStr = selectedStr + " " +
                                        getResources().getStringArray(R.array.issuerList)[j];
                            }
                        }

                        EditText editText = (EditText) findViewById(R.id.issuerSelected);
                        editText.setText(selectedStr);
                    }
                });
                builder.show();
            }
        });
    }
}