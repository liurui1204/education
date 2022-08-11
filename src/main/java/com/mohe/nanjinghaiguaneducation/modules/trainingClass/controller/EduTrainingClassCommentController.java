package com.mohe.nanjinghaiguaneducation.modules.trainingClass.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.ConvertUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.DynamicHeaderListener;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.UploadUtils;
import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.employee.service.EduEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.item.dto.EduItemScoreDto;
import com.mohe.nanjinghaiguaneducation.modules.item.entity.EduItemScoreEntity;
import com.mohe.nanjinghaiguaneducation.modules.item.service.EduItemScoreService;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto.EduCommentUploadDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto.EduTrainingClassCommentDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto.EduTrainingClassCommentListDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto.EduTrainingClassCourseCommentDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.*;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

@Api(tags = "培训班情况评估管理")
@RestController
@RequestMapping("/trainingClass/classComment")
public class EduTrainingClassCommentController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EduItemScoreService eduItemScoreService;

    @Autowired
    private EduTrainingClassCommentService eduTrainingClassCommentService;

    @Autowired
    private EduTrainingClassCourseCommentService eduTrainingClassCourseCommentService;

    @Autowired
    private EduTrainingClassCourseService eduTrainingClassCourseService;

    @Autowired
    private EduTrainingClassService eduTrainingClassService;

    @Autowired
    private EduTrainingClassEmployeeApplyService eduTrainingClassEmployeeApplyService;

    @Autowired
    private EduTrainingClassEmployeeLeaveService eduTrainingClassEmployeeLeaveService;

    @Autowired
    private EduEmployeeService eduEmployeeService;


    @ApiOperation("新增情况评估")
    @PostMapping("/toAdd")
    @Transactional
    public ResultData<String> toAdd(HttpServletRequest request, @ApiParam(value = "新增情况评估参数") @RequestBody EduTrainingClassCommentDto eduTrainingClassCommentDto){
        ResultData<String> resultData = new ResultData<>();

        logger.info("情况评估新增申请 参数为： {}", JSON.toJSONString(eduTrainingClassCommentDto));
        if (ObjectUtils.isEmpty(eduTrainingClassCommentDto)){
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.FAIL);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            logger.info("情况评估新增失败 参数为空");
            return resultData;
        }
        EduTrainingClassCommentEntity eduTrainingClassCommentEntity =
                ConvertUtils.convert(eduTrainingClassCommentDto,EduTrainingClassCommentEntity::new);
        //创建人信息
        EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity) request.getAttribute("userInfo");
        //创建评估id
        String id = IdUtil.simpleUUID();
        eduTrainingClassCommentEntity.setId(id);
        eduTrainingClassCommentEntity.setCreateBy(eduEmployeeEntity.getEmployeeName());
        eduTrainingClassCommentEntity.setIsEnable(1);
        if (ObjectUtils.isEmpty(eduEmployeeService.getUserDepartment(eduEmployeeEntity.getId()).getId())){
            eduTrainingClassCommentEntity.setCreateDepartmentId(eduEmployeeService.getUserDepartment(eduEmployeeEntity.getId()).getId());
        }
        eduTrainingClassCommentEntity.setCreateTime(new Date());
        //获取请求参数中的评估项及评分
        List<EduItemScoreDto>eduItemScoreDtos = eduTrainingClassCommentDto.getEduItemScoreDtos();
        if (ObjectUtils.isEmpty(eduItemScoreDtos)){
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.FAIL);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            logger.info("情况评估新增失败 评估项参数为空");
            return resultData;
        }
        List<EduItemScoreEntity> eduItemScoreEntitys = ConvertUtils.convertList(eduItemScoreDtos,EduItemScoreEntity::new);
        eduItemScoreEntitys.forEach(k->{
            //创建评估项分数ID
            String itemScoreId = IdUtil.simpleUUID();
            k.setId(itemScoreId);
            k.setCommentId(id);
            k.setIsEnable(1);
            k.setCreateTime(new Date());
            k.setCreateBy(eduEmployeeEntity.getEmployeeCode());
            eduItemScoreService.insert(k);
        });
        eduTrainingClassCommentService.insert(eduTrainingClassCommentEntity);
        resultData.setData(id);
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        return resultData;
    }

    @Transactional
    @ApiOperation("新增课程评估 ")
    @PostMapping("/course/toAdd")
    public ResultData<String> courseToAdd(HttpServletRequest request, @ApiParam(value = "新增课程评估参数") @RequestBody Map map ){
        ResultData resultData = new ResultData<>();
        List<EduTrainingClassCourseCommentDto> eduTrainingClassCourseCommentDtoList = (List<EduTrainingClassCourseCommentDto>) map.get("eduTrainingClassCourseCommentDtos");
        String str = JSON.toJSONString(eduTrainingClassCourseCommentDtoList);
        List<EduTrainingClassCourseCommentDto> eduTrainingClassCourseCommentDtos = JSON.parseArray(str,EduTrainingClassCourseCommentDto.class);
        logger.info("课程评估新增申请 参数为： {}", JSON.toJSONString(eduTrainingClassCourseCommentDtos));
        if (ObjectUtils.isEmpty(eduTrainingClassCourseCommentDtos)){
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.FAIL);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            logger.info("课程评估新增失败 参数为空");
            return resultData;
        }
        List<String> list = new ArrayList<>();
        for (EduTrainingClassCourseCommentDto eduTrainingClassCourseCommentDto : eduTrainingClassCourseCommentDtos) {
            EduTrainingClassCourseCommentEntity eduTrainingClassCourseCommentEntity =
                    ConvertUtils.convert(eduTrainingClassCourseCommentDto,EduTrainingClassCourseCommentEntity::new);
            //创建人信息
            EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity) request.getAttribute("userInfo");
            //创建评估id
            String id = IdUtil.simpleUUID();
            list.add(id);
            eduTrainingClassCourseCommentEntity.setId(id);
            eduTrainingClassCourseCommentEntity.setCreateBy(eduEmployeeEntity.getEmployeeName());
            eduTrainingClassCourseCommentEntity.setIsEnable(1);
