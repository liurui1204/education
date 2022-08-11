package com.mohe.nanjinghaiguaneducation.modules.trainingClass.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel("培训班导出列表返回参数")
@Data
public class EduTrainingClassAllExcel {
    @ExcelProperty("序号")
    private Integer order;
    @ExcelProperty("培训班编号")
    private String classCode;

    @ExcelProperty("培训班名称")
    private String className;

    @ExcelProperty("学时类型")
    private String studyHourType;

    @ExcelProperty("学时")
    private Integer studyHour;
    @ExcelProperty("归属")
    private String studyBelong;
    @ExcelProperty("学分")
    private BigDecimal studyScore;
    @ExcelProperty("培训类型")
    private String trainingTypeName;
    @ExcelProperty("审批人")
    private String checkByName;
    @ExcelProperty("培训计划名称")
    private String trainingPlanName;

    @ExcelProperty("培训对象")
    private String trainingTrainee;

    @ExcelProperty("培训开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date trainingStartDate;

    @ExcelProperty("培训结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date trainingEndDate;

    @ExcelProperty("培训课时")
    private Integer trainingClassHour;

    @ExcelProperty("培训人数")
    private Integer trainingPeopleNum;

    @ExcelProperty("培训内容")
    private String trainingContent;

    @ExcelProperty("培训目的")
    private String trainingObjective;

    @ExcelProperty("培训地点")
    private String trainingAddr;

    @ExcelProperty("费用来源")
    private String feeOrigin;

    @ExcelProperty("申请部门名称")
    private String applyDepartmentName;
    /**
     *  1线上   0线下
     */
    @ExcelProperty("培训方式")
    private String trainingWay;

    @ExcelProperty("联系电话")
    private String tel;
    @ExcelProperty("备注")
    private String memo;

    @ExcelProperty("状态")
    private String status;
    @ExcelProperty("是否需要评估")
    private String needAssess;
    @ExcelProperty("评估达标率")
    private String assessRate;

    @ExcelProperty("报名开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date enrollStartTime;
    @ExcelProperty("报名结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date enrollEndTime;

}
