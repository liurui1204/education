package com.mohe.nanjinghaiguaneducation.modules.trainingBase.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.constant.SystemBusConst;
import com.mohe.nanjinghaiguaneducation.common.exception.RRException;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemSettingsEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemSettingsService;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.dto.*;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.entity.EduTrainingBaseEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.service.EduTrainingBaseRecordService;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.service.EduTrainingBaseResourceService;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.service.EduTrainingBaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RequestMapping("/front/trainingBase")
@RestController
@Api(tags = "实训基地-前台接口")
public class EduTrainingBaseFrontController {

    @Autowired
    private EduSystemSettingsService eduSystemSettingsService;

    @Autowired
    private EduTrainingBaseService eduTrainingBaseService;

    @Autowired
    private EduTrainingBaseResourceService eduTrainingBaseResourceService;

    @Autowired
    private EduTrainingBaseRecordService eduTrainingBaseRecordService;

    //红色基地总览信息
    @PostMapping("/mainInfo")
    @ApiOperation("实训基地总览信息")
    public ResultData trainingBaseMainInfo(){
        ResultData<Map<String, Object>> result = new ResultData<>();
        Map<String, Object> resData = new HashMap<>();
        try{
            EduSystemSettingsEntity desc = eduSystemSettingsService.selectOne(new EntityWrapper<EduSystemSettingsEntity>().eq("`key`", SystemBusConst.TRAINING_BASE_DESC_KEY));
            EduSystemSettingsEntity map = eduSystemSettingsService.selectOne(new EntityWrapper<EduSystemSettingsEntity>().eq("`key`", SystemBusConst.TRAINING_BASE_MAP_KEY));
            if(!ObjectUtils.isEmpty(desc) && !desc.getValue().equals("")){
                resData.put("desc", desc.getValue());
            }else{
                resData.put("desc", "实训基地描述未设置，请管理员设置");
            }
            if(!ObjectUtils.isEmpty(map) && !desc.getValue().equals("")){
                List<String> mapList = new ArrayList<>(Arrays.asList(map.getValue().split(",")));
                resData.put("map", mapList);
            }else{
                resData.put("map", new ArrayList<>());
            }
            result.setData(resData);
            result.setSuccess(true);
            result.setCode(SysConstant.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("设置有误，请联系管理员");
            result.setSuccess(false);
            result.setCode(SysConstant.PARAMETER_ERROR);
        }
        return result;
    }

    //红色基地总览信息
    @PostMapping("/list")
    @ApiOperation("实训基地列表")
    public ResultData<EduTrainingBaseListDto> trainingBaseList(@RequestBody EduTrainingBaseListRequestDto eduTrainingBaseListRequestDto){
        ResultData<EduTrainingBaseListDto> eduTrainingBaseListDtoResultData = new ResultData<>();
        try {
            Map<String, Object> params = new HashMap<>();
            Integer page = eduTrainingBaseListRequestDto.getPage();
            Integer limit = eduTrainingBaseListRequestDto.getLimit();
            String searchKey = eduTrainingBaseListRequestDto.getCustomsName();
            if (page == null || limit == null
                    || page < 1 || limit < 1) {
                eduTrainingBaseListDtoResultData.setSuccess(false);
                eduTrainingBaseListDtoResultData.setCode(SysConstant.PARAMETER_ERROR);
                eduTrainingBaseListDtoResultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
                return eduTrainingBaseListDtoResultData;
            }
            params.put("page", page);
            params.put("limit", limit);
            if(!"".equals(searchKey)){
                params.put("customsName", searchKey);
            }
            PageUtils pageUtils = eduTrainingBaseService.queryPageSearch(params);
            EduTrainingBaseListDto eduTrainingBaseListDto = new EduTrainingBaseListDto();
            eduTrainingBaseListDto.setList(pageUtils);
            List<String> strings = eduTrainingBaseService.selectCustomsList();
            eduTrainingBaseListDto.setCustomsList(strings);
            eduTrainingBaseListDtoResultData.setData(eduTrainingBaseListDto);
            eduTrainingBaseListDtoResultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            eduTrainingBaseListDtoResultData.setCode(SysConstant.SUCCESS);
            eduTrainingBaseListDtoResultData.setSuccess(true);
            return eduTrainingBaseListDtoResultData;
        }catch (Exception e) {
            eduTrainingBaseListDtoResultData.setSuccess(false);
            eduTrainingBaseListDtoResultData.setCode(SysConstant.PARAMETER_ERROR);
            eduTrainingBaseListDtoResultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
            return eduTrainingBaseListDtoResultData;
        }
    }

    @PostMapping("/detail/recordList")
    @ApiOperation("实训基地培训记录列表")
    public ResultData<PageUtils> recordList(@RequestBody EduTrainingBaseResourceListRequestDto eduTrainingBaseResourceListRequestDto){
        ResultData<PageUtils> resultData = new ResultData<PageUtils>();
        try {
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            if(null==eduTrainingBaseResourceListRequestDto.getId() || eduTrainingBaseResourceListRequestDto.getId()<1){
                throw new RRException("参数错误");
            }
            Map<String, Object> params = new HashMap<>();
            params.put("page", eduTrainingBaseResourceListRequestDto.getPage());
            params.put("limit", eduTrainingBaseResourceListRequestDto.getLimit());
            params.put("baseId", eduTrainingBaseResourceListRequestDto.getId());
            PageUtils pageUtils = eduTrainingBaseRecordService.queryPage(params);
            resultData.setData(pageUtils);
        }catch (Exception e){
            e.printStackTrace();
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
        }
        return resultData;
    }

    @PostMapping("/detail/mediaList")
    @ApiOperation("实训基地详情-媒体列表")
    public ResultData<PageUtils> mediaList(@RequestBody EduTrainingBaseResourceListRequestDto eduTrainingBaseResourceListRequestDto){
        ResultData<PageUtils> resultData = new ResultData<PageUtils>();
        try {
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            if(null==eduTrainingBaseResourceListRequestDto.getId() || eduTrainingBaseResourceListRequestDto.getId()<1){
                throw new RRException("参数错误");
            }
            Map<String, Object> params = new HashMap<>();
            params.put("page", eduTrainingBaseResourceListRequestDto.getPage());
            params.put("limit", eduTrainingBaseResourceListRequestDto.getLimit());
            params.put("baseId", eduTrainingBaseResourceListRequestDto.getId());
            PageUtils pageUtils = eduTrainingBaseResourceService.queryPage(params);
            resultData.setData(pageUtils);
        }catch (Exception e){
            e.printStackTrace();
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
        }
        return resultData;
    }

    @PostMapping("/detail/mediaAddPlayRecord")
    @ApiModelProperty("实训基地详情-增加播放记录")
    public ResultData<Integer> mediaAddPlayRecord(@RequestBody EduTrainingBaseResourceAddPlayRecordDto eduTrainingBaseResourceAddPlayRecordDto){
        ResultData<Integer> resultData = new ResultData<>();
        try{
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            Integer updateCount = eduTrainingBaseResourceService.addRecordByResourceId(eduTrainingBaseResourceAddPlayRecordDto.getResourceId());
            resultData.setData(updateCount);
        }catch (Exception e){
            e.printStackTrace();
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
        }
        return resultData;
    }

    @PostMapping("/detail/content")
    @ApiModelProperty("实训基地详情-图文内容")
    public ResultData<EduTrainingBaseEntity> detailContent(@RequestBody EduTrainingBaseGetInfoDto eduTrainingBaseGetInfoDto){
        ResultData<EduTrainingBaseEntity> resultData = new ResultData<>();
        try{
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            if(null==eduTrainingBaseGetInfoDto.getId() || eduTrainingBaseGetInfoDto.getId()<1){
                throw new RRException("实训基地ID不能为空");
            }
            EduTrainingBaseEntity eduTrainingBaseEntity = eduTrainingBaseService.selectOne(new EntityWrapper<EduTrainingBaseEntity>().eq("id", eduTrainingBaseGetInfoDto.getId()));
            resultData.setData(eduTrainingBaseEntity);
        }catch (Exception e){
            e.printStackTrace();
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
        }
        return resultData;
    }

}
