package com.mohe.nanjinghaiguaneducation.modules.employee.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 职员信息
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-02-16 16:21:53
 */
@Mapper
public interface EduEmployeeDao extends BaseMapper<EduEmployeeEntity> {
	List<Map<String,Object>>  query();

	List<Map<String,Object>> queryChilder(String id);
}
