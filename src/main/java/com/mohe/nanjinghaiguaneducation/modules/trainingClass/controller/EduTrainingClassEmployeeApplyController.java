package com.mohe.nanjinghaiguaneducation.modules.trainingClass.controller;

import com.alibaba.fastjson2.JSON;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto.EdutrainingClassEmployeeApplyAddDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.EduTrainingClassEmployeeApplyService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 * 学员报名表
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-03-02 13:24:28
 */
@RestController
@RequestMapping("edutrainingClassEmployeeApply")
public class EduTrainingClassEmployeeApplyController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EduTrainingClassEmployeeApplyService eduTrainingClassEmployeeApplyService;


    /**
     * 添加学员报名列表
     */
    @PostMapping("/employeeApplyAdd")
    @ApiOperation("添加学员报名列表")
    public ResultData<Boolean> employeeApplyAdd(HttpServletRequest request, @RequestBody EdutrainingClassEmployeeApplyAddDto edutrainingClassEmployeeApplyAddDto) {
        logger.info("添加学员报名列表 参数为： {}", JSON.toJSONString(edutrainingClassEmployeeApplyAddDto));
        ResultData<Boolean> resultData = new ResultData<>();
        String userId = request.getAttribute("userId").toString();
        try {
            edutrainingClassEmployeeApplyAddDto.setUserId(userId);
            eduTrainingClassEmployeeApplyService.employeeApplyAdd(edutrainingClassEmployeeApplyAddDto);
            resultData.setData(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setSuccess(true);
            resultData.setCode(SysConstant.SUCCESS);
            return resultData;
        }catch (Exception e) {
            logger.error("添加学员报名列表异常: {}",e.getMessage());
            resultData.setCode(SysConstant.FAIL);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            resultData.setSuccess(false);
            return resultData;
        }
    }

    /**
     * 根据id查看报名人员
     */
    @PostMapping("/view")
    @ApiOperation("查看报名人员")
    public ResultData<List<Map<String,Object>>> view(@RequestBody Map<String,Object> params) {
        logger.info("查看报名人员 参数为: {}",JSON.toJSONString(params));
        ResultData<List<Map<String,Object>>> resultData = new ResultData<>();
        try {
            List<Map<String, Object>> viewEntity = eduTrainingClassEmployeeApplyService.view(params.get("classId").toString());
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setData(viewEntity);
            resultData.setSuccess(true);
            resultData.setCode(SysConstant.SUCCESS);
            return resultData;
        }catch (Exception e) {
            logger.error("查看报名人员 异常：{}",e.getMessage());
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            return resultData;
        }
    }

    @PostMapping("/del")
    @ApiOperation("报名人员")
    public ResultData<Boolean> del(@RequestBody Map<String,Object> params) {
        logger.info("删除报名人员: {}",JSON.toJSONString(params));
        ResultData<Boolean> resultData = new ResultData<>();
        try {
            eduTrainingClassEmployeeApplyService.deleteById(params.get("id").toString());
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setData(true);
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e){
            logger.error("删除报名人员异常:{}",e.getMessage());
            resultData.setCode(SysConstant.FAIL);
            resultData.setData(false);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            return resultData;
        }
    }
}
