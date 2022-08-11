package com.mohe.nanjinghaiguaneducation.modules.trainingBase.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.ConvertUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.UploadUtils;
import com.mohe.nanjinghaiguaneducation.modules.redBase.entity.EduRedBaseResourceEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.dto.EduTrainingBaseAddDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.dto.EduTrainingBaseResourceAddDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.dto.EduTrainingBaseResourceUpdateDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.dto.EduTrainingBaseUpdateDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.entity.EduTrainingBaseEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.entity.EduTrainingBaseResourceEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.service.EduTrainingBaseResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;

import javax.servlet.http.HttpServletRequest;


/**
 * 实训基地素材
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:22
 */
@RestController
@Api(tags = "实训基地资源")
@RequestMapping("site/trainingBase/resource")
public class EduTrainingBaseResourceController {
    @Autowired
    private EduTrainingBaseResourceService eduTrainingBaseResourceService;
    @ApiOperation("实训基地实训资源列表")
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
            PageUtils pageUtils = eduTrainingBaseResourceService.queryPage(params);
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

    @ApiOperation("实训基地实训资源新增")
    @PostMapping("/add")
    public ResultData add(@RequestBody EduTrainingBaseResourceAddDto eduTrainingBaseResourceAddDto, HttpServletRequest request){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        String userId = request.getAttribute("userId").toString();
        EduTrainingBaseResourceEntity entity = ConvertUtils.convert(eduTrainingBaseResourceAddDto,EduTrainingBaseResourceEntity::new);
        entity.setCreateBy(userId);
        entity.setCreateTime(new Date());

        eduTrainingBaseResourceService.insert(entity);
        EduTrainingBaseResourceEntity id = eduTrainingBaseResourceService.selectOne(new EntityWrapper<EduTrainingBaseResourceEntity>().orderBy("id", false)
                .last("limit 1"));
        resultData.setData(id.getId());
        return resultData;
    }
    @ApiOperation("实训基地实训资源查看")
    @PostMapping("/info")
    public ResultData info(@RequestBody Map<String,Object>params){
        ResultData resultData = new ResultData();
        EduTrainingBaseResourceEntity eduTrainingBaseEntity = eduTrainingBaseResourceService.selectById(params.get("id").toString());
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setData(eduTrainingBaseEntity);
        return resultData;
    }
    @ApiOperation("实训基地实训资源删除")
    @PostMapping("/delete")
    public ResultData delete(@RequestBody Map<String,Object>params){
        ResultData resultData = new ResultData();
        Integer id = (Integer)params.get("id");
        eduTrainingBaseResourceService.deleteById(id);
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        return resultData;
    }
    @ApiOperation("实训基地资源修改")
    @PostMapping("/update")
    public ResultData update(@RequestBody EduTrainingBaseResourceUpdateDto eduTrainingBaseResourceUpdateDto){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        EduTrainingBaseResourceEntity entity = eduTrainingBaseResourceService.selectById(eduTrainingBaseResourceUpdateDto.getId());
        if (ObjectUtils.isEmpty(entity)){
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage("找不到该记录");
        }
        BeanUtil.copyProperties(eduTrainingBaseResourceUpdateDto,entity);
        eduTrainingBaseResourceService.updateById(entity);
        return resultData;
    }
}
