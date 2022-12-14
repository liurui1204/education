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
        String groupCode = "";// ???????????????????????????????????????????????????
        try {
            FunctionsInApplication[] allFunctions = func.getBeanFunctionsInApplication(groupCode);
            if (null != allFunctions) {
                //?????????????????????????????????????????????
                for (FunctionsInApplication funcItem : allFunctions) {
                    //??????????????????code??????????????????
                    EntityWrapper<EduSystemAuthorityEntity> filter = new EntityWrapper<EduSystemAuthorityEntity>();
                    filter.eq("code", funcItem.getCode_name());
                    EduSystemAuthorityService eduSystemAuthorityService = (EduSystemAuthorityService) ApplicationContextUtil.getBean("eduSystemAuthorityService");
                    EduSystemAuthorityEntity eduSystemAuthorityEntity = eduSystemAuthorityService.selectOne(filter);
                    int _type; //???????????????
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
                        //????????????????????????
                        eduSystemAuthorityEntity = new EduSystemAuthorityEntity();
                        eduSystemAuthorityEntity.setId(IdUtil.simpleUUID());
                        eduSystemAuthorityEntity.setAuthorityType(_type);
                        eduSystemAuthorityEntity.setCode(funcItem.getCode_name());
                        eduSystemAuthorityEntity.setName(funcItem.getFunc_name());
                        eduSystemAuthorityEntity.setLastUpdateTime(new Date());
                        eduSystemAuthorityService.insert(eduSystemAuthorityEntity);
                    } else {
                        //?????????????????????
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
                //?????????????????????????????????????????????????????????????????????
                if (!ObjectUtils.isEmpty(systemRolesEntity)) {
                    systemRolesEntity.setName(beanRole.getRole_name());
                    systemRolesEntity.setLastUpdateTime(new Date());
                    systemRolesEntity.setMemo(beanRole.getRole_description());
                    eduSystemRolesService.updateById(systemRolesEntity);
                    roleId = systemRolesEntity.getId();
                    //continue;
                } else {
                    //??????????????????????????????
                    EduSystemRolesEntity eduSystemRolesEntity = new EduSystemRolesEntity();
                    eduSystemRolesEntity.setCode(beanRole.getCode_name());
                    eduSystemRolesEntity.setName(beanRole.getRole_name());
                    eduSystemRolesEntity.setLastUpdateTime(new Date());
                    eduSystemRolesEntity.setMemo(beanRole.getRole_description());
                    roleId = IdUtil.simpleUUID();
                    eduSystemRolesEntity.setId(roleId);
                    eduSystemRolesService.insert(eduSystemRolesEntity);
                }

                //?????????????????????????????????
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
            //????????????????????????????????????????????????????????? version
            EduSystemRolesEmployeeService eduSystemRolesEmployeeService = (EduSystemRolesEmployeeService) ApplicationContextUtil.getBean("eduSystemRolesEmployeeService");
            eduSystemRolesEmployeeService.delete(new EntityWrapper<EduSystemRolesEmployeeEntity>().lt("version", version));
            eduSystemRolesEmployeeService.delete(new EntityWrapper<EduSystemRolesEmployeeEntity>().isNull("version"));
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }

        System.err.println("??????????????????????????????: " + LocalDateTime.now());
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
                //??????????????????????????????????????????????????????????????????????????????????????????????????????
                break;
            case "ORGANIZATIONS":
                Organization organization = new Organization();
                //?????????????????????????????????????????????
                OrganizationChildren[] orgSonItems = organization.getBeanOrganizationChildrenByAllPath(orgInfo.getAll_path_name());
                for(OrganizationChildren sonItem : orgSonItems){
                    //??????????????????????????????????????????
                    if(sonItem.getAll_path_name().equals(orgInfo.getAll_path_name())){
                        continue;
                    }
                    getOrgToLast(sonItem, code_name, roleId, version);
                }
                break;
        }
    }

    private void saveChildAndRole(ChildrenInRoles usersInRole, String roleId, Long version){
        //???????????????????????????
        EntityWrapper<EduEmployeeEntity> eduEmployeeEntityEntityWrapper = new EntityWrapper<>();
//                        eduEmployeeEntityEntityWrapper.eq("h4aUserGuid", usersInRole.getGuid());
        eduEmployeeEntityEntityWrapper.eq("h4aUserGuid", usersInRole.getGuid());
        EduEmployeeService eduEmployeeService = (EduEmployeeService) ApplicationContextUtil.getBean("eduEmployeeService");
        EduEmployeeEntity eduEmployeeEntity = eduEmployeeService.selectOne(eduEmployeeEntityEntityWrapper);
        String employeeId;
        if (null == eduEmployeeEntity) {
            //?????????????????????????????????????????????
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
            eduEmployeeEntity.setEmployeeCode(usersInRole.getPerson_id());//2222?????????
//                            eduEmployeeEntity.setRandCode(usersInRole.getRank_code());
//                            eduEmployeeEntity.setRankName(usersInRole.getRank_name());
//                            eduEmployeeEntity.setMobile(usersInRole.getSyscontent1()); //?????????
//                            eduEmployeeEntity.setEmail(usersInRole.getE_mail());//??????
            eduEmployeeService.insert(eduEmployeeEntity);
        } else {
            employeeId = eduEmployeeEntity.getId();
            //???????????????????????????????????????????????????????????????????????????????????????????????????
            //???????????????????????????
//            if(usersInRole.getSideline().equals("0")){
                //?????????????????????????????????
//                eduEmployeeEntity.setId(IdUtil.simpleUUID());
                eduEmployeeEntity.setH4aAllPathName(usersInRole.getAll_path_name());
//                eduEmployeeEntity.setH4aUserGuid(usersInRole.getGuid());
                eduEmployeeEntity.setH4aUserGuid(usersInRole.getGuid());
                eduEmployeeEntity.setH4aViewGuid("");
                eduEmployeeEntity.setH4aDisplayName(usersInRole.getDisplay_name());
                eduEmployeeEntity.setH4aParentGuid(usersInRole.getParent_guid());
                eduEmployeeEntity.setEmployeeName(usersInRole.getDisplay_name());
                eduEmployeeEntity.setEmployeeCode(usersInRole.getPerson_id());//2222?????????
                eduEmployeeService.updateById(eduEmployeeEntity);
//            }
        }

        //????????????????????? ????????????????????????????????????
        EntityWrapper<EduSystemRolesEmployeeEntity> eduSystemRolesEmployeeEntityEntityWrapper = new EntityWrapper<>();
//                        eduSystemRolesEmployeeEntityEntityWrapper.eq("h4aGuid", usersInRole.getGuid());
        eduSystemRolesEmployeeEntityEntityWrapper.eq("h4aGuid", usersInRole.getGuid());
        eduSystemRolesEmployeeEntityEntityWrapper.eq("roleCode", usersInRole.getRole_value());
        EduSystemRolesEmployeeService eduSystemRolesEmployeeService = (EduSystemRolesEmployeeService) ApplicationContextUtil.getBean("eduSystemRolesEmployeeService");
        EduSystemRolesEmployeeEntity eduSystemRolesEmployeeEntity = eduSystemRolesEmployeeService.selectOne(eduSystemRolesEmployeeEntityEntityWrapper);
        if (null == eduSystemRolesEmployeeEntity) {
            //??????????????????????????????
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
            //???????????????????????????????????????
            eduSystemRolesEmployeeEntity.setVersion(version);
            eduSystemRolesEmployeeEntity.setLastUpdateTime(new Date());
            eduSystemRolesEmployeeService.updateById(eduSystemRolesEmployeeEntity);
        }

        //TODO ????????????????????????
    }

    void syncAuthorityAndRole() {
        Functions func = new Functions();
        //????????????????????????
        EduSystemRolesService eduSystemRolesService = (EduSystemRolesService) ApplicationContextUtil.getBean("eduSystemRolesService");
        List<EduSystemRolesEntity> eduSystemRolesEntities = eduSystemRolesService.selectList(null);
        //??????
        for (EduSystemRolesEntity rolesEntity : eduSystemRolesEntities) {
            //????????????????????????????????????????????????
            try {
                FunctionsInRoles[] funcList = func.getBeanFunctionsInRoles(rolesEntity.getCode());
                if (funcList == null) {
                    //????????????????????????????????????????????? ?????????
                    continue;
                }
                for (FunctionsInRoles funcItem : funcList) {
                    //??????????????????????????????????????????????????????
                    EntityWrapper<EduSystemAuthorityRoleEntity> _filter = new EntityWrapper<EduSystemAuthorityRoleEntity>();
                    _filter.eq("authorityCode", funcItem.getCode_name());
                    _filter.eq("roleCode", rolesEntity.getCode());
                    EduSystemAuthorityRoleService eduSystemAuthorityRoleService = (EduSystemAuthorityRoleService) ApplicationContextUtil.getBean("eduSystemAuthorityRoleService");
                    EduSystemAuthorityRoleEntity eduSystemAuthorityRoleEntity = eduSystemAuthorityRoleService.selectOne(_filter);
                    if (null == eduSystemAuthorityRoleEntity) {
                        //?????????????????????????????????
                        EduSystemAuthorityService eduSystemAuthorityService = (EduSystemAuthorityService) ApplicationContextUtil.getBean("eduSystemAuthorityService");
                        EduSystemAuthorityEntity authorityInfo = eduSystemAuthorityService.selectOne(new EntityWrapper<EduSystemAuthorityEntity>().eq("code", funcItem.getCode_name()));
                        if (null == authorityInfo) {
                            //?????????????????????????????????????????????????????????????????????????????????
                            continue;
                        }
                        //??????????????????????????????????????????
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
