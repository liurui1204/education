package com.mohe.nanjinghaiguaneducation.modules.site.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.ConvertUtils;
import com.mohe.nanjinghaiguaneducation.modules.site.dto.EduSiteContentAddDto;
import com.mohe.nanjinghaiguaneducation.modules.site.dto.EduSiteContentUpdateDto;
import com.mohe.nanjinghaiguaneducation.modules.site.dto.EduSiteNavAddDto;
import com.mohe.nanjinghaiguaneducation.modules.site.dto.EduSiteNavUpdateDto;
import com.mohe.nanjinghaiguaneducation.modules.site.entity.EduSiteContentEntity;
import com.mohe.nanjinghaiguaneducation.modules.site.service.EduSiteContentService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;

import javax.servlet.http.HttpServletRequest;


/**
 * 底部文章表
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:22
 */
@RestController
@RequestMapping("eduTrainingClass/edusitecontent")
public class EduSiteContentController {
    @Autowired
    private EduSiteContentService eduSiteContentService;

    @PostMapping("/list")
    @ApiOperation("底部文章列表")
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
            PageUtils pageUtils = eduSiteContentService.queryPage(params);

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

    @ApiOperation("底部文章新增")
    @PostMapping("/add")
    public ResultData add(@RequestBody EduSiteContentAddDto eduTrainingBaseDto, HttpServletRequest request){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        String userId = request.getAttribute("userId").toString();
        EduSiteContentEntity entity = ConvertUtils.convert(eduTrainingBaseDto,EduSiteContentEntity::new);
        entity.setCreateBy(userId);
        entity.setUpdateBy(userId);
        entity.setCreateTime(new Date());
        eduSiteContentService.insert(entity);
        EduSiteContentEntity id = eduSiteContentService.selectOne(new EntityWrapper<EduSiteContentEntity>().orderBy("id", false)
                .last("limit 1"));
        resultData.setData(id.getId());
        return resultData;
    }
    @ApiOperation("底部文章删除")
    @PostMapping("/delete")
    public ResultData delete(@RequestBody Map<String,Object>params){
        ResultData resultData = new ResultData();
        Integer id = (Integer)params.get("id");
        eduSiteContentService.deleteById(id);
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        return resultData;
    }

    @ApiOperation("底部文章修改")
    @PostMapping("/update")
    public ResultData update(@RequestBody EduSiteContentUpdateDto eduTrainingBaseUpdateDto, HttpServletRequest request){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        EduSiteContentEntity entity = eduSiteContentService.selectById(eduTrainingBaseUpdateDto.getId());
        if (ObjectUtils.isEmpty(entity)){
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage("找不到该记录");
        }

        BeanUtil.copyProperties(eduTrainingBaseUpdateDto,entity);
        entity.setUpdateBy(request.getAttribute("userId").toString());
        eduSiteContentService.updateById(entity);
        return resultData;
    }
}
