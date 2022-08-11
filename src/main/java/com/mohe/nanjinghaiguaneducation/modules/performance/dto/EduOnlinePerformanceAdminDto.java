package com.mohe.nanjinghaiguaneducation.modules.performance.dto;

import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;

import java.util.Date;

/**
 * author: CC
 * date:   2022/4/15
 * description:
 **/
@Data
public class EduOnlinePerformanceAdminDto {

    /**
     *
     */
//    @TableId
//    private Long id;
    /**
     *
     */
    private String name;
    private String roleCode;
    private Integer isAllStuff;

//    private String passRate;
//
//    private String departmentCustomsRate;
//    /**
//     *
//     */
//    private Integer isPass;
//    /**
//     *
//     */
//    private String createBy;
//    /**
//     *
//     */
//    private String createByName;
//    /**
//     *
//     */
//    private Date createTime;
//    /**
//     *
//     */
//    private Date lastModify;
//    /**
//     *
//     */
//    private String customsRateJson;
//    /**
//     * 1-待处理 2-处理中 3-自动处理失败 4-已处理
//     */
//    private Integer status;

}
