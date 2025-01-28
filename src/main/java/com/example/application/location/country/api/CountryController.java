package com.example.application.location.country.api;

import com.example.application.common.controller.BaseController;
import com.example.application.location.country.CountryEnum;
import com.example.application.location.country.dao.dto.CountryDto;
import com.example.application.location.country.service.CountryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CountryController extends BaseController {

    public static final String API_PATH = "country";
    private final CountryService countryService;

    @GetMapping(CountryController.API_PATH)
    public ResponseEntity<CountryDto> getCountryByName(@RequestParam CountryEnum name) {
        return ResponseEntity.ok(countryService.getCountryByName(name));
    }
}