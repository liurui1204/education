package com.mohe.nanjinghaiguaneducation.modules.site.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
*
* @description: 成果展示实体类
* @author liurui
* @date 2022/7/23 10:19 上午
*/
@TableName("edu_training_resource")
public class EduTrainingResourceEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 教培动态标题
     */
    private String title;
    /**
     * 概述
     */
    private String desc;
    /**
     * 详情 - 可能是图文信息
     */
    private String content;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建者账号
     */
    private String createBy;
    /**
     * 是否可用 默认1  0-禁用
     */
    private Integer isEnable;
    /**
     * 所属海关
     */
    private String customsName;
    /**
     * 是否在首页展示 0-不展示 1-展示 默认0
     */
    private Integer displayInHome;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public String getCustomsName() {
        return customsName;
    }

    public void setCustomsName(String customsName) {
        this.customsName = customsName;
    }

    public Integer getDisplayInHome() {
        return displayInHome;
    }

    public void setDisplayInHome(Integer displayInHome) {
        this.displayInHome = displayInHome;
    }
}
