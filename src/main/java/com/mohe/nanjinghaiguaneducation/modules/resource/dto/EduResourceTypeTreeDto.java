package com.mohe.nanjinghaiguaneducation.modules.resource.dto;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.mohe.nanjinghaiguaneducation.modules.resource.entity.EduResourceTypeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class EduResourceTypeTreeDto {
//    @ApiModelProperty("资源分类信息")
//    private EduResourceTypeEntity eduResourceTypeEntity;
    @ApiModelProperty("资源分类子节点")
    private List<EduResourceTypeTreeDto> children;

    private Integer id;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 分类级别 默认1 最大3
     */
    private Integer level;
    /**
     * 父级分类ID
     */
    private Integer parentId;
    /**
     * 排序 (排序数字 数字越大越优先)
     */
    private Integer order;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建者账号
     */
    private String createBy;
    /**
     * 是否可用 默认 1
     */
    private Integer isEnable;
    /**
     * 备注
     */
    private String remark;
}
