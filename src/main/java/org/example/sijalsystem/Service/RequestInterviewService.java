package org.example.sijalsystem.Service;

import lombok.RequiredArgsConstructor;
import org.example.sijalsystem.API.APIException;
import org.example.sijalsystem.DTO.IN.CvDataDTO;
import org.example.sijalsystem.Model.*;
import org.example.sijalsystem.Repository.CustomerRepository;
import org.example.sijalsystem.Repository.HrRepository;
import org.example.sijalsystem.Repository.InterviewWithHrRepository;
import org.example.sijalsystem.Repository.RequestInterviewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestInterviewService {

    private final RequestInterviewRepository requestInterviewRepository;
    private final HrRepository hrRepository;
    private final CustomerRepository customerRepository;
    private final SendMailService sendMailService;
    private final JitsiService jitsiService;
    private final InterviewWithHrRepository interviewWithHrRepository;

    public List<RequestInterview> getRequestInterview() {
        return requestInterviewRepository.findAll();
    }

    public void sendRequestInterview(Integer customer_id, Integer hr_id, RequestInterview requestInterview) {
        HR hr = hrRepository.findHRById(hr_id);
        Customer customer = customerRepository.findCustomerById(customer_id);

        if (hr == null || customer == null) {
            throw new APIException("HR or Customer not found");
        }
        if(customer.getCv() == null) {
            throw new APIException("Please enter your cv first");
        }
        if (!hr.getStatus().equalsIgnoreCase("active")){
            throw new APIException("The HR account is not active");
        }


        Subscription lastSubscription = customer.getSubscriptionSet().stream()
                .max(Comparator.comparing(Subscription::getId))
                .orElse(null);

        if (lastSubscription != null && lastSubscription.getEndDate().isBefore(LocalDate.now())) {
            throw new APIException("Subscription is expired");
        }
        boolean hasPreviousRequest = !customer.getRequestInterviewSet().isEmpty();
        boolean hasActiveSubscription = customer.getSubscriptionSet().stream()
                .anyMatch(s -> s.getEndDate().isAfter(LocalDate.now()));

        // أول طلب → يسمح
        // طلب سابق → يسمح فقط إذا يوجد اشتراك جاري
        if (hasPreviousRequest && !hasActiveSubscription) {
            throw new APIException("Cannot send request: no active subscription for previous request");
        }

        requestInterview.setStatus("PENDING");
        requestInterview.setHr(hr);
        requestInterview.setCustomer(customer);
        requestInterviewRepository.save(requestInterview);
        CvDataDTO cv = new CvDataDTO();
        cv.setSummary(customer.getCv().getSummary());
        cv.setEducation( customer.getCv().getEducation());
        cv.setExperience(customer.getCv().getExperience());
        cv.setSkills(customer.getCv().getSkills());
        // إرسال الإيميل للـ HR
        sendMailService.sendMessage(
                hr.getUser().getEmail(),
                "طلب مقابلة من " + customer.getUser().getName() + " 📩",
                "مرحبًا " + hr.getUser().getName() + ",\n\n" +
                        "لقد قام " + customer.getUser().getName() + " بطلب مقابلة معكم.\n\n" +
                        "رسالة العميل:\n" +
                        "\"" + requestInterview.getMessage() + "\"\n\n" +
                        "السيره الذاتيه للعميل:\n" +
                        "Summary:\n" +
                        "\"" + customer.getCv().getSummary() + "\"\n\n" +
                        "Education:\n" +
                        "\"" + customer.getCv().getEducation() + "\"\n\n" +
                        "Experience:\n" +
                        "\"" + customer.getCv().getExperience() + "\"\n\n" +
                        "Skills:\n" +
                        "\"" + customer.getCv().getSkills() + "\"\n\n" +
                        "موعد المقابلة المقترح: " + requestInterview.getStartTime() + "\n\n" +
                        "مع تحياتنا،\n" +
                        "نظام إدارة المقابلات"
        );
    }



        public void updateRequestInterview(Integer customer_id,Integer requestInterview_id, RequestInterview requestInterview) {
            RequestInterview oldRequestInterview = requestInterviewRepository.findRequestInterviewById(requestInterview_id);
            Customer customer = customerRepository.findCustomerById(customer_id);

            if (oldRequestInterview == null || customer == null) {
                throw new APIException("Request interview or customer not found");
            }
            if(!requestInterview.getCustomer().getId().equals(customer.getId())){
                throw new APIException("Customer not authorized to delete this request");
            }
            oldRequestInterview.setMessage(requestInterview.getMessage());
            oldRequestInterview.setStartTime(requestInterview.getStartTime());
            requestInterviewRepository.save(oldRequestInterview);
        }

    public void deleteRequestInterview(Integer customer_id ,Integer requestInterview_id) {
        RequestInterview requestInterview = requestInterviewRepository.findRequestInterviewById(requestInterview_id);
        Customer customer = customerRepository.findCustomerById(customer_id);
        if (requestInterview == null || customer == null) {
            throw new APIException("Request interview or customer not found");
        }
        if(requestInterview.getStatus().equalsIgnoreCase("APPROVE")){
            throw new APIException("You can't delete approved interview request");
        }
        if(!requestInterview.getCustomer().getId().equals(customer.getId())){
            throw new APIException("Customer not authorized to delete this request");
        }

        requestInterviewRepository.delete(requestInterview);
    }

    public void approveRequest(Integer hr_id, Integer request_id) {
        HR hr = hrRepository.findHRById(hr_id);
        RequestInterview requestInterview = requestInterviewRepository.findRequestInterviewById(request_id);
        if (hr == null || requestInterview == null) {
            throw new APIException("HR or request interview not found");
        }
        if (requestInterview.getStatus().equalsIgnoreCase("APPROVE")) {
            throw new APIException("Request interview already approved");
        }
        if (!hr.getRequestInterviewSet().contains(requestInterview)) {
            throw new APIException("HR not authorized to approve request");
        }
        if (!hr.getStatus().equalsIgnoreCase("active")){
            throw new APIException("The HR account is not active");
        }
        requestInterview.setStatus("APPROVE");
        requestInterviewRepository.save(requestInterview);

        String requestId = String.valueOf(requestInterview.getId());
        String meetingLink = jitsiService.createRoomLink(requestId);

        InterviewWithHR interviewWithHR = new InterviewWithHR();
        interviewWithHR.setStatus("UPCOMING");
        interviewWithHR.setMeetingURL(meetingLink);
        interviewWithHR.setRequest(requestInterview);
        interviewWithHrRepository.save(interviewWithHR);

        // Customer Email
        String subjectOne = "تم قبول طلب المقابلة – نتطلع للقاءك 🎉";
        String bodyOne =
                "مرحبًا " + requestInterview.getCustomer().getUser().getName() + "،\n\n" +
                        "يسعدنا إعلامك بأنه تم قبول طلبك لإجراء المقابلة، ونتطلع للقائك قريبًا.\n\n" +
                        "🗓 موعد المقابلة:\n" +
                        requestInterview.getStartTime() + "\n\n" +
                        "🔗 رابط الاجتماع:\n" +
                        meetingLink + "\n\n" +
                        "يرجى التأكد من توفر اتصال جيد بالإنترنت والانضمام قبل الموعد بدقائق.\n" +
                        "في حال تعذّر عليك الحضور أو رغبت بتعديل الموعد، لا تتردد بالتواصل معنا.\n\n" +
                        "نتمنى لك التوفيق 🌟\n\n" +
                        "مع أطيب التحيات،\n" +
                        hr.getUser().getName() + "\n";
        sendMailService.sendMessage(requestInterview.getCustomer().getUser().getEmail(), subjectOne, bodyOne);

        //HR Email
        String subjectTwo = "تم تأكيد قبول الاجتماع بنجاح ✅";
        String bodyTwo =
                "مرحبًا " + hr.getUser().getName() + "،\n\n" +
                        "نود إشعاركم بأنه تم قبول طلب الاجتماع بنجاح، وتم تأكيد الموعد المحدد مع العميل.\n\n" +
                        "🗓 موعد الاجتماع:\n" +
                        requestInterview.getStartTime() + "\n\n" +
                        " ID الاجتماع :\n" +
                        interviewWithHR.getId() +
                        "🔗 رابط الاجتماع:\n" +
                        meetingLink + "\n\n" +
                        "يرجى التأكد من الانضمام إلى الاجتماع في الوقت المحدد.\n" +
                        "في حال الرغبة بتعديل الموعد أو وجود أي ملاحظات إضافية، يمكنكم إدارتها عبر النظام.\n\n" +
                        "مع خالص الشكر والتقدير،\n";

        sendMailService.sendMessage(hr.getUser().getEmail(), subjectTwo, bodyTwo);
    }


    public void rejectRequest(Integer hr_id, Integer request_id) {
        HR hr = hrRepository.findHRById(hr_id);
        RequestInterview requestInterview = requestInterviewRepository.findRequestInterviewById(request_id);

        if (hr == null || requestInterview == null) {
            throw new APIException("HR or request interview not found");
        }

        if (requestInterview.getStatus().equalsIgnoreCase("REJECTED")) {
            throw new APIException("Request interview already rejected");
        }

        if (!hr.getRequestInterviewSet().contains(requestInterview)) {
            throw new APIException("HR not authorized to reject request");
        }

        requestInterview.setStatus("REJECTED");
        requestInterviewRepository.save(requestInterview);

        //  Customer Email
        String subjectOne = "بخصوص طلب المقابلة الخاص بك " + " 📩";
        String bodyOne =
                "مرحبًا " + requestInterview.getCustomer().getUser().getName() + "،\n\n" +
                        "نشكرك على اهتمامك، ونود إفادتك بأنه بعد مراجعة طلب المقابلة، تعذّر قبول الطلب في الوقت الحالي.\n\n" +
                        "نقدّر اهتمامك ونتمنى لك التوفيق في الفرص القادمة.\n\n" +
                        "مع أطيب التحيات،\n" +
                        hr.getUser().getName() + "\n";

        sendMailService.sendMessage(requestInterview.getCustomer().getUser().getEmail(), subjectOne, bodyOne);

        //  HR Email
        String subjectTwo = " تم رفض طلب المقابلة " + " 📩";
        String bodyTwo =
                "مرحبًا " + hr.getUser().getName() + "،\n\n" +
                        "نود إشعاركم بأنه تم رفض طلب المقابلة بنجاح.\n\n" +
                        "🗓 موعد المقابلة المقترح:\n" +
                        requestInterview.getStartTime() + "\n\n" +
                        "يمكنكم مراجعة الطلب أو إضافة ملاحظات داخل النظام عند الحاجة.\n\n" +
                        "مع خالص الشكر والتقدير،\n";

        sendMailService.sendMessage(hr.getUser().getEmail(), subjectTwo, bodyTwo);
    }

}
