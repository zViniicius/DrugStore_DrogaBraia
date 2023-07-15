package br.com.drogabraia.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;

@ControllerAdvice
public class ApiExceptionHandler {


    @ResponseBody
    @ExceptionHandler(GenericException.class)
    public ResponseEntity<MessageExceptionHandler> HandleGenericException(GenericException ex, WebRequest request) {
     MessageExceptionHandler errorResponse = new MessageExceptionHandler(
             new Date(), ex.getCode().value(), ex.getCode().getReasonPhrase(), ex.getMessage(), getRequestPath(request));
        return ResponseEntity.status(ex.getCode()).body(errorResponse);

    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResponseEntity<MessageExceptionHandler> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException ex, WebRequest request) {
        String message = "O método " + ex.getMethod() + " não é suportado para este endpoint";

        MessageExceptionHandler errorResponse = new MessageExceptionHandler(new Date(), HttpStatus.METHOD_NOT_ALLOWED.value(),
                HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase(), message, getRequestPath(request));

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<MessageExceptionHandler> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, WebRequest request) {
        String message = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
        MessageExceptionHandler errorResponse = new MessageExceptionHandler(new Date(), HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), message, getRequestPath(request));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public ResponseEntity<MessageExceptionHandler> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        String message = "O parâmetro '" + ex.getName() + "' deve ser do tipo '" + Objects.requireNonNull(ex.getRequiredType()).getSimpleName() + "'";

        MessageExceptionHandler errorResponse = new MessageExceptionHandler(new Date(), HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), message, getRequestPath(request));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public ResponseEntity<MessageExceptionHandler> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex, WebRequest request) {
        String message = "Ocorreu um erro de integridade de dados: " + ex.getMostSpecificCause().getMessage();
        MessageExceptionHandler errorResponse = new MessageExceptionHandler(new Date(), HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), message, getRequestPath(request));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<MessageExceptionHandler> handleException(Exception ex, WebRequest request) {
        MessageExceptionHandler errorResponse = new MessageExceptionHandler(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getMessage(), getRequestPath(request));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @Getter @Setter
    @AllArgsConstructor
    public static class GenericException extends RuntimeException {
        private final HttpStatus code;
        private final String message;
    }

    @Getter @Setter
    @AllArgsConstructor
    private static class MessageExceptionHandler {
        private Date timestamp;
        private int status;
        private String error;
        private String message;
        private String path;
    }

    private String getRequestPath(WebRequest request) {
        HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();
        return servletRequest.getRequestURI();
    }
}
