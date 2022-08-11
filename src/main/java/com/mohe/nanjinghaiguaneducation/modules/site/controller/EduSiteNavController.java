package com.mohe.nanjinghaiguaneducation.modules.site.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.ConvertUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.site.dto.EduSiteNavAddDto;
import com.mohe.nanjinghaiguaneducation.modules.site.dto.EduSiteNavUpdateDto;
import com.mohe.nanjinghaiguaneducation.modules.site.entity.EduSiteNavEntity;
import com.mohe.nanjinghaiguaneducation.modules.site.service.EduSiteNavService;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.dto.EduTrainingBaseAddDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.dto.EduTrainingBaseUpdateDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * 导航设置
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:21
 */
@RestController
@Api(tags = "头部导航")
@RequestMapping("site/nav")
public class EduSiteNavController {
    @Autowired
    private EduSiteNavService eduSiteNavService;

    @PostMapping("/list")
    @ApiOperation("头部导航列表")
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
            PageUtils pageUtils = eduSiteNavService.queryPage(params);

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

    @ApiOperation("头部导航新增")
    @PostMapping("/add")
    public ResultData add(@RequestBody EduSiteNavAddDto eduTrainingBaseDto, HttpServletRequest request){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        String userId = request.getAttribute("userId").toString();
        EduSiteNavEntity entity = ConvertUtils.convert(eduTrainingBaseDto,EduSiteNavEntity::new);
        entity.setCreateBy(userId);
        entity.setUpdateBy(userId);
        entity.setCreateTime(new Date());
        eduSiteNavService.insert(entity);
        EduSiteNavEntity id = eduSiteNavService.selectOne(new EntityWrapper<EduSiteNavEntity>().orderBy("id", false)
                .last("limit 1"));
        resultData.setData(id.getId());
        return resultData;
    }
    @ApiOperation("头部导航查看")
    @PostMapping("/info")
    public ResultData info(@RequestBody Map<String,Object>params){
        ResultData resultData = new ResultData();
        EduSiteNavEntity eduSiteNavEntity = eduSiteNavService.selectById(params.get("id").toString());
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setData(eduSiteNavEntity);
        return resultData;
    }
    @ApiOperation("头部导航删除")
    @PostMapping("/delete")
    public ResultData delete(@RequestBody Map<String,Object>params){
        ResultData resultData = new ResultData();
        Integer id = (Integer)params.get("id");
        eduSiteNavService.deleteById(id);
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        return resultData;
    }

    @ApiOperation("头部导航修改")
    @PostMapping("/update")
    public ResultData update(@RequestBody EduSiteNavUpdateDto eduTrainingBaseUpdateDto,HttpServletRequest request){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        EduSiteNavEntity entity = eduSiteNavService.selectById(eduTrainingBaseUpdateDto.getId());
        if (ObjectUtils.isEmpty(entity)){
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage("找不到该记录");
        }

        BeanUtil.copyProperties(eduTrainingBaseUpdateDto,entity);
        entity.setUpdateBy(request.getAttribute("userId").toString());
        eduSiteNavService.updateById(entity);
        return resultData;
    }
}
