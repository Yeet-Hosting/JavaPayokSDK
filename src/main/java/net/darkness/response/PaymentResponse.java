package net.darkness.response;

import java.util.Map;

public interface PaymentResponse {
    int getPaymentID();

    int getShopID();

    float getAmount();

    float getProfit();

    String getDesc();

    String getCurrency();

    float getCurrencyAmount();

    String getSign();

    default String getEmail() {
        return null;
    }

    default String getDate() {
        return null;
    }

    default String getMethod() {
        return null;
    }

    default Map<String, String> getCustom() {
        return null;
    }


    default int getUnderpayment() {
        return 0;
    }
}