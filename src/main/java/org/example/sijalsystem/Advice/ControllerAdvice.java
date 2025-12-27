package org.example.sijalsystem.Advice;
import jakarta.validation.ConstraintViolationException;
import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.example.sijalsystem.API.APIException;
import org.example.sijalsystem.API.APIResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import java.sql.SQLIntegrityConstraintViolationException;


@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = APIException.class)
    public ResponseEntity<?> APIException(APIException apiException) {
        String message = apiException.getMessage();
        return ResponseEntity.status(400).body(new APIResponse(message));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<?> ValidException(MethodArgumentNotValidException e){
        String error = e.getFieldError().getDefaultMessage();
        return ResponseEntity.status(400).body(new APIResponse(error));
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<?> HttpMessageNotReadable(){
        return ResponseEntity.status(400).body(new APIResponse("your input not readable"));
    }

    @ExceptionHandler(value = NoResourceFoundException.class)
    public ResponseEntity<?> urlError(){
        return ResponseEntity.status(404).body(new APIResponse("wrong Url"));
    }

    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)

    public ResponseEntity<?> SQLError(APIException apiException){
        return ResponseEntity.status(400).body(new APIResponse(apiException.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolation(DataIntegrityViolationException duplicateEntry) {
        return ResponseEntity.status(400).body(new APIResponse(duplicateEntry.getMostSpecificCause().getMessage()));
    }


    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<?> ConstraintViolationException(ConstraintViolationException e){
        String error = e.getConstraintViolations().iterator().next().getMessage();
        return ResponseEntity.status(400).body(new APIResponse(error));
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<?> DuplicateKeyException(DuplicateKeyException duplicateEntry) {
        return ResponseEntity.status(400).body(new APIResponse("the email or username is already taken please choose another"));
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<?> NullPointerException(NullPointerException e){
        return ResponseEntity.status(400).body(new APIResponse(e.getMessage()));
    }

    @ExceptionHandler(value = InvalidFileException.class)
    public ResponseEntity<?> handleInvalidFile(InvalidFileException ex) {
        return ResponseEntity.status(400).body(new APIResponse(ex.getMessage()));
    }

    @ExceptionHandler(value = CVProcessingException.class)
    public ResponseEntity<?> handleCVProcessing(CVProcessingException ex) {
        return ResponseEntity.status(500).body(new APIResponse(ex.getMessage()));
    }

    @ExceptionHandler(value = PDFParsingException.class)
    public ResponseEntity<?> handlePDFParsing(PDFParsingException ex) {
        return ResponseEntity.status(400).body(new APIResponse(ex.getMessage()));
    }

    @ExceptionHandler(value = CVNotFoundException.class)
    public ResponseEntity<?> handleCVNotFound(CVNotFoundException ex) {
        return ResponseEntity.status(404).body(new APIResponse(ex.getMessage()));
    }

    @ExceptionHandler(value = CustomerNotFoundException.class)
    public ResponseEntity<?> handleCustomerNotFound(CustomerNotFoundException ex) {
        return ResponseEntity.status(404).body(new APIResponse(ex.getMessage()));
    }

    @ExceptionHandler(value = N8nProcessingException.class)
    public ResponseEntity<?> handleN8nProcessing(N8nProcessingException ex) {
        return ResponseEntity.status(503).body(new APIResponse(ex.getMessage()));
    }

    @ExceptionHandler(value = CVAlreadyExistsException.class)
    public ResponseEntity<?> handleCVAlreadyExists(CVAlreadyExistsException ex) {
        return ResponseEntity.status(409).body(new APIResponse(ex.getMessage()));
    }

    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public ResponseEntity<?> handleMaxUploadSize(MaxUploadSizeExceededException ex) {
        return ResponseEntity.status(413).body(new APIResponse("File size exceeds maximum allowed size"));
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> TypeMismatchError(MethodArgumentTypeMismatchException mismatchError) {
        return ResponseEntity.status(400).body(new APIResponse("Wrong value type entered. Did you use a word in place of a number?"));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex) {
        return ResponseEntity.status(500).body(new APIResponse("An unexpected error occurred: " + ex.getMessage()));
    }
}
