package com.example.application.company.hotel.dao.dto;

import com.example.application.common.model.AbstractDto;
import com.example.application.company.hotel.CategoryEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CategoryDto  extends AbstractDto {
    private static final long serialVersionUID = 7595243348105937805L;
    @NotBlank
    private Long id;
    @NotBlank
    private CategoryEnum name;
}
