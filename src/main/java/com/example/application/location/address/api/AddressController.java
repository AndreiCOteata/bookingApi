package com.example.application.location.address.api;

import com.example.application.common.controller.BaseController;
import com.example.application.location.address.dao.dto.AddressDto;
import com.example.application.location.address.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AddressController extends BaseController {

    private static final String API_PATH = "address";
    private final AddressService addressService;

    @GetMapping(AddressController.API_PATH + "/{id}")
    public ResponseEntity<AddressDto> getAddressById(@PathVariable Long id) {
        return ResponseEntity.ok(addressService.getAddressById(id));
    }

    @PutMapping(AddressController.API_PATH + "/{id}")
    public ResponseEntity<AddressDto> updateAddress(@PathVariable Long id, @RequestBody @Valid AddressDto addressDto) {
        return new ResponseEntity<>
                (addressService.updateAddress(id, addressDto), HttpStatus.ACCEPTED);
    }
}
