package com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity;

import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EduTrainingClassCommentViewEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("评论项大类")
    private String itemType;

    @ApiModelProperty("评论项")
    private String itemName;

    @ApiModelProperty("非常满意")
    private String verySatisfied;

    @ApiModelProperty("满意")
    private String satisfied;

    @ApiModelProperty("不满意")
    private String dissSatisfied;

    @ApiModelProperty("非常不满意")
    private String veryDissSatisfied;

    @ApiModelProperty("一般")
    private String generally;

    @ApiModelProperty("冗余字段,前端无视")
    private String score;
}
