package org.example.sijalsystem.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.sijalsystem.DTO.OUT.PaymentResult;
import org.example.sijalsystem.Model.Card;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class PaymentService {

    @Value("${moyasar.api.key}")
    private String apiKey;

    private static final String MOYASAR_API_URL =
            "https://api.moyasar.com/v1/payments";

    private final RestTemplate restTemplate = new RestTemplate();
    public PaymentResult processPayment(Card card, Integer amount) {
        try {
            int amountInHalala = amount * 100;

            String requestBody = String.format(
                    "amount=%d&currency=%s&description=%s&callback_url=%s&" +
                            "source[type]=card&source[name]=%s&source[number]=%s&" +
                            "source[cvc]=%s&source[month]=%s&source[year]=%s",
                    amountInHalala,
                    "SAR",
                    "Subscription payment",
                    "http://localhost:8080/api/v1/payments/callback",
                    card.getName(),
                    card.getNumber(),
                    card.getCvc(),
                    card.getMonth(),
                    card.getYear()
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setBasicAuth(apiKey, "");

            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    MOYASAR_API_URL,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(response.getBody());

            PaymentResult result = new PaymentResult();
            result.setSuccess(response.getStatusCode().is2xxSuccessful()); // true إذا 2xx
            result.setMessage("Payment initiated successfully");
            result.setPaymentId(jsonNode.get("id").asText());
            result.setTransactionUrl(jsonNode.get("source").get("transaction_url").asText());

            return result;

        } catch (Exception e) {
            PaymentResult errorResult = new PaymentResult();
            errorResult.setSuccess(false);
            errorResult.setMessage("Payment processing failed: " + e.getMessage());
            errorResult.setPaymentId(null);
            errorResult.setTransactionUrl(null);

            return errorResult;
        }
    }

    public String getPaymentStatus(String paymentId) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(apiKey, "");
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    MOYASAR_API_URL + "/" + paymentId,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            return response.getBody();
        } catch (Exception e) {
            return "Failed to get payment status: " + e.getMessage();
        }
    }

}
