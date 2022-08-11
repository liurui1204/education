package com.mohe.nanjinghaiguaneducation.modules.trainingBase.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.ConvertUtils;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.dto.EduTrainingBaseAddDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.dto.EduTrainingBaseRecordAddDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.dto.EduTrainingBaseRecordUpdateDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.dto.EduTrainingBaseUpdateDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.entity.EduTrainingBaseEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.entity.EduTrainingBaseRecordEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.service.EduTrainingBaseRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;

import javax.servlet.http.HttpServletRequest;


/**
 * 实训基地实训记录
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-11 11:36:46
 */
@Api(tags = "实训基地实训记录")
@RestController
@RequestMapping("site/trainingBase/record")
public class EduTrainingBaseRecordController {
    @Autowired
    private EduTrainingBaseRecordService eduTrainingBaseRecordService;

    @PostMapping("/list")
    @ApiOperation("实训基地实训记录列表")
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
            PageUtils pageUtils = eduTrainingBaseRecordService.queryPage(params);

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

    @ApiOperation("实训基地实训记录新增")
    @PostMapping("/add")
    public ResultData add(@RequestBody EduTrainingBaseRecordAddDto eduTrainingBaseDto, HttpServletRequest request){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        String userId = request.getAttribute("userId").toString();
        EduTrainingBaseRecordEntity entity = ConvertUtils.convert(eduTrainingBaseDto,EduTrainingBaseRecordEntity::new);
        entity.setCreateBy(userId);
        entity.setCreateTime(new Date());
        eduTrainingBaseRecordService.insert(entity);
        EduTrainingBaseRecordEntity id = eduTrainingBaseRecordService.selectOne(new EntityWrapper<EduTrainingBaseRecordEntity>().orderBy("id", false)
                .last("limit 1"));
        resultData.setData(id.getId());
        return resultData;
    }
    @ApiOperation("实训基地实训记录查看")
    @PostMapping("/info")
    public ResultData info(@RequestBody Map<String,Object>params){
        ResultData resultData = new ResultData();
        EduTrainingBaseRecordEntity EduTrainingBaseRecordEntity = eduTrainingBaseRecordService.selectById(params.get("id").toString());
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setData(EduTrainingBaseRecordEntity);
        return resultData;
    }
    @ApiOperation("实训基地实训记录删除")
    @PostMapping("/delete")
    public ResultData delete(@RequestBody Map<String,Object>params){
        ResultData resultData = new ResultData();
        Integer id = (Integer)params.get("id");
        eduTrainingBaseRecordService.deleteById(id);
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        return resultData;
    }

    @ApiOperation("实训基地实训记录更新")
    @PostMapping("/update")
    public ResultData update(@RequestBody EduTrainingBaseRecordUpdateDto eduTrainingBaseUpdateDto){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        EduTrainingBaseRecordEntity entity = eduTrainingBaseRecordService.selectById(eduTrainingBaseUpdateDto.getId());
        if (ObjectUtils.isEmpty(entity)){
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage("找不到该记录");
        }

        BeanUtil.copyProperties(eduTrainingBaseUpdateDto,entity);
        eduTrainingBaseRecordService.updateById(entity);
        return resultData;
    }

}
