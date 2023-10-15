package net.darkness.response;

import lombok.*;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimplePaymentResponse implements PaymentResponse {
    private int paymentID;
    private int shopID;
    private float amount;
    private float profit;
    private String desc;
    private String currency;
    private float currencyAmount;
    private String sign;
    private String email;
    private String date;
    private String method;
    private Map<String, String> custom;
    private boolean underpayment;
}