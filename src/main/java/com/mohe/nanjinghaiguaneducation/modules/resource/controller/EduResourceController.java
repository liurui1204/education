package com.mohe.nanjinghaiguaneducation.modules.resource.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.ConvertUtils;
import com.mohe.nanjinghaiguaneducation.modules.resource.dto.EduResourceAddDto;
import com.mohe.nanjinghaiguaneducation.modules.resource.dto.EduResourceUpdateDto;
import com.mohe.nanjinghaiguaneducation.modules.resource.entity.EduResourceEntity;
import com.mohe.nanjinghaiguaneducation.modules.resource.service.EduResourceService;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.dto.EduTrainingBaseAddDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.dto.EduTrainingBaseUpdateDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.entity.EduTrainingBaseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;

import javax.servlet.http.HttpServletRequest;


/**
 * 资源表
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:23
 */
@Api(tags = "资源表")
@RestController
@RequestMapping("site/resourceManage/")
public class EduResourceController {
    @Autowired
    private EduResourceService eduResourceService;
    @ApiOperation("资源列表（某一个分类下的）")
    @PostMapping("/list")
    public ResultData list(@RequestBody Map<String,Object> params){
        ResultData<PageUtils> resultData = new ResultData<>();
        try {
            Integer page = (Integer) params.get("page");
            Integer limit = (Integer) params.get("limit");
            if (page == null || limit == null
                    || page < 1 || limit < 1) {
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.PARAMETER_ERROR);
                resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
                return resultData;
            }
            PageUtils pageUtils = eduResourceService.queryPage(params);
            resultData.setData(pageUtils);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e) {
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.PARAMETER_ERROR);
            resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
            return resultData;
        }
    }
    @ApiOperation("资源新增")
    @PostMapping("/add")
    public ResultData add(@RequestBody EduResourceAddDto eduResourceAddDto, HttpServletRequest request){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        String userId = request.getAttribute("userId").toString();
        EduResourceEntity entity = ConvertUtils.convert(eduResourceAddDto,EduResourceEntity::new);
        entity.setCreateBy(userId);
        entity.setCreateTime(new Date());
        eduResourceService.insert(entity);
        EduResourceEntity id = eduResourceService.selectOne(new EntityWrapper<EduResourceEntity>().orderBy("id", false)
                .last("limit 1"));
        resultData.setData(id.getId());
        return resultData;
    }
    @ApiOperation("资源查看")
    @PostMapping("/info")
    public ResultData info(@RequestBody Map<String,Object>params){
        ResultData resultData = new ResultData();
        EduResourceEntity entity = eduResourceService.selectById((Integer)params.get("id"));
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setData(entity);
        return resultData;
    }
    @ApiOperation("资源删除")
    @PostMapping("/delete")
    public ResultData delete(@RequestBody Map<String,Object>params){
        ResultData resultData = new ResultData();
        Integer id = (Integer)params.get("id");
        eduResourceService.deleteById(id);
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        return resultData;
    }

    @ApiOperation("资源更新")
    @PostMapping("/update")
    public ResultData update(@RequestBody EduResourceUpdateDto eduResourceUpdateDto){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        EduResourceEntity entity = eduResourceService.selectById(eduResourceUpdateDto.getId());
        if (ObjectUtils.isEmpty(entity)){
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage("找不到该记录");
        }

        BeanUtil.copyProperties(eduResourceUpdateDto,entity);
        eduResourceService.updateById(entity);
        return resultData;
    }
}
