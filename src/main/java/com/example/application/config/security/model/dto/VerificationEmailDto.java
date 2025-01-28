package com.example.application.config.security.model.dto;

import com.example.application.common.model.AbstractDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerificationEmailDto extends AbstractDto {
    private static final long serialVersionUID = 6980676107648132422L;

    private String email;
}
