package net.darkness;

import lombok.*;
import net.darkness.request.*;
import net.darkness.response.*;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.util.UrlEncoded;
import spark.*;

import java.math.BigInteger;
import java.security.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Pattern;

@Setter
public class Payok {
    @Getter
    private static final String payokAPIUrl = "https://payok.io/pay";

    @Getter
    private static final Set<String> payokAPIAddresses = Set.of(
            "195.64.101.191",
            "194.124.49.173",
            "45.8.156.144",
            "5.180.194.179",
            "5.180.194.127",
            "2a0b:1580:5ad7:0dea:de47:10ae:ecbf:111a"
    );

    @Getter
    private static final Pattern customParameterPattern = Pattern.compile("custom\\[(.*?)]");

    @Getter
    @Setter
    private static String secretKey;

    /**
     * Handles a response after successful payment
     * @param path HTTP path to your handler
     * @param responseHandler A handler to handle the response
     */
    public static void handlePaymentResponse(String path, Consumer<PaymentResponse> responseHandler) {
        Spark.post(path, (request, response) -> {
            if (!payokAPIAddresses.contains(request.ip())) {
                response.status(403);
                return HttpStatus.getCode(403);
            }

            var map = new HashMap<String, String>();

            for (var entry : request.body().split("&")) {
                var parts = entry.split("=");
                map.put(UrlEncoded.decodeString(parts[0]), UrlEncoded.decodeString(parts[1]));
            }

            responseHandler.accept(SimplePaymentResponse.builder()
                    .amount(Float.parseFloat(map.get("amount")))
                    .profit(Float.parseFloat(map.get("profit")))
                    .paymentID(Integer.parseInt(map.get("payment_id")))
                    .shopID(Integer.parseInt(map.get("shop")))
                    .desc(map.get("desc"))
                    .currency(map.get("currency"))
                    .currencyAmount(Float.parseFloat(map.get("currency_amount")))
                    .sign(map.get("sign"))
                    .email(map.get("email"))
                    .date(map.get("date"))
                    .method(map.get("method"))
                    .custom(new HashMap<>() {{
                        map.forEach((key, value) -> {
                            var matcher = customParameterPattern.matcher(key);
                            if (matcher.find())
                                put(matcher.group(1), value);
                        });
                    }})
                    .underpayment(Integer.parseInt(map.getOrDefault("underpayment", "0")))
                    .build());

            return "";
        });
    }

    /**
     * Generates a payment link for Payok API
     * @param request A request with all payment information
     * @return The generated link
     */
    public static String generatePaymentLink(PaymentRequest request) {
        var builder = new StringBuilder(payokAPIUrl);

        builder.append("?amount=").append(request.getAmount());
        builder.append("&payment=").append(request.getPaymentID());
        builder.append("&shop=").append(request.getShopID());
        builder.append("&desc=").append(UrlEncoded.encodeString(request.getDesc()));

        if (request.getCurrency() != null)
            builder.append("&currency=").append(UrlEncoded.encodeString(request.getCurrency()));

        if (request.getEmail() != null)
            builder.append("&email=").append(UrlEncoded.encodeString(request.getEmail()));

        if (request.getSuccessUrl() != null)
            builder.append("&success_url=").append(UrlEncoded.encodeString(request.getSuccessUrl()));

        if (request.getMethod() != null)
            builder.append("&method=").append(UrlEncoded.encodeString(request.getMethod()));

        if (request.getLang() != null)
            builder.append("&lang=").append(UrlEncoded.encodeString(request.getLang()));

        if (request.getCustom() != null)
            request.getCustom().forEach((key, value) -> builder.append("&").append(UrlEncoded.encodeString(key)).append("=").append(UrlEncoded.encodeString(value)));

        return builder.append("&sign=").append(request.getSign()).toString();
    }

    @SneakyThrows(NoSuchAlgorithmException.class)
    public static String generateSign(int amount, int paymentID, int shopID, String currency, String desc) {
        return new BigInteger(1, MessageDigest.getInstance("MD5").digest(String.join("|",
                String.valueOf(amount),
                String.valueOf(paymentID),
                String.valueOf(shopID),
                currency,
                desc,
                Objects.requireNonNull(secretKey)
        ).getBytes())).toString(16);
    }
}