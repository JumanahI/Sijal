package org.example.sijalsystem.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.sijalsystem.Advice.N8nProcessingException;
import org.example.sijalsystem.Config.N8nConfig;
import org.example.sijalsystem.DTO.IN.CvDataDTO;
import org.example.sijalsystem.DTO.IN.N8nCVRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@RequiredArgsConstructor
@Service
@Slf4j
public class N8nIntegrationService {

    private final N8nConfig n8nConfig;
    private final RestTemplate restTemplate;

    public CvDataDTO parseCV(String cvText, Integer customerId) {
        try {
            log.info("Sending CV to n8n for AI parsing, customer: {}", customerId);

            // Prepare request
            N8nCVRequest request = new N8nCVRequest(cvText, customerId.toString());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<N8nCVRequest> entity = new HttpEntity<>(request, headers);

            // Call n8n webhook - استقبل String بدل N8nCVResponse
            ResponseEntity<String> response = restTemplate.exchange(
                    n8nConfig.getUrl(),
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                String responseBody = response.getBody();
                log.info("n8n response: {}", responseBody);

                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(responseBody);

                String summary = jsonNode.has("summary") ? jsonNode.get("summary").asText() : "Not found";
                String skills = jsonNode.has("skills") ? jsonNode.get("skills").asText() : "Not found";
                String education = jsonNode.has("education") ? jsonNode.get("education").asText() : "Not found";
                String experience = jsonNode.has("experience") ? jsonNode.get("experience").asText() : "Not found";

                log.info("Successfully parsed CV with AI for customer: {}", customerId);
                return new CvDataDTO(summary, skills, education, experience);
            }

            throw new N8nProcessingException("Invalid response from n8n");

        } catch (Exception e) {
            log.error("Error parsing n8n response: {}", e.getMessage());
            throw new N8nProcessingException("Failed to parse n8n response: " + e.getMessage());
        }
    }
}