package com.mohe.nanjinghaiguaneducation.modules.head.controller;

import java.util.*;
import java.util.stream.Collectors;

import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.ConvertUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.head.dto.EduHeadOfficeTrainingDto;
import com.mohe.nanjinghaiguaneducation.modules.head.entity.EduHeadOfficeTrainingEntity;
import com.mohe.nanjinghaiguaneducation.modules.head.service.EduHeadOfficeTrainingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * 署级培训参训情况
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-06-21 14:47:20
 */
@RestController
@Api(tags = "署级培训参训情况")
@RequestMapping("/head/office")
public class EduHeadOfficeTrainingController {
    @Autowired
    private EduHeadOfficeTrainingService eduHeadOfficeTrainingService;

    @ApiOperation("署级培训参训情况查询")
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
            PageUtils pageUtils = eduHeadOfficeTrainingService.queryPage(params);

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

    @ApiOperation("署级培训参训情况新增")
    @PostMapping("/insert")
    public ResultData insert(@RequestBody EduHeadOfficeTrainingDto eduHeadOfficeTrainingDto, HttpServletRequest request){
        ResultData resultData = new ResultData();
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        String userId = request.getAttribute("userId").toString();
        EduHeadOfficeTrainingEntity entity = ConvertUtils.convert(eduHeadOfficeTrainingDto,EduHeadOfficeTrainingEntity::new);
        entity.setCreateBy(userId);
        entity.setCreateTime(new Date());
        entity.setDisabled(0);
        eduHeadOfficeTrainingService.insert(entity);
        resultData.setData(entity.getId());
        return resultData;
    }

    public static void main(String[] args) {
        List<String> stringList = new ArrayList<>();
        stringList.add("222");
        stringList.add("22qq2");
        List<String>  filterInfo= stringList.stream().filter(orderInfo-> Boolean.FALSE?orderInfo.equals("2"):
                        orderInfo.contains("q")).collect(Collectors.toList());
        System.out.println(Arrays.asList(filterInfo));
    }

    @ApiOperation("署级培训参训情况修改")
    @PostMapping("/update")
    public ResultData update(@RequestBody EduHeadOfficeTrainingDto eduHeadOfficeTrainingDto, HttpServletRequest request){
        ResultData resultData = new ResultData();
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        String userId = request.getAttribute("userId").toString();
        EduHeadOfficeTrainingEntity entity = eduHeadOfficeTrainingService.selectById(eduHeadOfficeTrainingDto.getId());
        entity = ConvertUtils.convert(eduHeadOfficeTrainingDto,EduHeadOfficeTrainingEntity::new);

        entity.setUpdateBy(userId);
        entity.setUpdateTime(new Date());
        eduHeadOfficeTrainingService.updateById(entity);
        return resultData;
    }
    @ApiOperation("署级培训参训情况删除")
    @PostMapping("/delete")
    public ResultData delete(@RequestBody Map<String,Object>params){
        ResultData resultData = new ResultData();
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        Integer id =(Integer) params.get("id");
        boolean b = eduHeadOfficeTrainingService.deleteById(id);
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
