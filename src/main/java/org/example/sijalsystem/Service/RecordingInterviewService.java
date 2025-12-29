package org.example.sijalsystem.Service;

import lombok.RequiredArgsConstructor;
import org.example.sijalsystem.API.APIException;
import org.example.sijalsystem.Model.InterviewSession;
import org.example.sijalsystem.Model.RecordingInterview;
import org.example.sijalsystem.Repository.InterviewSessionRepository;
import org.example.sijalsystem.Repository.RecordingInterviewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RecordingInterviewService {

    private final RecordingInterviewRepository recordingInterviewService;
    private final InterviewSessionRepository sessionRepo;
    private final InterviewAnalysisByAiService interviewAnalysisByAiService;

    public void handleWebhook(Map<String, Object> payload) {

        Map<String, Object> message = asMap(payload.get("message"));
        if (message == null) return;

        String type = asString(message.get("type"));
        if (!"end-of-call-report".equals(type)) {
            return;
        }


        Map<String, Object> artifact = asMap(message.get("artifact"));
        if (artifact == null) return;


        Integer sessionId = extractSessionIdFromArtifact(artifact);
        if (sessionId == null) {
            return;
        }


        String recordingUrl = asString(artifact.get("recordingUrl"));
        String transcript   = asString(artifact.get("transcript"));


        if (recordingUrl != null && !recordingUrl.isBlank()) {
            saveRecording(sessionId, recordingUrl);
        }

        if (transcript != null && !transcript.isBlank()) {
            saveTranscript(sessionId, transcript);
            interviewAnalysisByAiService.analyzeAndSave(sessionId, transcript);

        }


    }



    private Integer extractSessionIdFromArtifact(Map<String, Object> artifact) {
        List<Map<String, Object>> messages = asListOfMaps(artifact.get("messages"));
        if (messages == null) return null;

        for (Map<String, Object> m : messages) {
            String role = asString(m.get("role"));
            String msg  = asString(m.get("message"));

            if ("user".equals(role) && msg != null) {
                Integer id = extractDigitsAfterPrefix(msg, "User's Keypad Entry:");
                if (id != null) return id;
            }
        }
        return null;
    }

    private Integer extractDigitsAfterPrefix(String text, String prefix) {
        int idx = text.indexOf(prefix);
        if (idx == -1) return null;

        String rest = text.substring(idx + prefix.length()).trim();
        StringBuilder digits = new StringBuilder();

        for (int i = 0; i < rest.length(); i++) {
            char c = rest.charAt(i);
            if (Character.isDigit(c)) digits.append(c);
            else if (digits.length() > 0) break;
        }

        if (digits.length() == 0) return null;
        try {
            return Integer.parseInt(digits.toString());
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> asMap(Object o) {
        if (o instanceof Map<?, ?> m) return (Map<String, Object>) m;
        return null;
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> asListOfMaps(Object o) {
        if (o instanceof List<?> list) {
            for (Object item : list) {
                if (!(item instanceof Map<?, ?>)) return null;
            }
            return (List<Map<String, Object>>) list;
        }
        return null;
    }

    private String asString(Object o) {
        return o == null ? null : String.valueOf(o);
    }



    public void saveRecording(Integer sessionId, String recordingUrl) {
        InterviewSession session = sessionRepo.findInterviewSessionById(sessionId);
        if (session == null) {
            throw new APIException("InterviewSession not found");
        }

        RecordingInterview rec = recordingInterviewService.findById(sessionId).orElseGet(RecordingInterview::new);

        rec.setInterviewSession(session);
        rec.setRecordingUrl(recordingUrl);
        recordingInterviewService.save(rec);
    }

    public void saveTranscript(Integer sessionId, String transcript) {
        RecordingInterview rec = recordingInterviewService.findRecordingInterviewById(sessionId);
        if (rec==null){
        throw new RuntimeException("Recording not found");
        }
        rec.setTranscript(transcript);
        rec.setCreatedAt(LocalDateTime.now());
        recordingInterviewService.save(rec);
    }
}