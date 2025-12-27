package org.example.sijalsystem.Service;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.example.sijalsystem.Advice.PDFParsingException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
public class PDFParserService {

    public String extractTextFromPDF(MultipartFile file) throws IOException {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            log.info("Successfully extracted text from PDF, length: {}", text.length());
            return text;
        } catch (IOException e) {
            log.error("Error extracting text from PDF: {}", e.getMessage());
            throw new PDFParsingException("Failed to parse PDF file");
        }
    }
}