package org.example.sijalsystem.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.sijalsystem.API.APIException;
import org.example.sijalsystem.Model.CV;
import org.example.sijalsystem.Repository.CVRepository;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CVPdfGeneratorService {

    private final CVRepository cvRepository;
    private final TemplateEngine templateEngine;

    public byte[] generateCVPdf(Integer customerId) {
        log.info("Generating PDF for CV id: {}", customerId);

        CV cv = cvRepository.findCVByCustomerId(customerId);
        if (cv == null){
            throw new APIException("CV for customer id :" + customerId + " Not found");
        }

        Context context = prepareContext(cv);
        String html = templateEngine.process("cv-template", context);

        try {
            return convertHtmlToPdf(html);
        } catch (Exception e) {
            log.error("Error generating PDF: {}", e.getMessage(), e);
            throw new APIException("Failed to generate PDF: " + e.getMessage());
        }
    }

    private Context prepareContext(CV cv) {
        Context context = new Context();

        String customerName = "Professional";
        String customerInitial = "P";
        String phoneNumber = null;
        String email = null;
        String location = "Riyadh";

        if (cv.getCustomer() != null && cv.getCustomer().getUser() != null) {
            var user = cv.getCustomer().getUser();

            customerName = user.getName();
            customerInitial = customerName.substring(0, 1).toUpperCase();
            phoneNumber = user.getPhoneNumber();
            email = user.getEmail();
        }

        context.setVariable("customerName", customerName);
        context.setVariable("customerInitial", customerInitial);
        context.setVariable("phoneNumber", phoneNumber);
        context.setVariable("email", email);
        context.setVariable("location", location);

        // CV content
        context.setVariable("summary", escapeXml(cv.getSummary()));
        context.setVariable("educationLines", splitLines(escapeXml(cv.getEducation())));
        context.setVariable("experienceLines", splitLines(escapeXml(cv.getExperience())));
        context.setVariable("skills", parseSkills(escapeXml(cv.getSkills())));
        return context;
    }

    private List<String> parseSkills(String skills) {
        if (skills == null || skills.trim().isEmpty()) {
            return List.of();
        }

        return Arrays.stream(skills.split("[,\n]"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    private String formatHtmlText(String text) {
        if (text == null || text.trim().isEmpty()) {
            return null;
        }
        return text.replace("\n", "<br/>");
    }

    private byte[] convertHtmlToPdf(String html) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream);
            outputStream.close();

            log.info("PDF generated successfully");
            return outputStream.toByteArray();

        } catch (Exception e) {
            log.error("Error converting HTML to PDF: {}", e.getMessage());
            throw new APIException("Failed to convert HTML to PDF: " + e.getMessage());
        }
    }


    private String escapeXml(String text) {
        if (text == null) return null;
        return text
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }

    private List<String> splitLines(String text) {
        if (text == null || text.isBlank()) {
            return List.of();
        }
        return Arrays.stream(text.split("\\r?\\n"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }
}