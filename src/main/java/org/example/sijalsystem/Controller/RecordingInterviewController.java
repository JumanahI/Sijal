package org.example.sijalsystem.Controller;

import lombok.RequiredArgsConstructor;
import org.example.sijalsystem.Service.RecordingInterviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vapi")
public class RecordingInterviewController {

    private final RecordingInterviewService recordingInterviewService;
//extra23
    @PostMapping("/webhook")
    public ResponseEntity<Void> vapiWebhook(@RequestBody Map<String, Object> payload) {
        recordingInterviewService.handleWebhook(payload);
        return ResponseEntity.ok().build();
    }


}
