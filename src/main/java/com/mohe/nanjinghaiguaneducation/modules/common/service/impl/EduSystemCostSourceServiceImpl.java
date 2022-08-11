package com.mohe.nanjinghaiguaneducation.modules.common.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.ConvertUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;
import com.mohe.nanjinghaiguaneducation.modules.common.dao.EduSystemCostSourceDao;
import com.mohe.nanjinghaiguaneducation.modules.common.dto.EduSystemCommonDto;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemCostSourceEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemCostSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
*
* @description: 费用来源业务层
* @author liurui
* @date 2022/7/20 5:19 下午
*/
@Service
public class EduSystemCostSourceServiceImpl extends ServiceImpl<EduSystemCostSourceDao, EduSystemCostSourceEntity> implements EduSystemCostSourceService {

    @Autowired
    private EduSystemCostSourceDao eduSystemCostSourceDao;
    /**
     * 新增费用来源
     * @param eduSystemCommonDto
     * @return
     */
    @Override
    public ResultData addCostSource(EduSystemCommonDto eduSystemCommonDto) {
        ResultData resultData = new ResultData();
        String id = IdUtil.simpleUUID();
        if (!BeanUtil.isEmpty(eduSystemCommonDto)){
            EduSystemCostSourceEntity eduSystemCostSourceEntity = ConvertUtils.convert(eduSystemCommonDto, EduSystemCostSourceEntity::new);
            eduSystemCostSourceEntity.setId(id);
            Integer insert = eduSystemCostSourceDao.insert(eduSystemCostSourceEntity);
            if(insert > 0){//新增成功
                resultData.setData(id);
                resultData.setCode(SysConstant.SUCCESS);
                resultData.setSuccess(true);
                resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
                return resultData;
            }else{//新增失败
                resultData.setSuccess(false);
                resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
                resultData.setCode(SysConstant.FAIL);
                return resultData;
            }
        }else{//参数错误
            resultData.setCode(SysConstant.PARAMETER_ERROR);
            resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
            resultData.setSuccess(false);
            return resultData;
        }
    }

    /**
     * 分页查询
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<EduSystemCostSourceEntity> page = this.selectPage(
                new Query<EduSystemCostSourceEntity>(params).getPage(),
                new EntityWrapper<EduSystemCostSourceEntity>()
        );
        return new PageUtils(page);
    }

    /**
     * 根据id删除费用来源
     * @param params
     * @return
     */
    @Override
    public ResultData deleteCostSourceById(Map<String, Object> params) {
        ResultData resultData = new ResultData();
        String id = params.get("id").toString();
        Integer delete = eduSystemCostSourceDao.deleteById(id);
        if(delete > 0){
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            return resultData;
        }else{
            resultData.setCode(SysConstant.PARAMETER_ERROR);
            resultData.setSuccess(false);
            resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
            return resultData;
        }
    }

    /**
     * 修改
     * @param eduSystemCommonDto
     * @return
     */
    @Override
    public ResultData updateCostSource(EduSystemCommonDto eduSystemCommonDto) {
        ResultData resultData = new ResultData();
        if(!BeanUtil.isEmpty(eduSystemCommonDto)){
            EduSystemCostSourceEntity eduSystemCostSourceEntity = ConvertUtils.convert(eduSystemCommonDto, EduSystemCostSourceEntity::new);
            Integer update = eduSystemCostSourceDao.updateById(eduSystemCostSourceEntity);
            if(update > 0){
                resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
                resultData.setSuccess(true);
                resultData.setCode(SysConstant.SUCCESS);
                return resultData;
            }else{
                resultData.setCode(SysConstant.FAIL);
                resultData.setSuccess(false);
                resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
                return resultData;
            }
        }else {
            resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.PARAMETER_ERROR);
            return resultData;
        }
    }

    /**
     * 查询所有启动的费用来源
     * @return
     */
    @Override
    public ResultData<List<EduSystemCostSourceEntity>> selectCostSourceList() {
        ResultData resultData = new ResultData();
        List<EduSystemCostSourceEntity> list =
                eduSystemCostSourceDao.selectList(new EntityWrapper<EduSystemCostSourceEntity>().eq("isEnable", 1));
        resultData.setData(list);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setCode(SysConstant.SUCCESS);
        return resultData;
    }

    @Override
    public ResultData<EduSystemCostSourceEntity> selectEntityById(Map<String, Object> params) {
        ResultData resultData = new ResultData();
        String id = params.get("id").toString();
        EduSystemCostSourceEntity eduSystemCostSourceEntity = eduSystemCostSourceDao.selectById(id);
        if(!BeanUtil.isEmpty(eduSystemCostSourceEntity)){
            resultData.setData(eduSystemCostSourceEntity);
            resultData.setSuccess(true);
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            return resultData;
        }else{
            resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
            resultData.setCode(SysConstant.PARAMETER_ERROR);
            resultData.setSuccess(true);
            return resultData;
        }
    }
}
