package com.mohe.nanjinghaiguaneducation.modules.common.controller;

import java.util.Map;

import cn.hutool.core.util.IdUtil;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.ConvertUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.common.dto.EduSystemCommonDto;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemTrainingGoalEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemTrainingObjectEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemTrainingObjectService;
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
@RequestMapping("common/edusystemtrainingobject")
@Api(tags = "系统参数")
public class EduSystemTrainingObjectController {
    @Autowired
    private EduSystemTrainingObjectService eduSystemTrainingObjectService;

    /**
     * 列表
     */
    @PostMapping("/list")
    @RequiresPermissions("system:edusystemtrainingobject:list")
    @ApiOperation("获取培训对象列表")
    public ResultData<PageUtils> list(@RequestBody Map<String, Object> params){
        ResultData<PageUtils> resultData = new ResultData<>();
        PageUtils page = eduSystemTrainingObjectService.queryPage(params);
        resultData.setData(page);
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        return resultData;
//
//        return R.ok().put("page", page);
    }

    @PostMapping("/addOrUpdate")
    @ApiOperation("新增培训对象")
    public ResultData addOrUpdate(@RequestBody EduSystemCommonDto eduSystemTrainingTypeDto){
        ResultData resultData = new ResultData();
        EduSystemTrainingObjectEntity entity  = ConvertUtils.convert(eduSystemTrainingTypeDto,EduSystemTrainingObjectEntity::new);
        String id = IdUtil.simpleUUID();
        if (!ObjectUtils.isEmpty(eduSystemTrainingTypeDto.getId())){
            EduSystemTrainingObjectEntity eduSystemTrainingGoalEntity = eduSystemTrainingObjectService.selectById(eduSystemTrainingTypeDto.getId());
            if (ObjectUtils.isEmpty(eduSystemTrainingGoalEntity)){
                entity.setId(id);
                eduSystemTrainingObjectService.insert(entity);
                resultData.setData(id);
            }else {
                eduSystemTrainingGoalEntity.setName(eduSystemTrainingTypeDto.getName());
                eduSystemTrainingGoalEntity.setIsEnable(eduSystemTrainingTypeDto.getIsEnable());
                eduSystemTrainingObjectService.updateById(eduSystemTrainingGoalEntity);
            }
        }else {
            entity.setId(id);
            eduSystemTrainingObjectService.insert(entity);
            resultData.setData(id);
        }
        resultData.setSuccess(true);
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        return resultData;
    }

    @PostMapping("/delete")
    @ApiOperation("删除培训对象")
    public ResultData delete(@RequestBody Map<String,Object>params){
        ResultData resultData = new ResultData();
        String id = params.get("id").toString();
        eduSystemTrainingObjectService.deleteById(id);
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        return resultData;
    }

}
