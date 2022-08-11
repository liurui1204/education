package com.mohe.nanjinghaiguaneducation.modules.trainingClass.controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.google.common.collect.Lists;
import com.mohe.nanjinghaiguaneducation.common.constant.CustomsScheduleConst;
import com.mohe.nanjinghaiguaneducation.common.constant.NanJingNumberGenerateConstant;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.CustomsScheduleManage;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.DeleteTaskEntity;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.Dto.InsertTaskDto;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.EntityFactory;
import com.mohe.nanjinghaiguaneducation.common.exception.RRException;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.*;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.*;
import com.mohe.nanjinghaiguaneducation.modules.common.service.*;
import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.employee.service.EduEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.cons.FeeItemEnum;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.vo.EduTrainingClassAllExcel;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.vo.EduTrainingClassFeeItemExcelVo;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.entity.EduFlowTraceEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.entity.EduTrainingPlanEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.service.EduFlowTraceService;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.service.EduTrainingPlanService;
import com.mohe.nanjinghaiguaneducation.modules.teacherInner.entity.EduInnerTeacherEntity;
import com.mohe.nanjinghaiguaneducation.modules.teacherInner.service.EduInnerTeacherService;
import com.mohe.nanjinghaiguaneducation.modules.teacherOuter.entity.EduOuterTeacher;
import com.mohe.nanjinghaiguaneducation.modules.teacherOuter.service.EduOutTeacherService;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto.*;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.*;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.*;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.vo.EduTrainingClassExcelVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* Swagger2 注解
 * * @Api：修饰整个类，描述Controller的作用
 * * @ApiOperation：描述一个类的一个方法，或者说一个接口
 * * @ApiParam：单个参数描述
 * * @ApiModel：用对象来接收参数
 * * @ApiProperty：用对象接收参数时，描述对象的一个字段
 * * @ApiResponse：HTTP响应其中1个描述
 * * @ApiResponses：HTTP响应整体描述
 * * @ApiIgnore：使用该注解忽略这个API
 * * @ApiError ：发生错误返回的信息
 * * @ApiImplicitParam：描述一个请求参数，可以配置参数的中文含义，还可以给参数设置默认值
 * * @ApiImplicitParams：描述由多个 @ApiImplicitParam 注解的参数组成的请求参数列表
 */

/**
 * 培训班
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-02-16 11:35:05
 */
@Api(tags = "培训班管理")
@RestController
@RequestMapping("/trainingClass")
public class EduTrainingClassController {

    @Autowired
    private EduTrainingClassAttachService eduTrainingClassAttachService;
    @Autowired
    private EduTrainingClassService eduTrainingClassService;

    @Autowired
    private EduTrainingPlanService eduTrainingPlanService;

    @Autowired
    private EduTrainingClassFeeItemService eduTrainingClassFeeItemService;

    @Autowired
    private EduTrainingClassStudyInfoService eduTrainingClassStudyInfoService;

    @Autowired
    private EduTrainingClassEmployeeApplyService eduTrainingClassEmployeeApplyService;
    @Autowired
    private EduTrainingClassEmployeeLeaveService eduTrainingClassEmployeeLeaveService;

    @Autowired
    private NumberSequenceUtil numberSequenceUtil;
    @Autowired
    private EduTrainingClassCourseService eduTrainingClassCourseService;

    @Autowired
    private EduFlowTraceService eduFlowTraceService;
    @Autowired
    private EduEmployeeService eduEmployeeService;
    @Autowired
    private EduInnerTeacherService eduInnerTeacherService;
    @Autowired
    private EduOutTeacherService eduOutTeacherService;

    @Autowired
    private EduSystemConfirmService eduSystemConfirmService;

    @Autowired
    private EduSystemTrainingTypeService eduSystemTrainingTypeService;
    @Autowired
    private EduSystemTrainingObjectService eduSystemTrainingObjectService;
    @Autowired
    private EduSystemTrainingGoalService eduSystemTrainingGoalService;

    @Autowired
    private EduSystemCustomsScheduleService eduSystemCustomsScheduleService;

