package com.mohe.nanjinghaiguaneducation.modules.site.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.ConvertUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.site.dto.EduTrainingResourceAddDto;
import com.mohe.nanjinghaiguaneducation.modules.site.dto.EduTrainingResourceUpdateDto;
import com.mohe.nanjinghaiguaneducation.modules.site.entity.EduTrainingResourceEntity;
import com.mohe.nanjinghaiguaneducation.modules.site.service.EduTrainingResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
*
* @description: 最新资源数据接口
* @author liurui
* @date 2022/7/23 10:23 上午
*/
@RestController
@Api(tags = "最新资源")
@RequestMapping("site/latestResource")
public class EduTrainingResourceController {
    @Autowired
    private EduTrainingResourceService eduTrainingResourceService;
    @PostMapping("/list")
    @ApiOperation("最新资源列表")
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
            PageUtils pageUtils = eduTrainingResourceService.queryPage(params);

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

    @ApiOperation("最新资源新增")
    @PostMapping("/add")
    public ResultData add(@RequestBody EduTrainingResourceAddDto eduTrainingResourceAddDto, HttpServletRequest request){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        String userId = request.getAttribute("userId").toString();
        EduTrainingResourceEntity entity = ConvertUtils.convert(eduTrainingResourceAddDto, EduTrainingResourceEntity::new);
        entity.setCreateBy(userId);
        entity.setCreateTime(new Date());
        eduTrainingResourceService.insert(entity);
        EduTrainingResourceEntity eduTrainingResourceEntity = eduTrainingResourceService.selectOne(new EntityWrapper<EduTrainingResourceEntity>().orderBy("id", false)
                .last("limit 1"));
        resultData.setData(eduTrainingResourceEntity.getId());
        return resultData;
    }
    @ApiOperation("最新资源查看")
    @PostMapping("/info")
    public ResultData info(@RequestBody Map<String,Object>params){
        ResultData resultData = new ResultData();
        EduTrainingResourceEntity eduTrainingResourceEntity = eduTrainingResourceService.selectById(params.get("id").toString());
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setData(eduTrainingResourceEntity);
        return resultData;
    }
    @ApiOperation("教培动态删除")
    @PostMapping("/delete")
    public ResultData delete(@RequestBody Map<String,Object>params){
        ResultData resultData = new ResultData();
        Integer id = (Integer)params.get("id");
        eduTrainingResourceService.deleteById(id);
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        return resultData;
    }

    @ApiOperation("教培动态修改")
    @PostMapping("/update")
    public ResultData update(@RequestBody EduTrainingResourceUpdateDto eduTrainingResourceUpdateDto, HttpServletRequest request){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        EduTrainingResourceEntity entity = eduTrainingResourceService.selectById(eduTrainingResourceUpdateDto.getId());
        if (ObjectUtils.isEmpty(entity)){
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage("找不到该记录");
        }

        BeanUtil.copyProperties(eduTrainingResourceUpdateDto,entity);
        eduTrainingResourceService.updateById(entity);
        return resultData;
    }
}
