package com.mohe.nanjinghaiguaneducation.modules.site.controller;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.ConvertUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.site.dto.EduSiteQualityCoursesDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassCourseEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.EduTrainingClassCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Api(tags = "精品课程维护")
@RestController
@RequestMapping("site/qualityCourses")
public class EduSiteQualityCoursesController {

    @Autowired
    private EduTrainingClassCourseService eduTrainingClassCourseService;

    @ApiOperation("课程列表")
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
            PageUtils pageUtils = eduTrainingClassCourseService.queryQualityPage(params);

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


    @PostMapping("/findByName")
    public ResultData findByName(@RequestBody Map<String,Object> params){
        ResultData resultData = new ResultData<>();
        String name = params.get("name").toString();
        if (ObjectUtils.isEmpty(name)){
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.PARAMETER_ERROR);
            resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
            return resultData;
        }
        List<EduTrainingClassCourseEntity> entityList = eduTrainingClassCourseService.selectList(new EntityWrapper<EduTrainingClassCourseEntity>()
                .like("courseWare", name).orderBy("createTime",false));
        for (EduTrainingClassCourseEntity entity: entityList) {
            if(null == entity.getCreateTime()){
                entity.setCreateTime(new Date());
            }
        }
        resultData.setData(entityList);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        return resultData;
    }
    @ApiOperation("设置精品课程")
    @PostMapping("/update")
    public ResultData update(@RequestBody EduSiteQualityCoursesDto eduSiteQualityCoursesDto){
        ResultData resultData = new ResultData<>();
        EduTrainingClassCourseEntity eduTrainingClassCourseEntity = ConvertUtils.convert(eduSiteQualityCoursesDto,EduTrainingClassCourseEntity::new);
        eduTrainingClassCourseService.updateById(eduTrainingClassCourseEntity);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        return resultData;
    }

    @ApiOperation("删除精品课程")
    @PostMapping("/delete")
    public ResultData update(@RequestBody Map<String,Object> params){
        ResultData resultData = new ResultData<>();
        String id = params.get("id").toString();
        eduTrainingClassCourseService.deleteById(id);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        return resultData;
    }

    @ApiOperation("新增课程维护")
    @PostMapping("/add")
    public ResultData add(@RequestBody EduSiteQualityCoursesDto eduSiteQualityCoursesDto){
        ResultData resultData = new ResultData<>();
        String id = IdUtil.simpleUUID();
        EduTrainingClassCourseEntity eduTrainingClassCourseEntity = ConvertUtils.convert(eduSiteQualityCoursesDto,EduTrainingClassCourseEntity::new);
        eduTrainingClassCourseEntity.setId(id);
        eduTrainingClassCourseEntity.setCreateTime(new Date());
        eduTrainingClassCourseService.insert(eduTrainingClassCourseEntity);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setData(id);
        return resultData;
    }

}
