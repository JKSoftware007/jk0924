package com.assessment.exceptions;

import com.assessment.responses.ErrorInfo;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({MissingRequiredFieldException.class, InvalidRequestException.class})
    public ResponseEntity<ErrorInfo> handleInvalidRequest(Exception ex, WebRequest request) {
        return new ResponseEntity<>(
                new ErrorInfo(ex.getMessage(), HttpStatus.BAD_REQUEST.value()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = ex.getMessage();
        if (ex.getMessage().contains("could not be parsed at index")) {
            message = "Invalid date format, must be MM/DD/YYYY";
        } else if(ex.getMessage().contains("Required request body is missing")){
            message = "No request body";
        }

        return new ResponseEntity<>(
                new ErrorInfo(message, HttpStatus.BAD_REQUEST.value()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ToolNotFoundException.class)
    public ResponseEntity<ErrorInfo> handleToolNotFound(Exception ex, WebRequest request) {
        return new ResponseEntity<>(
                new ErrorInfo(ex.getMessage(), HttpStatus.NOT_FOUND.value()), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}
