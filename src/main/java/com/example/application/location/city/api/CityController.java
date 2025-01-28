package com.example.application.location.city.api;

import com.example.application.common.controller.BaseController;
import com.example.application.location.city.dao.dto.CityDto;
import com.example.application.location.city.service.CityService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CityController extends BaseController {

    private static final String API_PATH = "city";
    private final CityService cityService;

    @GetMapping(value = CityController.API_PATH, produces = "application/json")
    public ResponseEntity<CityDto> getCityById(@RequestParam Long id) {
        return ResponseEntity.ok(cityService.getCityById(id));
    }

    @PutMapping(value = CityController.API_PATH, consumes = "application/json")
    public ResponseEntity<CityDto> updateCity(@RequestParam Long id, @RequestBody @Valid CityDto cityDto) {
        return new ResponseEntity<>
                (cityService.updateCity(id, cityDto), HttpStatus.ACCEPTED);
    }
}
