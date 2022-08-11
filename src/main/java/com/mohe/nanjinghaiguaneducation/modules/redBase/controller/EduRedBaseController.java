package com.mohe.nanjinghaiguaneducation.modules.redBase.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.ConvertUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.UploadUtils;
import com.mohe.nanjinghaiguaneducation.modules.redBase.dto.EduRedBaseAddDto;
import com.mohe.nanjinghaiguaneducation.modules.redBase.dto.EduRedBaseUpdateDto;
import com.mohe.nanjinghaiguaneducation.modules.redBase.entity.EduRedBaseEntity;
import com.mohe.nanjinghaiguaneducation.modules.redBase.service.EduRedBaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;

import javax.servlet.http.HttpServletRequest;


/**
 * 红色基地
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:22
 */
@RestController
@Api(tags = "红色基地")
@RequestMapping("site/redResource")
public class EduRedBaseController {
    @Autowired
    private EduRedBaseService eduRedBaseService;

    @ApiOperation("红色基地列表")
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
            PageUtils pageUtils = eduRedBaseService.queryPage(params);
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

    @ApiOperation("新增红色基地")
    @PostMapping("/add")
    @Transactional
    public ResultData add(@RequestBody EduRedBaseAddDto eduRedBaseAddDto, HttpServletRequest request){
        ResultData resultData = new ResultData();
        try {
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            EduRedBaseEntity entity = ConvertUtils.convert(eduRedBaseAddDto,EduRedBaseEntity::new);
            entity.setUpdateTime(new Date());
            entity.setCreateTime(new Date());
            entity.setCreateBy(request.getAttribute("userId").toString());
            eduRedBaseService.insert(entity);
            EduRedBaseEntity eduRedBaseEntity = eduRedBaseService.selectOne(new EntityWrapper<EduRedBaseEntity>().orderBy("id", false)
                    .last("limit 1"));
            resultData.setData(eduRedBaseEntity.getId());
        }catch (Exception e){
            e.printStackTrace();
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
        }
        return resultData;
    }


    @ApiOperation("红色资源查看")
    @PostMapping("/info")
    public ResultData info(@RequestBody Map<String,Object> params){
        ResultData resultData = new ResultData();
        String id = params.get("id").toString();
        EduRedBaseEntity entity = eduRedBaseService.selectById(id);
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setData(entity);
        return resultData;
    }
    @ApiOperation("红色资源删除")
    @PostMapping("/delete")
    public ResultData delete(@RequestBody Map<String,Object> params){
        ResultData resultData = new ResultData();
        Integer id = (Integer) params.get("id");
        eduRedBaseService.deleteById(id);
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        return resultData;
    }

    @ApiOperation("红色资源修改")
    @PostMapping("/update")
    public ResultData update(@RequestBody EduRedBaseUpdateDto eduRedBaseUpdateDto){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        EduRedBaseEntity entity = eduRedBaseService.selectById(eduRedBaseUpdateDto.getId());
        entity.setUpdateTime(new Date());
        if (ObjectUtils.isEmpty(entity)){
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage("找不到该记录");
        }
        BeanUtil.copyProperties(eduRedBaseUpdateDto,entity);
        eduRedBaseService.updateById(entity);
        return resultData;
    }


}
