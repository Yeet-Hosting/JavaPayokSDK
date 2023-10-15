package net.darkness.request;

import lombok.*;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimplePaymentRequest implements PaymentRequest {
    private int amount;
    private int paymentID;
    private int shopID;
    private String desc;
    private String currency;
    private String email;
    private String successUrl;
    private String method;
    private String lang;
    private Map<String, String> custom;
}