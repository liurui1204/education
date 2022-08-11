package com.mohe.nanjinghaiguaneducation.modules.trainingPlan.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import io.swagger.annotations.ApiModel;

import java.util.Date;

@ApiModel("隶属关培训计划列表返回参数")
@ContentRowHeight(30)
@HeadRowHeight(20)
@ColumnWidth(18)
public class EduTrainingPlanLSListVo {
    @ExcelIgnore
    private String id;
    @ExcelProperty(value = "培训班编号")
    private String planCode;
    @ExcelProperty(value = "培训班名称")
    private String planName;

    @ExcelIgnore
    private Integer phase;
    @ExcelIgnore
    private String trainingMonth;
    @ExcelProperty(value = "培训内容")
    private String trainingContent;
    @ExcelProperty(value = "培训人数")
    private String trainingPeopleNum;
    @ExcelIgnore
    private String trainingClassHour;
    @ExcelProperty(value = "审核状态")
    private String status;
    @ExcelIgnore
    private String employeeId;
    @ExcelIgnore
    private String employeeName;
    @ExcelProperty(value = "审核人")
    private String checkName;
    @ExcelProperty(value = "所需费用")
    private String needExpenditure;
    @ExcelIgnore
    private Date startTime;
    @ExcelIgnore
    private Date endTime;
    @ExcelIgnore
    private String studyTime;
    @ExcelIgnore
    private String trainingWay;

    @ExcelIgnore
    private String applyDepartmentId;
    @ExcelIgnore
    private String applyDepartmentName;

    public String getApplyDepartmentName() {
        return applyDepartmentName;
    }

    public void setApplyDepartmentName(String applyDepartmentName) {
        this.applyDepartmentName = applyDepartmentName;
    }

    public Integer getPhase() {
        return phase;
    }

    public void setPhase(Integer phase) {
        this.phase = phase;
    }

    public String getApplyDepartmentId() {
        return applyDepartmentId;
    }

    public void setApplyDepartmentId(String applyDepartmentId) {
        this.applyDepartmentId = applyDepartmentId;
    }

    public String getTrainingWay() {
        return trainingWay;
    }

    public void setTrainingWay(String trainingWay) {
        this.trainingWay = trainingWay;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getStudyTime() {
        return studyTime;
    }

    public void setStudyTime(String studyTime) {
        this.studyTime = studyTime;
    }

    public String getNeedExpenditure() {
        return needExpenditure;
    }

    public void setNeedExpenditure(String needExpenditure) {
        this.needExpenditure = needExpenditure;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getTrainingMonth() {
        return trainingMonth;
    }

    public void setTrainingMonth(String trainingMonth) {
        this.trainingMonth = trainingMonth;
    }

    public String getTrainingContent() {
        return trainingContent;
    }

    public void setTrainingContent(String trainingContent) {
        this.trainingContent = trainingContent;
    }

    public String getTrainingPeopleNum() {
        return trainingPeopleNum;
    }

    public void setTrainingPeopleNum(String trainingPeopleNum) {
        this.trainingPeopleNum = trainingPeopleNum;
    }

    public String getTrainingClassHour() {
        return trainingClassHour;
    }

    public void setTrainingClassHour(String trainingClassHour) {
        this.trainingClassHour = trainingClassHour;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }
}
