package org.example.sijalsystem.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class OpenAiService {

    @Value("${openai.api-key}")
    private String apiKey;

    // تقدر تغيّره من application.properties
    @Value("${openai.model:gpt-5}")
    private String model;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();


    public String ask(String prompt) {
        try {
            JsonNode resp = callResponsesApi(prompt);
            return extractOutputText(resp);
        } catch (Exception e) {
            throw new RuntimeException("OpenAI ask() failed: " + e.getMessage(), e);
        }
    }

//    public List<String> askForQuestionsList(String prompt) {
//        // نضمن فورمات ثابت: JSON Array فقط بدون شرح
//        String strictPrompt =
//                "Return ONLY a valid JSON array of interview questions (strings). " +
//                        "No markdown, no extra text.\n\n" +
//                        prompt;
//
//        String text = ask(strictPrompt);
//        if (text == null || text.isBlank()) return Collections.emptyList();
//
//        try {
//            // لو رجّع array مباشرة
//            if (text.trim().startsWith("[")) {
//                return mapper.readValue(text, new TypeReference<List<String>>() {});
//            }
//
//            // لو رجّع JSON object فيه field اسمه questions
//            JsonNode n = mapper.readTree(text);
//            JsonNode q = n.path("questions");
//            if (q.isArray()) {
//                return mapper.convertValue(q, new TypeReference<List<String>>() {});
//            }
//
//            return Collections.emptyList();
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to parse questions list from OpenAI response: " + e.getMessage()
//                    + "\nRaw text: " + text, e);
//        }
//    }

    // ===================== Internal helpers =====================

    private JsonNode callResponsesApi(String input) throws Exception {
        String url = "https://api.openai.com/v1/responses";

        var body = mapper.createObjectNode();
        body.put("model", model);
        body.put("input", input);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<String> req = new HttpEntity<>(mapper.writeValueAsString(body), headers);

        ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.POST, req, String.class);

        if (!res.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("OpenAI HTTP " + res.getStatusCode().value() + ": " + res.getBody());
        }

        return mapper.readTree(res.getBody());
    }

    private String extractOutputText(JsonNode responseJson) {
        JsonNode output = responseJson.path("output");
        if (!output.isArray()) return null;

        for (JsonNode item : output) {
            if (!"message".equals(item.path("type").asText())) continue;
            JsonNode contentArr = item.path("content");
            if (!contentArr.isArray()) continue;

            for (JsonNode c : contentArr) {
                if ("output_text".equals(c.path("type").asText())) {
                    String t = c.path("text").asText(null);
                    if (t != null && !t.isBlank()) return t;
                }
            }
        }
        return null;
    }
    public String askForJson(String prompt) {
        String strict = """
    Output ONLY valid JSON. No markdown. No extra text.
    """ + "\n\n" + prompt;

        String text = ask(strict);
        if (text == null) return null;
        return text.trim();
    }


}