package com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassEmployeeApplyEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

@ApiModel(value = "新增培训班参数")
public class EduTrainingClassDto {
    @ApiModelProperty(value= "主键")
    private String id;
    @ApiModelProperty(value= "培训计划id")
    private String trainingPlanId;
    @ApiModelProperty(value= "培训计划名称")
    private String trainingPlanName;
    @ApiModelProperty(value= "培训班编号")
    private String classCode;

    @ApiModelProperty(value= "培训班名称")
    private String className;
    @ApiModelProperty(value= "培训类型")
    private String trainingType;
    @ApiModelProperty(value= "培训对象")
    private String trainingTrainee;
    @ApiModelProperty(value= "培训类型名称")
    private String trainingTypeName;
    @ApiModelProperty(value= "培训对象名称")
    private String trainingTraineeName;
    @ApiModelProperty(value= "培训开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date trainingStartDate;
    @ApiModelProperty(value= "培训结束")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date trainingEndDate;
    @ApiModelProperty(value= "培训课时", example = "1")
    private Integer trainingClassHour;
    @ApiModelProperty(value= "培训人数", example = "1")
    private Integer trainingPeopleNum;
    @ApiModelProperty(value= "培训内容")
    private String trainingContent;
    @ApiModelProperty(value= "培训目的")
    private String trainingObjective;
    @ApiModelProperty(value= "培训目的名称")
    private String trainingObjectiveName;
    @ApiModelProperty(value= "培训地点")
    private String trainingAddr;
    @ApiModelProperty(value= "费用来源")
    private String feeOrigin;
    @ApiModelProperty(value= "申请部门id")
    private String applyDepartmentId;
    @ApiModelProperty(value= "申请部门名称")
    private String applyDepartmentName;
    @ApiModelProperty(value= "培训方式 1线上   0线下", example = "1")
    private Integer trainingWay;
    @ApiModelProperty(value= "联系电话")
    private String tel;
    @ApiModelProperty(value= "备注")
    private String memo;
    @ApiModelProperty(value= "状态，-1 草稿  0待审核， 1 审核通过 2审核不通过 3已过期", example = "1")
    private Integer status;
    @ApiModelProperty(value= "阶段", example = "1")
    private Integer phase;
    @ApiModelProperty(value= "是否需要评估 1需要 0不需要", example = "1")
    private Integer needAssess;
    @ApiModelProperty(value= "报名开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date enrollStartTime;
    @ApiModelProperty(value= "报名结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date enrollEndTime;
    @ApiModelProperty(value = "培训天数")
    private Double trainingClassDays;
    @ApiModelProperty(value= "下级审核部门")
    private String checkNextDepartmentId;
    @ApiModelProperty(value= "审核时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date checkTime;
    @ApiModelProperty(value= "审核人账号")
    private String checkBy;
    @ApiModelProperty(value= "审核人姓名")
    private String checkByName;
    @ApiModelProperty(value= "审核人部门名称")
    private String checkByDepartment;
    @ApiModelProperty(value= "审核备注")
    private String checkMemo;
    //    @ApiModelProperty(value= "创建时间")
//    private Date createTime;
//    @ApiModelProperty(value= "最后修改时间")
//    private Date updateTime;
//    @ApiModelProperty(value= "创建者账号")
//    private String createBy;
//    @ApiModelProperty(value= "最后修改人账号")
//    private String updateBy;
    @ApiModelProperty(value= "是否可用 默认 1", example = "1")
    private Integer isEnable;
    @ApiModelProperty(value="决算开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date finalStartTime;
    @ApiModelProperty(value = "决算结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date finalEndTime;
    @ApiModelProperty(value = "决算天数")
    private Double finalDay;
    //关联表数据 - 学时学分关联表
    @ApiModelProperty(value = "学时学分信息")
    private List<EduTrainingClassStudyInfoDto> eduTrainingClassStudyInfoDtos;
    //关联表数据 - 培训费目
    @ApiModelProperty(value = "培训费目")
    private List<EduTrainingClassFeeItemDto> eduTrainingClassFeeItemDto;
    //关联表数据 - 培训班学员关联表
    //@ApiModelProperty(value = "培训班学员")
    //private List<EduTrainingClassEmployeeDto> eduTrainingClassEmployeeDtos;
    @ApiModelProperty(value = "培训班流程跟踪信息（保存时不用提交）")
    private List<EduFlowTraceEntityDto> eduFlowTraceEntities;

    @ApiModelProperty("查询报名人员")
    private List<EduTrainingClassEmployeeApplyEntity> entities;
    @ApiModelProperty(value = "角色code")
    private String roleCode;

    @ApiModelProperty(value = "菜单Code")
    private String menuCode;

    public List<EduTrainingClassEmployeeApplyEntity> getEntities() {
        return entities;
    }

    public void setEntities(List<EduTrainingClassEmployeeApplyEntity> entities) {
        this.entities = entities;
    }

    public Integer getPhase() {
        return phase;
    }

    public void setPhase(Integer phase) {
        this.phase = phase;
    }

    public String getTrainingPlanName() {
        return trainingPlanName;
    }

    public void setTrainingPlanName(String trainingPlanName) {
        this.trainingPlanName = trainingPlanName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getApplyDepartmentName() {
        return applyDepartmentName;
    }

    public void setApplyDepartmentName(String applyDepartmentName) {
        this.applyDepartmentName = applyDepartmentName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrainingPlanId() {
        return trainingPlanId;
    }

    public void setTrainingPlanId(String trainingPlanId) {
        this.trainingPlanId = trainingPlanId;
    }

//    public String getTrainingPlanName() {
//        return trainingPlanName;
//    }
//
//    public void setTrainingPlanName(String trainingPlanName) {
//        this.trainingPlanName = trainingPlanName;
//    }

//    public String getClassCode() {
//        return classCode;
//    }
//
//    public void setClassCode(String classCode) {
//        this.classCode = classCode;
//    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(String trainingType) {
        this.trainingType = trainingType;
    }

    public String getTrainingTrainee() {
        return trainingTrainee;
    }

    public void setTrainingTrainee(String trainingTrainee) {
        this.trainingTrainee = trainingTrainee;
    }

    public Date getTrainingStartDate() {
        return trainingStartDate;
    }

    public void setTrainingStartDate(Date trainingStartDate) {
        this.trainingStartDate = trainingStartDate;
    }

    public Date getTrainingEndDate() {
        return trainingEndDate;
    }

    public void setTrainingEndDate(Date trainingEndDate) {
        this.trainingEndDate = trainingEndDate;
    }

    public Integer getTrainingClassHour() {
        return trainingClassHour;
    }

    public void setTrainingClassHour(Integer trainingClassHour) {
        this.trainingClassHour = trainingClassHour;
    }

    public Integer getTrainingPeopleNum() {
        return trainingPeopleNum;
    }

    public void setTrainingPeopleNum(Integer trainingPeopleNum) {
        this.trainingPeopleNum = trainingPeopleNum;
    }

    public String getTrainingContent() {
        return trainingContent;
    }

    public void setTrainingContent(String trainingContent) {
        this.trainingContent = trainingContent;
    }

    public String getTrainingObjective() {
        return trainingObjective;
    }

    public void setTrainingObjective(String trainingObjective) {
        this.trainingObjective = trainingObjective;
    }

    public String getTrainingAddr() {
        return trainingAddr;
    }

    public void setTrainingAddr(String trainingAddr) {
        this.trainingAddr = trainingAddr;
    }

    public String getFeeOrigin() {
        return feeOrigin;
    }

    public void setFeeOrigin(String feeOrigin) {
        this.feeOrigin = feeOrigin;
    }

    public String getApplyDepartmentId() {
        return applyDepartmentId;
    }

    public void setApplyDepartmentId(String applyDepartmentId) {
        this.applyDepartmentId = applyDepartmentId;
    }

//    public String getApplyDepartmentName() {
//        return applyDepartmentName;
//    }
//
//    public void setApplyDepartmentName(String applyDepartmentName) {
//        this.applyDepartmentName = applyDepartmentName;
//    }

    public Integer getTrainingWay() {
        return trainingWay;
    }

    public void setTrainingWay(Integer trainingWay) {
        this.trainingWay = trainingWay;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getNeedAssess() {
        return needAssess;
    }

    public void setNeedAssess(Integer needAssess) {
        this.needAssess = needAssess;
    }

    public Date getEnrollStartTime() {
        return enrollStartTime;
    }

    public void setEnrollStartTime(Date enrollStartTime) {
        this.enrollStartTime = enrollStartTime;
    }

    public Date getEnrollEndTime() {
        return enrollEndTime;
    }

    public void setEnrollEndTime(Date enrollEndTime) {
        this.enrollEndTime = enrollEndTime;
    }

    public String getCheckNextDepartmentId() {
        return checkNextDepartmentId;
    }

    public void setCheckNextDepartmentId(String checkNextDepartmentId) {
        this.checkNextDepartmentId = checkNextDepartmentId;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckBy() {
        return checkBy;
    }

    public void setCheckBy(String checkBy) {
        this.checkBy = checkBy;
    }

    public String getCheckByDepartment() {
        return checkByDepartment;
    }

    public void setCheckByDepartment(String checkByDepartment) {
        this.checkByDepartment = checkByDepartment;
    }

    public String getCheckMemo() {
        return checkMemo;
    }

    public void setCheckMemo(String checkMemo) {
        this.checkMemo = checkMemo;
    }

//    public Date getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(Date createTime) {
//        this.createTime = createTime;
//    }
//
//    public Date getUpdateTime() {
//        return updateTime;
//    }
//
//    public void setUpdateTime(Date updateTime) {
//        this.updateTime = updateTime;
//    }
//
//    public String getCreateBy() {
//        return createBy;
//    }
//
//    public void setCreateBy(String createBy) {
//        this.createBy = createBy;
//    }
//
//    public String getUpdateBy() {
//        return updateBy;
//    }
//
//    public void setUpdateBy(String updateBy) {
//        this.updateBy = updateBy;
//    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public List<EduTrainingClassStudyInfoDto> getEduTrainingClassStudyInfoDtos() {
        return eduTrainingClassStudyInfoDtos;
    }

    public void setEduTrainingClassStudyInfoDtos(List<EduTrainingClassStudyInfoDto> eduTrainingClassStudyInfoDtos) {
        this.eduTrainingClassStudyInfoDtos = eduTrainingClassStudyInfoDtos;
    }

    public List<EduTrainingClassFeeItemDto> getEduTrainingClassFeeItemDto() {
        return eduTrainingClassFeeItemDto;
    }

    public void setEduTrainingClassFeeItemDto(List<EduTrainingClassFeeItemDto> eduTrainingClassFeeItemDto) {
        this.eduTrainingClassFeeItemDto = eduTrainingClassFeeItemDto;
    }

//    public List<EduTrainingClassEmployeeDto> getEduTrainingClassEmployeeDtos() {
//        return eduTrainingClassEmployeeDtos;
//    }
//
//    public void setEduTrainingClassEmployeeDtos(List<EduTrainingClassEmployeeDto> eduTrainingClassEmployeeDtos) {
//        this.eduTrainingClassEmployeeDtos = eduTrainingClassEmployeeDtos;
//    }


    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getCheckByName() {
        return checkByName;
    }

    public void setCheckByName(String checkByName) {
        this.checkByName = checkByName;
    }

//    public List<EduFlowTraceEntity> getEduFlowTraceEntities() {
//        return eduFlowTraceEntities;
//    }
//
//    public void setEduFlowTraceEntities(List<EduFlowTraceEntity> eduFlowTraceEntities) {
//        this.eduFlowTraceEntities = eduFlowTraceEntities;
//    }


    public List<EduFlowTraceEntityDto> getEduFlowTraceEntities() {
        return eduFlowTraceEntities;
    }

    public void setEduFlowTraceEntities(List<EduFlowTraceEntityDto> eduFlowTraceEntities) {
        this.eduFlowTraceEntities = eduFlowTraceEntities;
    }

    public String getTrainingTypeName() {
        return trainingTypeName;
    }

    public void setTrainingTypeName(String trainingTypeName) {
        this.trainingTypeName = trainingTypeName;
    }

    public String getTrainingTraineeName() {
        return trainingTraineeName;
    }

    public void setTrainingTraineeName(String trainingTraineeName) {
        this.trainingTraineeName = trainingTraineeName;
    }

    public String getTrainingObjectiveName() {
        return trainingObjectiveName;
    }

    public void setTrainingObjectiveName(String trainingObjectiveName) {
        this.trainingObjectiveName = trainingObjectiveName;
    }

    public Double getTrainingClassDays() {
        return trainingClassDays;
    }

    public void setTrainingClassDays(Double trainingClassDays) {
        this.trainingClassDays = trainingClassDays;
    }

    public Date getFinalStartTime() {
        return finalStartTime;
    }

    public void setFinalStartTime(Date finalStartTime) {
        this.finalStartTime = finalStartTime;
    }

    public Date getFinalEndTime() {
        return finalEndTime;
    }

    public void setFinalEndTime(Date finalEndTime) {
        this.finalEndTime = finalEndTime;
    }

    public Double getFinalDay() {
        return finalDay;
    }

    public void setFinalDay(Double finalDay) {
        this.finalDay = finalDay;
    }
}
