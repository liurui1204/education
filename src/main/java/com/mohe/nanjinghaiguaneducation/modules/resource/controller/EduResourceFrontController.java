package com.mohe.nanjinghaiguaneducation.modules.resource.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.exception.RRException;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.ConvertUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.resource.dto.EduResourceGetResourceDetailFrontDto;
import com.mohe.nanjinghaiguaneducation.modules.resource.dto.EduResourceGetResourceListFrontDto;
import com.mohe.nanjinghaiguaneducation.modules.resource.dto.EduResourceTypeTreeDto;
import com.mohe.nanjinghaiguaneducation.modules.resource.entity.EduResourceEntity;
import com.mohe.nanjinghaiguaneducation.modules.resource.entity.EduResourceTypeEntity;
import com.mohe.nanjinghaiguaneducation.modules.resource.service.EduResourceService;
import com.mohe.nanjinghaiguaneducation.modules.resource.service.EduResourceTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Api(tags = "资源管理-前台")
@RestController
@RequestMapping("/front/resourceManage")
public class EduResourceFrontController {

    @Autowired
    private EduResourceService eduResourceService;
    @Autowired
    private EduResourceTypeService eduResourceTypeService;

    @ApiOperation("资源类型树-全部树节点-只取3级")
    @PostMapping("/typeTree")
    public ResultData<List<EduResourceTypeTreeDto>> getTypeTree(){
        ResultData<List<EduResourceTypeTreeDto>> eduResourceTypeTreeDtoResultData = new ResultData<List<EduResourceTypeTreeDto>>();
        try {
            List<EduResourceTypeTreeDto> eduResourceTypeTreeDtoList = new ArrayList<>();
            //不写递归了，手动取三级，太多级也不好显示
            List<EduResourceTypeEntity> level1 = eduResourceTypeService.selectList(new EntityWrapper<EduResourceTypeEntity>()
                    .eq("level", 1).eq("isEnable", 1));
            for(EduResourceTypeEntity entityLv1 : level1){
                List<EduResourceTypeTreeDto> level2List = new ArrayList<>();
                EduResourceTypeTreeDto lv1Dto = ConvertUtils.convert(entityLv1,EduResourceTypeTreeDto::new);
                if(eduResourceTypeService.selectCount(new EntityWrapper<EduResourceTypeEntity>().eq("parentId", entityLv1.getId()))>0){
                    //如果当前节点有子节点，就取下一级的节点
                    List<EduResourceTypeEntity> level2 = eduResourceTypeService.selectList(new EntityWrapper<EduResourceTypeEntity>()
                            .eq("level", 2).eq("parentId",entityLv1.getId()).eq("isEnable", 1));
                    for(EduResourceTypeEntity entityLv2 : level2){
                        List<EduResourceTypeTreeDto> level3List = new ArrayList<>();
                        if(eduResourceTypeService.selectCount(new EntityWrapper<EduResourceTypeEntity>().eq("parentId", entityLv1.getId()))>0){
                            List<EduResourceTypeEntity> level3 = eduResourceTypeService.selectList(new EntityWrapper<EduResourceTypeEntity>()
                                    .eq("level", 3).eq("parentId",entityLv2.getId()).eq("isEnable", 1));
                            for(EduResourceTypeEntity level3Entity : level3){
                                EduResourceTypeTreeDto tmp3 = ConvertUtils.convert(level3Entity,EduResourceTypeTreeDto::new);
                                level3List.add(tmp3);
                            }
                        }
                        EduResourceTypeTreeDto tmp2 = ConvertUtils.convert(entityLv2,EduResourceTypeTreeDto::new);
                        tmp2.setChildren(level3List);
                        level2List.add(tmp2);
                    }
                }
                lv1Dto.setChildren(level2List);
                eduResourceTypeTreeDtoList.add(lv1Dto);
            }
            eduResourceTypeTreeDtoResultData.setData(eduResourceTypeTreeDtoList);
            eduResourceTypeTreeDtoResultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            eduResourceTypeTreeDtoResultData.setCode(SysConstant.SUCCESS);
            eduResourceTypeTreeDtoResultData.setSuccess(true);
        }catch (Exception e) {
            eduResourceTypeTreeDtoResultData.setSuccess(false);
            eduResourceTypeTreeDtoResultData.setCode(SysConstant.PARAMETER_ERROR);
            eduResourceTypeTreeDtoResultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
            return eduResourceTypeTreeDtoResultData;
        }
        return eduResourceTypeTreeDtoResultData;
    }

