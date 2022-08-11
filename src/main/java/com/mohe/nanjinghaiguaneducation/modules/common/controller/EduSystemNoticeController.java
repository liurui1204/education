package com.mohe.nanjinghaiguaneducation.modules.common.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.ConvertUtils;
import com.mohe.nanjinghaiguaneducation.modules.common.dto.EduSystemNoticeAddDto;
import com.mohe.nanjinghaiguaneducation.modules.common.dto.EduSystemNoticeUpdateDto;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemNoticeEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemNoticeService;
import com.mohe.nanjinghaiguaneducation.modules.site.dto.EduTrainingElegantAddDto;
import com.mohe.nanjinghaiguaneducation.modules.site.dto.EduTrainingElegantUpdateDto;
import com.mohe.nanjinghaiguaneducation.modules.site.entity.EduTrainingElegantEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;

import javax.servlet.http.HttpServletRequest;


/**
 * 通知
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:23
 */
@RestController
@Api(tags = "通知")
@RequestMapping("eduTrainingClass/edusystemnotice")
public class EduSystemNoticeController {
    @Autowired
    private EduSystemNoticeService eduSystemNoticeService;

    @PostMapping("/list")
    @ApiOperation("通知列表")
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
            PageUtils pageUtils = eduSystemNoticeService.queryPage(params);

            resultData.setData(pageUtils);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e) {
            e.printStackTrace();
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.PARAMETER_ERROR);
            resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
            return resultData;
        }
    }

    @ApiOperation("通知新增")
    @PostMapping("/add")
    public ResultData add(@RequestBody EduSystemNoticeAddDto eduSystemNoticeAddDto, HttpServletRequest request){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        String userId = request.getAttribute("userId").toString();
        EduSystemNoticeEntity entity = ConvertUtils.convert(eduSystemNoticeAddDto,EduSystemNoticeEntity::new);
        entity.setCreateBy(userId);
        entity.setCreateTime(new Date());
        eduSystemNoticeService.insert(entity);
        EduSystemNoticeEntity id = eduSystemNoticeService.selectOne(new EntityWrapper<EduSystemNoticeEntity>().orderBy("id", false)
                .last("limit 1"));
        resultData.setData(id.getId());
        return resultData;
    }
    @ApiOperation("通知删除")
    @PostMapping("/delete")
    public ResultData delete(@RequestBody Map<String,Object>params){
        ResultData resultData = new ResultData();
        Integer id = (Integer)params.get("id");
        eduSystemNoticeService.deleteById(id);
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        return resultData;
    }

    @ApiOperation("通知修改")
    @PostMapping("/update")
    public ResultData update(@RequestBody EduSystemNoticeUpdateDto eduSystemNoticeUpdateDto, HttpServletRequest request){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        EduSystemNoticeEntity eduSystemNoticeEntity = eduSystemNoticeService.selectById(eduSystemNoticeUpdateDto.getId());
        if (ObjectUtils.isEmpty(eduSystemNoticeEntity)){
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage("找不到该记录");
        }

        BeanUtil.copyProperties(eduSystemNoticeUpdateDto,eduSystemNoticeEntity);
        eduSystemNoticeService.updateById(eduSystemNoticeEntity);
        return resultData;
    }


}
