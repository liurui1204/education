package com.mohe.nanjinghaiguaneducation.modules.resource.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.exception.RRException;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.ConvertUtils;
import com.mohe.nanjinghaiguaneducation.modules.resource.dto.EduResourceAddDto;
import com.mohe.nanjinghaiguaneducation.modules.resource.dto.EduResourceTypeAddDto;
import com.mohe.nanjinghaiguaneducation.modules.resource.dto.EduResourceTypeUpdateDto;
import com.mohe.nanjinghaiguaneducation.modules.resource.dto.EduResourceUpdateDto;
import com.mohe.nanjinghaiguaneducation.modules.resource.entity.EduResourceEntity;
import com.mohe.nanjinghaiguaneducation.modules.resource.entity.EduResourceTypeEntity;
import com.mohe.nanjinghaiguaneducation.modules.resource.service.EduResourceService;
import com.mohe.nanjinghaiguaneducation.modules.resource.service.EduResourceTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;

import javax.servlet.http.HttpServletRequest;


/**
 * 资源分类
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:23
 */
@Api(tags = "资源分类")
@RestController
@RequestMapping("site/resourceManage/type")
public class EduResourceTypeController {
    @Autowired
    private EduResourceTypeService eduResourceTypeService;
    @Autowired
    private EduResourceService eduResourceService;
    @ApiOperation("资源分类列表")
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
            PageUtils pageUtils = eduResourceTypeService.queryPage(params);
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
    @ApiOperation("资源分类新增")
    @PostMapping("/add")
    public ResultData add(@RequestBody EduResourceTypeAddDto eduResourceAddDto, HttpServletRequest request){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        String userId = request.getAttribute("userId").toString();
        EduResourceTypeEntity entity = ConvertUtils.convert(eduResourceAddDto,EduResourceTypeEntity::new);
        entity.setCreateBy(userId);
        entity.setCreateTime(new Date());
        eduResourceTypeService.insert(entity);
        EduResourceTypeEntity id = eduResourceTypeService.selectOne(new EntityWrapper<EduResourceTypeEntity>().orderBy("id", false)
                .last("limit 1"));
        resultData.setData(id.getId());
        return resultData;
    }
    @ApiOperation("资源分类查看明细")
    @PostMapping("/info")
    public ResultData info(@RequestBody Map<String,Object>params){
        ResultData resultData = new ResultData();
        EduResourceTypeEntity entity = eduResourceTypeService.selectById((Integer)params.get("id"));
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setData(entity);
        return resultData;
    }
    @ApiOperation("资源分类删除")
    @PostMapping("/delete")
    public ResultData delete(@RequestBody Map<String,Object>params){
        ResultData resultData = new ResultData();
        try{
            Integer id = (Integer)params.get("id");
            int parentIdNum = eduResourceTypeService.selectCount(new EntityWrapper<EduResourceTypeEntity>().eq("parentId", id));
            if(parentIdNum>0){
                throw new RRException("该分类包含子分类，请先删除子分类");
            }
            int typeIdNum = eduResourceService.selectCount(new EntityWrapper<EduResourceEntity>().eq("typeId", id));
            if(typeIdNum>0){
                throw new RRException("该分类下有资源，请先删除");
            }
            eduResourceTypeService.deleteById(id);
            parentDelete(id);
            eduResourceService.delete(new EntityWrapper<EduResourceEntity>().eq("typeId",id));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        }catch (Exception e){
            e.printStackTrace();
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(e.getMessage());
        }

        return resultData;
    }
    public void parentDelete(Integer id){
        List<EduResourceTypeEntity> parentId = eduResourceTypeService.selectList(new EntityWrapper<EduResourceTypeEntity>().eq("parentId", id));
        if (!ObjectUtils.isEmpty(parentId)){
            for (EduResourceTypeEntity eduResourceTypeEntity : parentId) {
                eduResourceTypeService.deleteById(eduResourceTypeEntity.getId());
                eduResourceService.delete(new EntityWrapper<EduResourceEntity>().eq("typeId",eduResourceTypeEntity.getId()));
                parentDelete(id);
            }
        }
    }
    @ApiOperation("资源分类更新")
    @PostMapping("/update")
    public ResultData update(@RequestBody EduResourceTypeUpdateDto eduResourceUpdateDto){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        EduResourceTypeEntity entity = eduResourceTypeService.selectById(eduResourceUpdateDto.getId());
        if (ObjectUtils.isEmpty(entity)){
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage("找不到该记录");
        }

        BeanUtil.copyProperties(eduResourceUpdateDto,entity);
        eduResourceTypeService.updateById(entity);
        return resultData;
    }

}
