package com.example.application.company.plan.dao.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PlanDto implements Serializable {

    private static final long serialVersionUID = 2327008688852486137L;

    private Long id;
    private String name;
    private String details;
    private Long rooms;
}
