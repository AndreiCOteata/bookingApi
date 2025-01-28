package com.example.application.company.hotel.api;

import com.example.application.common.controller.BaseController;
import com.example.application.common.dtos.ErrorResponse;
import com.example.application.company.hotel.dao.dto.HotelDto;
import com.example.application.company.hotel.exception.HotelNotFoundException;
import com.example.application.company.hotel.service.HotelService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import static com.example.application.company.hotel.exception.CompanyErrorResponseType.HOTEL_NOT_FOUND;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class HotelController extends BaseController {

    private static final String API_PATH = "hotel";
    private final HotelService hotelService;

    @PostMapping(HotelController.API_PATH)
    public ResponseEntity<String> createHotel(@Valid @RequestBody HotelDto hotelDto) {
        hotelService.createHotel(hotelDto);
        return ResponseEntity.ok("Hotel created");
    }


    @PutMapping(HotelController.API_PATH)
    public ResponseEntity<String> updateHotel(@RequestParam Long id, @Valid @RequestBody HotelDto hotelDto) {
        hotelService.updateHotel(id, hotelDto);
        return new ResponseEntity<>("Hotel updated", HttpStatus.ACCEPTED);
    }

    @GetMapping(HotelController.API_PATH)
    public ResponseEntity<HotelDto> getHotel(@RequestParam String companyName, @RequestParam String name) {
        return ResponseEntity.ok(hotelService.getHotel(companyName, name));
    }

    @PostMapping(value = API_PATH + "/search", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> getHotels(@RequestBody @Valid HotelsRequestDto request){
        try{
            System.out.println(request.toString());
            return ResponseEntity.ok(hotelService.filterHotelList(request));
        } catch (HotelNotFoundException ex) {
            LOGGER.warn(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HOTEL_NOT_FOUND.getCode(),
                    HOTEL_NOT_FOUND.getMessage()));
        }
    }
}
