package com.mohe.nanjinghaiguaneducation.modules.redBase.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import cn.hutool.core.bean.BeanUtil;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.ConvertUtils;
import com.mohe.nanjinghaiguaneducation.modules.redBase.dto.EduRedBaseResourceAddDto;
import com.mohe.nanjinghaiguaneducation.modules.redBase.dto.EduRedBaseResourceUpdateDto;
import com.mohe.nanjinghaiguaneducation.modules.redBase.entity.EduRedBaseEntity;
import com.mohe.nanjinghaiguaneducation.modules.redBase.entity.EduRedBaseResourceEntity;
import com.mohe.nanjinghaiguaneducation.modules.redBase.service.EduRedBaseResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;

import javax.servlet.http.HttpServletRequest;


/**
 * 红色基地素材
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:23
 */
@RestController
@Api(tags = "红色基地素材")
@RequestMapping("site/redResource/data")
public class EduRedBaseResourceController {
    @Autowired
    private EduRedBaseResourceService eduRedBaseResourceService;

    @ApiOperation("红色资源资料新增")
    @PostMapping("/add")
    public ResultData add(@RequestBody EduRedBaseResourceAddDto eduRedBaseResourceAddDto, HttpServletRequest request){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        EduRedBaseResourceEntity entity = ConvertUtils.convert(eduRedBaseResourceAddDto,EduRedBaseResourceEntity::new);
        entity.setCreateBy(request.getAttribute("userId").toString());
        entity.setCreateTime(new Date());
        eduRedBaseResourceService.insert(entity);
        return resultData;
    }

    @ApiOperation("红色资源资料删除")
    @PostMapping("/delete")
    public ResultData delete(@RequestBody Map<String,Object> params){
        ResultData resultData = new ResultData();
        Integer id = (Integer)params.get("id");
        eduRedBaseResourceService.deleteById(id);
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        return resultData;
    }

    @ApiOperation("红色资源资料列表")
    @PostMapping("/list")
    public ResultData list(@RequestBody Map<String,Object>params){
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
            PageUtils pageUtils = eduRedBaseResourceService.queryPage(params);
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

    @ApiOperation("红色资源资料查看")
    @PostMapping("/info")
    public ResultData info(@RequestBody Map<String,Object>params){
        ResultData resultData = new ResultData();
        String id = params.get("id").toString();
        EduRedBaseResourceEntity entity = eduRedBaseResourceService.selectById(id);
        resultData.setData(entity);
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        return resultData;
    }

    @ApiOperation("红色资源资料修改")
    @PostMapping("/update")
    public ResultData update(@RequestBody EduRedBaseResourceUpdateDto eduRedBaseResourceUpdateDto){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        EduRedBaseResourceEntity entity = eduRedBaseResourceService.selectById(eduRedBaseResourceUpdateDto.getId());
        if (ObjectUtils.isEmpty(entity)){
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage("找不到该记录");
        }
        BeanUtil.copyProperties(eduRedBaseResourceUpdateDto,entity);
        eduRedBaseResourceService.updateById(entity);
        return resultData;
    }
}
