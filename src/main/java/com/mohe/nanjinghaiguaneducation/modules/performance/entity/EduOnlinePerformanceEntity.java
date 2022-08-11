package com.mohe.nanjinghaiguaneducation.modules.performance.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author mohe
 * @email mohe@163.com
 * @date 2022-04-11 15:49:48
 */
@TableName("edu_online_performance")
@Data
public class EduOnlinePerformanceEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private String passRate;

    private String departmentCustomsRate;
    /**
     *
     */
    private Integer isPass;
    private String notPassReason;
    /**
     *
     */
    private String createBy;
    /**
     *
     */
    private String createByName;
    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastModify;
    /**
     *
     */
    private String customsRateJson;
    /**
     * 1-待处理 2-处理中 3-自动处理失败 4-已处理
     */
    private Integer status;
    /**
     * 是否全员参加 0-非全员参加 1-全员参加
     */
    private Integer isAllStuff;


}
