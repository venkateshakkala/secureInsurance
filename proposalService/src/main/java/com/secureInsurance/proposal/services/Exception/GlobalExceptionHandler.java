package com.secureInsurance.proposal.services.Exception;

import com.secureInsurance.proposal.services.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomerServicesException.class)
    public ResponseEntity<ErrorResponseDto> handlerCustomerServicesException(
            CustomerServicesException ex, WebRequest request) {
        ErrorResponseDto error = new ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.CONFLICT.toString(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> globalException(
            Exception ex, WebRequest request){
        ErrorResponseDto error = new ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                ex.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
