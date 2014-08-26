package com.example.calculator;

/**
 * Created by huangsiwei on 14-8-18.
 */
public class InterestRateCal {
    float countEarning(float investment_amount, float annualized_rate, int investment_days, int base_days) {
        //预期期末收益=投资本金*预期年化收益率/基本天数*实际存续天数
        float earnedMoney;
        earnedMoney = investment_amount * annualized_rate / base_days * investment_days;
        return earnedMoney;
    }
}
