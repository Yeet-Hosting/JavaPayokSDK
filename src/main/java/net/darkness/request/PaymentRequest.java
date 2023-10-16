package net.darkness.request;

import net.darkness.Payok;

import java.util.*;

public interface PaymentRequest {
    int getAmount();

    int getPaymentID();

    default String getDesc() {
        return "No Description Provided";
    }

    default String getCurrency() {
        return "RUB";
    }

    default String getSign() {
        return Payok.generateSign(getAmount(), getPaymentID(), getCurrency(), getDesc());
    }

    default String getEmail() {
        return null;
    }

    default String getSuccessUrl() {
        return null;
    }

    default String getMethod() {
        return null;
    }

    default String getLang() {
        return null;
    }

    default Map<String, String> getCustom() {
        return null;
    }
}