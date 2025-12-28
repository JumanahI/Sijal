package org.example.sijalsystem.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.sijalsystem.API.APIException;
import org.example.sijalsystem.Model.CV;
import org.example.sijalsystem.Repository.CVRepository;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SendMailService {

    private final JavaMailSender javaEmailSender;
    private final CVRepository cvRepository;
    private final CVPdfGeneratorService pdfGeneratorService;


    public void sendMessage(String email, String subject, String body) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);
        javaEmailSender.send(simpleMailMessage);
    }

    public void sendMessageWithAttachment(String email, String subject, String body,
                                          byte[] attachment, String attachmentName) {
        try {
            MimeMessage mimeMessage = javaEmailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(body, true);
            helper.addAttachment(attachmentName, new ByteArrayResource(attachment));

            javaEmailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("Failed to send email with attachment: {}", e.getMessage());
            throw new APIException("Failed to send email: " + e.getMessage());
        }
    }

    public void sendCVByEmail(Integer customerId, String recipientEmail, String message) {
        log.info("Sending CV for customer id: {} to email: {}", customerId, recipientEmail);

        CV cv = cvRepository.findCVByCustomerId(customerId);
        if (cv == null){
            throw new APIException("CV for customer id: " + customerId + " not found");
        }
        byte[] pdfBytes = pdfGeneratorService.generateCVPdf(customerId);

        if (pdfBytes == null || pdfBytes.length == 0) {
            throw new APIException("Generated CV PDF is empty");
        }
        String emailBody = buildEmailBody(cv, message);

        sendMessageWithAttachment(
                recipientEmail,
                "Generate CV with Sijal website", emailBody, pdfBytes, "Cv-From-Sijal.pdf"
        );

        log.info("CV sent successfully to: {}", recipientEmail);
    }

    private String buildEmailBody(CV cv, String customMessage) {
        StringBuilder body = new StringBuilder();

        body.append("<!DOCTYPE html>");
        body.append("<html>");
        body.append("<head>");
        body.append("<style>");
        body.append("body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }");
        body.append(".container { max-width: 600px; margin: 0 auto; padding: 20px; }");
        body.append(".header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 30px; text-align: center; border-radius: 10px 10px 0 0; }");
        body.append(".content { background: #f9f9f9; padding: 30px; border-radius: 0 0 10px 10px; }");
        body.append(".message { background: white; padding: 20px; border-left: 4px solid #667eea; margin: 20px 0; }");
        body.append(".footer { text-align: center; margin-top: 30px; color: #888; font-size: 12px; }");
        body.append("</style>");
        body.append("</head>");
        body.append("<body>");
        body.append("<div class='container'>");

        body.append("<div class='header'>");
        body.append("<h1>📄 CV - Professional Resume</h1>");
        body.append("</div>");

        body.append("<div class='content'>");

        if (customMessage != null && !customMessage.trim().isEmpty()) {
            body.append("<div class='message'>");
            body.append("<h3>Message:</h3>");
            body.append("<p>").append(customMessage).append("</p>");
            body.append("</div>");
        }

        body.append("<h3>CV Summary:</h3>");
        body.append("<p>").append(cv.getSummary() != null ? cv.getSummary() : "Professional CV attached").append("</p>");

        body.append("<p style='margin-top: 20px;'>");
        body.append("Please find the attached CV in PDF format.");
        body.append("</p>");

        body.append("</div>");

        body.append("<div class='footer'>");
        body.append("<p>© 2025 Sijal System</p>");
        body.append("</div>");

        body.append("</div>");
        body.append("</body>");
        body.append("</html>");

        return body.toString();
    }

}
