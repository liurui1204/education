package com.mohe.nanjinghaiguaneducation.modules.trainingBase.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.ConvertUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.UploadUtils;
import com.mohe.nanjinghaiguaneducation.modules.redBase.entity.EduRedBaseEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.dto.EduTeacherDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.dto.EduTrainingBaseAddDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.dto.EduTrainingBaseUpdateDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.entity.EduTrainingBaseAssociationEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.entity.EduTrainingBaseEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.service.EduTrainingBaseAssociationService;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.service.EduTrainingBaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;

import javax.servlet.http.HttpServletRequest;


/**
 * 实训基地
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:22
 */
@RestController
@Api(tags = "实训基地")
@RequestMapping("site/trainingBase")
public class EduTrainingBaseController {
    @Autowired
    private EduTrainingBaseService eduTrainingBaseService;

    @Autowired
    private EduTrainingBaseAssociationService eduTrainingBaseAssociationService;
    @PostMapping("/list")
    @ApiOperation("实训基地列表")
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
            PageUtils pageUtils = eduTrainingBaseService.queryPage(params);

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

    @ApiOperation("实训基地新增")
    @PostMapping("/add")
    public ResultData add(@RequestBody EduTrainingBaseAddDto eduTrainingBaseDto, HttpServletRequest request){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        String userId = request.getAttribute("userId").toString();
        EduTrainingBaseEntity entity = ConvertUtils.convert(eduTrainingBaseDto,EduTrainingBaseEntity::new);
        entity.setCreateBy(userId);
        entity.setUpdateTime(new Date());
        entity.setCreateTime(new Date());
        eduTrainingBaseService.insert(entity);
//        EduTrainingBaseEntity id = eduTrainingBaseService.selectOne(new EntityWrapper<EduTrainingBaseEntity>().orderBy("id", false)
//                .last("limit 1"));
        eduTrainingBaseAssociationService.insertBase(entity.getId(),eduTrainingBaseDto);
        resultData.setData(entity.getId());
        return resultData;
    }
    @ApiOperation("实训基地查看")
    @PostMapping("/info")
    public ResultData info(@RequestBody Map<String,Object>params){
        ResultData resultData = new ResultData();
        EduTrainingBaseEntity eduTrainingBaseEntity = eduTrainingBaseService.selectById(params.get("id").toString());
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setData(eduTrainingBaseEntity);
        return resultData;
    }
    @ApiOperation("实训基地删除")
    @PostMapping("/delete")
    public ResultData delete(@RequestBody Map<String,Object>params){
        ResultData resultData = new ResultData();
        Integer id = (Integer)params.get("id");
        eduTrainingBaseService.deleteById(id);
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        return resultData;
    }

    @ApiOperation("实训基地修改")
    @PostMapping("/update")
    public ResultData update(@RequestBody EduTrainingBaseUpdateDto eduTrainingBaseUpdateDto){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        EduTrainingBaseEntity entity = eduTrainingBaseService.selectById(eduTrainingBaseUpdateDto.getId());
        if (ObjectUtils.isEmpty(entity)){
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage("找不到该记录");
        }
        //查询该实训基地下的所有教师
        List<EduTrainingBaseAssociationEntity> eduTrainingBaseAssociationEntities = eduTrainingBaseAssociationService.selectList(new EntityWrapper<EduTrainingBaseAssociationEntity>()
                .eq("baseId", eduTrainingBaseUpdateDto.getId()).eq("disabled",0));
        List<EduTeacherDto> eduTeacherDtos = eduTrainingBaseUpdateDto.getEduTeacherDtos();
        if (!ObjectUtils.isEmpty(eduTeacherDtos)){
            //for循环排除已经删除的数据
            for (EduTrainingBaseAssociationEntity eduTrainingBaseAssociationEntity : eduTrainingBaseAssociationEntities) {
                Boolean isDel = true;
                for (EduTeacherDto eduTeacherDto: eduTeacherDtos) {
                    if (eduTeacherDto.getTeacherId().equals(eduTrainingBaseAssociationEntity.getTeacherId())){
                        isDel = false;
                    }
                }
                if (isDel){
                    eduTrainingBaseAssociationService.deleteById(eduTrainingBaseAssociationEntity.getId());
                }
            }
            //for循环查找表里没有的数据 然后新增

            for (EduTeacherDto eduTeacherDto : eduTeacherDtos) {
                EduTrainingBaseAssociationEntity eduTrainingBaseAssociationEntity = eduTrainingBaseAssociationService.selectOne(new EntityWrapper<EduTrainingBaseAssociationEntity>()
                        .eq("baseId", eduTrainingBaseUpdateDto.getId()).eq("teacherId", eduTeacherDto.getTeacherId()).eq("disabled", 0));
                if (ObjectUtils.isEmpty(eduTrainingBaseAssociationEntity)){
                    EduTrainingBaseAssociationEntity eduTrainingBaseAssociation  = new EduTrainingBaseAssociationEntity();
                    eduTrainingBaseAssociation.setBaseId(eduTrainingBaseUpdateDto.getId());
                    eduTrainingBaseAssociation.setTeacherId(eduTeacherDto.getTeacherId());
                    eduTrainingBaseAssociation.setTeacherType(eduTeacherDto.getTeacherType());
                    eduTrainingBaseAssociation.setCreateTime(new Date());
                    eduTrainingBaseAssociationService.insert(eduTrainingBaseAssociation);
                }
            }
        }



        BeanUtil.copyProperties(eduTrainingBaseUpdateDto,entity);
        entity.setUpdateTime(new Date());
        eduTrainingBaseService.updateById(entity);
        return resultData;
    }

}
