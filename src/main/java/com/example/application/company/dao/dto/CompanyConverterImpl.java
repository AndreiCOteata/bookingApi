package com.example.application.company.dao.dto;
import com.example.application.company.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyConverterImpl implements CompanyConverter {
    @Override
    public Company createFrom(CompanyDto dto) {
        Company entity = new Company();
        updateEntity(entity, dto);
        entity.setActive(true);
        return entity;
    }

    @Override
    public CompanyDto createFrom(Company entity) {
        CompanyDto companyDto = new CompanyDto();
        if (entity != null) {
            companyDto.setCompanyName(entity.getName());
            companyDto.setVatId(entity.getVatId());
            companyDto.setEmail(entity.getEmail());
            companyDto.setCity(entity.getCity());
            companyDto.setDetails(entity.getDetails());
            companyDto.setActive(entity.isActive());
        }
        return companyDto;
    }

    @Override
    public Company updateEntity(Company entity, CompanyDto dto) {
        if (entity != null & dto != null) {
            entity.setVatId(dto.getVatId());
            entity.setName(dto.getCompanyName());
            entity.setEmail(dto.getEmail());
            entity.setDetails(dto.getDetails());
            entity.setCity(dto.getCity());
            entity.setActive(dto.isActive());
        }
        return entity;
    }
}
