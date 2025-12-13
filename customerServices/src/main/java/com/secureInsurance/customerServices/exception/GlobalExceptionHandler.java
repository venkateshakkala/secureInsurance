package com.secureInsurance.customerServices.exception;

import com.secureInsurance.customerServices.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerAlreadyExitsException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomerAlreadyExitsException(
            CustomerAlreadyExitsException ex, WebRequest request){
        ErrorResponseDto error = new ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.CONFLICT.toString(),
                ex.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public  ResponseEntity<ErrorResponseDto> resourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request){
        ErrorResponseDto error = new ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.NOT_FOUND.toString(),
                ex.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

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