    /**
     * 列表
     */
    @ApiOperation("培训班列表（status: 1-全部 2-进行中 3-已完成 4-已延期 / trainingWay: 1线上  0线下 / 必填参数： menuCode-菜单view中children里面的code 角色编码：roleCode）")
    @PostMapping("/list")
    public ResultData<PageUtils> list(HttpServletRequest request, @RequestBody Map<String, Object> params){
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
            params.put("employeeId", request.getAttribute("userId"));
            PageUtils pageUtils = eduTrainingClassService.queryPage(params);

            resultData.setData(pageUtils);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e) {
            e.printStackTrace();
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.PARAMETER_ERROR);
//            resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
            resultData.setMessage(e.getMessage());
            return resultData;
        }
    }

    @ApiOperation("待审核培训班列表")
    @PostMapping("/check/list")
    public ResultData<PageUtils> checkList(@RequestBody Map<String, Object> params){
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
            params.put("status", "0"); //意思是已经送审了，但是还没有审核。所以不包含草稿-1
            PageUtils pageUtils = eduTrainingClassService.queryPage(params);
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

    @PostMapping("/toAdd")
    @ApiOperation("添加培训班草稿")
    public ResultData<Map<String,Object>> toAdd(HttpServletRequest request, @ApiParam(value = "添加培训班草稿参数") @RequestBody EduTrainingClassDto eduTrainingClassDto){
        ResultData<Map<String,Object>> resultData = new ResultData<>();
        try {
            if (BeanUtil.isEmpty(eduTrainingClassDto)) {
                resultData.setCode(SysConstant.FAIL);
                resultData.setSuccess(false);
                resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
                return resultData;
            }
            EduTrainingClassEntity eduTrainingClassEntity = new EduTrainingClassEntity();
            BeanUtil.copyProperties(eduTrainingClassDto, eduTrainingClassEntity, true);
            String id = IdUtil.simpleUUID();
            eduTrainingClassEntity.setId(id);
            //默认是 phase 1
            eduTrainingClassEntity.setPhase(1);
            eduTrainingClassEntity.setStatus(1);
            //培训计划信息带出
            EduTrainingPlanEntity eduTrainingPlanEntity = eduTrainingPlanService.selectById(eduTrainingClassDto.getTrainingPlanId());
            eduTrainingClassEntity.setTrainingPlanName(eduTrainingPlanEntity.getPlanName());

            //创建时间
            eduTrainingClassEntity.setCreateTime(new Date());
            eduTrainingClassEntity.setUpdateTime(new Date());
            //创建人信息
            EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity) request.getAttribute("userInfo");
            eduTrainingClassEntity.setCreateBy(eduEmployeeEntity.getId());
            //申请人部门信息要检查
            String applyDepartmentId = eduTrainingClassEntity.getApplyDepartmentId();
            String applyDepartmentName = eduTrainingClassEntity.getApplyDepartmentName();
            if(BeanUtil.isEmpty(applyDepartmentId) || BeanUtil.isEmpty(applyDepartmentName)){
                resultData.setCode(SysConstant.FAIL);
                resultData.setSuccess(false);
                //resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
                resultData.setMessage("申请人部门有误，请刷新后重试");
                return resultData;
            }
            //是否可用：默认可用 1
            eduTrainingClassEntity.setIsEnable(1);
            //生成培训班编号 暂时弃用
            //NumberSequenceUtil numberSequenceUtil = new NumberSequenceUtil();
            //eduTrainingClassEntity.setClassCode(numberSequenceUtil.getNumberSequenceByService("GQ"));
            //带出培训班的编号,不可修改
            //eduTrainingClassEntity.setClassCode(eduTrainingPlanEntity.getPlanCode());

            //插入培训班
            //eduTrainingClassService.insert(eduTrainingClassEntity);

            //学时学分相关信息
            List<EduTrainingClassStudyInfoEntity> eduTrainingClassStudyInfoEntities = new ArrayList<>();
            if (ObjectUtils.isEmpty(eduTrainingClassDto.getEduTrainingClassStudyInfoDtos())){
                resultData.setCode(SysConstant.FAIL);
                resultData.setSuccess(false);
                //resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
                resultData.setMessage("学时学分信息不能为空");
                return resultData;
            }
            for(EduTrainingClassStudyInfoDto eduTrainingClassStudyInfoDto : eduTrainingClassDto.getEduTrainingClassStudyInfoDtos()){
                EduTrainingClassStudyInfoEntity eduTrainingClassStudyInfoEntity = new EduTrainingClassStudyInfoEntity();
                eduTrainingClassStudyInfoEntity.setStudyHourType(eduTrainingClassStudyInfoDto.getStudyHourType());
                eduTrainingClassStudyInfoEntity.setStudyHour(eduTrainingClassStudyInfoDto.getStudyHour());
                eduTrainingClassStudyInfoEntity.setStudyBelong(eduTrainingClassStudyInfoDto.getStudyBelong());
                eduTrainingClassStudyInfoEntity.setStudyScore(eduTrainingClassStudyInfoDto.getStudyScore());
                String studyInfoId = IdUtil.simpleUUID();
                eduTrainingClassStudyInfoEntity.setId(studyInfoId);
                //加入默认值
                eduTrainingClassStudyInfoEntity.setOperator(eduEmployeeEntity.getEmployeeName());
                eduTrainingClassStudyInfoEntity.setCreateTime(new Date());
                eduTrainingClassStudyInfoEntity.setCreateBy(eduEmployeeEntity.getId());
                eduTrainingClassStudyInfoEntity.setUpdateTime(new Date());
                eduTrainingClassStudyInfoEntity.setUpdateBy(eduEmployeeEntity.getId());
                eduTrainingClassStudyInfoEntity.setIsEnable(1);//是否可用 默认 1
                eduTrainingClassStudyInfoEntities.add(eduTrainingClassStudyInfoEntity);
            }

            //费用信息
//            EduTrainingClassFeeItemDto eduTrainingClassFeeItemDto = eduTrainingClassDto.getEduTrainingClassFeeItemDto();
//            if (ObjectUtils.isEmpty(eduTrainingClassDto.getEduTrainingClassFeeItemDto())){
//                resultData.setCode(SysConstant.FAIL);
//                resultData.setSuccess(false);
//                //resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
//                resultData.setMessage("培训费目不能为空");
//            }

            List<EduTrainingClassFeeItemDto> feeItemDtos = eduTrainingClassDto.getEduTrainingClassFeeItemDto();
            List<EduTrainingClassFeeItemEntity> eduTrainingClassFeeItemEntities = new ArrayList<>();
            List<EduTrainingClassFeeItemIdEntity> entities  = new ArrayList<>();
            if (ObjectUtils.isEmpty(feeItemDtos)){
                resultData.setCode(SysConstant.FAIL);
                resultData.setSuccess(false);
                //resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
                resultData.setMessage("培训费目不能为空");
                return resultData;
            }
            for(EduTrainingClassFeeItemDto feeItemDto : feeItemDtos){
                EduTrainingClassFeeItemEntity eduTrainingClassFeeItemEntity = new EduTrainingClassFeeItemEntity();
                //默认值
                eduTrainingClassFeeItemEntity.setCreateTime(new Date());
                eduTrainingClassFeeItemEntity.setCreateBy(eduEmployeeEntity.getEmployeeName());
                eduTrainingClassFeeItemEntity.setUpdateTime(new Date());
                eduTrainingClassFeeItemEntity.setUpdateBy(eduEmployeeEntity.getEmployeeName());
                eduTrainingClassFeeItemEntity.setIsEnable(1);//是否可用 默认 1
                BeanUtil.copyProperties(feeItemDto, eduTrainingClassFeeItemEntity);
                if (ObjectUtils.isEmpty(eduTrainingClassFeeItemEntity.getId())){
                    EduTrainingClassFeeItemIdEntity entity = new EduTrainingClassFeeItemIdEntity();
                    String feeItemId = IdUtil.simpleUUID();
                    eduTrainingClassFeeItemEntity.setId(feeItemId);
                    eduTrainingClassFeeItemEntities.add(eduTrainingClassFeeItemEntity);
                    entity.setFeeType(eduTrainingClassFeeItemEntity.getFeeType());
                    entity.setId(feeItemId);
                    entities.add(entity);
                    continue;
                }
                eduTrainingClassFeeItemService.updateById(eduTrainingClassFeeItemEntity);
            }

            //插入对应的审核表
            EduSystemConfirmEntity confirmEntity = new EduSystemConfirmEntity();
            confirmEntity.setType(2);
            confirmEntity.setOriginalId(id);
            confirmEntity.setLastModify(new Date());
            confirmEntity.setCreateTime(new Date());
            confirmEntity.setPhase(1);
            confirmEntity.setStatus(1);
            confirmEntity.setConfirmEmployeeId(eduTrainingClassDto.getCheckBy());
            if(BeanUtil.isEmpty(eduTrainingClassDto.getCheckByName())){
                //前端没给传审核人名字，那就自己取
                EduEmployeeEntity confirmEmpINfo = eduEmployeeService.selectById(eduTrainingClassDto.getCheckBy());
                confirmEntity.setConfirmEmployeeName(confirmEmpINfo.getEmployeeName());
            }else{
                confirmEntity.setConfirmEmployeeName(eduTrainingClassDto.getCheckByName());
            }
            eduSystemConfirmService.insert(confirmEntity);

//            EduTrainingClassFeeItemEntity eduTrainingClassFeeItemEntity = new EduTrainingClassFeeItemEntity();
//            eduTrainingClassFeeItemEntity.setFee(eduTrainingClassFeeItemDto.getFee());
//            eduTrainingClassFeeItemEntity.setClassHour(eduTrainingClassFeeItemDto.getClassHour());
//            eduTrainingClassFeeItemEntity.setPeopleNum(eduTrainingClassFeeItemDto.getPeopleNum());
//            eduTrainingClassFeeItemEntity.setTeacherFee(eduTrainingClassFeeItemDto.getTeacherFee());
//            eduTrainingClassFeeItemEntity.setTotalFee(eduTrainingClassFeeItemDto.getTotalFee());
//            //默认值
//            eduTrainingClassFeeItemEntity.setFeeType(2);//1 预计费用 2申请费用 3教育处初核费用  4教育处复核费用
//            eduTrainingClassFeeItemEntity.setCreateTime(new Date());
//            eduTrainingClassFeeItemEntity.setCreateBy(eduEmployeeEntity.getEmployeeName());
//            eduTrainingClassFeeItemEntity.setUpdateTime(new Date());
//            eduTrainingClassFeeItemEntity.setUpdateBy(eduEmployeeEntity.getEmployeeName());
//            eduTrainingClassFeeItemEntity.setIsEnable(1);//是否可用 默认 1

            EduFlowTraceEntity entity = new EduFlowTraceEntity();
            entity.setCreateBy(eduEmployeeEntity.getId());
            entity.setOperationLink("新建培训班");
            entity.setRelevanceId(id);
            entity.setId(IdUtil.simpleUUID());
            entity.setTransactionMotion("新增");
            entity.setCreateBy(eduEmployeeEntity.getId());
            entity.setCreateTime(new Date());
            //插入
            eduTrainingClassService.createTrainingClass(eduTrainingClassEntity,
                    eduTrainingClassFeeItemEntities,
                    eduTrainingClassStudyInfoEntities,entity, eduTrainingClassDto.getCheckBy());


            Map<String,Object>map = new HashMap<>();
            map.put("studyInfo",eduTrainingClassStudyInfoEntities);
            map.put("classId",id);
            map.put("feeItem",entities);
            resultData.setSuccess(true);
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setData(map);
            return resultData;
        }catch (Exception e){
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            //resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            resultData.setMessage(e.getMessage());
            System.out.println(e.getMessage());
            return resultData;
        }
    }

    @PostMapping("/toEdit")
    @ApiOperation("编辑培训班草稿")
    public ResultData<String> toEdit(HttpServletRequest request, @ApiParam(value = "编辑培训班草稿参数") @RequestBody EduTrainingClassDto eduTrainingClassDto){
        ResultData<String> resultData = new ResultData<>();
        try {
            if (BeanUtil.isEmpty(eduTrainingClassDto)) {
                resultData.setCode(SysConstant.FAIL);
                resultData.setSuccess(false);
                resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
                return resultData;
            }
            EduTrainingClassEntity eduTrainingClassEntity = new EduTrainingClassEntity();
            BeanUtil.copyProperties(eduTrainingClassDto, eduTrainingClassEntity, true);
            String id = eduTrainingClassDto.getId();
            eduTrainingClassEntity.setId(id);
            eduTrainingClassEntity.setClassName(eduTrainingClassDto.getClassName());
            eduTrainingClassEntity.setTrainingTrainee(eduTrainingClassDto.getTrainingTrainee());//培训对象
            eduTrainingClassEntity.setEnrollStartTime(eduTrainingClassDto.getEnrollStartTime());
            eduTrainingClassEntity.setEnrollEndTime(eduTrainingClassDto.getEnrollEndTime());
            eduTrainingClassEntity.setTrainingAddr(eduTrainingClassDto.getTrainingAddr());
            eduTrainingClassEntity.setTrainingContent(eduTrainingClassDto.getTrainingContent());
            eduTrainingClassEntity.setTrainingObjective(eduTrainingClassDto.getTrainingObjective());
            eduTrainingClassEntity.setTrainingType(eduTrainingClassDto.getTrainingType());
            eduTrainingClassEntity.setApplyDepartmentId(eduTrainingClassDto.getApplyDepartmentId());
            eduTrainingClassEntity.setTrainingPeopleNum(eduTrainingClassDto.getTrainingPeopleNum());
            eduTrainingClassEntity.setFeeOrigin(eduTrainingClassDto.getFeeOrigin());
            eduTrainingClassEntity.setTel(eduTrainingClassDto.getTel());
            eduTrainingClassEntity.setTrainingWay(eduTrainingClassDto.getTrainingWay());
            eduTrainingClassEntity.setNeedAssess(eduTrainingClassDto.getNeedAssess());
            eduTrainingClassEntity.setMemo(eduTrainingClassDto.getMemo());
            //TODO 根据部门id获取部门name？
            //创建人信息
            EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity) request.getAttribute("userInfo");
            //学时学分相关信息
            List<EduTrainingClassStudyInfoEntity> eduTrainingClassStudyInfoEntities = new ArrayList<>();
            if(eduTrainingClassDto.getEduTrainingClassStudyInfoDtos() != null){
                for(EduTrainingClassStudyInfoDto eduTrainingClassStudyInfoDto : eduTrainingClassDto.getEduTrainingClassStudyInfoDtos()){
                    EduTrainingClassStudyInfoEntity eduTrainingClassStudyInfoEntity = new EduTrainingClassStudyInfoEntity();
                    eduTrainingClassStudyInfoEntity.setStudyHourType(eduTrainingClassStudyInfoDto.getStudyHourType());
                    eduTrainingClassStudyInfoEntity.setStudyHour(eduTrainingClassStudyInfoDto.getStudyHour());
                    eduTrainingClassStudyInfoEntity.setStudyBelong(eduTrainingClassStudyInfoDto.getStudyBelong());
                    eduTrainingClassStudyInfoEntity.setStudyScore(eduTrainingClassStudyInfoDto.getStudyScore());
                    eduTrainingClassStudyInfoEntity.setId(eduTrainingClassStudyInfoDto.getId());
                    //加入默认值
                    eduTrainingClassStudyInfoEntity.setOperator(eduEmployeeEntity.getEmployeeName());
                    eduTrainingClassStudyInfoEntity.setUpdateTime(new Date());
                    eduTrainingClassStudyInfoEntity.setUpdateBy(eduEmployeeEntity.getEmployeeName());
                    eduTrainingClassStudyInfoEntities.add(eduTrainingClassStudyInfoEntity);
                }
            }

            //费用信息
            List<EduTrainingClassFeeItemEntity> eduTrainingClassFeeItemEntities = new ArrayList<>();
            if(eduTrainingClassDto.getEduTrainingClassFeeItemDto()!=null){
                for(EduTrainingClassFeeItemDto feeDto : eduTrainingClassDto.getEduTrainingClassFeeItemDto()){
                    EduTrainingClassFeeItemEntity feeItemEntity = ConvertUtils.convert(feeDto,EduTrainingClassFeeItemEntity::new);
                    //默认值
                    feeItemEntity.setUpdateTime(new Date());
                    feeItemEntity.setUpdateBy(eduEmployeeEntity.getEmployeeName());
                    eduTrainingClassFeeItemEntities.add(feeItemEntity);
                }
            }

            //插入
            eduTrainingClassService.updateTrainingClass(eduTrainingClassEntity,
                    eduTrainingClassFeeItemEntities,
                    eduTrainingClassStudyInfoEntities, eduTrainingClassDto.getRoleCode(), eduEmployeeEntity);

            resultData.setSuccess(true);
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setData(id);
            return resultData;
        }catch (Exception e){
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(e.getMessage());
            e.printStackTrace();
            return resultData;
        }
    }

    @PostMapping("/edit")
    @ApiOperation("培训班编辑(审核前编辑)")
    public ResultData<String> edit(HttpServletRequest request, @ApiParam(value = "编辑培训班参数") @RequestBody EduTrainingClassDto eduTrainingClassDto){
        return toEdit(request, eduTrainingClassDto);
    }

    @PostMapping("/toDel")
    @ApiOperation("删除培训班草稿")
    public ResultData<String> toDel(@ApiParam(value = "删除培训班草稿参数") @RequestBody EduTrainingClassEditDto eduTrainingClassEditDto){
        ResultData<String> resultData = new ResultData<>();
        try {
            String id = eduTrainingClassEditDto.getId();
            if (id.equals("")) {
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.PARAMETER_ERROR);
                resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
                return resultData;
            }
            eduTrainingClassService.deleteTrainingClass(eduTrainingClassEditDto);
            resultData.setData(id);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e) {
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.PARAMETER_ERROR);
            //resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
            resultData.setMessage(e.getMessage());
            return resultData;
        }
    }

    @PostMapping("/check/send")
    @ApiOperation("培训班草稿送审")
    public ResultData<String> checkSend(HttpServletRequest request, @ApiParam(value = "培训班草稿送审参数") @RequestBody EduTrainingClassEditDto eduTrainingClassEditDto){
        ResultData<String> resultData = new ResultData<>();
        try {
            String id = eduTrainingClassEditDto.getId();
            if (id.equals("")) {
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.PARAMETER_ERROR);
                resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
                return resultData;
            }
            EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity) request.getAttribute("userInfo");
            eduTrainingClassService.checkSendTrainingClass(eduTrainingClassEditDto, eduEmployeeEntity.getId(),request);
            EduFlowTraceEntity entity = new EduFlowTraceEntity();
            entity.setOperationLink("发送审批");
            entity.setRelevanceId(id);
            entity.setId(IdUtil.simpleUUID());
            entity.setTransactionMotion("申请");
            entity.setCreateBy(eduEmployeeEntity.getId());
            entity.setCreateTime(new Date());
            eduFlowTraceService.insert(entity);
            CustomsScheduleDeleteUtil.scheduleDelete(id,eduSystemCustomsScheduleService,CustomsScheduleConst.DIRECT_TRAINING_CLASS_CONFIRM_BACK);
            resultData.setData(id);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e){
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            //resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            resultData.setMessage(e.getMessage());
            return resultData;
        }
    }

    /**
     * 有审批权限的教育局领导进行审批
     */
    @PostMapping("/check")
    @ApiOperation("有审批权限的教育局领导进行审批")
    public ResultData<String> check(HttpServletRequest request,@RequestBody EduTrainingClassCheckDto eduTrainingClassCheckDto) {
        ResultData<String> resultData = new ResultData<>();
        String userId = request.getAttribute("userId").toString();
        try {
            if(BeanUtil.isEmpty(eduTrainingClassCheckDto)) {
                resultData.setSuccess(false);
                resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
                resultData.setCode(SysConstant.FAIL);
                return resultData;
            }
//            if (ObjectUtils.isEmpty(eduTrainingClassCheckDto.getNeedAssess())){
//                resultData.setSuccess(false);
//                resultData.setMessage("未录入是否需要评估");
//                resultData.setCode(SysConstant.FAIL);
//                return resultData;
//            }
//            if (ObjectUtils.isEmpty(eduTrainingClassCheckDto.getEduTrainingClassFeeItemDto())){
//                resultData.setSuccess(false);
//                resultData.setMessage("未录入初核费用");
//                resultData.setCode(SysConstant.FAIL);
//                return resultData;
//            }
            /*审核状态通过  拒绝*/
            String id = eduTrainingClassCheckDto.getId();
            String status = eduTrainingClassCheckDto.getStatus();
            EduTrainingClassEntity eduTrainingClassEntity = eduTrainingClassService.selectById(id);
            //eduTrainingClassEntity.setStatus(status); // 设置审核状态
            //eduTrainingPlanEntity.setOpinion(eduTrainingPlanEntity.getOpinion());
            //if(!eduTrainingClassService.isTrainingClassAllowConfirm(status, userId)){
            if(eduTrainingClassEntity.getStatus()!=2){
                resultData.setMessage("该培训班不是待审核状态，请刷新后重试");
                resultData.setCode(SysConstant.FAIL);
                resultData.setSuccess(false);
                return resultData;
            }
            EduFlowTraceEntity eduFlowTrace = new EduFlowTraceEntity();
            if(status.equals("1")){
                eduFlowTrace.setOperationLink("审核通过");
                eduFlowTrace.setTransactionMotion("审核");
                eduFlowTrace.setCreateTime(new Date());
                eduFlowTrace.setCreateBy(userId);
                eduFlowTrace.setRelevanceId(eduTrainingClassCheckDto.getId());
                resultData.setData("审核成功");
            }else if(status.equals("2")){
                eduFlowTrace.setOperationLink("审核被拒");
                eduFlowTrace.setTransactionMotion("审核");
                eduFlowTrace.setCreateTime(new Date());
                eduFlowTrace.setCreateBy(userId);
                eduFlowTrace.setRelevanceId(eduTrainingClassCheckDto.getId());
                resultData.setData("审核被拒绝");
            }
            eduFlowTrace.setRemark(eduTrainingClassCheckDto.getOpinion());
//            eduTrainingClassEntity.setCheckTime(new Date());
//            eduTrainingClassEntity.setCheckBy(userId);
//            eduTrainingClassService.updateById(eduTrainingClassEntity);
            //审核改成修改审核表的状态
            boolean pass = false;
            if(status.equals("1")){
                pass = true;
            }
            eduTrainingClassService.trainingClassConfirmPass(eduTrainingClassCheckDto, userId, pass, eduTrainingClassCheckDto.getOpinion(),request);
            eduFlowTraceService.insert(eduFlowTrace);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e) {
            resultData.setSuccess(false);
            //resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            resultData.setMessage(e.getMessage());
            resultData.setCode(SysConstant.FAIL);
            return resultData;
        }
    }

    @PostMapping("/view")
    @ApiOperation("查看培训班明细")
    public ResultData<EduTrainingClassDto> view(@ApiParam(value = "查看培训班明细参数") @RequestBody EduTrainingClassEditDto eduTrainingClassEditDto){
        ResultData<EduTrainingClassDto> resultData = new ResultData<>();
        EduTrainingClassDto eduTrainingClassDto = new EduTrainingClassDto();

        try {
            String id = eduTrainingClassEditDto.getId();
            if (id.equals("")) {
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.PARAMETER_ERROR);
                resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
                return resultData;
            }
            EduTrainingClassEntity eduTrainingClassEntity = eduTrainingClassService.selectById(id);
            BeanUtil.copyProperties(eduTrainingClassEntity, eduTrainingClassDto, true);

            //把审核人的姓名也放进去，用于前端展示
//            EduSystemConfirmEntity confirmEntity = eduSystemConfirmService.selectOne(new EntityWrapper<EduSystemConfirmEntity>().eq("type", 2)
//                    .eq("originalId", eduTrainingClassEntity.getId()).orderBy("lastModify", false));
//            if(confirmEntity != null){
//                eduTrainingClassDto.setCheckBy(confirmEntity.getConfirmEmployeeId());
//                eduTrainingClassDto.setCheckByName(confirmEntity.getConfirmEmployeeName());
//            }
            eduTrainingClassDto.setCheckByName(eduEmployeeService.selectById(eduTrainingClassDto.getCheckBy()).getEmployeeName());
            Map<String, Object> filter = new HashMap<>();
            //把状态放进去，否则前端显示不正常
            eduTrainingClassDto.setStatus(eduTrainingClassEntity.getStatus());
            eduTrainingClassDto.setPhase(eduTrainingClassEntity.getPhase());

            //再加几个名称，用于前端展示
            eduTrainingClassDto.setTrainingTypeName(eduSystemTrainingTypeService.selectOne(
                    new EntityWrapper<EduSystemTrainingTypeEntity>().eq("id", eduTrainingClassEntity.getTrainingType())).getName());//培训类型名称
            eduTrainingClassDto.setTrainingObjectiveName(eduSystemTrainingGoalService.selectOne(
                    new EntityWrapper<EduSystemTrainingGoalEntity>().eq("id", eduTrainingClassEntity.getTrainingObjective())).getName());//培训目的名称
            eduTrainingClassDto.setTrainingTraineeName(eduSystemTrainingObjectService.selectOne(
                    new EntityWrapper<EduSystemTrainingObjectEntity>().eq("id", eduTrainingClassEntity.getTrainingTrainee())).getName());//培训对象名称
            filter.put("trainingClassId", id);
            //学时学分信息放进来
            List<EduTrainingClassStudyInfoDto> eduTrainingClassStudyInfoDtos = new ArrayList<>();
            List<EduTrainingClassStudyInfoEntity> eduTrainingClassStudyInfoEntities = eduTrainingClassStudyInfoService.selectByMap(filter);
            for(EduTrainingClassStudyInfoEntity eduTrainingClassStudyInfoEntity : eduTrainingClassStudyInfoEntities){
                EduTrainingClassStudyInfoDto tmpStudyInfoDto = new EduTrainingClassStudyInfoDto();
                BeanUtil.copyProperties(eduTrainingClassStudyInfoEntity, tmpStudyInfoDto, true);
                eduTrainingClassStudyInfoDtos.add(tmpStudyInfoDto);
            }
            eduTrainingClassDto.setEduTrainingClassStudyInfoDtos(eduTrainingClassStudyInfoDtos);
            //费用信息放进来
            List<EduTrainingClassFeeItemDto> eduTrainingClassFeeItemDtos = new ArrayList<>();
            List<EduTrainingClassFeeItemEntity> eduTrainingClassFeeItemEntities = eduTrainingClassFeeItemService.selectByMap(filter);
            for(EduTrainingClassFeeItemEntity eduFeeItemEntity : eduTrainingClassFeeItemEntities){
                EduTrainingClassFeeItemDto tempDto = new EduTrainingClassFeeItemDto();
                BeanUtil.copyProperties(eduFeeItemEntity, tempDto, true);
                eduTrainingClassFeeItemDtos.add(tempDto);
            }
            //BeanUtil.copyProperties(eduTrainingClassFeeItemEntities.get(0), eduTrainingClassFeeItemDto, true);
            eduTrainingClassDto.setEduTrainingClassFeeItemDto(eduTrainingClassFeeItemDtos);

            //流程跟踪信息
            List<EduFlowTraceEntityDto> flowList = new ArrayList<>();
            for(EduFlowTraceEntity flowEntity : eduFlowTraceService.selectList(
                    new EntityWrapper<EduFlowTraceEntity>().eq("relevanceId", id).orderBy("createTime", false))){
                EduFlowTraceEntityDto eduFlowTraceEntityDto = new EduFlowTraceEntityDto();
                BeanUtil.copyProperties(flowEntity, eduFlowTraceEntityDto, true);
                //添加几个名字，用于前端显示
                EduEmployeeEntity employeeInfo = eduEmployeeService.selectOne(new EntityWrapper<EduEmployeeEntity>().eq("id", flowEntity.getCreateBy()));
                String[] splits = employeeInfo.getH4aAllPathName().split("\\\\");
                eduFlowTraceEntityDto.setDepartmentName(splits[2]);
                eduFlowTraceEntityDto.setOperation(employeeInfo.getEmployeeName());
                flowList.add(eduFlowTraceEntityDto);
            }
            eduTrainingClassDto.setEduFlowTraceEntities(flowList);

            List<EduTrainingClassEmployeeApplyEntity> eduTrainingClassEmployeeApplyEntities = eduTrainingClassEmployeeApplyService.selectList(new EntityWrapper<EduTrainingClassEmployeeApplyEntity>()
                    .eq("trainingClassId", id));
            eduTrainingClassDto.setEntities(eduTrainingClassEmployeeApplyEntities);
            resultData.setData(eduTrainingClassDto);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e){
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            return resultData;
        }
    }

    @PostMapping("/setEnrollTime")
    @ApiOperation("设置培训班的报名时间")
    public ResultData<String> setEnrollTime(HttpServletRequest request,@ApiParam(value = "设置培训班的报名时间的参数") @RequestBody EduTrainingClassSetEnrollTimeDto eduTrainingClassSetEnrollTimeDto){
        ResultData<String> resultData = new ResultData<>();
        try {
            String id = eduTrainingClassSetEnrollTimeDto.getId();
            if (id.equals("")) {
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.PARAMETER_ERROR);
                resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
                return resultData;
            }
            EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity) request.getAttribute("userInfo");
            EduTrainingClassEntity eduTrainingClassEntity = eduTrainingClassService.selectById(id);
            eduTrainingClassEntity.setEnrollStartTime(eduTrainingClassSetEnrollTimeDto.getEnrollStartTime());
            eduTrainingClassEntity.setEnrollEndTime(eduTrainingClassSetEnrollTimeDto.getEnrollEndTime());
            eduTrainingClassEntity.setUpdateTime(new Date());
            eduTrainingClassEntity.setUpdateBy(eduEmployeeEntity.getId());
            eduTrainingClassService.updateById(eduTrainingClassEntity);
            resultData.setData(id);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e){
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            //resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            resultData.setMessage(e.getMessage());
            return resultData;
        }
    }

    @PostMapping("/enrollEmployee/add")
    @ApiOperation("联络员添加报名人员")
    public ResultData<String> enrollEmployeeAdd(HttpServletRequest request,@ApiParam(value = "联络员添加报名人员") @RequestBody EduTrainingClassEmployeeAddDeleteDto eduTrainingClassEmployeeAddDeleteDto){
        ResultData<String> resultData = new ResultData<>();
        try {
            String id = eduTrainingClassEmployeeAddDeleteDto.getTrainingClassId();
            if (id.equals("")) {
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.PARAMETER_ERROR);
                resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
                return resultData;
            }
            EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity) request.getAttribute("userInfo");
            List<String> ids = eduTrainingClassEmployeeAddDeleteDto.getEmployeeIds();
            List<EduTrainingClassEmployeeApplyEntity> employeeApplyEntities = new ArrayList<>();
            int insertCount = 0;
            for(String employeeId : ids){
                Map<String, Object> selectFilter = new HashMap<>();
                selectFilter.put("trainingClassId", id);
                selectFilter.put("employeeId", employeeId);
                List<EduTrainingClassEmployeeApplyEntity> eduTrainingClassEmployeeApplyEntities = eduTrainingClassEmployeeApplyService.selectByMap(selectFilter);
                if(!eduTrainingClassEmployeeApplyEntities.isEmpty() || !ObjectUtils.isEmpty(eduTrainingClassEmployeeApplyEntities)){
                    continue;//如果已经存在了，就跳过
                }
                insertCount++;
                EduTrainingClassEmployeeApplyEntity entity = new EduTrainingClassEmployeeApplyEntity();
                entity.setTrainingClassId(id);
                entity.setEmployeeId(employeeId);
                //默认信息
                entity.setCreateTime(new Date());
                entity.setCreateBy(eduEmployeeEntity.getId());
                entity.setStatus(1); //报名不用审核
                entity.setIsEnable(1);
                employeeApplyEntities.add(entity);
            }
            if (employeeApplyEntities.isEmpty()){
                resultData.setCode(SysConstant.FAIL);
                resultData.setSuccess(false);
                resultData.setMessage("数据已经存在");
                return resultData;
            }
            eduTrainingClassEmployeeApplyService.insertBatch(employeeApplyEntities);
            resultData.setData(""+insertCount);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e){
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(e.getMessage());
            return resultData;
        }
    }

    @PostMapping("/enrollEmployee/remove")
    @ApiOperation("联络员移除报名人员")
    public ResultData<String> enrollEmployeeRemove(@ApiParam(value = "联络员添加报名人员") @RequestBody EduTrainingClassEmployeeAddDeleteDto eduTrainingClassEmployeeAddDeleteDto){
        ResultData<String> resultData = new ResultData<>();
        try {
            if ("".equals(eduTrainingClassEmployeeAddDeleteDto.getTrainingClassId())
                    || eduTrainingClassEmployeeAddDeleteDto.getEmployeeIds().size()<1) {
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.PARAMETER_ERROR);
                resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
                return resultData;
            }
            EntityWrapper<EduTrainingClassEmployeeApplyEntity> wrapper = new EntityWrapper<>();
            wrapper.eq("trainingClassId", eduTrainingClassEmployeeAddDeleteDto.getTrainingClassId());
            wrapper.in("employeeId",eduTrainingClassEmployeeAddDeleteDto.getEmployeeIds());
//            Map<String, Object> filter = new HashMap<>();
//            filter.put("trainingClassId", eduTrainingClassEmployeeAddDeleteDto.getTrainingClassId());
//            filter.put("employeeId", eduTrainingClassEmployeeAddDeleteDto.getEmployeeIds());
            eduTrainingClassEmployeeApplyService.delete(wrapper);
            resultData.setData(eduTrainingClassEmployeeAddDeleteDto.getTrainingClassId());
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e){
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(e.getMessage());
            return resultData;
        }
    }

    @PostMapping("/employee/list")
    @ApiOperation("培训班人员信息列表")
    public ResultData<PageUtils> employeeList(@ApiParam(value = "培训班ID") @RequestBody EduTrainingClassEmployeeListDto eduTrainingClassEmployeeListDto,HttpServletRequest request){
        ResultData<PageUtils> resultData = new ResultData<>();
        EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity) request.getAttribute("userInfo");
        String employeeId = eduEmployeeEntity.getId();
        try {
            String id = eduTrainingClassEmployeeListDto.getClassId();
            if (id==null || eduTrainingClassEmployeeListDto.getPage()<0 || eduTrainingClassEmployeeListDto.getLimit()<0) {
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.PARAMETER_ERROR);
                resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
                return resultData;
            }
            Map<String, Object> filter = new HashMap<>();
            filter.put("trainingClassId", id);
            filter.put("page", eduTrainingClassEmployeeListDto.getPage());
            filter.put("limit", eduTrainingClassEmployeeListDto.getLimit());
            filter.put("employeeId",employeeId);
            filter.put("roleCode",eduTrainingClassEmployeeListDto.getRoleCode());
            PageUtils pageUtils = eduTrainingClassEmployeeApplyService.queryPage(filter);
            resultData.setData(pageUtils);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e){
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(e.getMessage());
            return resultData;
        }

    }

    @PostMapping("/employee/leave/add")
    @ApiOperation("添加请假人员")
    public ResultData<String> employeeLeaveAdd(HttpServletRequest request,@ApiParam(value = "联络员添加报名人员") @RequestBody EduTrainingClassEmployeeAddDeleteDto eduTrainingClassEmployeeAddDeleteDto){
        ResultData<String> resultData = new ResultData<>();
        String userId = request.getAttribute("userId").toString();  // 当前登录人id
        EduEmployeeEntity userInfo = (EduEmployeeEntity)request.getAttribute("userInfo");
        try {
            String id = eduTrainingClassEmployeeAddDeleteDto.getTrainingClassId();
            if (id.equals("")) {
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.PARAMETER_ERROR);
                resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
                return resultData;
            }
            List<String> ids = eduTrainingClassEmployeeAddDeleteDto.getEmployeeIds();
            int insertCount = 0;
            for(String employeeId : ids){
                Map<String, Object> selectFilter = new HashMap<>();
                selectFilter.put("trainingClassId", id);
                selectFilter.put("employeeId", employeeId);
                //查询所有的报名人员
                List<EduTrainingClassEmployeeApplyEntity> eduTrainingClassEmployeeApplyEntities = eduTrainingClassEmployeeApplyService.selectByMap(selectFilter);
                if(eduTrainingClassEmployeeApplyEntities.isEmpty()){
                    throw new RRException("只有已经报名的人员才能请假");
                }
                //如果该培训班
                List<EduTrainingClassEmployeeLeaveEntity> eduTrainingClassEmployeeLeaveEntities = eduTrainingClassEmployeeLeaveService.selectByMap(selectFilter);
                if(!ObjectUtils.isEmpty(eduTrainingClassEmployeeLeaveEntities)){
                    throw new RRException("请勿重复请假");
                }
                insertCount++;
                EduTrainingClassEmployeeLeaveEntity entity = new EduTrainingClassEmployeeLeaveEntity();
                entity.setTrainingClassId(id);
                entity.setEmployeeId(employeeId);
                //默认信息
                entity.setCreateTime(new Date());
                entity.setCreateBy(getCurrentUserName(request));
                EduTrainingClassEntity eduTrainingClassEntity = eduTrainingClassService.selectById(id);
                if(eduTrainingClassEntity.getCreateBy().equals(userId)){
                    entity.setStatus(1); //报名不用审核
                }else {
                    entity.setStatus(0);
                }
                entity.setIsEnable(1);
                eduTrainingClassEmployeeLeaveService.insert(entity);
                if(!eduTrainingClassEntity.getCreateBy().equals(userId)){
                    //发布到代办平台
                    CustomsScheduleManage customsScheduleManage = new CustomsScheduleManage();
                    EntityFactory entityFactory = new EntityFactory();
                    //创建一个Key
                    String  key  = IdUtil.simpleUUID();
                    String scheduleId = IdUtil.simpleUUID();
                    InsertTaskDto insertTaskDto = new InsertTaskDto();
                    insertTaskDto.setId(scheduleId);
                    insertTaskDto.setKey(key);
                    insertTaskDto.setTaskTitle("关区培训班请假人员:"+eduEmployeeService.selectById(employeeId).getEmployeeName()+" 审核");//
                    insertTaskDto.setType(CustomsScheduleConst.DIRECT_TRAINING_CLASS_APPLY);//
                    insertTaskDto.setFromUserGuid(userInfo.getH4aUserGuid());
                    insertTaskDto.setFromUserName(userInfo.getEmployeeName());
                    insertTaskDto.setToUserGuid(eduEmployeeService.selectById(eduTrainingClassEntity.getCreateBy()).getH4aUserGuid());
                    insertTaskDto.setToUserName(eduEmployeeService.selectById(eduTrainingClassEntity.getCreateBy()).getEmployeeName());
                    boolean b = customsScheduleManage.addSchedule(entityFactory.createTaskEntity(insertTaskDto));
                    //本地数据库存储一份
                    EduSystemCustomsScheduleEntity eduSystemCustomsScheduleEntity = new EduSystemCustomsScheduleEntity();
                    eduSystemCustomsScheduleEntity.setScheduleId(scheduleId);
                    eduSystemCustomsScheduleEntity.setKey(key);
                    eduSystemCustomsScheduleEntity.setOriginalBn(entity.getId());
                    eduSystemCustomsScheduleEntity.setType(CustomsScheduleConst.DIRECT_TRAINING_CLASS_APPLY);
                    eduSystemCustomsScheduleEntity.setUserGuid(eduEmployeeService.selectById(eduTrainingClassEntity.getCreateBy()).getH4aUserGuid());
                    eduSystemCustomsScheduleEntity.setRoleCode("JGCSLLY");
                    eduSystemCustomsScheduleEntity.setCreateTime(new Date());
                    if (!b){
                        eduSystemCustomsScheduleEntity.setSyncStatus(-1);
                    }else {
                        eduSystemCustomsScheduleEntity.setSyncStatus(1);
                    }
                    eduSystemCustomsScheduleService.insert(eduSystemCustomsScheduleEntity);
                }
            }

            resultData.setData(""+insertCount);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e){
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(e.getMessage());
            return resultData;
        }
    }

    @PostMapping("/employee/leave/remove")
    @ApiOperation("请假人员删除（变成正常培训，不请假了）")
    public ResultData<String> employeeLeaveRemove(@ApiParam(value = "联络员添加报名人员") @RequestBody EduTrainingClassEmployeeAddDeleteDto eduTrainingClassEmployeeAddDeleteDto,HttpServletRequest request){
        ResultData<String> resultData = new ResultData<>();
        try {
            if (eduTrainingClassEmployeeAddDeleteDto.getTrainingClassId().equals("")
                    || eduTrainingClassEmployeeAddDeleteDto.getEmployeeIds().size()<1) {
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.PARAMETER_ERROR);
                resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
                return resultData;
            }

            EntityWrapper<EduTrainingClassEmployeeLeaveEntity> wrapper = new EntityWrapper<>();
            wrapper.eq("trainingClassId", eduTrainingClassEmployeeAddDeleteDto.getTrainingClassId());
            wrapper.in("employeeId",eduTrainingClassEmployeeAddDeleteDto.getEmployeeIds());
//            Map<String, Object> filter = new HashMap<>();
//            filter.put("trainingClassId", eduTrainingClassEmployeeAddDeleteDto.getTrainingClassId());
//            filter.put("employeeId", eduTrainingClassEmployeeAddDeleteDto.getEmployeeIds());

            if (eduTrainingClassEmployeeAddDeleteDto.getEmployeeIds().size()==1){
                EduTrainingClassEmployeeLeaveEntity entity = eduTrainingClassEmployeeLeaveService.selectOne(wrapper);
                CustomsScheduleDeleteUtil.scheduleDelete(entity.getId(),eduSystemCustomsScheduleService,CustomsScheduleConst.DIRECT_TRAINING_CLASS_APPLY);
            }
            eduTrainingClassEmployeeLeaveService.delete(wrapper);
            resultData.setData(eduTrainingClassEmployeeAddDeleteDto.getTrainingClassId());
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e){
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(e.getMessage());
            return resultData;
        }
    }

    @PostMapping("/employee/leave/list")
    @ApiOperation("请假人员列表")
    public ResultData<PageUtils> employeeLeaveList(@ApiParam(value = "培训班ID") @RequestBody EduTrainingClassEmployeeListDto eduTrainingClassEmployeeListDto,HttpServletRequest request){
        ResultData<PageUtils> resultData = new ResultData<>();
        String userId = request.getAttribute("userId").toString();
        try {
            String id = eduTrainingClassEmployeeListDto.getClassId();
            if (id==null || eduTrainingClassEmployeeListDto.getPage()<0 || eduTrainingClassEmployeeListDto.getLimit()<0) {
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.PARAMETER_ERROR);
                resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
                return resultData;
            }
            String classId = eduTrainingClassEmployeeListDto.getClassId();
            Map<String,Object> map = new HashMap<>();
            map.put("userId",userId);
            map.put("classId",classId);
            Map<String, Object> filter = new HashMap<>();
            filter.put("trainingClassId", id);
            filter.put("page", eduTrainingClassEmployeeListDto.getPage());
            filter.put("limit", eduTrainingClassEmployeeListDto.getLimit());
            filter.put("map",map);
            PageUtils pageUtils = eduTrainingClassEmployeeLeaveService.queryPage(filter);
            resultData.setData(pageUtils);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e){
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(e.getMessage());
            return resultData;
        }

    }


    private String getCurrentUserName(HttpServletRequest request){
        //当前用户的信息
        EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity) request.getAttribute("userInfo");
        return eduEmployeeEntity.getId();
    }

    @PostMapping("/settlement/fee")
    @ApiOperation("新增决算费用")
    public ResultData<String> fee(@RequestBody EduTrainingClassFeeItemDto eduTrainingClassFeeItemDto,HttpServletRequest request){
        ResultData resultData = new ResultData();
        if (ObjectUtils.isEmpty(eduTrainingClassFeeItemDto)){
            resultData.setCode(SysConstant.FAIL);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            resultData.setSuccess(false);
            return resultData;
        }
        EduTrainingClassFeeItemEntity eduTrainingClassFeeItemEntity = ConvertUtils.convert(eduTrainingClassFeeItemDto,EduTrainingClassFeeItemEntity::new);
        Integer classHour = eduTrainingClassFeeItemEntity.getClassHour();
        Double day = Double.valueOf(classHour / 8);
        DecimalFormat df = new DecimalFormat("#.00");
        String format = df.format(day * eduTrainingClassFeeItemDto.getPeopleNum());
        eduTrainingClassFeeItemEntity.setTotalFee(Double.parseDouble(format));
        EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity) request.getAttribute("userInfo");
        eduTrainingClassFeeItemEntity.setCreateBy(eduEmployeeEntity.getEmployeeCode());
        eduTrainingClassFeeItemEntity.setCreateTime(new Date());
        eduTrainingClassFeeItemEntity.setFeeType(3);
        String id = IdUtil.simpleUUID();
        eduTrainingClassFeeItemEntity.setId(id);
        eduTrainingClassFeeItemService.insert(eduTrainingClassFeeItemEntity);
        EduFlowTraceEntity entity = new EduFlowTraceEntity();
        entity.setOperationLink("新增决算");
        entity.setRelevanceId(id);
        entity.setId(IdUtil.simpleUUID());
        entity.setTransactionMotion("新增");
        entity.setCreateTime(new Date());
        entity.setCreateBy(eduEmployeeEntity.getId());
        eduFlowTraceService.insert(entity);
        resultData.setData(id);
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setSuccess(true);
        return resultData;
    }

    @PostMapping("/settlement/refee")
    @ApiOperation("新增复核费用")
    public ResultData<String> refee(@RequestBody EduTrainingClassFeeItemDto eduTrainingClassFeeItemDto,HttpServletRequest request){
        ResultData resultData = new ResultData();
        if (ObjectUtils.isEmpty(eduTrainingClassFeeItemDto)){
            resultData.setCode(SysConstant.FAIL);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            resultData.setSuccess(false);
            return resultData;
        }
        EduTrainingClassFeeItemEntity eduTrainingClassFeeItemEntity = ConvertUtils.convert(eduTrainingClassFeeItemDto,EduTrainingClassFeeItemEntity::new);
        Integer classHour = eduTrainingClassFeeItemEntity.getClassHour();
        int day = classHour / 8;
        DecimalFormat df = new DecimalFormat("#.00");
        String format = df.format(day * eduTrainingClassFeeItemDto.getPeopleNum());
        eduTrainingClassFeeItemEntity.setTotalFee(Double.parseDouble(format));
        EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity) request.getAttribute("userInfo");
        eduTrainingClassFeeItemEntity.setCreateBy(eduEmployeeEntity.getEmployeeCode());
        eduTrainingClassFeeItemEntity.setCreateTime(new Date());
        eduTrainingClassFeeItemEntity.setFeeType(4);
        String id = IdUtil.simpleUUID();
        eduTrainingClassFeeItemEntity.setId(id);
        eduTrainingClassFeeItemService.insert(eduTrainingClassFeeItemEntity);
        resultData.setData(id);
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setSuccess(true);
        return resultData;
    }

