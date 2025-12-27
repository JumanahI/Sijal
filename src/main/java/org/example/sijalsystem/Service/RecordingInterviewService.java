package org.example.sijalsystem.Service;

import org.example.sijalsystem.Model.InterviewSession;
import org.example.sijalsystem.Model.RecordingInterview;
import org.example.sijalsystem.Repository.InterviewSessionRepository;
import org.example.sijalsystem.Repository.RecordingInterviewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RecordingInterviewService {

    private final RecordingInterviewRepository recordingRepo;
    private final InterviewSessionRepository sessionRepo;

    public RecordingInterviewService(RecordingInterviewRepository recordingRepo, InterviewSessionRepository sessionRepo) {
        this.recordingRepo = recordingRepo;
        this.sessionRepo = sessionRepo;
    }

    public void saveRecording(Integer sessionId, String recordingUrl) {
        InterviewSession session = sessionRepo.findById(sessionId).orElseThrow(() -> new RuntimeException("InterviewSession not found: " + sessionId));

        RecordingInterview rec = recordingRepo.findById(sessionId).orElse(new RecordingInterview());
        rec.setInterviewSession(session);
        rec.setRecordingUrl(recordingUrl);
        if (rec.getCreatedAt() == null) rec.setCreatedAt(LocalDateTime.now());

        recordingRepo.save(rec);
    }

    public void saveTranscript(Integer sessionId, String transcript) {
        RecordingInterview rec = recordingRepo.findById(sessionId).orElseThrow(() -> new RuntimeException("RecordingInterview not found for session: " + sessionId));

        rec.setTranscript(transcript);
        recordingRepo.save(rec);
    }
}