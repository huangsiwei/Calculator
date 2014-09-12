package com.example.calculator;

import java.util.Date;

/**
 * Created by huangsiwei on 14-9-8.
 */
public class FinancialProduct {


    String productName;         //产品名称
    String issuer;              //发行银行
    int minAmountOfInvestment;  //最小投资金额
    int investmentTime;         //投资期限
    float expectedYield;        //预期收益率
    float expectedEarning;      //预期收益
    Date startDate;             //起始时间
    Date endDate;               //结束时间
    String riskRank;            //风险等级
    String preservationType;    //是否保本
    String profitType;          //是否浮动收益
}
