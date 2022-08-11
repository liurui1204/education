package com.mohe.nanjinghaiguaneducation.modules.common.service;

import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.common.dto.EduSystemCommonDto;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemCostSourceEntity;

import java.util.List;
import java.util.Map;

/**
 * @author liurui
 * @description: 费用来源业务层接口
 * @date 2022/7/20 5:18 下午
 */
public interface EduSystemCostSourceService extends IService<EduSystemCostSourceEntity> {

    /**
     * 新增费用来源
     * @param eduSystemCommonDto
     * @return
     */
    ResultData addCostSource(EduSystemCommonDto eduSystemCommonDto);

    /**
     * 分页查询
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据id删除费用来源
     * @param params
     * @return
     */
    ResultData deleteCostSourceById(Map<String, Object> params);

    /**
     * 修改
     * @param eduSystemCommonDto
     * @return
     */
    ResultData updateCostSource(EduSystemCommonDto eduSystemCommonDto);

    /**
     * 查询所有启动状态下的费用来源
     * @return
     */
    ResultData<List<EduSystemCostSourceEntity>> selectCostSourceList();

    /**
     * 根据id获取费用来源信息
     * @param params
     * @return
     */
    ResultData<EduSystemCostSourceEntity> selectEntityById(Map<String, Object> params);
}
