package com.example.application.company.service;

import com.example.application.common.exceptions.ResourceNotFoundException;
import com.example.application.company.Company;
import com.example.application.company.dao.CompanyRepository;
import com.example.application.company.dao.dto.CompanyConverterImpl;
import com.example.application.company.dao.dto.CompanyDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyConverterImpl companyConverter;

    @Transactional
    public void createCompany(CompanyDto companyDto) {
        Company company = companyConverter.createFrom(companyDto);
        companyRepository.save(company);
    }

    public CompanyDto updateCompany(String name, CompanyDto companyDto) {
        Company company = companyRepository.findCompanyByName(name).orElseThrow(() ->
                new ResourceNotFoundException("Company with name " + name + " not found"));
        companyConverter.createFrom(companyDto);
        companyRepository.save(company);
        return companyConverter.createFrom(company);
    }

    public void deleteCompany(Long vatId) {
        companyRepository.deleteById(vatId);
    }

}
