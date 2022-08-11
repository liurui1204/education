package com.mohe.nanjinghaiguaneducation.modules.head.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 署级培训参训情况
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-06-21 14:47:20
 */
@TableName("edu_head_office_training")
@Data
public class EduHeadOfficeTrainingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 培训班名称
	 */
	private String className;
	/**
	 * 主办司局
	 */
	private String hostDepartment;
	/**
	 * 培训开始时间
	 */
	private Date beginTime;
	/**
	 * 培训结束时间
	 */
	private Date endTime;
	/**
	 * 参训人数
	 */
	private Integer count;
	/**
	 * 作废 1-作废 0-有效 默认0
	 */
	private Integer disabled;
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
	 * 备注
	 */
	private String remark;

}
