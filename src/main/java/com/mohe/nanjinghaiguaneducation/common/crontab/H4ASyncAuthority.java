package com.mohe.nanjinghaiguaneducation.common.crontab;

import cn.gov.customs.casp.sdk.h4a.entity.*;
import cn.gov.customs.casp.sdk.h4a.ogu.ws.IOguReaderGetOrganizationChildrenCupaaFaultArgsFaultFaultMessage;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.mohe.nanjinghaiguaneducation.common.h4a.Functions;
import com.mohe.nanjinghaiguaneducation.common.h4a.Organization;
import com.mohe.nanjinghaiguaneducation.common.h4a.Users;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemAuthorityEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemAuthorityRoleEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemRolesEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemRolesEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemAuthorityRoleService;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemAuthorityService;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemRolesEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemRolesService;
import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.employee.service.EduEmployeeService;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class H4ASyncAuthority {

//    @Autowired
//    private EduSystemAuthorityService eduSystemAuthorityService;
//    @Autowired
//    private EduSystemRolesService eduSystemRolesService;
//    @Autowired
//    private EduSystemRolesEmployeeService eduSystemRolesEmployeeService;
//    @Autowired
//    private EduEmployeeService eduEmployeeService;
//    @Autowired
//    private EduSystemAuthorityRoleService eduSystemAuthorityRoleService;


    void syncAuthority() {
        Functions func = new Functions();
        String groupCode = "";// 选择权限组来获取，不传就是获取所有
        try {
            FunctionsInApplication[] allFunctions = func.getBeanFunctionsInApplication(groupCode);
            if (null != allFunctions) {
                //如果获取到了权限列表，直接遍历
                for (FunctionsInApplication funcItem : allFunctions) {
                    //查询当前权限code是否已经存在
                    EntityWrapper<EduSystemAuthorityEntity> filter = new EntityWrapper<EduSystemAuthorityEntity>();
                    filter.eq("code", funcItem.getCode_name());
                    EduSystemAuthorityService eduSystemAuthorityService = (EduSystemAuthorityService) ApplicationContextUtil.getBean("eduSystemAuthorityService");
                    EduSystemAuthorityEntity eduSystemAuthorityEntity = eduSystemAuthorityService.selectOne(filter);
                    int _type; //权限的类型
                    switch (funcItem.getCode_name().substring(0, 2)) {
                        case "JM":
                            _type = 1;
                            break;
                        case "CZ":
                            _type = 2;
                            break;
                        default:
                            _type = 3;
                            break;
                    }
                    if (null == eduSystemAuthorityEntity) {
                        //如果不存在，新增
                        eduSystemAuthorityEntity = new EduSystemAuthorityEntity();
                        eduSystemAuthorityEntity.setId(IdUtil.simpleUUID());
                        eduSystemAuthorityEntity.setAuthorityType(_type);
                        eduSystemAuthorityEntity.setCode(funcItem.getCode_name());
                        eduSystemAuthorityEntity.setName(funcItem.getFunc_name());
                        eduSystemAuthorityEntity.setLastUpdateTime(new Date());
                        eduSystemAuthorityService.insert(eduSystemAuthorityEntity);
                    } else {
                        //已经存在，更新
                        eduSystemAuthorityEntity.setId(IdUtil.simpleUUID());
                        eduSystemAuthorityEntity.setAuthorityType(_type);
                        eduSystemAuthorityEntity.setCode(funcItem.getCode_name());
                        eduSystemAuthorityEntity.setName(funcItem.getFunc_name());
                        eduSystemAuthorityEntity.setLastUpdateTime(new Date());
                        eduSystemAuthorityService.updateById(eduSystemAuthorityEntity);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void syncRoleAndUsers() {
        Long version = new Date().getTime()/1000;
        Users users = new Users();
        try {
            Roles[] beanRoles = users.getBeanRoles();
            for (Roles beanRole : beanRoles) {
                EntityWrapper<EduSystemRolesEntity> entityEntityWrapper = new EntityWrapper<>();
                Wrapper<EduSystemRolesEntity> eduSystemRolesEntityWrapper = entityEntityWrapper.eq("code", beanRole.getCode_name());
                String code_name = beanRole.getCode_name();
                if (code_name.equals("SYSTEM_ADMIN") || code_name.equals("APP_ADMIN")) {
                    continue;
                }
                EduSystemRolesService eduSystemRolesService = (EduSystemRolesService)  ApplicationContextUtil.getBean("eduSystemRolesService");
                EduSystemRolesEntity systemRolesEntity = eduSystemRolesService.selectOne(new EntityWrapper<EduSystemRolesEntity>().eq("code", code_name));
                String roleId;
                //如果该角色在系统中已经存在了，更新角色信息即可
                if (!ObjectUtils.isEmpty(systemRolesEntity)) {
                    systemRolesEntity.setName(beanRole.getRole_name());
                    systemRolesEntity.setLastUpdateTime(new Date());
                    systemRolesEntity.setMemo(beanRole.getRole_description());
                    eduSystemRolesService.updateById(systemRolesEntity);
                    roleId = systemRolesEntity.getId();
                    //continue;
                } else {
                    //如果没有，要新增角色
                    EduSystemRolesEntity eduSystemRolesEntity = new EduSystemRolesEntity();
                    eduSystemRolesEntity.setCode(beanRole.getCode_name());
                    eduSystemRolesEntity.setName(beanRole.getRole_name());
                    eduSystemRolesEntity.setLastUpdateTime(new Date());
                    eduSystemRolesEntity.setMemo(beanRole.getRole_description());
                    roleId = IdUtil.simpleUUID();
                    eduSystemRolesEntity.setId(roleId);
                    eduSystemRolesService.insert(eduSystemRolesEntity);
                }

                //获取角色下面的用户列表
//                UsersInRoles[] usersInRoles = users.getUsersInRoles(beanRole.getCode_name());
                ChildrenInRoles[] usersInRoles = users.getChildrenInRoles(beanRole.getCode_name());
                if (null != usersInRoles) {
                    for (ChildrenInRoles usersInRole : usersInRoles) {
                        switch (usersInRole.getObjectclass()){
                            case "USERS":
                                this.saveChildAndRole(usersInRole, roleId, version);
                                break;
                            case "GROUPS":
                                Organization organization = new Organization();
                                UsersInGroups[] beanUsersInGroupsByAllPath = organization.getBeanUsersInGroupsByAllPath(usersInRole.getAll_path_name());
                                if(null != beanUsersInGroupsByAllPath){
                                    for(UsersInGroups user : beanUsersInGroupsByAllPath){
                                        ChildrenInRoles childrenInRoles = new ChildrenInRoles();
                                        childrenInRoles.setGuid(user.getUser_guid());
                                        childrenInRoles.setAll_path_name(user.getAll_path_name());
                                        childrenInRoles.setDisplay_name(user.getDisplay_name());
                                        childrenInRoles.setParent_guid(user.getParent_guid());
                                        childrenInRoles.setPerson_id(user.getPerson_id());
                                        childrenInRoles.setRole_value(code_name);
                                        this.saveChildAndRole(childrenInRoles, roleId, version);
                                    }
                                }
                                break;
                            case "ORGANIZATIONS":
                                OrganizationChildren organizationChildren = new OrganizationChildren();
                                organizationChildren.setAll_path_name(usersInRole.getAll_path_name());
                                organizationChildren.setObjectclass(usersInRole.getObjectclass());
                                this.getOrgToLast(organizationChildren, code_name, roleId, version);
                                break;
                        }
                    }
                }
            }
            //同步完成了，版本号低于当前版本的都删掉 version
            EduSystemRolesEmployeeService eduSystemRolesEmployeeService = (EduSystemRolesEmployeeService) ApplicationContextUtil.getBean("eduSystemRolesEmployeeService");
            eduSystemRolesEmployeeService.delete(new EntityWrapper<EduSystemRolesEmployeeEntity>().lt("version", version));
            eduSystemRolesEmployeeService.delete(new EntityWrapper<EduSystemRolesEmployeeEntity>().isNull("version"));
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }

        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
    }

    private void getOrgToLast(OrganizationChildren orgInfo, String code_name, String roleId, Long version) throws IOguReaderGetOrganizationChildrenCupaaFaultArgsFaultFaultMessage {
        switch (orgInfo.getObjectclass()){
            case "USERS":
                ChildrenInRoles childrenInRoles = new ChildrenInRoles();
                childrenInRoles.setGuid(orgInfo.getUser_guid());
                childrenInRoles.setAll_path_name(orgInfo.getAll_path_name());
                childrenInRoles.setDisplay_name(orgInfo.getDisplay_name());
                childrenInRoles.setParent_guid(orgInfo.getParent_guid());
                childrenInRoles.setPerson_id(orgInfo.getPerson_id());
                childrenInRoles.setRole_value(code_name);
                this.saveChildAndRole(childrenInRoles, roleId, version);
                break;
            case "GROUPS":
                //组织架构下面的用户组不用重复添加，因为用户组中的人，再组织架构中都有
                break;
            case "ORGANIZATIONS":
                Organization organization = new Organization();
                //取当前节点的下一级，以便往下走
                OrganizationChildren[] orgSonItems = organization.getBeanOrganizationChildrenByAllPath(orgInfo.getAll_path_name());
                for(OrganizationChildren sonItem : orgSonItems){
                    //取出来的第一个是自己，要跳过
                    if(sonItem.getAll_path_name().equals(orgInfo.getAll_path_name())){
                        continue;
                    }
                    getOrgToLast(sonItem, code_name, roleId, version);
                }
                break;
        }
    }

    private void saveChildAndRole(ChildrenInRoles usersInRole, String roleId, Long version){
        //先看当前用户有没有
        EntityWrapper<EduEmployeeEntity> eduEmployeeEntityEntityWrapper = new EntityWrapper<>();
//                        eduEmployeeEntityEntityWrapper.eq("h4aUserGuid", usersInRole.getGuid());
        eduEmployeeEntityEntityWrapper.eq("h4aUserGuid", usersInRole.getGuid());
        EduEmployeeService eduEmployeeService = (EduEmployeeService) ApplicationContextUtil.getBean("eduEmployeeService");
        EduEmployeeEntity eduEmployeeEntity = eduEmployeeService.selectOne(eduEmployeeEntityEntityWrapper);
        String employeeId;
        if (null == eduEmployeeEntity) {
            //如果没有用户，直接新增一个用户
            eduEmployeeEntity = new EduEmployeeEntity();
            employeeId = IdUtil.simpleUUID();
            eduEmployeeEntity.setId(employeeId);
            eduEmployeeEntity.setH4aAllPathName(usersInRole.getAll_path_name());
//                            eduEmployeeEntity.setH4aUserGuid(usersInRole.getGuid());
            eduEmployeeEntity.setH4aUserGuid(usersInRole.getGuid());
            eduEmployeeEntity.setH4aViewGuid("");
            eduEmployeeEntity.setH4aDisplayName(usersInRole.getDisplay_name());
            eduEmployeeEntity.setH4aParentGuid(usersInRole.getParent_guid());
            eduEmployeeEntity.setEmployeeName(usersInRole.getDisplay_name());
            eduEmployeeEntity.setEmployeeCode(usersInRole.getPerson_id());//2222员工号
//                            eduEmployeeEntity.setRandCode(usersInRole.getRank_code());
//                            eduEmployeeEntity.setRankName(usersInRole.getRank_name());
//                            eduEmployeeEntity.setMobile(usersInRole.getSyscontent1()); //手机号
//                            eduEmployeeEntity.setEmail(usersInRole.getE_mail());//邮箱
            eduEmployeeService.insert(eduEmployeeEntity);
        } else {
            employeeId = eduEmployeeEntity.getId();
            //更新的时候要谨慎，如果获取的是兼职的信息，那就不用更新了，直接跳过
            //主职的信息才要更新
//            if(usersInRole.getSideline().equals("0")){
                //已经有用户了，更新信息
//                eduEmployeeEntity.setId(IdUtil.simpleUUID());
                eduEmployeeEntity.setH4aAllPathName(usersInRole.getAll_path_name());
//                eduEmployeeEntity.setH4aUserGuid(usersInRole.getGuid());
                eduEmployeeEntity.setH4aUserGuid(usersInRole.getGuid());
                eduEmployeeEntity.setH4aViewGuid("");
                eduEmployeeEntity.setH4aDisplayName(usersInRole.getDisplay_name());
                eduEmployeeEntity.setH4aParentGuid(usersInRole.getParent_guid());
                eduEmployeeEntity.setEmployeeName(usersInRole.getDisplay_name());
                eduEmployeeEntity.setEmployeeCode(usersInRole.getPerson_id());//2222员工号
                eduEmployeeService.updateById(eduEmployeeEntity);
//            }
        }

        //查看是否已经有 当前角色和当前用户关联了
        EntityWrapper<EduSystemRolesEmployeeEntity> eduSystemRolesEmployeeEntityEntityWrapper = new EntityWrapper<>();
//                        eduSystemRolesEmployeeEntityEntityWrapper.eq("h4aGuid", usersInRole.getGuid());
        eduSystemRolesEmployeeEntityEntityWrapper.eq("h4aGuid", usersInRole.getGuid());
        eduSystemRolesEmployeeEntityEntityWrapper.eq("roleCode", usersInRole.getRole_value());
        EduSystemRolesEmployeeService eduSystemRolesEmployeeService = (EduSystemRolesEmployeeService) ApplicationContextUtil.getBean("eduSystemRolesEmployeeService");
        EduSystemRolesEmployeeEntity eduSystemRolesEmployeeEntity = eduSystemRolesEmployeeService.selectOne(eduSystemRolesEmployeeEntityEntityWrapper);
        if (null == eduSystemRolesEmployeeEntity) {
            //如果没有就新增条记录
            EduSystemRolesEmployeeEntity eduSystemRolesEmployeeEntityTmp = new EduSystemRolesEmployeeEntity();
            eduSystemRolesEmployeeEntityTmp.setEmployeeId(employeeId);
//                            eduSystemRolesEmployeeEntityTmp.setH4aGuid(usersInRole.getGuid());
            eduSystemRolesEmployeeEntityTmp.setH4aGuid(usersInRole.getGuid());
            eduSystemRolesEmployeeEntityTmp.setRolesId(roleId);
            eduSystemRolesEmployeeEntityTmp.setRoleCode(usersInRole.getRole_value());
            eduSystemRolesEmployeeEntityTmp.setLastUpdateTime(new Date());
            eduSystemRolesEmployeeEntityTmp.setVersion(version);
            eduSystemRolesEmployeeService.insert(eduSystemRolesEmployeeEntityTmp);
        }else{
            //如果有的话，更新版本号即可
            eduSystemRolesEmployeeEntity.setVersion(version);
            eduSystemRolesEmployeeEntity.setLastUpdateTime(new Date());
            eduSystemRolesEmployeeService.updateById(eduSystemRolesEmployeeEntity);
        }

        //TODO 要删除的暂不处理
    }

    void syncAuthorityAndRole() {
        Functions func = new Functions();
        //先获取所有的角色
        EduSystemRolesService eduSystemRolesService = (EduSystemRolesService) ApplicationContextUtil.getBean("eduSystemRolesService");
        List<EduSystemRolesEntity> eduSystemRolesEntities = eduSystemRolesService.selectList(null);
        //遍历
        for (EduSystemRolesEntity rolesEntity : eduSystemRolesEntities) {
            //调用接口，查询当前角色下面的权限
            try {
                FunctionsInRoles[] funcList = func.getBeanFunctionsInRoles(rolesEntity.getCode());
                if (funcList == null) {
                    //当前角色下满没有权限，那就直接 下一位
                    continue;
                }
                for (FunctionsInRoles funcItem : funcList) {
                    //查询是否已经有这个角色和权限的关联了
                    EntityWrapper<EduSystemAuthorityRoleEntity> _filter = new EntityWrapper<EduSystemAuthorityRoleEntity>();
                    _filter.eq("authorityCode", funcItem.getCode_name());
                    _filter.eq("roleCode", rolesEntity.getCode());
                    EduSystemAuthorityRoleService eduSystemAuthorityRoleService = (EduSystemAuthorityRoleService) ApplicationContextUtil.getBean("eduSystemAuthorityRoleService");
                    EduSystemAuthorityRoleEntity eduSystemAuthorityRoleEntity = eduSystemAuthorityRoleService.selectOne(_filter);
                    if (null == eduSystemAuthorityRoleEntity) {
                        //准备数据，获取权限信息
                        EduSystemAuthorityService eduSystemAuthorityService = (EduSystemAuthorityService) ApplicationContextUtil.getBean("eduSystemAuthorityService");
                        EduSystemAuthorityEntity authorityInfo = eduSystemAuthorityService.selectOne(new EntityWrapper<EduSystemAuthorityEntity>().eq("code", funcItem.getCode_name()));
                        if (null == authorityInfo) {
                            //如果已经要保存关联了，但是权限却没有，那就不管，下一位
                            continue;
                        }
                        //如果不存在关联，那就新增一个
                        eduSystemAuthorityRoleEntity = new EduSystemAuthorityRoleEntity();
                        eduSystemAuthorityRoleEntity.setAuthorityId(authorityInfo.getId());
                        eduSystemAuthorityRoleEntity.setAuthorityCode(authorityInfo.getCode());
                        eduSystemAuthorityRoleEntity.setRolesId(rolesEntity.getId());
                        eduSystemAuthorityRoleEntity.setRoleCode(rolesEntity.getCode());
                        eduSystemAuthorityRoleService.insert(eduSystemAuthorityRoleEntity);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
    }
}
