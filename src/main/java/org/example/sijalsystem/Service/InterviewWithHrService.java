package org.example.sijalsystem.Service;

import lombok.RequiredArgsConstructor;
import org.example.sijalsystem.API.APIException;
import org.example.sijalsystem.Model.HR;
import org.example.sijalsystem.Model.InterviewWithHR;
import org.example.sijalsystem.Model.RequestInterview;
import org.example.sijalsystem.Repository.HrRepository;
import org.example.sijalsystem.Repository.InterviewWithHrRepository;
import org.example.sijalsystem.Repository.RequestInterviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class InterviewWithHrService {

    private final InterviewWithHrRepository interviewWithHrRepository;
    private final RequestInterviewRepository requestInterviewRepository;
    private final HrRepository hrRepository;
    private final SendMailService sendMailService;

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

    public void startInterview(Integer hr_id, Integer interview_id){
        HR hr = hrRepository.findHRById(hr_id);
        InterviewWithHR interviewWithHR = interviewWithHrRepository.findInterviewWithHrById(interview_id);

        if(hr == null || interviewWithHR == null){
            throw new APIException("Hr or Interview not found");
        }
        if(!interviewWithHR.getRequest().getHr().getId().equals(hr_id)){
            throw new APIException("This hr not authorized to start this interview");
        }
        interviewWithHR.setStatus("RUNNING");
        interviewWithHrRepository.save(interviewWithHR);

        String subjectReminder = "⏰ تذكير: اجتماعك الآن – المختص بانتظارك";
        String bodyReminder =
                "مرحبًا " + interviewWithHR.getRequest().getCustomer().getUser().getName() + "،\n\n" +
                        "نود تذكيركم بأن موعد اجتماعكم قد حان أو سيبدأ خلال دقائق قليلة.\n\n" +
                        "🗓 موعد الاجتماع:\n" +
                        interviewWithHR.getRequest().getStartTime() + "\n\n" +
                        "👤 المختص بانتظاركم الآن للانضمام إلى الاجتماع.\n\n" +
                        "يرجى الدخول إلى الاجتماع في أقرب وقت ممكن حتى نتمكن من البدء.\n\n" +
                        "في حال واجهتم أي مشكلة تقنية، يمكنكم التواصل معنا عبر النظام.\n\n" +
                        "نتمنى لكم اجتماعًا مثمرًا،\n" +
                        "مع خالص التحية،\n" ;
        sendMailService.sendMessage(interviewWithHR.getRequest().getCustomer().getUser().getEmail(), subjectReminder, bodyReminder);
    }

    public void endInterview(Integer hr_id, Integer interview_id){
        HR hr = hrRepository.findHRById(hr_id);
        InterviewWithHR interviewWithHR = interviewWithHrRepository.findInterviewWithHrById(interview_id);

        if(hr == null || interviewWithHR == null){
            throw new APIException("Hr or Interview not found");
        }
        if(!interviewWithHR.getRequest().getHr().getId().equals(hr_id)){
            throw new APIException("This hr not authorized to end this interview");
        }
        interviewWithHR.setStatus("COMPLETE");
        interviewWithHrRepository.save(interviewWithHR);
    }

    public void cancelInterview(Integer hr_id, Integer interview_id){
        HR hr = hrRepository.findHRById(hr_id);
        InterviewWithHR interviewWithHR = interviewWithHrRepository.findInterviewWithHrById(interview_id);

        if(hr == null || interviewWithHR == null){
            throw new APIException("Hr or Interview not found");
        }
        if(!interviewWithHR.getRequest().getHr().getId().equals(hr_id)){
            throw new APIException("This hr not authorized to cancel this interview");
        }
        interviewWithHR.setStatus("CANCEL");
        interviewWithHrRepository.save(interviewWithHR);

        String subjectCancel = "تم إلغاء المقابلة بسبب عدم الحضور ❌";
        String bodyCancel =
                "مرحبًا " + interviewWithHR.getRequest().getCustomer().getUser().getName() + "،\n\n" +
                        "نود إشعاركم بأنه تم إلغاء المقابلة المجدولة مع المختص، وذلك بسبب عدم حضوركم في الموعد المحدد.\n\n" +
                        "🗓 موعد المقابلة الذي تم إلغاؤه:\n" +
                        interviewWithHR.getRequest().getStartTime() + "\n\n" +
                        "في حال رغبتم بحجز موعد جديد، يمكنكم تقديم طلب مقابلة جديد عبر النظام.\n\n" +
                        "نود التأكيد على أهمية الالتزام بالمواعيد المحددة، حيث يكون المختص بانتظاركم خلال وقت الاجتماع.\n\n" +
                        "شاكرين لكم تفهمكم،\n" +
                        "مع خالص التحية،\n" ;

        sendMailService.sendMessage(interviewWithHR.getRequest().getCustomer().getUser().getEmail(), subjectCancel, bodyCancel);

    }


}
