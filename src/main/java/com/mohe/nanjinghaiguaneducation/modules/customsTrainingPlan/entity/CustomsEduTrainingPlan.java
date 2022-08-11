package com.mohe.nanjinghaiguaneducation.modules.customsTrainingPlan.entity;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
@TableName("edu_training_plan")
public class CustomsEduTrainingPlan implements Serializable {
    private static final long serialVersionUID = 1L;
    //主键
    @TableId
    private String id;
    //培训计划编号
    private String planCode;
    //培训计划名称
    private String planName;
    //培训类型
    private String trainingType;
    //培训对象
    private String trainingTrainee;
    //培训月份
    private String trainingMonth;
    //培训课时
    private Integer trainingClassHour;
    //培训人数
    private Integer trainingPeopleNum;
    //培训内容
    private String trainingContent;
    //培训目的
    private String trainingObjective;
    //培训地点
    private String trainingAddr;
    //费用来源
    private String feeOrigin;
    //申请部门id
    private String applyDepartmentId;
    //申请部门名称
    private String applyDepartmentName;
    //培训方式
    private Integer trainingWay;
    //联系电话
    private String tel;
    //备注
    private String memo;
    //状态
    private Integer status;
    //送审时间
    private Date applyTime;
    //是否为内部部门
    private Integer isInner;
    //下级部门审核
    private String checkNextDepartmentId;
    //审核时间
    private Date checkTime;
    //审核人账号
    private String checkBy;
    //审核人部门名称
    private String checkByDepartment;
    //审核备注
    private String checkMemo;
    //创建时间
    private Date createTime;
    //最后修改时间
    private Date updateTime;
    //创建者账号
    private String createBy;
    //最后修改人账号
    private String updateBy;
    //是否可用
    @TableLogic
    private Integer isEnable;
}
