package net.darkness.response;

import java.util.Map;

public interface PaymentResponse {
    int getPaymentID();
    void setPaymentID(int paymentID);

    int getShopID();
    void setShopID(int shopID);

    float getAmount();
    void setAmount(float amount);

    float getProfit();
    void setProfit(float profit);

    String getDesc();
    void setDesc(String desc);

    String getCurrency();
    void setCurrency(String currency);

    float getCurrencyAmount();
    void setCurrencyAmount(float currencyAmount);

    String getSign();
    void setSign(String sign);

    default String getEmail() {
        return null;
    }

    default void setEmail(String email) {
    }

    default String getDate() {
        return null;
    }

    default void setDate(String date) {
    }

    default String getMethod() {
        return null;
    }

    default void setMethod(String method) {
    }

    default Map<String, String> getCustom() {
        return null;
    }

    default void setCustom(Map<String, String> custom) {
    }

    default boolean getUnderpayment() {
        return false;
    }

    default void setUnderpayment(boolean underpayment) {
    }
}