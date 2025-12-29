package org.example.sijalsystem;

import org.example.sijalsystem.Model.InterviewSession;
import org.example.sijalsystem.Model.InterviewWithHR;
import org.example.sijalsystem.Model.RequestInterview;
import org.example.sijalsystem.Repository.InterviewSessionRepository;
import org.example.sijalsystem.Repository.InterviewWithHrRepository;
import org.example.sijalsystem.Repository.RequestInterviewRepository;
import org.example.sijalsystem.Service.InterviewSessionService;
import org.example.sijalsystem.Service.InterviewWithHrService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @InjectMocks
    InterviewSessionService interviewSessionService;

    @InjectMocks
    InterviewWithHrService interviewWithHrService;

    @Mock
    InterviewSessionRepository interviewSessionRepository;

    @Mock
    InterviewWithHrRepository interviewWithHrRepository;

    @Mock
    RequestInterviewRepository requestInterviewRepository;

    Integer customerId;
    Integer sessionId;

    InterviewSession session1, session2;

    Integer requestId;
    RequestInterview requestInterview;

    Integer interviewWithHrId;
    InterviewWithHR oldInterviewWithHr;
    InterviewWithHR newInterviewWithHr;
    InterviewWithHR updateInterviewWithHr;

    List<InterviewSession> sessionList;
    List<InterviewWithHR> interviewWithHrList;

    @BeforeEach
    void setUp() {

        customerId = 10;
        sessionId = 100;

        session1 = new InterviewSession();
        session1.setId(100);
        session1.setStatus("CREATED");

        session2 = new InterviewSession();
        session2.setId(101);
        session2.setStatus("CREATED");

        sessionList = new ArrayList<>();
        sessionList.add(session1);
        sessionList.add(session2);

        requestId = 50;
        requestInterview = new RequestInterview();
        requestInterview.setId(requestId);

        interviewWithHrId = 200;

        oldInterviewWithHr = new InterviewWithHR();
        oldInterviewWithHr.setId(interviewWithHrId);
        oldInterviewWithHr.setMeetingURL("https://zoom.us/j/old");

        newInterviewWithHr = new InterviewWithHR();
        newInterviewWithHr.setMeetingURL("https://zoom.us/j/new-one");

        updateInterviewWithHr = new InterviewWithHR();
        updateInterviewWithHr.setMeetingURL("https://zoom.us/j/updated");

        interviewWithHrList = new ArrayList<>();
        interviewWithHrList.add(oldInterviewWithHr);
    }


    @Test
    public void getMySessionsTest() {
        when(interviewSessionRepository.findInterviewSessionsByCustomerId(customerId)).thenReturn(sessionList);

        List<InterviewSession> result = interviewSessionService.getMySessions(customerId);

        Assertions.assertEquals(2, result.size());
        verify(interviewSessionRepository, times(1)).findInterviewSessionsByCustomerId(customerId);
    }


    @Test
    public void getMySessionByIdTest() {
        when(interviewSessionRepository.existsByIdAndCustomer_Id(sessionId, customerId)).thenReturn(true);

        when(interviewSessionRepository.findInterviewSessionById(sessionId)).thenReturn(session1);

        InterviewSession result = interviewSessionService.getMySessionById(customerId, sessionId);

        Assertions.assertEquals(session1, result);
        verify(interviewSessionRepository, times(1)).existsByIdAndCustomer_Id(sessionId, customerId);
        verify(interviewSessionRepository, times(1)).findInterviewSessionById(sessionId);
    }


    @Test
    public void getAllInterviewWithHrTest() {
        when(interviewWithHrRepository.findAll()).thenReturn(interviewWithHrList);

        List<InterviewWithHR> result = interviewWithHrService.getAllInterviewWithHr();

        Assertions.assertEquals(1, result.size());
        verify(interviewWithHrRepository, times(1)).findAll();
    }


    @Test
    public void addInterviewWithHrTest() {
        when(requestInterviewRepository.findRequestInterviewById(requestId)).thenReturn(requestInterview);

        interviewWithHrService.addInterviewWithHr(requestId, newInterviewWithHr);

        Assertions.assertEquals(requestInterview, newInterviewWithHr.getRequest());
        verify(requestInterviewRepository, times(1)).findRequestInterviewById(requestId);
        verify(interviewWithHrRepository, times(1)).save(newInterviewWithHr);
    }

    @Test
    public void updateInterviewWithHrTest() {
        when(interviewWithHrRepository.findInterviewWithHrById(interviewWithHrId)).thenReturn(oldInterviewWithHr);

        interviewWithHrService.updateInterviewWithHr(interviewWithHrId, updateInterviewWithHr);

        Assertions.assertEquals("https://zoom.us/j/updated", oldInterviewWithHr.getMeetingURL());
        verify(interviewWithHrRepository, times(1)).findInterviewWithHrById(interviewWithHrId);
        verify(interviewWithHrRepository, times(1)).save(oldInterviewWithHr);
    }
}