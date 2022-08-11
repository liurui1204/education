package com.mohe.nanjinghaiguaneducation.modules.site.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.exception.RRException;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.employee.service.EduEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.resource.entity.EduResourceEntity;
import com.mohe.nanjinghaiguaneducation.modules.site.dto.EduSiteTeacherAttachListRequestDto;
import com.mohe.nanjinghaiguaneducation.modules.site.dto.EduSiteTeacherDetailInfoDto;
import com.mohe.nanjinghaiguaneducation.modules.site.dto.EduSiteTeacherDetailRequestDto;
import com.mohe.nanjinghaiguaneducation.modules.site.dto.EduSiteTeacherListFrontDto;
import com.mohe.nanjinghaiguaneducation.modules.teacherInner.entity.EduInnerTeacherEntity;
import com.mohe.nanjinghaiguaneducation.modules.teacherInner.service.EduInnerTeacherService;
import com.mohe.nanjinghaiguaneducation.modules.teacherOuter.entity.EduOuterTeacher;
import com.mohe.nanjinghaiguaneducation.modules.teacherOuter.service.EduOutTeacherService;
import com.mohe.nanjinghaiguaneducation.modules.teacherOuter.service.impl.EduOuterTeacherServiceImpl;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassAttachEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassCourseEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.EduTrainingClassAttachService;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.EduTrainingClassCourseService;
import io.jsonwebtoken.lang.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/front/teacher")
@Api(tags = "前端接口-教师")
public class EduSiteTeacherFrontController {

    @Autowired
    private EduInnerTeacherService eduInnerTeacherService;
    @Autowired
    private EduOutTeacherService eduOutTeacherService;

    @Autowired
    private EduTrainingClassCourseService eduTrainingClassCourseService;
    @Autowired
    private EduTrainingClassAttachService eduTrainingClassAttachService;
    @Autowired
    private EduEmployeeService eduEmployeeService;

    @ApiOperation("师资库列表")
    @PostMapping("/list")
    public ResultData<PageUtils> teacherListFront(@RequestBody EduSiteTeacherListFrontDto eduSiteTeacherListFrontDto){
        ResultData<PageUtils> resultData = new ResultData<>();
        try {
            if(BeanUtil.isEmpty(eduSiteTeacherListFrontDto.getType()) || eduSiteTeacherListFrontDto.getType()<1 || eduSiteTeacherListFrontDto.getType()>2){
                throw new RRException("教师类型只能是1/2");
            }
            // 1-内聘教师 2-外聘教师
            Map<String, Object> params = new HashMap<>();
            params.put("page", eduSiteTeacherListFrontDto.getPage());
            params.put("limit", eduSiteTeacherListFrontDto.getLimit());
            if(eduSiteTeacherListFrontDto.getType()==1){
                PageUtils pageUtils = eduInnerTeacherService.queryPageFront(params);
                resultData.setData(pageUtils);
            }else{
                PageUtils pageUtils = eduOutTeacherService.queryPageFront(params);
                resultData.setData(pageUtils);
            }
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
        }catch (Exception e) {
            e.printStackTrace();
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.PARAMETER_ERROR);
            resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
        }
        return resultData;
    }

    @ApiOperation("教师专题介绍页面")
    @PostMapping("/detail/info")
    public ResultData<EduSiteTeacherDetailInfoDto> teacherDetailInfo(@RequestBody EduSiteTeacherDetailRequestDto eduTeacherGetDetailDto){
        ResultData<EduSiteTeacherDetailInfoDto> resultData = new ResultData<>();
        try {
            EduSiteTeacherDetailInfoDto eduSiteTeacherDetailInfoDto = new EduSiteTeacherDetailInfoDto();
            Integer teacherType = eduTeacherGetDetailDto.getType();
            if(teacherType!=2 && teacherType!=1){
                throw new RRException("教师类型错误");
            }
            String belong = "无";
            if(teacherType==1){
                //内聘
                EduInnerTeacherEntity info = eduInnerTeacherService.selectOne(new EntityWrapper<EduInnerTeacherEntity>()
                        .eq("id", eduTeacherGetDetailDto.getId()));
                BeanUtil.copyProperties(info, eduSiteTeacherDetailInfoDto);
                //所属部门，内聘教师，到 “处级” 即可
                String employeeId = info.getEmployeeId();
                if(!"".equals(employeeId)){
                    EduEmployeeEntity eduEmployeeEntity = eduEmployeeService.selectById(employeeId);
                    String h4aAllPathName = eduEmployeeEntity.getH4aAllPathName();
                    if(!"".equals(h4aAllPathName)){
//                        String[] splits = Strings.split(h4aAllPathName, "\\");
                        String[] splits = h4aAllPathName.split("\\\\");
                        if(splits.length>=3){
                            belong = splits[2];
                        }
                    }
                }
                eduSiteTeacherDetailInfoDto.setBelongDepartment(belong);
            }else{
                EduOuterTeacher info = eduOutTeacherService.selectOne(new EntityWrapper<EduOuterTeacher>()
                        .eq("id", eduTeacherGetDetailDto.getId()));
                BeanUtil.copyProperties(info, eduSiteTeacherDetailInfoDto);
                if(!"".equals(info.getCompany())){
                    belong = info.getCompany();
                }
                eduSiteTeacherDetailInfoDto.setBelongDepartment(belong);
            }
            eduSiteTeacherDetailInfoDto.setType(eduTeacherGetDetailDto.getType());
            //讲师讲课数
            int courseTotalCount = eduTrainingClassCourseService.selectCount(new EntityWrapper<EduTrainingClassCourseEntity>()
                    .eq("teacherId", eduTeacherGetDetailDto.getId()));
            eduSiteTeacherDetailInfoDto.setCourseTotalCount(courseTotalCount);
            //教师课件数
            int courseDataCount = eduTrainingClassAttachService.getCountByTeacherId(eduTeacherGetDetailDto.getId());
            eduSiteTeacherDetailInfoDto.setCourseDataCount(courseDataCount);
            resultData.setData(eduSiteTeacherDetailInfoDto);
            resultData.setSuccess(true);
            resultData.setCode(SysConstant.SUCCESS);
        }catch (Exception e) {
            e.printStackTrace();
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.PARAMETER_ERROR);
            resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
        }
        return resultData;
    }

    @ApiOperation("教师专题介绍页面 课件列表")
    @PostMapping("/detail/attachEntities")
    public ResultData<PageUtils> teacherListFront(@RequestBody EduSiteTeacherAttachListRequestDto eduSiteTeacherAttachListRequestDto){
        ResultData<PageUtils> resultData = new ResultData<>();
        try {
            int page = eduSiteTeacherAttachListRequestDto.getPage();
            int limit = eduSiteTeacherAttachListRequestDto.getLimit();
            if(limit==0){
                limit = 10;
            }
            if(page>0){
                page = page-1;
            }
            Map<String, Object> params = new HashMap<>();
            params.put("page", page);
            params.put("limit", limit);
//            params.put("teacherId", eduSiteTeacherAttachListRequestDto.getId());
            PageUtils pageUtils = eduTrainingClassAttachService.queryPageByTeacherId(eduSiteTeacherAttachListRequestDto.getId(), page, limit);
            resultData.setData(pageUtils);
            resultData.setSuccess(true);
            resultData.setCode(SysConstant.SUCCESS);
        }catch (Exception e) {
            e.printStackTrace();
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.PARAMETER_ERROR);
            resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
        }
        return resultData;
    }
}
