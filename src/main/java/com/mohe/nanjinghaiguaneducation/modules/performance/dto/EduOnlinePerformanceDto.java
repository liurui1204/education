package com.mohe.nanjinghaiguaneducation.modules.performance.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * author: CC
 * date:   2022/4/14
 * description:
 **/
@Data
public class EduOnlinePerformanceDto {
    private Long id;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastModify;
    private Integer isAllStuff;
    private String passRate;

}
