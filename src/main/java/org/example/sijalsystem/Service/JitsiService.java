package org.example.sijalsystem.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JitsiService {

    @Value("${jitsi.base-url}")
    private String baseUrl;

    @Value("${jitsi.room-prefix}")
    private String roomPrefix;

    public String createRoomLink(String sessionId) {
        String safe = sessionId.replaceAll("[^a-zA-Z0-9_-]", "");
        return baseUrl + "/" + roomPrefix + safe;
    }

}
