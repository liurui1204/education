package com.mohe.nanjinghaiguaneducation.modules.site.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class EduNewMessageEntity {
    @JsonFormat(pattern = "yyyy.MM.dd ",timezone="GMT+8")
    private Date createTime;
    private String message;
}
