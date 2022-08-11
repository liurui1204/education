package com.mohe.nanjinghaiguaneducation.modules.site.dto;

import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassAttachEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class EduSiteTeacherDetailInfoDto {

    //教师基本信息
    @ApiModelProperty("教师类型 1-内聘 2-外聘")
    private Integer type;
    @ApiModelProperty("教师ID")
    private String id;
    @ApiModelProperty("教师名称")
    private String teacherName;
    @ApiModelProperty("教师编号")
    private String teacherCode;
    @ApiModelProperty("级别1-科级 2-处级 3-厅级 4-中级 5-副高 6-正高 7-知名学者专家")
    private String teacherLevel;
    @ApiModelProperty("手机")
    private String teacherMobile;
    @ApiModelProperty("邮箱")
    private String teacherEmail;
    @ApiModelProperty("署级")
    private String departmentLevel;
    // 内聘教师-处级
    // 外聘教师-单位
    @ApiModelProperty("所属部门")
    private String belongDepartment;
    //讲师讲课数
    @ApiModelProperty("讲师讲课数")
    private Integer courseTotalCount;
    //教师课件数
    @ApiModelProperty("教师课件数")
    private Integer courseDataCount;

    //展示该教师关联的课件列表
//    @ApiModelProperty("课件列表")
//    private List<EduTrainingClassAttachEntity> attachEntities;

}
