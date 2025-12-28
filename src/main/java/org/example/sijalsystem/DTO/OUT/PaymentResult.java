package org.example.sijalsystem.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResult {

    private boolean success;
    private String message;
    private String paymentId;
    private String transactionUrl;

}
