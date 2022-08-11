package com.mohe.nanjinghaiguaneducation.modules.common.controller;

import java.util.Map;

import cn.hutool.core.util.IdUtil;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.ConvertUtils;
import com.mohe.nanjinghaiguaneducation.modules.common.dto.EduSystemCommonDto;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemPeriodTypeEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemTrainingTypeEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemPeriodTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
//import com.mohe.nanjinghaiguaneducation.common.utils.R;



/**
 * 
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-02-16 18:11:37
 */
@RestController
@RequestMapping("common/edusystemperiodtype")
@Api(tags = "系统参数")
public class EduSystemPeriodTypeController {

    @Autowired
    private EduSystemPeriodTypeService eduSystemPeriodTypeService;

    /**
     * 列表
     */
    @PostMapping("/list")
    @RequiresPermissions("system:edusystemperiodtype:list")
    @ApiOperation("获取学时类型")
    public ResultData<PageUtils> list(@RequestBody Map<String, Object> params){
        ResultData<PageUtils> resultData = new ResultData<>();
        PageUtils page = eduSystemPeriodTypeService.queryPage(params);
        resultData.setData(page);
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        return resultData;
//        PageUtils page = eduSystemPeriodTypeService.queryPage(params);
//
//        return R.ok().put("page", page);
    }

    @PostMapping("/addOrUpdate")
    @ApiOperation("新增 学时类型")
    public ResultData addOrUpdate(@RequestBody EduSystemCommonDto eduSystemTrainingTypeDto){
        ResultData resultData = new ResultData();
        EduSystemPeriodTypeEntity entity  = ConvertUtils.convert(eduSystemTrainingTypeDto,EduSystemPeriodTypeEntity::new);
        String id = IdUtil.simpleUUID();
        if (!ObjectUtils.isEmpty(eduSystemTrainingTypeDto.getId())){
            EduSystemPeriodTypeEntity eduSystemTrainingTypeEntity = eduSystemPeriodTypeService.selectById(eduSystemTrainingTypeDto.getId());
            if (ObjectUtils.isEmpty(eduSystemTrainingTypeEntity)){
                entity.setId(id);
                eduSystemPeriodTypeService.insert(entity);
                resultData.setData(id);
            }else {
                eduSystemTrainingTypeEntity.setName(eduSystemTrainingTypeDto.getName());
                eduSystemTrainingTypeEntity.setIsEnable(eduSystemTrainingTypeDto.getIsEnable());
                eduSystemPeriodTypeService.updateById(eduSystemTrainingTypeEntity);
            }
        }else {
            entity.setId(id);
            eduSystemPeriodTypeService.insert(entity);
            resultData.setData(id);
        }
        resultData.setSuccess(true);
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        return resultData;
    }

    @PostMapping("/delete")
    @ApiOperation("删除 学时类型")
    public ResultData delete(@RequestBody Map<String,Object>params){
        ResultData resultData = new ResultData();
        String id = params.get("id").toString();
        eduSystemPeriodTypeService.deleteById(id);
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        return resultData;
    }

}
