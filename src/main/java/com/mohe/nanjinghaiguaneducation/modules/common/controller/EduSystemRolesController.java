package com.mohe.nanjinghaiguaneducation.modules.common.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.common.dto.EduSystemRolesListDto;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemRolesEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemRolesEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemBelongToService;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemRolesEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemRolesService;
import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.employee.service.EduEmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-02-16 18:11:37
 */
@RestController
@RequestMapping("common/eduSystemRoles")
@Api(tags = "角色管理")
public class EduSystemRolesController {
    @Autowired
    private EduSystemRolesService eduSystemRolesService;
    @Autowired
    private EduSystemRolesEmployeeService eduSystemRolesEmployeeService;
    @Autowired
    private EduEmployeeService eduEmployeeService;

    @PostMapping("/list")
    @ApiOperation("角色获取用户列表")
    public ResultData<List<EduEmployeeEntity>> list(@RequestBody EduSystemRolesListDto eduSystemRolesListDto){
        ResultData<List<EduEmployeeEntity>> resultData = new ResultData<>();
        resultData.setSuccess(true);
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        List<EduEmployeeEntity> eduEmployeeEntities = new ArrayList<>();
        EntityWrapper<EduSystemRolesEntity> entityEntityWrapper = new EntityWrapper<>();
        if (!ObjectUtils.isEmpty(eduSystemRolesListDto.getId())){
            entityEntityWrapper.eq("id",eduSystemRolesListDto.getId());
        }
        if (!ObjectUtils.isEmpty(eduSystemRolesListDto.getCode())){
            entityEntityWrapper.eq("code",eduSystemRolesListDto.getCode());
        }
        EduSystemRolesEntity eduSystemRolesEntity = eduSystemRolesService.selectOne(entityEntityWrapper);
        if (ObjectUtils.isEmpty(eduSystemRolesEntity)){
            return resultData;
        }
        List<EduSystemRolesEmployeeEntity> entityList = eduSystemRolesEmployeeService.selectList(new EntityWrapper<EduSystemRolesEmployeeEntity>()
                .eq("roleCode", eduSystemRolesEntity.getCode()));
        if (ObjectUtils.isEmpty(entityList) || entityList.isEmpty()){
            return resultData;
        }
        entityList.forEach(k->{
            EduEmployeeEntity eduEmployeeEntity = eduEmployeeService.selectOne(new EntityWrapper<EduEmployeeEntity>().eq("id",k.getEmployeeId()));
            if (!ObjectUtils.isEmpty(eduEmployeeEntity)){
                String [] departmentName = eduEmployeeEntity.getH4aAllPathName().split("\\\\");
                eduEmployeeEntity.setDepartmentName(departmentName[2]);
                eduEmployeeEntities.add(eduEmployeeEntity);
            }
        });
        resultData.setData(eduEmployeeEntities);
        return resultData;
    }
}
