package com.paymentServices.example.paymentServices.dto;

import java.time.LocalDateTime;

public class ErrorResponseDto {
    private String apiPath;
    private String errorMessage;
    private String errorCode;
    private LocalDateTime errorTime;
}
