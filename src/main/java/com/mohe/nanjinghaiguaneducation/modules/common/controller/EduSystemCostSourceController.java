package com.mohe.nanjinghaiguaneducation.modules.common.controller;

import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.common.dto.EduSystemCommonDto;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemCostSourceEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemCostSourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
*
* @description: 费用来源接口地址
* @author liurui
* @date 2022/7/20 5:22 下午
*/
@RestController
@Api("费用来源接口地址")
@RequestMapping("system/eduSystemCostSource")
public class EduSystemCostSourceController {

    @Autowired
    private EduSystemCostSourceService eduSystemCostSourceService;

    @PostMapping("toAdd")
    @ApiOperation("新增费用来源")
    public ResultData toAdd(@RequestBody EduSystemCommonDto eduSystemCommonDto){
        return eduSystemCostSourceService.addCostSource(eduSystemCommonDto);
    }

    @PostMapping("list")
    @ApiOperation("费用来源列表")
    public ResultData<PageUtils> list(@RequestBody Map<String, Object> params){
        ResultData<PageUtils> resultData = new ResultData<>();
        resultData.setData(eduSystemCostSourceService.queryPage(params));
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setCode(SysConstant.SUCCESS);
        return resultData;
    }

    @PostMapping("delete")
    @ApiOperation("费用来源删除")
    public ResultData delete(@RequestBody Map<String, Object> params){
        return eduSystemCostSourceService.deleteCostSourceById(params);
    }

    @PostMapping("update")
    @ApiOperation("费用来源修改")
    public ResultData update(@RequestBody EduSystemCommonDto eduSystemCommonDto){
        return eduSystemCostSourceService.updateCostSource(eduSystemCommonDto);
    }

    @PostMapping("getSelectList")
    @ApiOperation("获取所有的费用来源作为下拉选项")
    public ResultData<List<EduSystemCostSourceEntity>> getSelectList(){
        return eduSystemCostSourceService.selectCostSourceList();
    }

    @PostMapping("getEntityInfo")
    @ApiOperation("根据id获取费用来源的信息")
    public ResultData<EduSystemCostSourceEntity> getEntityById(@RequestBody Map<String, Object> params){
        return eduSystemCostSourceService.selectEntityById(params);
    }

}
