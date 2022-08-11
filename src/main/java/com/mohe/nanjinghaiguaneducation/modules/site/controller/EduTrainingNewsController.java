package com.mohe.nanjinghaiguaneducation.modules.site.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.ConvertUtils;
import com.mohe.nanjinghaiguaneducation.modules.site.dto.EduSiteSwiperAddDto;
import com.mohe.nanjinghaiguaneducation.modules.site.dto.EduSiteSwiperUpdateDto;
import com.mohe.nanjinghaiguaneducation.modules.site.dto.EduTrainingNewsAddDto;
import com.mohe.nanjinghaiguaneducation.modules.site.dto.EduTrainingNewsUpdateDto;
import com.mohe.nanjinghaiguaneducation.modules.site.entity.EduTrainingNewsEntity;
import com.mohe.nanjinghaiguaneducation.modules.site.service.EduTrainingNewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;

import javax.servlet.http.HttpServletRequest;


/**
 * 教培动态
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-17 12:05:24
 */
@RestController
@Api(tags = "教培动态")
@RequestMapping("site/trainingNews")
public class EduTrainingNewsController {
    @Autowired
    private EduTrainingNewsService eduTrainingNewsService;

    @PostMapping("/list")
    @ApiOperation("教培动态列表")
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
            PageUtils pageUtils = eduTrainingNewsService.queryPage(params);

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

    @ApiOperation("教培动态新增")
    @PostMapping("/add")
    public ResultData add(@RequestBody EduTrainingNewsAddDto eduTrainingNewsAddDto, HttpServletRequest request){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        String userId = request.getAttribute("userId").toString();
        EduTrainingNewsEntity entity = ConvertUtils.convert(eduTrainingNewsAddDto,EduTrainingNewsEntity::new);
        entity.setCreateBy(userId);
        entity.setCreateTime(new Date());
        eduTrainingNewsService.insert(entity);
        EduTrainingNewsEntity id = eduTrainingNewsService.selectOne(new EntityWrapper<EduTrainingNewsEntity>().orderBy("id", false)
                .last("limit 1"));
        resultData.setData(id.getId());
        return resultData;
    }
    @ApiOperation("教培动态查看")
    @PostMapping("/info")
    public ResultData info(@RequestBody Map<String,Object>params){
        ResultData resultData = new ResultData();
        EduTrainingNewsEntity eduSiteNavEntity = eduTrainingNewsService.selectById(params.get("id").toString());
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setData(eduSiteNavEntity);
        return resultData;
    }
    @ApiOperation("教培动态删除")
    @PostMapping("/delete")
    public ResultData delete(@RequestBody Map<String,Object>params){
        ResultData resultData = new ResultData();
        Integer id = (Integer)params.get("id");
        eduTrainingNewsService.deleteById(id);
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        return resultData;
    }

    @ApiOperation("教培动态修改")
    @PostMapping("/update")
    public ResultData update(@RequestBody EduTrainingNewsUpdateDto eduTrainingNewsUpdateDto, HttpServletRequest request){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        EduTrainingNewsEntity entity = eduTrainingNewsService.selectById(eduTrainingNewsUpdateDto.getId());
        if (ObjectUtils.isEmpty(entity)){
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage("找不到该记录");
        }

        BeanUtil.copyProperties(eduTrainingNewsUpdateDto,entity);
        eduTrainingNewsService.updateById(entity);
        return resultData;
    }

}
