package com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.cons.MenuEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.lang.management.MemoryManagerMXBean;
import java.util.Date;
import java.util.List;

/**
 * 培训班附件
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-03-03 17:38:16
 */
@Data
public class EduTrainingClassAttachDto  {
	@ApiModelProperty("文件")
	private MultipartFile files;

	@ApiModelProperty("文件名称")
	private String names;

	@ApiModelProperty("培训班id")
	private String trainingClassId;

	@ApiModelProperty("课程信息id")
	private String courseId;

	@ApiModelProperty(value = "是否展示首页 1是 0否", example = "1")
	private Integer isShowIndex;
}
