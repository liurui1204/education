package com.mohe.nanjinghaiguaneducation.modules.performance.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-04-11 15:49:48
 */
@Data
public class EduOnlinePerformanceImportDetailExport {
	@ExcelIgnore
	private String employeeId;
	@ExcelProperty("工号")
	private String employeeCode;
	@ExcelProperty("特殊情况明细")
	private String exception_remark;
	@ExcelProperty("姓名")
	private String name;

	@ExcelProperty("单位")
	private String department;



}
