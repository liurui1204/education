package com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 培训费目
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-03-04 13:51:53
 */
@Data
@TableName("edu_training_class_fee_item")
public class EduTrainingClassFeeItemEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private String id;
	/**
	 * 所属培训班id
	 */
	private String trainingClassId;
	/**
	 * 1 预计费用 2申请费用 3教育处初核费用  4教育处复核费用
	 */
	private Integer feeType;
	/**
	 * 费目名称
	 */
	private String itemName;
	/**
	 * 费用/人
	 */
	private double fee;
	/**
	 * 课时
	 */
	private Integer classHour;
	/**
	 * 人数
	 */
	private Integer peopleNum;
	/**
	 * 授课费
	 */
	private double teacherFee;
	/**
	 * 总费用
	 */
	private double totalFee;
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
	 * 是否可用 默认 1
	 */
	private Integer isEnable;

	/**
	 * 培训班天数
	 */
	private Double trainingClassDays;

	public String getType(Integer feeType){
		String str = null;
		switch (feeType){
			case 1:
				str="预计费用";
				break;
			case 2:
				str ="申请费用";
				break;
			case 3:
				str="教育处初核费用";
				break;
			case 4:
				str="决算申请费用";
				break;
			case 5:
				str="教育处复核费用";
				break;
		}
		return str;
	}

}
