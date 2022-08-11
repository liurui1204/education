package com.mohe.nanjinghaiguaneducation.modules.teacherOuter.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/**
 * 外聘教师
 */
@TableName("edu_outer_teacher")
@Data
public class EduOuterTeacher implements Serializable{
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 教师编号
     */
    private String teacherCode;
    /**
     * 教师名称
     */
    private String teacherName;
    /**
     * 公司
     */
    private String company;
    /**
     * 级别
     */
    private String teacherLevel;
    /**
     * 手机号
     */
    private String teacherMobile;
    /**
     * 邮箱
     */
    private String teacherEmail;
    /**
     * 审批状态
     */
    private Integer status;
    /**
     * 送审时间
     */
    private Date applyTime;
    /**
     * 下级审核部门
     */
    private String checkNextDepartmentId;
    /**
     * 最后审核时间
     */
    private Date checkTime;
    /**
     * 最后审核人账号
     */
    private String checkBy;
    /**
     * 最后审核备注
     */
    private String checkMemo;
    /**
     * 雇佣开始日期
     */
    private Date employStartDate;
    /**
     * 雇佣结束日期
     */
    private Date employEndDate;
    /**
     * 创建时间
     */

    private Date createTime;

    /**
     * 最后修改时间
     */
    private Date updateTime;
    /**
     * 创建者账号
     */
    private String createBy;
    /**
     * 最后修改人账号
     */
    private String updateBy;
    /**
     * 是否可用
     */
    @TableLogic
    private Integer isEnable;

    @TableField(exist = false)
    private String departmentName;

    private Integer strokesNum;

    private Boolean departmentLevel;
}
