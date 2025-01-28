package com.example.application.company.hotel.room.reservation.api;

import com.example.application.account.exception.AccountDisabledException;
import com.example.application.account.exception.AccountNotFoundException;
import com.example.application.common.controller.BaseController;
import com.example.application.common.dtos.ErrorResponse;
import com.example.application.company.hotel.room.reservation.ReservationService;
import com.example.application.company.hotel.room.reservation.dto.ReservationRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.validation.Valid;

import static com.example.application.account.exception.AccountErrorResponseType.ACCOUNT_DISABLED;
import static com.example.application.account.exception.AccountErrorResponseType.ACCOUNT_NOT_FOUND;
import static com.example.application.common.dtos.CommonErrorResponseType.SERVICE_UNAVAILABLE;

@RestController
@Slf4j
public class BookingController extends BaseController {
    private final String API = "/book";

    private final ReservationService service;

    @Autowired
    public BookingController(ReservationService service) {
        this.service = service;
    }

    @PostMapping(value = API, produces = "application/json", consumes = "application/json")
    public ResponseEntity<Object> bookHotel(@RequestBody @Valid ReservationRequestDto request){
        try{
            service.reserveRooms(request);
            return ResponseEntity.ok("Successfully reserved");
        } catch (AccountNotFoundException ex) {
            LOGGER.warn(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ACCOUNT_NOT_FOUND.getCode(),
                    ACCOUNT_NOT_FOUND.getMessage()));
        } catch (AccountDisabledException ex) {
            LOGGER.warn(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ACCOUNT_DISABLED.getCode(),
                    ACCOUNT_DISABLED.getMessage()));
        } catch (MessagingException ex) {
            LOGGER.warn(ex.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ErrorResponse(SERVICE_UNAVAILABLE.getCode(),
                    SERVICE_UNAVAILABLE.getMessage()));
        }
    }
}
