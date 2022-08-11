package com.mohe.nanjinghaiguaneducation.modules.common.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-03-10 16:02:19
 */
@TableName("edu_system_confirm")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EduSystemConfirmEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	private String id;
	/**
	 * 类型 1-培训计划 2-培训班 3-预留
	 */
	private Integer type;
	/**
	 * 关联数据主键
	 */
	private String originalId;
	/**
	 * 阶段
	 */
	private Integer phase;
	/**
	 * 审核人ID
	 */
	private String confirmEmployeeId;
	/**
	 * 审核人名字【冗余，形如 张三(2020123)】
	 */
	private String confirmEmployeeName;
	/**
	 * 状态  0-作废  1-待审核 2-已审核 3-已退回
	 */
	private Integer status;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 最后修改时间
	 */
	private Date lastModify;

}