    @ApiOperation("资源列表")
    @PostMapping("/list")
    public ResultData<PageUtils> getResourceList(@RequestBody EduResourceGetResourceListFrontDto eduResourceGetResourceListFrontDto){
        ResultData<PageUtils> resultData = new ResultData<>();
        try {
//            List<EduResourceEntity> eduResourceEntities = eduResourceService.selectList(new EntityWrapper<EduResourceEntity>().eq("isEnable", 1)
//                    .eq("typeId", eduResourceGetResourceListFrontDto.getTypeId()));
            HashMap<String, Object> params = new HashMap<>();
            params.put("page", eduResourceGetResourceListFrontDto.getPage());
            params.put("limit", eduResourceGetResourceListFrontDto.getLimit());
            params.put("typeId", eduResourceGetResourceListFrontDto.getTypeId());
            params.put("title",eduResourceGetResourceListFrontDto.getTitle());
            params.put("endTime",eduResourceGetResourceListFrontDto.getEndTime());
            params.put("startTime",eduResourceGetResourceListFrontDto.getStartTime());
            PageUtils pageUtils = eduResourceService.queryPage(params);
            resultData.setData(pageUtils);
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

    @ApiOperation("资源详情")
    @PostMapping("/detail")
    public ResultData<EduResourceEntity> detail(@RequestBody EduResourceGetResourceDetailFrontDto eduResourceGetResourceDetailFrontDto){
        ResultData<EduResourceEntity> resultData = new ResultData<>();
        try {
            if(BeanUtil.isEmpty(eduResourceGetResourceDetailFrontDto.getId()) || eduResourceGetResourceDetailFrontDto.getId()<1){
                throw new RRException("资源ID有误");
            }
            EduResourceEntity entity = eduResourceService.selectOne(new EntityWrapper<EduResourceEntity>().eq("id", eduResourceGetResourceDetailFrontDto.getId()));
            resultData.setData(entity);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
        }catch (Exception e) {
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.PARAMETER_ERROR);
            resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
        }
        return resultData;
    }

    @ApiOperation("资源详情-增加查看记录")
    @PostMapping("/detail/viewRecord")
    public ResultData<Boolean> viewRecord(@RequestBody EduResourceGetResourceDetailFrontDto eduResourceGetResourceDetailFrontDto) {
        ResultData<Boolean> resultData = new ResultData<>();
        try {
            if(BeanUtil.isEmpty(eduResourceGetResourceDetailFrontDto.getId()) || eduResourceGetResourceDetailFrontDto.getId()<1){
                throw new RRException("资源ID有误");
            }
            EduResourceEntity entity = eduResourceService.selectOne(new EntityWrapper<EduResourceEntity>().eq("id", eduResourceGetResourceDetailFrontDto.getId()));
            Long cnt = 1L;
            if(BeanUtil.isNotEmpty(entity.getViewCount()) && entity.getViewCount()>0){
                cnt = entity.getViewCount()+1;
            }
            entity.setViewCount(cnt);
            boolean b = eduResourceService.updateById(entity);
            resultData.setData(b);
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

    @ApiOperation("资源详情-增加下载记录")
    @PostMapping("/detail/downloadRecord")
    public ResultData<Boolean> downloadRecord(@RequestBody EduResourceGetResourceDetailFrontDto eduResourceGetResourceDetailFrontDto){
        ResultData<Boolean> resultData = new ResultData<>();
        try {
            if(BeanUtil.isEmpty(eduResourceGetResourceDetailFrontDto.getId()) || eduResourceGetResourceDetailFrontDto.getId()<1){
                throw new RRException("资源ID有误");
            }
            EduResourceEntity entity = eduResourceService.selectOne(new EntityWrapper<EduResourceEntity>().eq("id", eduResourceGetResourceDetailFrontDto.getId()));
            Long cnt = 1L;
            if(BeanUtil.isNotEmpty(entity.getDownloadCount()) && entity.getDownloadCount()>0){
                cnt = entity.getDownloadCount()+1;
            }
            entity.setDownloadCount(cnt);
            boolean b = eduResourceService.updateById(entity);
            resultData.setData(b);
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


}
