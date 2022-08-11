package com.mohe.nanjinghaiguaneducation.modules.trainingClass.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.ConvertUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto.*;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.*;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Api(tags = "课程信息管理")
@RestController
@RequestMapping("/trainingClass/course")
public class EduTrainingClassCourseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EduTrainingClassCourseService eduTrainingClassCourseService;

    @PostMapping("/add")
    @ApiOperation("新增课程信息")
    public ResultData<String> add(@RequestBody EduTrainingClassCourseDto eduTrainingClassCourseDto,HttpServletRequest request){
        ResultData<String>resultData = new ResultData<>();
        if (ObjectUtils.isEmpty(eduTrainingClassCourseDto)){
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.FAIL);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            return resultData;
        }
        String id = IdUtil.simpleUUID();
        EduTrainingClassCourseEntity eduTrainingClassCourseEntity = ConvertUtils.convert(eduTrainingClassCourseDto,EduTrainingClassCourseEntity::new);
        //type string -> integer
        int teacherTypeInt = Integer.parseInt(eduTrainingClassCourseDto.getTeacherType());
        eduTrainingClassCourseEntity.setTeacherType(teacherTypeInt);
        eduTrainingClassCourseEntity.setCreateTime(new Date());
        EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity) request.getAttribute("userInfo");
        eduTrainingClassCourseEntity.setCreateBy(eduEmployeeEntity.getEmployeeCode());
        eduTrainingClassCourseEntity.setId(id);
        eduTrainingClassCourseEntity.setIsEnable(1);
        boolean insert = eduTrainingClassCourseService.insert(eduTrainingClassCourseEntity);
        if (insert==false){
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.FAIL);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            return resultData;
        }
        resultData.setSuccess(true);
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setData(id);
        return resultData;
    }

    @PostMapping("/list")
    @ApiOperation("课程信息列表")
    public ResultData<PageUtils> list(@RequestBody Map<String, Object> params){
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
            PageUtils pageUtils = eduTrainingClassCourseService.queryPage(params);
            resultData.setData(pageUtils);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e) {
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.PARAMETER_ERROR);
            resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
            return resultData;
        }
    }


    @PostMapping("/trainingPlanAdd")
    @ApiOperation("培训计划课程信息新增")
    public ResultData trainingPlanAdd(HttpServletRequest request,@RequestBody TrainingPlanAddDto trainingPlanAddDto) {
        logger.info("培训计划课程信息新增：{}", JSON.toJSONString(trainingPlanAddDto));
        ResultData resultData = new ResultData();
        try {
            EduTrainingClassCourseEntity eduTrainingClassCourseEntity = new EduTrainingClassCourseEntity();
            BeanUtil.copyProperties(trainingPlanAddDto,eduTrainingClassCourseEntity);
            String id  = IdUtil.simpleUUID();
            eduTrainingClassCourseEntity.setCreateBy(request.getAttribute("userId").toString());
            eduTrainingClassCourseEntity.setCreateTime(new Date());
            eduTrainingClassCourseEntity.setIsEnable(1);
            eduTrainingClassCourseEntity.setId(id);
            eduTrainingClassCourseEntity.setStatus(1);
            eduTrainingClassCourseService.insert(eduTrainingClassCourseEntity);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e) {
            logger.error("培训计划课程信息新增异常: {}",e.getMessage());
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.FAIL);
            return resultData;
        }
    }

    @ApiOperation("培训计划课程列表")
    @PostMapping("/queryCourseList")
    public ResultData<List<TrainingPlanAddDto>> queryCourseList(@RequestBody Map<String,Object> params){
        logger.info("培训计划课程列表查询: {}");
        ResultData<List<TrainingPlanAddDto>> resultData = new ResultData<>();
        List<TrainingPlanAddDto> list = new ArrayList<>();
        try {
            String planId = params.get("id").toString();
            List<EduTrainingClassCourseEntity> trainingClassCourseEntities = eduTrainingClassCourseService.selectList(new EntityWrapper<EduTrainingClassCourseEntity>().eq("trainingClassId", planId));
            for (EduTrainingClassCourseEntity trainingClassCourseEntity : trainingClassCourseEntities) {
                TrainingPlanAddDto trainingPlanAddDto = new TrainingPlanAddDto();
                BeanUtil.copyProperties(trainingClassCourseEntity,trainingPlanAddDto);
                list.add(trainingPlanAddDto);
            }
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setData(list);
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            return resultData;
        }catch (Exception e) {
            logger.error("培训计划课程列表查询异常：{}",e.getMessage());
            resultData.setSuccess(false);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            resultData.setCode(SysConstant.FAIL);
            return resultData;
        }
    }


    /**
     * 课程删除
     */
    @PostMapping("/courseDel")
    @ApiOperation("课程删除")
    public ResultData courseDel(@RequestBody Map<String,Object> params) {
        logger.info("培训计划培训班课程删除",JSON.toJSONString(params));
        ResultData resultData = new ResultData();
        try {
            String id = params.get("id").toString();
            eduTrainingClassCourseService.deleteById(id);
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            return resultData;
        }catch (Exception e){
            logger.error("培训计划培训班课程删除异常原因为:{}",e.getMessage());
            resultData.setSuccess(false);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            resultData.setCode(SysConstant.FAIL);
            return resultData;
        }
    }

    @ApiOperation("下拉框查询课程信息")
    @PostMapping("/select")
    public ResultData select(@RequestBody Map<String,Object> params){
        ResultData resultData = new ResultData();
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setCode(SysConstant.SUCCESS);
        String classId = params.get("classId").toString();
        List<EduTrainingClassCourseEntity> eduTrainingClassCourseEntitys = eduTrainingClassCourseService.selectList(new EntityWrapper<EduTrainingClassCourseEntity>()
                .eq("trainingClassId", classId));
        resultData.setData(eduTrainingClassCourseEntitys);
        return resultData;
    }
}
