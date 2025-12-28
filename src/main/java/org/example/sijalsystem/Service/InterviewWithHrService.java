package org.example.sijalsystem.Service;

import lombok.RequiredArgsConstructor;
import org.example.sijalsystem.API.APIException;
import org.example.sijalsystem.Model.InterviewWithHR;
import org.example.sijalsystem.Model.RequestInterview;
import org.example.sijalsystem.Repository.InterviewWithHrRepository;
import org.example.sijalsystem.Repository.RequestInterviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InterviewWithHrService {

    private final InterviewWithHrRepository interviewWithHrRepository;
    private final RequestInterviewRepository requestInterviewRepository;

    public List<InterviewWithHR> getAllInterviewWithHr(){
        return interviewWithHrRepository.findAll();
    }

    public void addInterviewWithHr(Integer request_id,InterviewWithHR interviewWithHr){
        RequestInterview requestInterview = requestInterviewRepository.findRequestInterviewById(request_id);
        if(requestInterview == null ){
            throw new APIException("request interview not found");
        }
        interviewWithHr.setRequest(requestInterview);
        interviewWithHrRepository.save(interviewWithHr);
    }

    public void updateInterviewWithHr(Integer interviewWithHr_id, InterviewWithHR interviewWithHr){
        InterviewWithHR oldInterviewWithHr = interviewWithHrRepository.findInterviewWithHrById(interviewWithHr_id);

        if(oldInterviewWithHr == null){
            throw new APIException("Interview not found");
        }
        oldInterviewWithHr.setMeetingURL(interviewWithHr.getMeetingURL());
        interviewWithHrRepository.save(oldInterviewWithHr);
    }

    public void deleteInterviewWithHr(Integer interviewWithHr_id){
        InterviewWithHR interviewWithHr = interviewWithHrRepository.findInterviewWithHrById(interviewWithHr_id);

        if(interviewWithHr == null){
            throw new APIException("Interview not found");
        }
        interviewWithHrRepository.delete(interviewWithHr);
    }

}
