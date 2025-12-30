package org.example.sijalsystem.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.sijalsystem.Model.CV;
import org.example.sijalsystem.Model.InterviewAnalysisByHR;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class OpenAiService {

    @Value("${openai.api-key:}")
    private String apiKey;


    @Value("${openai.model:}")
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

    private String safe(String text) {
        if (text == null) return "";
        return text
                .replace("&", "and")
                .replace("<", "")
                .replace(">", "")
                .replace("\"", "'");
    }


    public String cvImprovementSuggestions(CV cv) {

        String prompt = """
                ROLE:
                You are a senior HR professional and CV reviewer.
                
                TASK:
                Review the following CV data and provide improvement suggestions only.
                
                RULES:
                - Do NOT rewrite the CV
                - Do NOT invent information
                - Only suggest improvements
                - Be clear and professional
                - Return output as JSON
                
                INPUT CV:
                Summary:
                "%s"
                
                Skills:
                "%s"
                
                Education:
                "%s"
                
                Experience:
                "%s"
                
                OUTPUT:
                Return ONLY valid JSON in the following exact format and nothing else:
                
                {
                "summarySuggestions": ["..."],
                "skillsSuggestions": ["..."],
                "experienceSuggestions": ["..."],
                "generalTips": ["..."]
                }
                
                
                IMPORTANT:
                - Do not include explanations
                - Do not include markdown
                - Do not include comments
                - Do not include text before or after the JSON
                
                
                """.formatted(
                safe(cv.getSummary()),
                safe(cv.getSkills()),
                safe(cv.getEducation()),
                safe(cv.getExperience())
        );

        return ask(prompt);
    }


    public String interviewDevelopmentPlanForCustomer(List<InterviewAnalysisByHR> analyses) {

        StringBuilder input = new StringBuilder();

        for (InterviewAnalysisByHR analysis : analyses) {
            input.append("""
                    Strengths:
                    "%s"
                    
                    Weaknesses:
                    "%s"
                    
                    Final Score:
                    %d
                    
                    --------------------
                    """.formatted(
                    safe(analysis.getStrengths()),
                    safe(analysis.getWeaknesses()),
                    analysis.getFinalScore()
            ));
        }


        String prompt = """
                ROLE:
                You are a senior HR career advisor.
                
                TASK:
                Analyze interview feedback for a job candidate and create a personalized development plan.
                
                RULES:
                - Base suggestions ONLY on the provided interview analysis
                - Do NOT repeat the feedback text
                - Do NOT invent skills or experience
                - Focus on improvement and career readiness
                - Be professional and concise
                - Return EXACTLY valid JSON
                
                INPUT INTERVIEW FEEDBACK:
                %s
                
                OUTPUT FORMAT:
                Return ONLY JSON with the exact structure below:
                
                {
                  "strengthsEnhancement": ["..."],
                  "weaknessesImprovement": ["..."],
                  "skillRecommendations": ["..."],
                  "generalCareerTips": ["..."]
                }
                
                IMPORTANT:
                - Do NOT add any extra text, markdown, or comments
                - Do NOT wrap JSON in code blocks
                """.formatted(input.toString());

        return ask(prompt);
    }



}