//        eduTrainingClassCourseCommentEntity.setCreateDepartmentId(eduEmployeeEntity.getDepartmentId());
            if (ObjectUtils.isEmpty(eduEmployeeService.getUserDepartment(eduEmployeeEntity.getId()).getId())){
                eduTrainingClassCourseCommentEntity.setCreateDepartmentId(eduEmployeeService.getUserDepartment(eduEmployeeEntity.getId()).getId());
            }
            eduTrainingClassCourseCommentEntity.setCreateTime(new Date());
            //获取请求参数中的评估项及评分
            List<EduItemScoreDto>eduItemScoreDtos = eduTrainingClassCourseCommentDto.getEduItemScoreDtos();
            if (ObjectUtils.isEmpty(eduItemScoreDtos)){
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.FAIL);
                resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
                logger.info("课程评估新增失败 评估项参数为空");
                return resultData;
            }
            List<EduItemScoreEntity> eduItemScoreEntitys = ConvertUtils.convertList(eduItemScoreDtos,EduItemScoreEntity::new);
            List<EduItemScoreEntity> entities = new ArrayList<>();
            eduItemScoreEntitys.forEach(k->{
                //创建评估项分数ID
                String itemScoreId = IdUtil.simpleUUID();
                k.setId(itemScoreId);
                k.setCommentId(id);
                k.setIsEnable(1);
                k.setCreateTime(new Date());
                k.setCreateBy(eduEmployeeEntity.getEmployeeName());
                entities.add(k);
            });
            eduItemScoreService.insertBatch(entities);
            eduTrainingClassCourseCommentService.insert(eduTrainingClassCourseCommentEntity);
        }