//    @PostMapping("/teacherList")
//    @ApiOperation("根据条件查询教师集合")
//    public ResultData<EduTrainingTeacherEntity> teacherList(@RequestBody EduTrainingClassTeacherDto eduTrainingClassTeacherDto){
//        ResultData<EduTrainingTeacherEntity> resultData = new ResultData<>();
//        EduTrainingTeacherEntity entity = new EduTrainingTeacherEntity();
//        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
//        resultData.setCode(SysConstant.SUCCESS);
//        resultData.setSuccess(true);
//        if (!ObjectUtils.isEmpty(eduTrainingClassTeacherDto.getEmployeeCode())){
//            List<String> employeeCodes = eduTrainingClassTeacherDto.getEmployeeCode();
////            List<EduInnerTeacherEntity>entities = new ArrayList<>();
//            List<EduInnerTeacherEntity> eduInnerTeacherEntitys = eduInnerTeacherService.selectList(new EntityWrapper<EduInnerTeacherEntity>().in("teacherCode", employeeCodes));
////
////            for (String employeeCode : employeeCodes) {
////                EduEmployeeEntity eduEmployeeEntity = eduEmployeeService.selectOne(new EntityWrapper<EduEmployeeEntity>().eq("employeeCode", employeeCode));
////                EduInnerTeacherEntity eduInnerTeacherEntity = eduInnerTeacherService.selectOne(new EntityWrapper<EduInnerTeacherEntity>().eq("employeeId", eduEmployeeEntity.getId()));
////                eduInnerTeacherEntity.setDepartmentName(eduEmployeeService.selectOne(new EntityWrapper<EduEmployeeEntity>()
////                        .eq("employeeCode",eduInnerTeacherEntity.getTeacherCode())).getH4aAllPathName().split("\\\\")[2]);
////                entities.add(eduInnerTeacherEntity);
////            }
//            entity.setEduInnerTeacherEntities(eduInnerTeacherEntitys);
//        }
//        if (!ObjectUtils.isEmpty(eduTrainingClassTeacherDto.getType())){
//            List<EduInnerTeacherEntity> eduInnerTeacherEntities = new ArrayList<>();
//            List<EduOuterTeacher> eduOuterTeachers = new ArrayList<>();
//            if (eduTrainingClassTeacherDto.getType()==1){
//                eduInnerTeacherEntities = eduInnerTeacherService.selectList(new EntityWrapper<>());
//            }
//            if (eduTrainingClassTeacherDto.getType()==2){
//                eduOuterTeachers = eduOutTeacherService.selectList(new EntityWrapper<>());
//                eduOuterTeachers.forEach(k->k.setDepartmentName(k.getCompany()));
//            }
//            entity.setEduOuterTeachers(eduOuterTeachers);
//            entity.setEduInnerTeacherEntities(eduInnerTeacherEntities);
//        }
//        if (ObjectUtils.isEmpty(eduTrainingClassTeacherDto.getType()) && ObjectUtils.isEmpty(eduTrainingClassTeacherDto.getEmployeeCode())){
//            List<EduInnerTeacherEntity> eduInnerTeacherEntities = eduInnerTeacherService.selectList(new EntityWrapper<>());
//            List<EduOuterTeacher> eduOuterTeachers = eduOutTeacherService.selectList(new EntityWrapper<>());
//            eduOuterTeachers.forEach(k->k.setDepartmentName(k.getCompany()));
//            entity.setEduOuterTeachers(eduOuterTeachers);
//            entity.setEduInnerTeacherEntities(eduInnerTeacherEntities);
//        }
//        resultData.setData(entity);
//        return resultData;
//    }
    @PostMapping("/teacherList")
    @ApiOperation("根据条件查询教师集合")
    public ResultData<EduTrainingTeacherEntity> teacherList(@RequestBody EduTrainingClassTeacherDto eduTrainingClassTeacherDto){
        ResultData<EduTrainingTeacherEntity> resultData = new ResultData<>();
        EduTrainingTeacherEntity entity = new EduTrainingTeacherEntity();
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        String name = eduTrainingClassTeacherDto.getName();
        //判断  如果 类型不为空 才走
        if (!ObjectUtils.isEmpty(eduTrainingClassTeacherDto.getType())){
            List<EduInnerTeacherEntity> eduInnerTeacherEntities = new ArrayList<>();
            List<EduOuterTeacher> eduOuterTeachers = new ArrayList<>();
            if (eduTrainingClassTeacherDto.getType()==1){
                EntityWrapper<EduInnerTeacherEntity> entityEntityWrapper = new EntityWrapper<>();
                entityEntityWrapper.where("(`status`=1 or `status`=4)");
                if (!ObjectUtils.isEmpty(name)){
                    Boolean character = isString(name);
                    if (character){
                        entityEntityWrapper.like("teacherName",name);
                    }
                    if (!character){
                        entityEntityWrapper.eq("teacherCode", name);
                    }
                }
                eduInnerTeacherEntities = eduInnerTeacherService.selectList(entityEntityWrapper);
            }
            if (eduTrainingClassTeacherDto.getType()==2){
                EntityWrapper<EduOuterTeacher> eduOuterTeacherEntityWrapper = new EntityWrapper<>();
                eduOuterTeacherEntityWrapper.where("(`status`=1 or `status`=4)");
                if (!ObjectUtils.isEmpty(name)){
                    eduOuterTeacherEntityWrapper.like("teacherName",name);
                }
                eduOuterTeachers = eduOutTeacherService.selectList(eduOuterTeacherEntityWrapper);
                eduOuterTeachers.forEach(k->k.setDepartmentName(k.getCompany()));
            }
            entity.setEduOuterTeachers(eduOuterTeachers);
            entity.setEduInnerTeacherEntities(eduInnerTeacherEntities);
        }

        resultData.setData(entity);
        return resultData;
    }

    public Boolean isString(String name){
        int n = 0;
        boolean character = true;
        for (int i=0;i<name.length();i++){
            n=(int) name.charAt(i);
            if (19968 <= n && n <40869){
                character = true;
            }else {
                character = false;
                break;
            }
        }
        return character;
    }


    @GetMapping("/numberSequence")
    @ApiOperation("生成培训班编号")
    public ResultData<String> numberSequence() {
        ResultData<String> resultData = new ResultData<>();
        String numberSequenceByService = numberSequenceUtil.getNumberSequenceByService(NanJingNumberGenerateConstant.PLAN_CLASS);
        resultData.setData(numberSequenceByService);
        resultData.setSuccess(true);
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        return resultData;
    }

    /**
     * 审核
     */
    @ApiOperation("请假人员审核")
    @PostMapping("/leaveCheck")
    public ResultData leaveCheck(@RequestBody Map<String,Object> params,HttpServletRequest request) {
        ResultData resultData = new ResultData();
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setSuccess(true);
        resultData.setCode(SysConstant.SUCCESS);
        String id = params.get("id").toString();
        EduTrainingClassEmployeeLeaveEntity eduTrainingClassEmployeeLeaveEntity = eduTrainingClassEmployeeLeaveService.selectById(id);
        eduTrainingClassEmployeeLeaveEntity.setStatus(1);

        CustomsScheduleManage customsScheduleManage = new CustomsScheduleManage();
        EntityFactory entityFactory = new EntityFactory();
        //把要删除的数据的id整理成一个List<String> 表 edu_system_customs_schedule

        Wrapper<EduSystemCustomsScheduleEntity> wrapper = new EntityWrapper<EduSystemCustomsScheduleEntity>()
                .eq("originalBn", params.get("rateUserDedetailId")).eq("syncStatus", 1);
        wrapper.eq("type", CustomsScheduleConst.DIRECT_TRAINING_CLASS_APPLY);
        List<EduSystemCustomsScheduleEntity> eduSystemCustomsScheduleEntities = eduSystemCustomsScheduleService.selectList(wrapper);
        List<String> idList = eduSystemCustomsScheduleEntities.stream().map(EduSystemCustomsScheduleEntity::getScheduleId).collect(Collectors.toList());
        String userGuid = eduEmployeeService.selectById(request.getAttribute("userId").toString()).getH4aUserGuid();
        for (EduSystemCustomsScheduleEntity eduSystemCustomsScheduleEntity : eduSystemCustomsScheduleEntities) {
            if (eduSystemCustomsScheduleEntity.getUserGuid().equals(userGuid)){
                eduSystemCustomsScheduleEntity.setStatus(1);
            }else {
                eduSystemCustomsScheduleEntity.setStatus(2);
            }
        }
        //test1 中加入的，调用接口删除
        List<DeleteTaskEntity> deleteTaskEntities = entityFactory.createDeleteTaskEntities(idList);
        List<Boolean> booleans = customsScheduleManage.deleteSchedule(deleteTaskEntities);
        for (int i = 0; i < booleans.size(); i++) {
            if (booleans.get(i)){
                eduSystemCustomsScheduleEntities.get(i).setCancelSyncStatus(1);
            }else {
                eduSystemCustomsScheduleEntities.get(i).setCancelSyncStatus(-1);
            }
        }
        //修改状态  一个人审核后 将其他未审核的人作废
        eduSystemCustomsScheduleService.updateBatchById(eduSystemCustomsScheduleEntities);
        eduTrainingClassEmployeeLeaveService.updateById(eduTrainingClassEmployeeLeaveEntity);
        return resultData;
    }

    /**
     * 报名人员导入
     */
    @PostMapping("/importData")
    @ApiOperation("报名人员导入")
    public ResultData importData(HttpServletRequest request,@RequestBody MultipartFile file,String classId) {
        ResultData resultData = new ResultData();
        List<ApplyPersonExcelDto> objects = EasyExcelUtils.importData(file, ApplyPersonExcelDto.class);
        boolean flag = true;
        for (ApplyPersonExcelDto object : objects) {
            if(object.getJobNumber() == null || null == object.getUserName()) {
                flag = false;
                break;
            }
        }
        if(!flag){
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.FAIL);
            resultData.setMessage("工号和姓名必须填写！");
            return resultData;
        }
        List<String> collect = objects
                .stream()
                .map(x -> x.getJobNumber())
                .collect(Collectors.toList());
        List<EduEmployeeEntity> employeeCode = eduEmployeeService.selectList(new EntityWrapper<EduEmployeeEntity>().in("employeeCode", collect));
        List<String> ids = employeeCode
                .stream()
                .map(x -> x.getId())
                .collect(Collectors.toList());
        List<EduTrainingClassEmployeeApplyEntity> employeeApplyEntities = new ArrayList<>();
        int insertCount = 0;
        for(String employeeId : ids){
            Map<String, Object> selectFilter = new HashMap<>();
            selectFilter.put("trainingClassId", classId);
            selectFilter.put("employeeId", employeeId);
            List<EduTrainingClassEmployeeApplyEntity> eduTrainingClassEmployeeApplyEntities = eduTrainingClassEmployeeApplyService.selectByMap(selectFilter);
            if(!eduTrainingClassEmployeeApplyEntities.isEmpty() || !ObjectUtils.isEmpty(eduTrainingClassEmployeeApplyEntities)){
                continue;//如果已经存在了，就跳过
            }
            insertCount++;
            EduTrainingClassEmployeeApplyEntity entity = new EduTrainingClassEmployeeApplyEntity();
            entity.setTrainingClassId(classId);
            entity.setEmployeeId(employeeId);
            //默认信息
            entity.setCreateTime(new Date());
            entity.setCreateBy(request.getAttribute("userId").toString());
            entity.setStatus(1); //报名不用审核
            entity.setIsEnable(1);
            employeeApplyEntities.add(entity);
        }
        if (employeeApplyEntities.isEmpty()){
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage("数据已经存在");
            return resultData;
        }
        eduTrainingClassEmployeeApplyService.insertBatch(employeeApplyEntities);
        resultData.setData(""+insertCount);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        return resultData;
    }

    @PostMapping("/excel")
    @ApiOperation("导出培训授课教师讲课费统计表")
    public ResultData excel(HttpServletResponse response, @RequestBody List<String>ids){
        ResultData resultData = new ResultData();
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        try {
            String fileName = "培训授课教师讲课费统计表";
            String date = DateUtil.format(new Date(), "yyyyMMdd-HHmmss");
            fileName = fileName.concat(date);
            List<EduTrainingClassExcelVo> list = new ArrayList();
            //查询所有要求被导出的培训班
            List<EduTrainingClassEntity> entities = eduTrainingClassService.selectBatchIds(ids);
            //设置序号
            int i = 1;
            for (EduTrainingClassEntity entity : entities) {

                //查询创建人的信息
                EduEmployeeEntity eduEmployeeEntity = eduEmployeeService.selectById(entity.getCreateBy());
                //查询该培训班的通知文号
                EduTrainingClassAttachEntity eduTrainingClassAttach = eduTrainingClassAttachService.selectOne(new EntityWrapper<EduTrainingClassAttachEntity>()
                        .eq("trainingClassId", entity.getId()).eq("attachType",2));
                //查询该培训班下的课程信息
                List<EduTrainingClassCourseEntity> entityList = eduTrainingClassCourseService.selectList(new EntityWrapper<EduTrainingClassCourseEntity>()
                        .eq("trainingClassId", entity.getId()).orderBy("createTime",false));

                for (EduTrainingClassCourseEntity eduTrainingClassCourseEntity : entityList) {
                    EduTrainingClassExcelVo eduTrainingClassExcelVo = new EduTrainingClassExcelVo();
                    //查询课程时间
                    Date startTime = eduTrainingClassCourseEntity.getCourseStartDate();
                    Date endTime = eduTrainingClassCourseEntity.getCourseEndDate();
                    //设置时间格式 待会加月日
                    SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
                    String startDate = String.valueOf(new StringBuffer(sdf.format(startTime)).insert(2,"月").append("日"));
                    String endDate = String.valueOf(new StringBuffer(sdf.format(endTime)).insert(2,"月").append("日"));
                    eduTrainingClassExcelVo.setCourseHour(eduTrainingClassCourseEntity.getCourseHour());
                    eduTrainingClassExcelVo.setStartTime(startDate);
                    eduTrainingClassExcelVo.setEndTime(endDate);
                    eduTrainingClassExcelVo.setCourseName(eduTrainingClassCourseEntity.getCourseName());
                    eduTrainingClassExcelVo.setClassName(entity.getClassName());
                    eduTrainingClassExcelVo.setOrder(i);
                    eduTrainingClassExcelVo.setDepartmentName(eduEmployeeEntity
                            .getH4aAllPathName().split("\\\\")[2]);
                    eduTrainingClassExcelVo.setTeacherName(eduTrainingClassCourseEntity.getTeacherName());

                    if (eduTrainingClassCourseEntity.getTeacherType()==1){
                        EduInnerTeacherEntity eduInnerTeacherEntity = eduInnerTeacherService.selectById(eduTrainingClassCourseEntity.getTeacherId());
                        eduTrainingClassExcelVo.setUserCode(eduInnerTeacherEntity.getTeacherCode());
                    }
                    int level = Integer.parseInt(eduTrainingClassCourseEntity.getTeacherLevel());
                    eduTrainingClassExcelVo.setStandard(FeeItemEnum.getByValue(level));
                    eduTrainingClassExcelVo.setAmount(FeeItemEnum.getByValue(level) * eduTrainingClassCourseEntity.getCourseHour());
                    if (!ObjectUtils.isEmpty(eduTrainingClassAttach)){
                        String attachTitle = eduTrainingClassAttach.getAttachTitle();
                        eduTrainingClassExcelVo.setAttachTitle(attachTitle);
                    }else {
                        eduTrainingClassExcelVo.setAttachTitle("无数据");
                    }
                    list.add(eduTrainingClassExcelVo);
                }
                i++;

            }
            //记录哪几列要合并 通过重写的合并策略合并单元格
            int[] mergeColumeIndex = {0,1,2,3};
            //需要从第一行开始，列头第一行
            int mergeRowIndex = 1;
            EasyExcelUtils.writeExcelByIndex(response,list,fileName,"培训授课教师讲课费统计表", EduTrainingClassExcelVo.class,mergeColumeIndex,mergeRowIndex);
        }catch (Exception e){
            e.printStackTrace();
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.FAIL);
            resultData.setMessage("存在未进行决算数据");
        }
        return resultData;
    }

    @ApiOperation("获取需要导出的数据")
    @PostMapping("/getExcelData")
    public ResultData getExcelData(@RequestBody Map<String, Object> params,HttpServletRequest request){
        ResultData<PageUtils> result = list(request, params);
        ResultData<EduTrainingClassGetExcelDto> resultData = new ResultData<>();
        resultData.setSuccess(true);
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        //存储基础信息的集合
        List<EduTrainingClassAllExcel> eduTrainingClassAllExcels = new ArrayList<>();
        //获取培训班查询的结果
        List<EduTrainingClassListItemDto> list = (List<EduTrainingClassListItemDto>) result.getData().getList();
        //定义序号作用
        int i = 1;
        //存储费用信息的集合
        List<EduTrainingClassFeeItemExcelVo> eduTrainingClassFeeItemExcelVos = new ArrayList<>();
        for (EduTrainingClassListItemDto eduTrainingClassListItemDto : list) {
            //转Excel对象
            EduTrainingClassAllExcel eduTrainingClassAllExcel = ConvertUtils.convert(eduTrainingClassListItemDto,EduTrainingClassAllExcel::new);
            eduTrainingClassAllExcel.setStatus(eduTrainingClassListItemDto.getStringStatus(eduTrainingClassListItemDto.getStatus()));
            if (ObjectUtils.isEmpty(eduTrainingClassListItemDto.getNeedAssess())){
                eduTrainingClassAllExcel.setNeedAssess("未录入");
            }else{
                eduTrainingClassAllExcel.setNeedAssess(eduTrainingClassListItemDto.getAssess(eduTrainingClassListItemDto.getNeedAssess()));
            }
            eduTrainingClassAllExcel.setTrainingTrainee(eduSystemTrainingObjectService.selectById(eduTrainingClassAllExcel.getTrainingTrainee()).getName());
            eduTrainingClassAllExcel.setTrainingObjective(eduSystemTrainingGoalService.selectById(eduTrainingClassAllExcel.getTrainingObjective()).getName());
            eduTrainingClassAllExcel.setTrainingWay(eduTrainingClassListItemDto.getWay(eduTrainingClassListItemDto.getTrainingWay()));
            eduTrainingClassAllExcel.setOrder(i);
            List<EduTrainingClassStudyInfoEntity> entities= eduTrainingClassStudyInfoService.selectList(new EntityWrapper<EduTrainingClassStudyInfoEntity>()
                    .eq("trainingClassId", eduTrainingClassListItemDto.getId()));
            for (EduTrainingClassStudyInfoEntity entity : entities) {
                eduTrainingClassAllExcel.setStudyBelong(entity.getBelong(entity.getStudyBelong()));
                eduTrainingClassAllExcel.setStudyHour(entity.getStudyHour());
                eduTrainingClassAllExcel.setStudyHourType(entity.getStudyType(entity.getStudyHourType()));
                eduTrainingClassAllExcel.setStudyScore(entity.getStudyScore());
            }
            eduTrainingClassAllExcels.add(eduTrainingClassAllExcel);

            List<EduTrainingClassFeeItemEntity> eduTrainingClassFeeItemEntities = eduTrainingClassFeeItemService.selectList(new EntityWrapper<EduTrainingClassFeeItemEntity>()
                    .eq("trainingClassId",eduTrainingClassListItemDto.getId()));

            for (EduTrainingClassFeeItemEntity eduTrainingClassFeeItemEntity : eduTrainingClassFeeItemEntities) {
                EduTrainingClassFeeItemExcelVo eduTrainingClassFeeItemExcelVo = ConvertUtils.convert(eduTrainingClassFeeItemEntity,EduTrainingClassFeeItemExcelVo::new);
                eduTrainingClassFeeItemExcelVo.setFeeType(eduTrainingClassFeeItemEntity.getType(eduTrainingClassFeeItemEntity.getFeeType()));
                eduTrainingClassFeeItemExcelVo.setOrder(i);
                eduTrainingClassFeeItemExcelVo.setClassCode(eduTrainingClassAllExcel.getClassCode());
                eduTrainingClassFeeItemExcelVos.add(eduTrainingClassFeeItemExcelVo);
            }
            i+=1;
        }
        //两个集合都存储给这个dto  方便前端分别调用接口
        EduTrainingClassGetExcelDto eduTrainingClassGetExcelDto = new EduTrainingClassGetExcelDto();
        eduTrainingClassGetExcelDto.setEduTrainingClassAllExcels(eduTrainingClassAllExcels);
        eduTrainingClassGetExcelDto.setEduTrainingClassFeeItemExcelVos(eduTrainingClassFeeItemExcelVos);
        resultData.setData(eduTrainingClassGetExcelDto);
        return resultData;
    }
    @PostMapping("/allExcel")
    @ApiOperation("导出基本信息的数据")
    public void allExcel(HttpServletResponse response,@RequestBody List<EduTrainingClassAllExcel> eduTrainingClassAllExcels){

        String fileName = "培训班基本信息表";
        String date = DateUtil.format(new Date(), "yyyyMMdd-HHmmss");
        fileName = fileName.concat(date);
        //记录哪几列要合并 通过重写的合并策略合并单元格
        int[] mergeColumeIndex = {0,1,2};
        //需要从第一行开始，列头第一行
        int mergeRowIndex = 1;
        EasyExcelUtils.writeExcelByIndex(response,eduTrainingClassAllExcels,fileName,"培训班基本信息表", EduTrainingClassAllExcel.class,mergeColumeIndex,mergeRowIndex);

    }
    @PostMapping("/feeExcel")
    @ApiOperation("导出费用信息的数据")
    public void feeExcel(@RequestBody List<EduTrainingClassFeeItemExcelVo> eduTrainingClassFeeItemExcelVos,HttpServletResponse response){
        int[] merIndex = {0,1};
        //需要从第一行开始，列头第一行
        int mergeRowIndex = 1;
        String date = DateUtil.format(new Date(), "yyyyMMdd-HHmmss");
        EasyExcelUtils.writeExcelByIndex(response,eduTrainingClassFeeItemExcelVos,"费用信息表".concat(date),"费用信息表", EduTrainingClassFeeItemExcelVo.class,merIndex,mergeRowIndex);

    }

}
