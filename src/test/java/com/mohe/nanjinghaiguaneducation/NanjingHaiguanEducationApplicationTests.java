package com.mohe.nanjinghaiguaneducation;

import cn.gov.customs.casp.sdk.h4a.entity.*;
import cn.gov.customs.casp.sdk.h4a.ogu.ws.IOguReaderGetOrganizationChildrenCupaaFaultArgsFaultFaultMessage;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.mohe.nanjinghaiguaneducation.common.h4a.Functions;
import com.mohe.nanjinghaiguaneducation.common.h4a.Organization;
import com.mohe.nanjinghaiguaneducation.common.h4a.Users;
import com.mohe.nanjinghaiguaneducation.common.utils.NumberSequenceUtil;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.*;
import com.mohe.nanjinghaiguaneducation.modules.common.service.*;
import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.employee.service.EduEmployeeService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
class NanjingHaiguanEducationApplicationTests {

//    @Autowired
//    private StringRedisTemplate redisTemplate;
//
//    @Autowired
//    private NumberSequenceUtil numberSequenceUtil;
//
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Autowired
//    private EduSystemRolesService eduSystemRolesService;
//    @Autowired
//    private EduSystemRolesEmployeeService eduSystemRolesEmployeeService;
//    @Autowired
//    private EduEmployeeService eduEmployeeService;
//    @Autowired
//    private EduSystemAuthorityService eduSystemAuthorityService;
//    @Autowired
//    private EduSystemAuthorityRoleService eduSystemAuthorityRoleService;
//
//    @Autowired
//    private EduDepartmentService eduDepartmentService;
//    @Autowired
//    private EduDepartmentEmployeeService eduDepartmentEmployeeService;
//
//    private Integer DepartmentOrderNumber;
//
//    /**
//     * ?????????????????????
//     */
//    @Test
//    void contextLoads() {
//        Users users = new Users();
//        try {
//            Roles[] beanRoles = users.getBeanRoles();
//            for (Roles beanRole : beanRoles) {
//                EntityWrapper<EduSystemRolesEntity> entityEntityWrapper = new EntityWrapper<>();
//                Wrapper<EduSystemRolesEntity> eduSystemRolesEntityWrapper = entityEntityWrapper.eq("code", beanRole.getCode_name());
//                String code_name = beanRole.getCode_name();
//                if (code_name.equals("SYSTEM_ADMIN") || code_name.equals("APP_ADMIN")) {
//                    continue;
//                }
//                logger.info("canshu : {}", code_name);
//                EduSystemRolesEntity systemRolesEntity = eduSystemRolesService.selectOne(new EntityWrapper<EduSystemRolesEntity>().eq("code", code_name));
//                String roleId;
//                //?????????????????????????????????????????????????????????????????????
//                if (!ObjectUtils.isEmpty(systemRolesEntity)) {
//                    systemRolesEntity.setName(beanRole.getRole_name());
//                    systemRolesEntity.setLastUpdateTime(new Date());
//                    systemRolesEntity.setMemo(beanRole.getRole_description());
//                    eduSystemRolesService.updateById(systemRolesEntity);
//                    roleId = systemRolesEntity.getId();
//                    //continue;
//                } else {
//                    //??????????????????????????????
//                    EduSystemRolesEntity eduSystemRolesEntity = new EduSystemRolesEntity();
//                    eduSystemRolesEntity.setCode(beanRole.getCode_name());
//                    eduSystemRolesEntity.setName(beanRole.getRole_name());
//                    eduSystemRolesEntity.setLastUpdateTime(new Date());
//                    eduSystemRolesEntity.setMemo(beanRole.getRole_description());
//                    roleId = IdUtil.simpleUUID();
//                    eduSystemRolesEntity.setId(roleId);
//                    eduSystemRolesService.insert(eduSystemRolesEntity);
//                }
//
//                //?????????????????????????????????
////                UsersInRoles[] usersInRoles = users.getUsersInRoles(beanRole.getCode_name());
//                ChildrenInRoles[] usersInRoles = users.getChildrenInRoles(beanRole.getCode_name());
//                if (null != usersInRoles) {
//                    for (ChildrenInRoles usersInRole : usersInRoles) {
//                        //???????????????????????????
//                        EntityWrapper<EduEmployeeEntity> eduEmployeeEntityEntityWrapper = new EntityWrapper<>();
//                        eduEmployeeEntityEntityWrapper.eq("h4aUserGuid", usersInRole.getGuid());
//                        EduEmployeeEntity eduEmployeeEntity = eduEmployeeService.selectOne(eduEmployeeEntityEntityWrapper);
//                        String employeeId;
//                        if (null == eduEmployeeEntity) {
//                            //?????????????????????????????????????????????
//                            eduEmployeeEntity = new EduEmployeeEntity();
//                            employeeId = IdUtil.simpleUUID();
//                            eduEmployeeEntity.setId(employeeId);
//                            eduEmployeeEntity.setH4aAllPathName(usersInRole.getAll_path_name());
//                            eduEmployeeEntity.setH4aUserGuid(usersInRole.getGuid());
//                            eduEmployeeEntity.setH4aViewGuid("");
//                            eduEmployeeEntity.setH4aDisplayName(usersInRole.getDisplay_name());
//                            eduEmployeeEntity.setH4aParentGuid(usersInRole.getParent_guid());
//                            eduEmployeeEntity.setEmployeeName(usersInRole.getDisplay_name());
//                            eduEmployeeEntity.setEmployeeCode(usersInRole.getPerson_id());//?????????
////                            eduEmployeeEntity.setRandCode(usersInRole.getRank_code());
////                            eduEmployeeEntity.setRankName(usersInRole.getRank_name());
////                            eduEmployeeEntity.setMobile(usersInRole.getSyscontent1()); //?????????
////                            eduEmployeeEntity.setEmail(usersInRole.getE_mail());//??????
//                            eduEmployeeService.insert(eduEmployeeEntity);
//                        } else {
//                            employeeId = eduEmployeeEntity.getId();
//                            //?????????????????????????????????
//                            eduEmployeeEntity.setId(IdUtil.simpleUUID());
//                            eduEmployeeEntity.setH4aAllPathName(usersInRole.getAll_path_name());
//                            eduEmployeeEntity.setH4aUserGuid(usersInRole.getGuid());
//                            eduEmployeeEntity.setH4aViewGuid("");
//                            eduEmployeeEntity.setH4aDisplayName(usersInRole.getDisplay_name());
//                            eduEmployeeEntity.setH4aParentGuid(usersInRole.getParent_guid());
//                            eduEmployeeEntity.setEmployeeName(usersInRole.getDisplay_name());
//                            eduEmployeeEntity.setEmployeeCode(usersInRole.getPerson_id());//?????????
//                            eduEmployeeService.updateById(eduEmployeeEntity);
//                        }
//
//                        //????????????????????? ????????????????????????????????????
//                        EntityWrapper<EduSystemRolesEmployeeEntity> eduSystemRolesEmployeeEntityEntityWrapper = new EntityWrapper<>();
//                        eduSystemRolesEmployeeEntityEntityWrapper.eq("h4aGuid", usersInRole.getGuid());
//                        eduSystemRolesEmployeeEntityEntityWrapper.eq("roleCode", usersInRole.getRole_value());
//                        EduSystemRolesEmployeeEntity eduSystemRolesEmployeeEntity = eduSystemRolesEmployeeService.selectOne(eduSystemRolesEmployeeEntityEntityWrapper);
//                        if (null == eduSystemRolesEmployeeEntity) {
//                            //??????????????????????????????
//                            EduSystemRolesEmployeeEntity eduSystemRolesEmployeeEntityTmp = new EduSystemRolesEmployeeEntity();
//                            eduSystemRolesEmployeeEntityTmp.setEmployeeId(employeeId);
//                            eduSystemRolesEmployeeEntityTmp.setH4aGuid(usersInRole.getGuid());
//                            eduSystemRolesEmployeeEntityTmp.setRolesId(roleId);
//                            eduSystemRolesEmployeeEntityTmp.setRoleCode(usersInRole.getRole_value());
//                            eduSystemRolesEmployeeEntityTmp.setLastUpdateTime(new Date());
//                            eduSystemRolesEmployeeService.insert(eduSystemRolesEmployeeEntityTmp);
//                        }
//
//                        //TODO ????????????????????????
//
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.getMessage();
//            e.printStackTrace();
//        }
//
//        System.err.println("??????????????????????????????: " + LocalDateTime.now());
//    }
//
//    /**
//     * ????????????
//     */
//    @Test
//    void syncAuthority() {
//        Functions func = new Functions();
//        String groupCode = "";// ???????????????????????????????????????????????????
//        try {
//            FunctionsInApplication[] allFunctions = func.getBeanFunctionsInApplication(groupCode);
//            if (null != allFunctions) {
//                //?????????????????????????????????????????????
//                for (FunctionsInApplication funcItem : allFunctions) {
//                    //??????????????????code??????????????????
//                    EntityWrapper<EduSystemAuthorityEntity> filter = new EntityWrapper<EduSystemAuthorityEntity>();
//                    filter.eq("code", funcItem.getCode_name());
//                    EduSystemAuthorityEntity eduSystemAuthorityEntity = eduSystemAuthorityService.selectOne(filter);
//                    int _type; //???????????????
//                    switch (funcItem.getCode_name().substring(0, 2)) {
//                        case "JM":
//                            _type = 1;
//                            break;
//                        case "CZ":
//                            _type = 2;
//                            break;
//                        default:
//                            _type = 3;
//                            break;
//                    }
//                    if (null == eduSystemAuthorityEntity) {
//                        //????????????????????????
//                        eduSystemAuthorityEntity = new EduSystemAuthorityEntity();
//                        eduSystemAuthorityEntity.setId(IdUtil.simpleUUID());
//                        eduSystemAuthorityEntity.setAuthorityType(_type);
//                        eduSystemAuthorityEntity.setCode(funcItem.getCode_name());
//                        eduSystemAuthorityEntity.setName(funcItem.getFunc_name());
//                        eduSystemAuthorityEntity.setLastUpdateTime(new Date());
//                        eduSystemAuthorityService.insert(eduSystemAuthorityEntity);
//                    } else {
//                        //?????????????????????
//                        eduSystemAuthorityEntity.setId(IdUtil.simpleUUID());
//                        eduSystemAuthorityEntity.setAuthorityType(_type);
//                        eduSystemAuthorityEntity.setCode(funcItem.getCode_name());
//                        eduSystemAuthorityEntity.setName(funcItem.getFunc_name());
//                        eduSystemAuthorityEntity.setLastUpdateTime(new Date());
//                        eduSystemAuthorityService.updateById(eduSystemAuthorityEntity);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    /**
//     * ??????????????????????????????????????????????????????????????????????????????
//     */
//    @Test
//    void syncAuthorityAndRole() {
//        Functions func = new Functions();
//        //????????????????????????
//        List<EduSystemRolesEntity> eduSystemRolesEntities = eduSystemRolesService.selectList(null);
//        //??????
//        for (EduSystemRolesEntity rolesEntity : eduSystemRolesEntities) {
//            //????????????????????????????????????????????????
//            try {
//                FunctionsInRoles[] funcList = func.getBeanFunctionsInRoles(rolesEntity.getCode());
//                if (funcList == null) {
//                    //????????????????????????????????????????????? ?????????
//                    continue;
//                }
//                for (FunctionsInRoles funcItem : funcList) {
//                    //??????????????????????????????????????????????????????
//                    EntityWrapper<EduSystemAuthorityRoleEntity> _filter = new EntityWrapper<EduSystemAuthorityRoleEntity>();
//                    _filter.eq("authorityCode", funcItem.getCode_name());
//                    _filter.eq("roleCode", rolesEntity.getCode());
//                    EduSystemAuthorityRoleEntity eduSystemAuthorityRoleEntity = eduSystemAuthorityRoleService.selectOne(_filter);
//                    if (null == eduSystemAuthorityRoleEntity) {
//                        //?????????????????????????????????
//                        EduSystemAuthorityEntity authorityInfo = eduSystemAuthorityService.selectOne(new EntityWrapper<EduSystemAuthorityEntity>().eq("code", funcItem.getCode_name()));
//                        if (null == authorityInfo) {
//                            //?????????????????????????????????????????????????????????????????????????????????
//                            continue;
//                        }
//                        //??????????????????????????????????????????
//                        eduSystemAuthorityRoleEntity = new EduSystemAuthorityRoleEntity();
//                        eduSystemAuthorityRoleEntity.setAuthorityId(authorityInfo.getId());
//                        eduSystemAuthorityRoleEntity.setAuthorityCode(authorityInfo.getCode());
//                        eduSystemAuthorityRoleEntity.setRolesId(rolesEntity.getId());
//                        eduSystemAuthorityRoleEntity.setRoleCode(rolesEntity.getCode());
//                        eduSystemAuthorityRoleService.insert(eduSystemAuthorityRoleEntity);
//                    }
//                }
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
//        }
//    }
//
//    /**
//     * ???????????????????????????????????????????????????
//     */
//    @Test
//    void syncOrgAndUser() {
//        this.DepartmentOrderNumber = 0;
//        String rootPath = "????????????\\????????????";
//        Organization organization = new Organization();
//        try {
//            //??????????????????????????????
//            OrganizationChildren rootOrg = new OrganizationChildren();
//            //??????????????????????????? ???????????????????????????
//            OrganizationChildren[] beanOrganizationChildrenByAllPath = organization.getBeanOrganizationChildrenByAllPath(rootPath);
//            for (OrganizationChildren children : beanOrganizationChildrenByAllPath) {
//                //??????????????????????????????????????????????????????
//                if (children.getObjectclass().equals("ORGANIZATIONS") && children.getAll_path_name().equals(rootPath)) {
//                    saveAndUpdateDepartment(children, "");
//                    rootOrg = children;
//                } else {
//                    System.out.println(" => ROOT_PATH: " + rootPath + "  SON_PATH: " + children.getDisplay_name());
//                    processCurrentOrg(children, rootOrg);
//                }
//
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    private void processCurrentOrg(OrganizationChildren orgInfo, OrganizationChildren parentInfo)
//            throws IOguReaderGetOrganizationChildrenCupaaFaultArgsFaultFaultMessage {
//        Organization organization = new Organization();
//        //???????????????????????????????????? USERS / GROUPS / ORGANIZATIONS
//        switch (orgInfo.getObjectclass()){
//            case "USERS":
//                //???????????????id
//                String employee_id;
//                //????????????????????????????????????????????????????????????
//                EntityWrapper<EduEmployeeEntity> filter = new EntityWrapper<EduEmployeeEntity>();
//                filter.eq("h4aUserGuid", orgInfo.getUser_guid());
//                EduEmployeeEntity eduEmployeeEntity = eduEmployeeService.selectOne(filter);
//                if(null == eduEmployeeEntity){
//                    EduEmployeeEntity employee = new EduEmployeeEntity();
//                    employee_id = IdUtil.simpleUUID();
//                    employee.setEmployeeCode(orgInfo.getUser_guid());
//                    employee.setH4aUserGuid(orgInfo.getUser_guid());
//                    employee.setEmployeeName(orgInfo.getDisplay_name());
//                    employee.setIsEnable(1);
//                    employee.setH4aViewGuid(orgInfo.getView_guid());
//                    employee.setH4aAllPathName(orgInfo.getAll_path_name());
//                    employee.setH4aParentGuid(orgInfo.getParent_guid());
//                    employee.setH4aDisplayName(orgInfo.getDisplay_name());
//                    employee.setEmployeeCode(orgInfo.getPerson_id());
//                    employee.setRandCode(orgInfo.getRank_code());
//                    employee.setRankName(orgInfo.getRank_name());
//                    employee.setMobile(orgInfo.getSyscontent1()); //?????????
//                    employee.setEmail(orgInfo.getE_mail());//??????
////                    employee.setGender(orgInfo.get);
//                    eduEmployeeService.insert(employee);
//                }else{
//                    employee_id = eduEmployeeEntity.getId();
//                    eduEmployeeEntity.setEmployeeCode(orgInfo.getUser_guid());
//                    eduEmployeeEntity.setH4aUserGuid(orgInfo.getUser_guid());
//                    eduEmployeeEntity.setEmployeeName(orgInfo.getDisplay_name());
//                    eduEmployeeEntity.setIsEnable(1);
//                    eduEmployeeEntity.setH4aViewGuid(orgInfo.getView_guid());
//                    eduEmployeeEntity.setH4aAllPathName(orgInfo.getAll_path_name());
//                    eduEmployeeEntity.setH4aParentGuid(orgInfo.getParent_guid());
//                    eduEmployeeEntity.setH4aDisplayName(orgInfo.getDisplay_name());
//                    eduEmployeeEntity.setEmployeeCode(orgInfo.getPerson_id());
//                    eduEmployeeEntity.setRandCode(orgInfo.getRank_code());
//                    eduEmployeeEntity.setRankName(orgInfo.getRank_name());
//                    eduEmployeeEntity.setMobile(orgInfo.getSyscontent1()); //?????????
//                    eduEmployeeEntity.setEmail(orgInfo.getE_mail());//??????
//                    eduEmployeeService.updateById(eduEmployeeEntity);
//                }
//                System.out.println(" ====> ???????????? ?????? "+orgInfo.getDisplay_name());
//                //?????????????????????????????????????????????????????????
//                EntityWrapper<EduDepartmentEmployeeEntity> relationFilter = new EntityWrapper<>();
//                String parentDepartmentCode = "";
//                switch (parentInfo.getObjectclass()){
//                    case "USERS":
//                        //?????????????????????????????????????????????????????????
//                        parentDepartmentCode = parentInfo.getUser_guid();
//                        break;
//                    case "GROUPS":
//                        parentDepartmentCode = parentInfo.getGroup_guid();
//                        break;
//                    case "ORGANIZATIONS":
//                        parentDepartmentCode = parentInfo.getOrg_guid();
//                        break;
//                }
//                relationFilter.eq("departmentCode", parentDepartmentCode);
//                relationFilter.eq("h4aGuid", orgInfo.getUser_guid());
//                EduDepartmentEmployeeEntity eduDepartmentEmployeeEntity = eduDepartmentEmployeeService.selectOne(relationFilter);
//                //?????????????????????????????? - department
//                EduDepartmentEntity departmentData = eduDepartmentService.selectOne(
//                        new EntityWrapper<EduDepartmentEntity>().eq("departmentCode", parentDepartmentCode));
//                if(null == eduDepartmentEmployeeEntity){
//                    //??????????????????????????????????????????????????????
//                    EduDepartmentEmployeeEntity relationData = new EduDepartmentEmployeeEntity();
//                    relationData.setId(IdUtil.simpleUUID());
//                    relationData.setDepartmentId(departmentData.getId());
//                    relationData.setDepartmentCode(departmentData.getDepartmentCode());
//                    relationData.setEmployee_id(employee_id);
//                    relationData.setH4aGuid(orgInfo.getUser_guid());
//                    relationData.setDelete(0);
//                    relationData.setLast_update_time(new Date());
//                    relationData.setIsMain(orgInfo.getSideline());
//                    eduDepartmentEmployeeService.insert(relationData);
//                }else{
//                    //????????????????????????????????????????????????
//                    eduDepartmentEmployeeEntity.setId(IdUtil.simpleUUID());
//                    eduDepartmentEmployeeEntity.setDepartmentId(departmentData.getId());
//                    eduDepartmentEmployeeEntity.setDepartmentCode(departmentData.getDepartmentCode());
//                    eduDepartmentEmployeeEntity.setEmployee_id(employee_id);
//                    eduDepartmentEmployeeEntity.setH4aGuid(orgInfo.getUser_guid());
//                    eduDepartmentEmployeeEntity.setDelete(0);
//                    eduDepartmentEmployeeEntity.setLast_update_time(new Date());
//                    eduDepartmentEmployeeEntity.setIsMain(orgInfo.getSideline());
//                    eduDepartmentEmployeeService.updateById(eduDepartmentEmployeeEntity);
//                }
//                break;
//            case "GROUPS":
//                System.out.println(" ====> ??????????????? ?????? "+orgInfo.getDisplay_name());
//                //????????????????????????????????????????????????????????????????????????????????????
//                //??????????????????????????????????????????????????????????????? parent_id ????????? org_guid
//                saveAndUpdateDepartment(orgInfo, parentInfo.getOrg_guid());
//                //?????????????????????????????????????????????
//                try {
//                    UsersInGroups[] users = organization.getBeanUsersInGroupsByAllPath(orgInfo.getAll_path_name());
//                    if(null != users){
//                        for(UsersInGroups user : users){
//                            saveUserAndGroup(user, orgInfo);
//                        }
//                    }
//                }catch (Exception ee){
//                    System.out.println(ee.getMessage());
////                    throw ee;
//                }
//                System.out.println(" =====> ??????????????? ?????? "+orgInfo.getDisplay_name());
//                break;
//            case "ORGANIZATIONS":
//                System.out.println(" ====> ???????????? ?????? "+orgInfo.getDisplay_name());
//                //??????????????????????????????????????????????????????????????????????????????
//                try {
//                    saveAndUpdateDepartment(orgInfo, parentInfo.getOrg_guid());
//                    //?????????????????????????????????????????????
//                    OrganizationChildren[] orgSonItems = organization.getBeanOrganizationChildrenByAllPath(orgInfo.getAll_path_name());
//                    for(OrganizationChildren sonItem : orgSonItems){
//                        //??????????????????????????????????????????
//                        if(sonItem.getAll_path_name().equals(orgInfo.getAll_path_name())){
//                            continue;
//                        }
//                        processCurrentOrg(sonItem, orgInfo);
//                    }
//                }catch (Exception e){
//                    System.out.println(e.getMessage());
//                    throw e;
//                }
//                System.out.println(" ====> ???????????? ?????? "+orgInfo.getDisplay_name());
//                break;
//        }
//    }
//
//    //??????department
//    private void saveAndUpdateDepartment(OrganizationChildren orgInfo, String parentId){
//        this.DepartmentOrderNumber++;
//        //???????????????????????????????????????
//        int slaveCustoms = 0;//????????????
//        int _type = 1;
//        String currentCode = orgInfo.getOrg_guid();
//        if(orgInfo.getObjectclass().equals("GROUPS")){
//            _type = 2;
//            slaveCustoms = 2; //??????
//            currentCode = orgInfo.getGroup_guid();
//        }else{
//            //?????????????????????????????????????????????????????????
//            //?????????????????????????????????????????????????????????????????????????????????
//            //?????? ????????????\????????????\xx??????\?????????
//            if(!orgInfo.getAll_path_name().equals("????????????\\????????????") && !orgInfo.getAll_path_name().substring("????????????\\????????????\\".length()).contains("??????\\")){
//                slaveCustoms = 1;//????????????
//            }
//        }
//
//        EntityWrapper<EduDepartmentEntity> filter = new EntityWrapper<EduDepartmentEntity>();
//        filter.eq("departmentCode", currentCode);
//        filter.eq("type", _type);
//        EduDepartmentEntity eduDepartmentEntity = eduDepartmentService.selectOne(filter);
//        if(null == eduDepartmentEntity){
//            //???????????????????????????
//            EduDepartmentEntity  department = new EduDepartmentEntity();
//            department.setId(IdUtil.simpleUUID());
//            department.setDepartmentCode(currentCode);
//            department.setDepartmentName(orgInfo.getDisplay_name());
//            department.setParentId(parentId);
//            department.setCreateTime(new Date());
//            department.setIsEnable(1);
//            department.setStatus(slaveCustoms);
//            department.setDepartmentAllPath(orgInfo.getAll_path_name());
//            department.setType(_type);
//            department.setOrder(this.DepartmentOrderNumber);
//            eduDepartmentService.insert(department);
//        }else{
//            //???????????????????????????
////            eduDepartmentEntity.setId(IdUtil.simpleUUID());
//            eduDepartmentEntity.setDepartmentCode(currentCode);
//            eduDepartmentEntity.setDepartmentName(orgInfo.getDisplay_name());
//            eduDepartmentEntity.setParentId(parentId);
//            eduDepartmentEntity.setCreateTime(new Date());
//            eduDepartmentEntity.setUpdateTime(new Date());
//            eduDepartmentEntity.setIsEnable(1);
//            eduDepartmentEntity.setStatus(slaveCustoms);
//            eduDepartmentEntity.setDepartmentAllPath(orgInfo.getAll_path_name());
//            eduDepartmentEntity.setType(_type);
//            eduDepartmentEntity.setOrder(this.DepartmentOrderNumber);
//            eduDepartmentService.updateById(eduDepartmentEntity);
//        }
//        System.out.println(" =========> ??????????????? ?????? ?????? "+orgInfo.getDisplay_name());
//    }
//
//    private void saveUserAndGroup(UsersInGroups user, OrganizationChildren org){
//        //???????????????id
//        String employee_id;
//        //????????????????????????????????????????????????????????????
//        EntityWrapper<EduEmployeeEntity> filter = new EntityWrapper<EduEmployeeEntity>();
//        filter.eq("h4aUserGuid", user.getUser_guid());
//        EduEmployeeEntity eduEmployeeEntity = eduEmployeeService.selectOne(filter);
//        if(null == eduEmployeeEntity){
//            EduEmployeeEntity employee = new EduEmployeeEntity();
//            employee_id = IdUtil.simpleUUID();
//            employee.setEmployeeCode(user.getPerson_id());
//            employee.setH4aUserGuid(user.getUser_guid());
//            employee.setEmployeeName(user.getDisplay_name());
//            employee.setIsEnable(1);
//            employee.setH4aViewGuid(user.getView_guid());
//            employee.setH4aAllPathName(user.getAll_path_name());
//            employee.setH4aParentGuid(user.getParent_guid());
//            employee.setH4aDisplayName(user.getDisplay_name());
//            employee.setRankName(user.getRank_name());
//            employee.setRandCode(user.getRank_code());
//            eduEmployeeService.insert(employee);
//        }else{
//            employee_id = eduEmployeeEntity.getId();
//            eduEmployeeEntity.setEmployeeCode(user.getPerson_id());
//            eduEmployeeEntity.setH4aUserGuid(user.getUser_guid());
//            eduEmployeeEntity.setEmployeeName(user.getDisplay_name());
//            eduEmployeeEntity.setIsEnable(1);
//            eduEmployeeEntity.setH4aViewGuid(user.getView_guid());
//            eduEmployeeEntity.setH4aAllPathName(user.getAll_path_name());
//            eduEmployeeEntity.setH4aParentGuid(user.getParent_guid());
//            eduEmployeeEntity.setH4aDisplayName(user.getDisplay_name());
//            eduEmployeeEntity.setRandCode(user.getRank_code());
//            eduEmployeeEntity.setRankName(user.getDisplay_name());
//            eduEmployeeService.updateById(eduEmployeeEntity);
//        }
////        System.out.println(" ====> ???????????? ?????? "+user.getDisplay_name());
//        //?????????????????????????????????????????????????????????
//        EntityWrapper<EduDepartmentEmployeeEntity> relationFilter = new EntityWrapper<>();
//        String parentDepartmentCode = "";
//        switch (org.getObjectclass()){
//            case "USERS":
//                //?????????????????????????????????????????????????????????
//                parentDepartmentCode = org.getUser_guid();
//                break;
//            case "GROUPS":
//                parentDepartmentCode = org.getGroup_guid();
//                break;
//            case "ORGANIZATIONS":
//                parentDepartmentCode = org.getOrg_guid();
//                break;
//        }
//        relationFilter.eq("departmentCode", parentDepartmentCode);
//        relationFilter.eq("h4aGuid", user.getUser_guid());
//        EduDepartmentEmployeeEntity eduDepartmentEmployeeEntity = eduDepartmentEmployeeService.selectOne(relationFilter);
//        //?????????????????????????????? - department
//        EduDepartmentEntity departmentData = eduDepartmentService.selectOne(
//                new EntityWrapper<EduDepartmentEntity>().eq("departmentCode", parentDepartmentCode));
//        if(null == eduDepartmentEmployeeEntity){
//            //??????????????????????????????????????????????????????
//            EduDepartmentEmployeeEntity relationData = new EduDepartmentEmployeeEntity();
//            relationData.setId(IdUtil.simpleUUID());
//            relationData.setDepartmentId(departmentData.getId());
//            relationData.setDepartmentCode(departmentData.getDepartmentCode());
//            relationData.setEmployee_id(employee_id);
//            relationData.setH4aGuid(org.getUser_guid());
//            relationData.setDelete(0);
//            relationData.setLast_update_time(new Date());
//            eduDepartmentEmployeeService.insert(relationData);
//        }else{
//            //????????????????????????????????????????????????
//            eduDepartmentEmployeeEntity.setId(IdUtil.simpleUUID());
//            eduDepartmentEmployeeEntity.setDepartmentId(departmentData.getId());
//            eduDepartmentEmployeeEntity.setDepartmentCode(departmentData.getDepartmentCode());
//            eduDepartmentEmployeeEntity.setEmployee_id(employee_id);
//            eduDepartmentEmployeeEntity.setH4aGuid(org.getUser_guid());
//            eduDepartmentEmployeeEntity.setDelete(0);
//            eduDepartmentEmployeeEntity.setLast_update_time(new Date());
//            eduDepartmentEmployeeService.updateById(eduDepartmentEmployeeEntity);
//        }
//    }
}
