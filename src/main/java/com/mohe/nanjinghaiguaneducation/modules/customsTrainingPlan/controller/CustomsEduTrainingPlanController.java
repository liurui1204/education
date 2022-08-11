package com.mohe.nanjinghaiguaneducation.modules.customsTrainingPlan.controller;

import com.alibaba.fastjson2.JSON;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.customsTrainingPlan.service.CustomsEduTrainingPlanService;
import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@Api(tags = "关区培训计划接口")
@RequestMapping("/trainingPlan")
public class CustomsEduTrainingPlanController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CustomsEduTrainingPlanService customsEduTrainingPlanService;

    /**
     * 分页查询关区培训计划列表
     */
    @PostMapping("/list")
    public ResultData<PageUtils> list(HttpServletRequest request, @RequestBody Map<String, Object> params) {
        logger.info("分页查询关区培训计划列表  参数为： {}", JSON.toJSONString(params));
        ResultData<PageUtils> resultData = new ResultData<>();
        EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity) request.getAttribute("userInfo");
        logger.info("当前登录的用户信息为： {}", JSON.toJSONString(eduEmployeeEntity));
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
            PageUtils pageUtils = customsEduTrainingPlanService.queryPage(params);
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setData(pageUtils);
            return resultData;
        } catch (Exception e) {
            logger.error("分页查询关区培训计划列表  异常原因为： {}", e.getMessage());
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            return resultData;
        }
    }
}
