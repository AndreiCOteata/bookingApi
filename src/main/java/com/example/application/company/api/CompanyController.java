package com.example.application.company.api;

import com.example.application.common.controller.BaseController;
import com.example.application.company.Company;
import com.example.application.company.dao.dto.CompanyDto;
import com.example.application.company.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CompanyController extends BaseController {

    private static final String API_PATH = "company";
    private final CompanyService companyService;

    @PostMapping(CompanyController.API_PATH)
    public ResponseEntity<String> createCompany(@Valid @RequestBody CompanyDto companyDto) {
        companyService.createCompany(companyDto);
        return ResponseEntity.ok("Company created");
    }

    @PutMapping(CompanyController.API_PATH + "/{name}")
    public ResponseEntity<CompanyDto> updateCompany(@PathVariable String name, @RequestBody @Valid CompanyDto companyDto) {
        return new ResponseEntity<>(companyService.updateCompany(name, companyDto), HttpStatus.ACCEPTED);
    }

    @DeleteMapping(CompanyController.API_PATH + "/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.accepted().build();
    }
}