//        for (EduTrainingClassCourseCommentDto eduTrainingClassCourseCommentDto : eduTrainingClassCourseCommentDtos) {
//
//        }

        resultData.setData(list);
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        return resultData;
    }


    @ApiOperation("情况评估导入")
    @PostMapping("/upload")
    public ResultData upload(EduCommentUploadDto eduCommentUploadDto){
        ResultData resultData = new ResultData();
        try {
            //加载拦截器 作用于 获取表头
            DynamicHeaderListener noModelDataListener = new DynamicHeaderListener();
            //解析数据
            List<Map<Integer,Object>> list = EasyExcel.read(eduCommentUploadDto.getFile().getInputStream(),noModelDataListener).sheet(0).doReadSync();
            EduTrainingClassEntity eduTrainingClassEntity = eduTrainingClassService.selectById(eduCommentUploadDto.getClassId());
            if (eduTrainingClassEntity.getHappenStatus()==1){
                eduTrainingClassCommentService.delete(new EntityWrapper<EduTrainingClassCommentEntity>().eq("trainingClassId",eduCommentUploadDto.getClassId()));
            }
            //获取拦截器拦截到的 表头的Map集合
            Map<Integer, String> key = noModelDataListener.getKey();
            String name = DateUtil.format(new Date(),"yyyyMMddHHmmss")+"网络培训班数据导入";
            //文件上传到服务器
            UploadUtils.upload(eduCommentUploadDto.getFile(), name);
            List<EduTrainingClassCommentEntity> entities = new ArrayList<>();

            for (Map<Integer, Object> integerObjectMap : list) {
                EduTrainingClassCommentEntity eduTrainingClassCommentEntity = new EduTrainingClassCommentEntity();
                String id = IdUtil.simpleUUID();
                eduTrainingClassCommentEntity.setId(id);
                eduTrainingClassCommentEntity.setIsEnable(1);
                eduTrainingClassCommentEntity.setSuggestion((String) integerObjectMap.get(18));
                String s = integerObjectMap.get(17).toString();
                eduTrainingClassCommentEntity.setScore(Integer.parseInt(s));
                eduTrainingClassCommentEntity.setTrainingClassId(eduCommentUploadDto.getClassId());
                eduTrainingClassCommentEntity.setCreateTime(new Date());
                entities.add(eduTrainingClassCommentEntity);
                insertScore(key,integerObjectMap,id);
            }
            eduTrainingClassCommentService.insertBatch(entities);
            eduTrainingClassEntity.setHappenStatus(1);
            eduTrainingClassService.updateById(eduTrainingClassEntity);
        }catch (Exception e){
            e.printStackTrace();
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage("文件格式不匹配");
            return resultData;
        }
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        return resultData;
    }

    @ApiOperation("课程评估导入")
    @PostMapping("/courseUpload")
    public ResultData courseUpload(EduCommentUploadDto eduCommentUploadDto){
        ResultData resultData = new ResultData();
        try {
            //加载拦截器 作用于 获取表头
            DynamicHeaderListener noModelDataListener = new DynamicHeaderListener();
            //解析数据
            List<Map<Integer,Object>> list = EasyExcel.read(eduCommentUploadDto.getFile().getInputStream(),noModelDataListener).sheet(0).doReadSync();
            //获取拦截器拦截到的 表头的Map集合
            Map<Integer, String> key = noModelDataListener.getKey();
            String name = DateUtil.format(new Date(),"yyyyMMddHHmmss")+"网络培训班数据导入";
            eduTrainingClassCourseCommentService.delete(new EntityWrapper<EduTrainingClassCourseCommentEntity>()
                    .eq("trainingClassId",eduCommentUploadDto.getClassId()).eq("courseId",eduCommentUploadDto.getCourseId()));
            //文件上传到服务器
            UploadUtils.upload(eduCommentUploadDto.getFile(), name);
            List<EduTrainingClassCourseCommentEntity> entities = new ArrayList<>();
            for (Map<Integer, Object> integerObjectMap : list) {
                EduTrainingClassCourseCommentEntity entity = new EduTrainingClassCourseCommentEntity();
                String id = IdUtil.simpleUUID();
                entity.setId(id);
                entity.setIsEnable(1);
                entity.setSuggestion((String) integerObjectMap.get(19));
                String score  = integerObjectMap.get(18).toString();
                entity.setScore(Integer.parseInt(score));
                entity.setTrainingClassId(eduCommentUploadDto.getClassId());
                entity.setCourseId(eduCommentUploadDto.getCourseId());
                entity.setCreateTime(new Date());
                entities.add(entity);
                insertCourseScore(key,integerObjectMap,id);
            }
            eduTrainingClassCourseCommentService.insertBatch(entities);
        }catch (Exception e){
            e.printStackTrace();
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage("文件格式不匹配");
            return resultData;
        }
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        return resultData;
    }

    private void insertScore(Map<Integer, String> key, Map<Integer, Object> integerObjectMap, String id) {
        List<EduItemScoreEntity> entities = new ArrayList<>();
        for (int i=0;i<11;i++){
            EduItemScoreEntity entity = new EduItemScoreEntity();
            String itemName = key.get(6 + i);
            String itemType = "";
            String type = itemName.substring(0,1);
            switch (type){
                case "1":
                    itemType = "培训设计";
                    break;
                case "2":
                    itemType = "培训实施";
                    break;
                case "3":
                    itemType = "培训管理";
                    break;
                case "4":
                    itemType = "培训效果";
                    break;
                case "5":
                    itemType = "培训环境";
                    break;
                default:
                    break;
            }
            if (itemName.contains("—")){
                itemName = itemName.substring(itemName.lastIndexOf("—")+1);
            }else {
                itemName = itemName.substring(2);
            }
            entity.setItemType(itemType);
            entity.setItemName(itemName);
            String s = integerObjectMap.get(6 + i).toString();
            String score = "";
            switch (s){
                case "5":
                    score = "4";
                    break;
                case "4":
                    score = "3";
                    break;
                case "3":
                    score = "2";
                    break;
                case "1":
                    score = "0";
                    break;
                case "2":
                    score = "1";
                    break;
                default:
                    break;
            }
            entity.setScore(score);
            entity.setCommentId(id);
            entity.setId(IdUtil.simpleUUID());
            entity.setIsEnable(1);
            entities.add(entity);
        }
        eduItemScoreService.insertBatch(entities);
    }


    private void insertCourseScore(Map<Integer, String> key, Map<Integer, Object> integerObjectMap, String id) {
        List<EduItemScoreEntity> entities = new ArrayList<>();
        for (int i=0;i<12;i++){
            EduItemScoreEntity entity = new EduItemScoreEntity();
            String itemName = key.get(6 + i);
            String itemType = "";
            if (itemName.contains("——") && itemName.length()>13){
                itemType = itemName.substring(itemName.indexOf("—",1)+1,itemName.lastIndexOf("—")-1);
                itemName = itemName.substring(itemName.lastIndexOf("—")+1);
            }else if (itemName.contains("—")){
                itemType = itemName.substring(2,itemName.lastIndexOf("—")-1);
                itemName = itemName.substring(itemName.lastIndexOf("—")+1);
            }else {
                itemName = itemName.substring(2);
            }

            entity.setItemType(itemType);
            entity.setItemName(itemName);
            String s = integerObjectMap.get(6 + i).toString();
            String score ="";
            switch (s){
                case "很满意":
                    score = "4";
                    break;
                case "满意":
                    score = "3";
                    break;
                case "一般":
                    score = "2";
                    break;
                case "很不满意":
                    score = "0";
                    break;
                case "不满意":
                    score = "1";
                    break;
                default:
                    break;
            }
            entity.setScore(score);
            entity.setCommentId(id);
            entity.setId(IdUtil.simpleUUID());

            entity.setIsEnable(1);
            entities.add(entity);

        }
        eduItemScoreService.insertBatch(entities);
    }


    @ApiOperation("培训班评论列表")
    @PostMapping("/list")
    public ResultData<EduTrainingClassCommentListEntity> list(@RequestBody EduTrainingClassCommentListDto eduTrainingClassCommentListDto){
        ResultData<EduTrainingClassCommentListEntity> resultData = new ResultData();
        logger.info("培训班评论列表参数为: {}",JSON.toJSONString(eduTrainingClassCommentListDto));
        if (ObjectUtils.isEmpty(eduTrainingClassCommentListDto.getTrainingClassId())){
            resultData.setMessage("培训班Id不能为空");
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
        }

        EduTrainingClassCommentListEntity eduTrainingClassCommentListEntity = new EduTrainingClassCommentListEntity();
//        //查询培训班下的人数 作为总量
//        EduTrainingClassEntity eduTrainingClassEntity = eduTrainingClassService.selectById(eduTrainingClassCommentListDto.getTrainingClassId());
//        if (ObjectUtils.isEmpty(eduTrainingClassEntity)){
//            resultData.setMessage("查无该培训班");
//            resultData.setCode(SysConstant.FAIL);
//            resultData.setSuccess(false);
//        }
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(0);
        List<EduTrainingClassEmployeeApplyEntity> eduTrainingClassEmployeeApplyEntities = eduTrainingClassEmployeeApplyService.selectList(new EntityWrapper<EduTrainingClassEmployeeApplyEntity>()
                .eq("trainingClassId",eduTrainingClassCommentListDto.getTrainingClassId()).eq("status",1));
        List<EduTrainingClassEmployeeLeaveEntity> eduTrainingClassEmployeeLeaveEntities = eduTrainingClassEmployeeLeaveService.selectList(new EntityWrapper<EduTrainingClassEmployeeLeaveEntity>()
                .eq("trainingClassId", eduTrainingClassCommentListDto.getTrainingClassId()).eq("status", 1));
        Integer trainingPeopleNum = eduTrainingClassEmployeeApplyEntities.size()-eduTrainingClassEmployeeLeaveEntities.size();
        eduTrainingClassCommentListEntity.setNeedNum(trainingPeopleNum);
        if (eduTrainingClassCommentListDto.getCommentType().equals("0")){
            //当日完成人数
            int count = eduTrainingClassCommentService.findByDate(new Date(),eduTrainingClassCommentListDto.getTrainingClassId());
            EntityWrapper<EduTrainingClassCommentEntity>entityEntityWrapper = new EntityWrapper<>();
            entityEntityWrapper.eq("trainingClassId",eduTrainingClassCommentListDto.getTrainingClassId());
            //已完成了的人的详情
            List<EduTrainingClassCommentEntity> eduTrainingClassCommentEntities = eduTrainingClassCommentService.selectList(entityEntityWrapper);
            //已完成人数
            int finishNum = eduTrainingClassCommentEntities.size();


            String satisfaction = "0%";
            if(trainingPeopleNum!=0){
                satisfaction = numberFormat.format((float)finishNum/(float)trainingPeopleNum*100) + "%";
            }
//            EntityWrapper<EduTrainingClassEmployeeApplyEntity>eduTrainingClassEmployeeApplyEntityEntityWrapper = new EntityWrapper<>();
//            eduTrainingClassEmployeeApplyEntityEntityWrapper.eq("trainingClassId",eduTrainingClassCommentListDto.getTrainingClassId());
//            //查询该培训班下的所有学员详情
//            List<EduTrainingClassEmployeeApplyEntity> eduTrainingClassEmployeeApplyEntities = eduTrainingClassEmployeeApplyService.selectList(eduTrainingClassEmployeeApplyEntityEntityWrapper);
            List undoneId = new ArrayList();
            List finalId = new ArrayList<>();
            undoneId.add("1");
            finalId.add("1");
            List<String> leaveIds = new ArrayList();
            for (EduTrainingClassEmployeeLeaveEntity eduTrainingClassEmployeeLeaveEntity : eduTrainingClassEmployeeLeaveEntities) {
                leaveIds.add(eduTrainingClassEmployeeLeaveEntity.getEmployeeId());
            }
            for (EduTrainingClassEmployeeApplyEntity eduTrainingClassEmployeeApplyEntity : eduTrainingClassEmployeeApplyEntities) {
                Boolean isTrue = false;
                Boolean isLeave = false;
                for (String leaveId : leaveIds) {
                    if (eduTrainingClassEmployeeApplyEntity.getEmployeeId().equals(leaveId)){
                        isLeave = true;
                        break;
                    }
                }
                if (isLeave){
                    continue;
                }
                for (EduTrainingClassCommentEntity eduTrainingClassCommentEntity : eduTrainingClassCommentEntities) {
                    if (eduTrainingClassEmployeeApplyEntity.getEmployeeId().equals(eduTrainingClassCommentEntity.getEmployeeId())){
                        isTrue = true;
                        finalId.add(eduTrainingClassEmployeeApplyEntity.getEmployeeId());
                        break;
//                        EduEmployeeEntity eduEmployeeEntity = eduEmployeeService.selectById(eduTrainingClassCourseCommentEntity.getEmployeeId());
//                        finishEduEmployeeEntities.add(eduEmployeeEntity);
                    }
                }
                if (isTrue == false){
                    undoneId.add(eduTrainingClassEmployeeApplyEntity.getEmployeeId());
//                    EduEmployeeEntity eduEmployeeEntity = eduEmployeeService.selectById(eduTrainingClassEmployeeApplyEntity.getEmployeeId());
//                    eduEmployeeEntities.add(eduEmployeeEntity);
                }
            }
            //用来存储 未完成的学员详情的集合
            List<EduEmployeeEntity> eduEmployeeEntities = eduEmployeeService.selectList(new EntityWrapper<EduEmployeeEntity>().in("id",undoneId));
            eduEmployeeEntities.forEach(k-> {
                String[] split = k.getH4aAllPathName().split("\\\\");
                k.setDepartmentName(split[2]);
            });
            //用来存储 完成的学员详情的集合
            List<EduEmployeeEntity> finishEduEmployeeEntities = eduEmployeeService.selectList(new EntityWrapper<EduEmployeeEntity>().in("id",finalId));
            finishEduEmployeeEntities.forEach(k->{
                String[] split = k.getH4aAllPathName().split("\\\\");
                k.setDepartmentName(split[2]);
            });

            eduTrainingClassCommentListEntity.setEduEmployeeEntityList(eduEmployeeEntities);
            eduTrainingClassCommentListEntity.setFinishEduEmployeeEntities(finishEduEmployeeEntities);
            eduTrainingClassCommentListEntity.setFinishNum(finishNum);
            eduTrainingClassCommentListEntity.setSatisfaction(satisfaction);
            eduTrainingClassCommentListEntity.setTodayNum(count);
        }
        if (eduTrainingClassCommentListDto.getCommentType().equals("1")){
            List<EduTrainingClassCourseCommentEntity> courseList  = eduTrainingClassCourseCommentService.selectList(new EntityWrapper<EduTrainingClassCourseCommentEntity>().eq("trainingClassId", eduTrainingClassCommentListDto.getTrainingClassId())
                    .groupBy("courseId"));
            Integer selectCount = courseList.size();
            int count = eduTrainingClassCourseCommentService.findByDate(new Date(),eduTrainingClassCommentListDto.getTrainingClassId());
            EntityWrapper<EduTrainingClassCourseCommentEntity>entityEntityWrapper = new EntityWrapper<>();
            entityEntityWrapper.eq("trainingClassId",eduTrainingClassCommentListDto.getTrainingClassId());
            //已完成了的人的详情
            List<EduTrainingClassCourseCommentEntity> eduTrainingClassCourseCommentEntities = eduTrainingClassCourseCommentService.selectList(entityEntityWrapper);
            //已完成人数
            int finishNum = eduTrainingClassCourseCommentEntities.size();
            String satisfaction = "0%";
            if(selectCount!=0 && trainingPeopleNum!=0){
                satisfaction = numberFormat.format((float)finishNum/selectCount/(float)trainingPeopleNum*100) + "%";
            }

            List undoneId = new ArrayList();
            List finalId = new ArrayList<>();
            undoneId.add("1");
            finalId.add("1");
            List<String> leaveIds = new ArrayList();
            for (EduTrainingClassEmployeeLeaveEntity eduTrainingClassEmployeeLeaveEntity : eduTrainingClassEmployeeLeaveEntities) {
                leaveIds.add(eduTrainingClassEmployeeLeaveEntity.getEmployeeId());
            }
            for (EduTrainingClassEmployeeApplyEntity eduTrainingClassEmployeeApplyEntity : eduTrainingClassEmployeeApplyEntities) {
                Boolean isTrue = false;
                Boolean isLeave = false;
                for (String leaveId : leaveIds) {
                    if (eduTrainingClassEmployeeApplyEntity.getEmployeeId().equals(leaveId)){
                        isLeave = true;
                        break;
                    }
                }
                if (isLeave){
                    continue;
                }
                for (EduTrainingClassCourseCommentEntity eduTrainingClassCourseCommentEntity : eduTrainingClassCourseCommentEntities) {
                    if (eduTrainingClassEmployeeApplyEntity.getEmployeeId().equals(eduTrainingClassCourseCommentEntity.getEmployeeId())){
                        isTrue = true;
                        finalId.add(eduTrainingClassEmployeeApplyEntity.getEmployeeId());
                        break;
//                        EduEmployeeEntity eduEmployeeEntity = eduEmployeeService.selectById(eduTrainingClassCourseCommentEntity.getEmployeeId());
//                        finishEduEmployeeEntities.add(eduEmployeeEntity);
                    }
                }
                if (isTrue == false){
                    undoneId.add(eduTrainingClassEmployeeApplyEntity.getEmployeeId());
//                    EduEmployeeEntity eduEmployeeEntity = eduEmployeeService.selectById(eduTrainingClassEmployeeApplyEntity.getEmployeeId());
//                    eduEmployeeEntities.add(eduEmployeeEntity);
                }
            }
            //用来存储 未完成的学员详情的集合
            List<EduEmployeeEntity> eduEmployeeEntities = eduEmployeeService.selectList(new EntityWrapper<EduEmployeeEntity>().in("id",undoneId));
            eduEmployeeEntities.forEach(k-> {
                        String[] split = k.getH4aAllPathName().split("\\\\");
                        k.setDepartmentName(split[2]);
                    });
            //用来存储 完成的学员详情的集合
            List<EduEmployeeEntity> finishEduEmployeeEntities = eduEmployeeService.selectList(new EntityWrapper<EduEmployeeEntity>().in("id",finalId));
            finishEduEmployeeEntities.forEach(k->{
                String[] split = k.getH4aAllPathName().split("\\\\");
                k.setDepartmentName(split[2]);
            });
            eduTrainingClassCommentListEntity.setEduEmployeeEntityList(eduEmployeeEntities);
            eduTrainingClassCommentListEntity.setFinishEduEmployeeEntities(finishEduEmployeeEntities);
            if (selectCount ==0){
                eduTrainingClassCommentListEntity.setFinishNum(finishNum);
                eduTrainingClassCommentListEntity.setTodayNum(count);
            }else {
                eduTrainingClassCommentListEntity.setFinishNum(finishNum/selectCount);
                eduTrainingClassCommentListEntity.setTodayNum(count/selectCount);
            }

            eduTrainingClassCommentListEntity.setSatisfaction(satisfaction);

        }

        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setData(eduTrainingClassCommentListEntity);
        return resultData;
    }

    @ApiOperation("培训班评估答题详情")
    @PostMapping("/view")
    public ResultData view(@RequestBody EduTrainingClassCommentListDto eduTrainingClassCommentListDto){
        logger.info("培训班评估答题详情参数为: {}",JSON.toJSONString(eduTrainingClassCommentListDto));
        ResultData resultData = new ResultData();
        if (ObjectUtils.isEmpty(eduTrainingClassCommentListDto.getTrainingClassId())){
            resultData.setMessage("培训班Id不能为空");
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
        }
        try {
            if (eduTrainingClassCommentListDto.getCommentType().equals("0")){
                EduTrainingClassViewEntity eduTrainingClassCommentServiceView = eduTrainingClassCommentService.findView(eduTrainingClassCommentListDto.getTrainingClassId());
                resultData.setData(eduTrainingClassCommentServiceView);
            }
            if (eduTrainingClassCommentListDto.getCommentType().equals("1")){
                List<EduTrainingClassViewEntity> eduTrainingClassCourseCommentServiceView = eduTrainingClassCourseCommentService.findView(eduTrainingClassCommentListDto.getTrainingClassId());
                resultData.setData(eduTrainingClassCourseCommentServiceView);
            }
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e){
            resultData.setMessage(e.getMessage());
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
        }
        return resultData;
    }

}
