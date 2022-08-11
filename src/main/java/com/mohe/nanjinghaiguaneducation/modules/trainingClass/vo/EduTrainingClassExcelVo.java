package com.mohe.nanjinghaiguaneducation.modules.trainingClass.vo;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentLoopMerge;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

@ApiModel("直属关培训计划列表返回参数")
@ContentRowHeight(30)
@HeadRowHeight(20)
@ColumnWidth(18)
@Data
public class EduTrainingClassExcelVo {
    @ExcelProperty("序号")
    private Integer order;
    @ExcelProperty("部门")
    private String departmentName;
    @ExcelProperty("培训班名称")
    private String className;
    @ExcelProperty("通知文号")
    private String attachTitle;

    @ExcelProperty("教师名称")
    private String teacherName;
    @ExcelProperty("工号")
    private String userCode;
    @ExcelProperty("课程名称")
    private String courseName;
    @ExcelProperty("开始时间")
    private String startTime;
    @ExcelProperty("结束时间")
    private String endTime;
    @ExcelProperty("学时")
    private Integer courseHour;
    @ExcelProperty("标准")
    private Integer standard;
    @ExcelProperty("金额")
    private Integer amount;
    @ExcelProperty("备注")
    private String memo;

}
