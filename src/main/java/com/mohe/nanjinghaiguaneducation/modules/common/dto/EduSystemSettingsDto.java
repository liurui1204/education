package com.mohe.nanjinghaiguaneducation.modules.common.dto;

import lombok.Data;

import java.util.List;

@Data
public class EduSystemSettingsDto {
    private String key;

    private String value;

    private List<String> imageValue;
}
