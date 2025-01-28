package com.example.application.common.controller;

import com.example.application.common.dtos.ContactUsDto;
import com.example.application.common.dtos.ErrorResponse;
import com.example.application.common.email.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.validation.Valid;

import static com.example.application.common.dtos.CommonErrorResponseType.SERVICE_UNAVAILABLE;

@RestController
@Slf4j
public class ContactUsController extends BaseController{

    private final String API = "/sendemail";
    private final NotificationService notificationService;

    @Autowired
    public ContactUsController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping (value = API, consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> sendContactUsEmail(@RequestBody @Valid ContactUsDto contactUsDto) {
        try {
            notificationService.sendContactUsEmail(contactUsDto);
            return ResponseEntity.ok("Email Sent");
        } catch (MessagingException ex) {
            LOGGER.warn(ex.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ErrorResponse(SERVICE_UNAVAILABLE.getCode(),
                    SERVICE_UNAVAILABLE.getMessage()));
        }
    }

}
