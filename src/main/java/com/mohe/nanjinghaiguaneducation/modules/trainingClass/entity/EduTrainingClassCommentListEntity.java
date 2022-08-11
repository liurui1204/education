package com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity;

import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EduTrainingClassCommentListEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("未完成的学生详情")
    private List<EduEmployeeEntity> eduEmployeeEntityList;

    @ApiModelProperty("已完成的学生详情")
    private List<EduEmployeeEntity> finishEduEmployeeEntities;

    @ApiModelProperty(value = "需求数量", example = "1")
    private Integer needNum;

    @ApiModelProperty(value = "今日收集数量", example = "1")
    private Integer todayNum;

    @ApiModelProperty(value = "数据总量", example = "1")
    private Integer finishNum;

    @ApiModelProperty("满意度---等同于完成率")
    private String satisfaction;
}
