package com.mohe.nanjinghaiguaneducation.modules.common.controller;

import java.util.Map;

import cn.hutool.core.util.IdUtil;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.ConvertUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.common.dto.EduSystemCommonDto;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemBelongToEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemTrainingGoalEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemBelongToService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;


/**
 * 
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-02-16 18:11:37
 */
@RestController
@RequestMapping("common/edusystembelongto")
@Api(tags = "系统参数")
public class EduSystemBelongToController {
    @Autowired
    private EduSystemBelongToService eduSystemBelongToService;

    /**
     * 列表
     */
    @PostMapping("/list")
    @RequiresPermissions("system:edusystembelongto:list")
    @ApiOperation("获取归属列表")
    public ResultData<PageUtils> list(@RequestBody Map<String, Object> params){
        ResultData<PageUtils> resultData = new ResultData<>();
        resultData.setData(eduSystemBelongToService.queryPage(params));
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        return resultData;
    }

    @PostMapping("/addOrUpdate")
    @ApiOperation("新增 归属")
    public ResultData addOrUpdate(@RequestBody EduSystemCommonDto eduSystemTrainingTypeDto){
        ResultData resultData = new ResultData();
        EduSystemBelongToEntity entity  = ConvertUtils.convert(eduSystemTrainingTypeDto,EduSystemBelongToEntity::new);
        String id = IdUtil.simpleUUID();
        if (!ObjectUtils.isEmpty(eduSystemTrainingTypeDto.getId())){
            EduSystemBelongToEntity eduSystemTrainingGoalEntity = eduSystemBelongToService.selectById(eduSystemTrainingTypeDto.getId());
            if (ObjectUtils.isEmpty(eduSystemTrainingGoalEntity)){
                entity.setId(id);
                eduSystemBelongToService.insert(entity);
                resultData.setData(id);
            }else {
                eduSystemTrainingGoalEntity.setName(eduSystemTrainingTypeDto.getName());
                eduSystemTrainingGoalEntity.setIsEnable(eduSystemTrainingTypeDto.getIsEnable());
                eduSystemBelongToService.updateById(eduSystemTrainingGoalEntity);
            }
        }else {
            entity.setId(id);
            eduSystemBelongToService.insert(entity);
            resultData.setData(id);
        }
        resultData.setSuccess(true);
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        return resultData;
    }

    @PostMapping("/delete")
    @ApiOperation("删除 归属")
    public ResultData delete(@RequestBody Map<String,Object>params){
        ResultData resultData = new ResultData();
        String id = params.get("id").toString();
        eduSystemBelongToService.deleteById(id);
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        return resultData;
    }

}
