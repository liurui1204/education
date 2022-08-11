package com.mohe.nanjinghaiguaneducation.modules.common.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("edu_system_customs_schedule")
public class EduSystemCustomsScheduleEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private String key;

    private Integer type;

    private String originalBn;

    private String roleCode;

    private Integer syncStatus;

    private Date createTime;

    private String userGuid;

    private Integer cancelSyncStatus;

    private Integer status;

    private String scheduleId;

    private Integer trainingWay;

}
