package com.mohe.nanjinghaiguaneducation.modules.honor.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.ConvertUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.head.dto.EduHeadOfficeTrainingDto;
import com.mohe.nanjinghaiguaneducation.modules.head.entity.EduHeadOfficeTrainingEntity;
import com.mohe.nanjinghaiguaneducation.modules.honor.dto.EduTrainingHonorSystemDto;
import com.mohe.nanjinghaiguaneducation.modules.honor.entity.EduTrainingHonorSystemEntity;
import com.mohe.nanjinghaiguaneducation.modules.honor.service.EduTrainingHonorSystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * 教培荣誉体系
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-06-22 13:45:24
 */
@RestController
@Api(tags = "教培荣誉体系接口")
@RequestMapping("honor")
public class EduTrainingHonorSystemController {
    @Autowired
    private EduTrainingHonorSystemService eduTrainingHonorSystemService;

    @PostMapping("/list")
    @ApiOperation("查询荣誉体系")
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
            PageUtils pageUtils = eduTrainingHonorSystemService.queryPage(params);

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

    @ApiOperation("新增荣誉体系")
    @PostMapping("/insert")
    public ResultData insert(@RequestBody EduTrainingHonorSystemDto eduTrainingHonorSystemDto, HttpServletRequest request){
        ResultData resultData = new ResultData();
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        String userId = request.getAttribute("userId").toString();
        EduTrainingHonorSystemEntity entity = ConvertUtils.convert(eduTrainingHonorSystemDto,EduTrainingHonorSystemEntity::new);
        entity.setCreateBy(userId);
        entity.setCreateTime(new Date());
        entity.setDisabled(0);
        eduTrainingHonorSystemService.insert(entity);
        resultData.setData(entity.getId());
        return resultData;
    }

    @ApiOperation("修改荣誉体系")
    @PostMapping("/update")
    public ResultData update(@RequestBody EduTrainingHonorSystemDto eduTrainingHonorSystemDto, HttpServletRequest request){
        ResultData resultData = new ResultData();
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        String userId = request.getAttribute("userId").toString();
        EduTrainingHonorSystemEntity entity = eduTrainingHonorSystemService.selectById(eduTrainingHonorSystemDto.getId());
        entity = ConvertUtils.convert(eduTrainingHonorSystemDto,EduTrainingHonorSystemEntity::new);
        entity.setUpdateBy(userId);
        entity.setUpdateTime(new Date());
        eduTrainingHonorSystemService.updateById(entity);
        return resultData;
    }
    @ApiOperation("删除荣誉体系")
    @PostMapping("/delete")
    public ResultData delete(@RequestBody Map<String,Object>params){
        ResultData resultData = new ResultData();
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        Integer id =(Integer) params.get("id");
        boolean b = eduTrainingHonorSystemService.deleteById(id);
        if (b){
            return resultData;
        }else {
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.PARAMETER_ERROR);
            resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
            return resultData;
        }
    }


}
