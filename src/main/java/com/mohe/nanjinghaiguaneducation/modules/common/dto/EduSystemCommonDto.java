package com.mohe.nanjinghaiguaneducation.modules.common.dto;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-02-16 18:11:37
 */
@Data
public class EduSystemCommonDto {

	@TableId
	private String id;
	/**
	 * 培训类型
	 */
	private String name;
	/**
	 * 是否可用 默认 1
	 */
	private Integer isEnable;

}
