package com.mohe.nanjinghaiguaneducation.modules.redBase.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mohe.nanjinghaiguaneducation.common.constant.CodeConstant;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.constant.SystemBusConst;
import com.mohe.nanjinghaiguaneducation.common.exception.RRException;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.ConvertUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemSettingsEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemSettingsService;
import com.mohe.nanjinghaiguaneducation.modules.redBase.dto.*;
import com.mohe.nanjinghaiguaneducation.modules.redBase.entity.EduRedBaseEntity;
import com.mohe.nanjinghaiguaneducation.modules.redBase.entity.EduRedBaseResourceEntity;
import com.mohe.nanjinghaiguaneducation.modules.redBase.service.EduRedBaseResourceService;
import com.mohe.nanjinghaiguaneducation.modules.redBase.service.EduRedBaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RequestMapping("/front/redBase")
@RestController
@Api(tags = "红色基地-前台接口")
public class EduRedBaseFrontController {

    @Autowired
    private EduSystemSettingsService eduSystemSettingsService;

    @Autowired
    private EduRedBaseService eduRedBaseService;

    @Autowired
    private EduRedBaseResourceService eduRedBaseResourceService;

    //红色基地总览信息
    @PostMapping("/mainInfo")
    @ApiOperation("红色基地总览信息")
    public ResultData redBaseMainInfo(){
        ResultData<Map<String, Object>> result = new ResultData<>();
        Map<String, Object> resData = new HashMap<>();
        try{
            EduSystemSettingsEntity desc = eduSystemSettingsService.selectOne(new EntityWrapper<EduSystemSettingsEntity>().eq("`key`", SystemBusConst.RED_BASE_DESC_KEY));
            EduSystemSettingsEntity map = eduSystemSettingsService.selectOne(new EntityWrapper<EduSystemSettingsEntity>().eq("`key`", SystemBusConst.RED_BASE_MAP_KEY));
            if(!ObjectUtils.isEmpty(desc) && !desc.getValue().equals("")){
                resData.put("desc", desc.getValue());
            }else{
                resData.put("desc", "红色基地描述未设置，请管理员设置");
            }
            if(!ObjectUtils.isEmpty(map) && !desc.getValue().equals("")){
                List<String> mapList = new ArrayList<>(Arrays.asList(map.getValue().split(",")));
                resData.put("map", mapList);
            }else{
//                resData.put("map", "红色基地分布未设置，请管理员设置");
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
    @ApiOperation("红色基地列表")
    public ResultData<EduRedBaseListDto> redBaseList(@RequestBody EduRedBaseListRequestDto eduRedBaseListRequestDto){
//        eduSystemSettingsService.queryPage();
        ResultData<EduRedBaseListDto> eduRedBaseListDtoResultData = new ResultData<>();
        try {
            Map<String, Object> params = new HashMap<>();
            Integer page = eduRedBaseListRequestDto.getPage();
            Integer limit = eduRedBaseListRequestDto.getLimit();
            String searchKey = eduRedBaseListRequestDto.getCustomsName();
            if (page == null || limit == null
                    || page < 1 || limit < 1) {
                eduRedBaseListDtoResultData.setSuccess(false);
                eduRedBaseListDtoResultData.setCode(SysConstant.PARAMETER_ERROR);
                eduRedBaseListDtoResultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
                return eduRedBaseListDtoResultData;
            }
            params.put("page", page);
            params.put("limit", limit);
            if(!"".equals(searchKey)){
                params.put("customsName", searchKey);
            }
            PageUtils pageUtils = eduRedBaseService.queryPageSearch(params);
            EduRedBaseListDto eduRedBaseListDto = new EduRedBaseListDto();
            eduRedBaseListDto.setList(pageUtils);
            List<String> strings = eduRedBaseService.selectCustomsList();
            eduRedBaseListDto.setCustomsList(strings);
            eduRedBaseListDtoResultData.setData(eduRedBaseListDto);
            eduRedBaseListDtoResultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            eduRedBaseListDtoResultData.setCode(SysConstant.SUCCESS);
            eduRedBaseListDtoResultData.setSuccess(true);
            return eduRedBaseListDtoResultData;
        }catch (Exception e) {
            eduRedBaseListDtoResultData.setSuccess(false);
            eduRedBaseListDtoResultData.setCode(SysConstant.PARAMETER_ERROR);
            eduRedBaseListDtoResultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
            return eduRedBaseListDtoResultData;
        }
    }

    @PostMapping("/detail/mediaList")
    @ApiOperation("红色基地详情-媒体列表")
    public ResultData<PageUtils> mediaList(@RequestBody EduRedBaseResourceListRequestDto eduRedBaseResourceListRequestDto){
        ResultData<PageUtils> resultData = new ResultData<PageUtils>();
        try {
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            if(null==eduRedBaseResourceListRequestDto.getId() || eduRedBaseResourceListRequestDto.getId()<1){
                throw new RRException("参数错误");
            }
            Map<String, Object> params = new HashMap<>();
            params.put("page", eduRedBaseResourceListRequestDto.getPage());
            params.put("limit", eduRedBaseResourceListRequestDto.getLimit());
            params.put("baseId", eduRedBaseResourceListRequestDto.getId());
            PageUtils pageUtils = eduRedBaseResourceService.queryPage(params);
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
    @ApiModelProperty("红色基地详情-新增播放记录")
    public ResultData<Integer> mediaAddPlayRecord(@RequestBody EduRedBaseResourceAddPlayRecordDto eduRedBaseResourceAddPlayRecordDto){
        ResultData<Integer> resultData = new ResultData<>();
        try{
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            Integer updateCount = eduRedBaseResourceService.addRecordByResourceId(eduRedBaseResourceAddPlayRecordDto.getResourceId());
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
    @ApiModelProperty("红色基地详情-图文内容")
    public ResultData<EduRedBaseEntity> detailContent(@RequestBody EduRedBaseGetInfoDto eduRedBaseGetInfoDto){
        ResultData<EduRedBaseEntity> resultData = new ResultData<>();
        try{
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            if(null==eduRedBaseGetInfoDto.getId() || eduRedBaseGetInfoDto.getId()<1){
                throw new RRException("红色基地ID不能为空");
            }
            EduRedBaseEntity eduRedBaseEntity = eduRedBaseService.selectOne(new EntityWrapper<EduRedBaseEntity>().eq("id", eduRedBaseGetInfoDto.getId()));
            resultData.setData(eduRedBaseEntity);
        }catch (Exception e){
            e.printStackTrace();
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
        }
        return resultData;
    }

}
