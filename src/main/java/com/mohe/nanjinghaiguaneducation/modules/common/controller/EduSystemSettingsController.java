package com.mohe.nanjinghaiguaneducation.modules.common.controller;

import java.util.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.modules.common.dto.EduSystemSettingsAddDto;
import com.mohe.nanjinghaiguaneducation.modules.common.dto.EduSystemSettingsBaseDto;
import com.mohe.nanjinghaiguaneducation.modules.common.dto.EduSystemSettingsDto;
import com.mohe.nanjinghaiguaneducation.modules.common.dto.EduSystemSettingsRedDto;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemSettingsEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemSettingsService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;



/**
 * 系统其他设置
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:24
 */
@RestController
@RequestMapping("system/setting")
public class EduSystemSettingsController {
    @Autowired
    private EduSystemSettingsService eduSystemSettingsService;

    @ApiOperation("系统设置查看")
    @PostMapping("/get")
    public ResultData get(){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        Map<String,Object> map = new HashMap<>();
        //红色基地Key值获取值
        EduSystemSettingsRedDto eduSystemSettingsRedDto = new EduSystemSettingsRedDto();
        EduSystemSettingsEntity eduSystemSettingsDescEntity = eduSystemSettingsService.selectOne(new EntityWrapper<EduSystemSettingsEntity>()
                .where("`key` ='RED_BASE_DESC_KEY'"));
        if (!ObjectUtils.isEmpty(eduSystemSettingsDescEntity)){
            eduSystemSettingsRedDto.setRED_BASE_DESC_KEY(eduSystemSettingsDescEntity.getValue());
        }
        EduSystemSettingsEntity eduSystemSettingsMapEntity = eduSystemSettingsService.selectOne(new EntityWrapper<EduSystemSettingsEntity>()
                .where("`key` = 'RED_BASE_MAP_KEY'"));
        if (!ObjectUtils.isEmpty(eduSystemSettingsMapEntity)){
            eduSystemSettingsRedDto.setRED_BASE_MAP_KEY(eduSystemSettingsMapEntity.getValue());
        }
        //给map赋值 红色基地的KEY-VALUE
        map.put("redTraningSetting",eduSystemSettingsRedDto);
        //基地配置的key
        EduSystemSettingsBaseDto eduSystemSettingsBaseDto = new EduSystemSettingsBaseDto();

        EduSystemSettingsEntity eduSystemSettingsBaseDescEntity = eduSystemSettingsService.selectOne(new EntityWrapper<EduSystemSettingsEntity>()
                .where("`key`='TRAINING_BASE_DESC_KEY'"));
        EduSystemSettingsEntity eduSystemSettingsBaseMapEntity = eduSystemSettingsService.selectOne(new EntityWrapper<EduSystemSettingsEntity>()
                .where("`key` = 'TRAINING_BASE_MAP_KEY'"));

        EduSystemSettingsEntity eduSystemSettingsEntity = eduSystemSettingsService.selectOne(new EntityWrapper<EduSystemSettingsEntity>()
                .where("`key` = 'HOME_PAGE_TRAINING_NEWS_IMAGE_KEY'"));
        if (!ObjectUtils.isEmpty(eduSystemSettingsBaseDescEntity)){
            eduSystemSettingsBaseDto.setTRAINING_BASE_DESC_KEY(eduSystemSettingsBaseDescEntity.getValue());
        }
        if (!ObjectUtils.isEmpty(eduSystemSettingsBaseMapEntity)){
            eduSystemSettingsBaseDto.setTRAINING_BASE_MAP_KEY(eduSystemSettingsBaseMapEntity.getValue());
        }
        //给基地配置赋值 key-value
        map.put("traningBaseSetting",eduSystemSettingsBaseDto);
        //设置主页等key-value
        map.put("HOME_PAGE_TRAINING_NEWS_IMAGE_KEY",eduSystemSettingsEntity==null?null:eduSystemSettingsEntity.getValue());
        resultData.setData(map);
        return resultData;
    }

    @ApiOperation("设置")
    @PostMapping("/set")
    public ResultData set(@RequestBody EduSystemSettingsAddDto eduSystemSettingsAddDto){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        List<EduSystemSettingsDto> list = eduSystemSettingsAddDto.getEduSystemSettingsDtos();
        try {
            for (EduSystemSettingsDto params : list) {
                String key = params.getKey();
                //查询是否有这个key值
                EduSystemSettingsEntity eduSystemSettingsEntity = eduSystemSettingsService.selectOne(new EntityWrapper<EduSystemSettingsEntity>()
                        .where("`key` = '"+ key+"'"));
                String value = params.getValue();
                if (ObjectUtils.isEmpty(eduSystemSettingsEntity)){
                    eduSystemSettingsEntity = new EduSystemSettingsEntity();
                }
                eduSystemSettingsEntity.setValue(value);
                if (params.getKey().equals("HOME_PAGE_TRAINING_NEWS_IMAGE_KEY")){
                    List<String> imageValue = params.getImageValue();
                    eduSystemSettingsEntity.setValue(StringUtils.join(imageValue,","));
                }
                if (ObjectUtils.isEmpty(eduSystemSettingsEntity.getKey())){
                    eduSystemSettingsEntity.setKey(key);
                    eduSystemSettingsService.insert(eduSystemSettingsEntity);
                }else {
                    eduSystemSettingsService.updateById(eduSystemSettingsEntity);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return resultData;
    }
}
