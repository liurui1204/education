package com.mohe.nanjinghaiguaneducation.modules.site.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.ConvertUtils;
import com.mohe.nanjinghaiguaneducation.modules.site.dto.EduSiteLinksAddDto;
import com.mohe.nanjinghaiguaneducation.modules.site.dto.EduSiteLinksUpdateDto;
import com.mohe.nanjinghaiguaneducation.modules.site.dto.EduSiteNavAddDto;
import com.mohe.nanjinghaiguaneducation.modules.site.dto.EduSiteNavUpdateDto;
import com.mohe.nanjinghaiguaneducation.modules.site.entity.EduSiteLinksEntity;
import com.mohe.nanjinghaiguaneducation.modules.site.entity.EduSiteLinksEntity;
import com.mohe.nanjinghaiguaneducation.modules.site.service.EduSiteLinksService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;

import javax.servlet.http.HttpServletRequest;


/**
 * 友情链接表
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:22
 */
@RestController
@RequestMapping("site/link")
public class EduSiteLinksController {
    @Autowired
    private EduSiteLinksService eduSiteLinksService;

    @PostMapping("/list")
    @ApiOperation("友情链接列表")
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
            PageUtils pageUtils = eduSiteLinksService.queryPage(params);

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

    @ApiOperation("友情链接新增")
    @PostMapping("/add")
    public ResultData add(@RequestBody EduSiteLinksAddDto eduTrainingBaseDto, HttpServletRequest request){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        String userId = request.getAttribute("userId").toString();
        EduSiteLinksEntity entity = ConvertUtils.convert(eduTrainingBaseDto,EduSiteLinksEntity::new);
        entity.setCreateBy(userId);
        entity.setCreateTime(new Date());
        eduSiteLinksService.insert(entity);
        EduSiteLinksEntity id = eduSiteLinksService.selectOne(new EntityWrapper<EduSiteLinksEntity>().orderBy("id", false)
                .last("limit 1"));
        resultData.setData(id.getId());
        return resultData;
    }
    @ApiOperation("友情链接查看")
    @PostMapping("/info")
    public ResultData info(@RequestBody Map<String,Object>params){
        ResultData resultData = new ResultData();
        EduSiteLinksEntity entity = eduSiteLinksService.selectById(params.get("id").toString());
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setData(entity);
        return resultData;
    }
    @ApiOperation("友情链接删除")
    @PostMapping("/delete")
    public ResultData delete(@RequestBody Map<String,Object>params){
        ResultData resultData = new ResultData();
        Integer id = (Integer)params.get("id");
        eduSiteLinksService.deleteById(id);
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        return resultData;
    }

    @ApiOperation("友情链接修改")
    @PostMapping("/update")
    public ResultData update(@RequestBody EduSiteLinksUpdateDto eduTrainingBaseUpdateDto, HttpServletRequest request){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        EduSiteLinksEntity entity = eduSiteLinksService.selectById(eduTrainingBaseUpdateDto.getId());
        if (ObjectUtils.isEmpty(entity)){
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage("找不到该记录");
        }

        BeanUtil.copyProperties(eduTrainingBaseUpdateDto,entity);
        eduSiteLinksService.updateById(entity);
        return resultData;
    }

}
