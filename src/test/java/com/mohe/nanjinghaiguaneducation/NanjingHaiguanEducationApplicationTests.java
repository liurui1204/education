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
//     * 同步角色和用户
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
//                //如果该角色在系统中已经存在了，更新角色信息即可
//                if (!ObjectUtils.isEmpty(systemRolesEntity)) {
//                    systemRolesEntity.setName(beanRole.getRole_name());
//                    systemRolesEntity.setLastUpdateTime(new Date());
//                    systemRolesEntity.setMemo(beanRole.getRole_description());
//                    eduSystemRolesService.updateById(systemRolesEntity);
//                    roleId = systemRolesEntity.getId();
//                    //continue;
//                } else {
//                    //如果没有，要新增角色
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
//                //获取角色下面的用户列表
////                UsersInRoles[] usersInRoles = users.getUsersInRoles(beanRole.getCode_name());
//                ChildrenInRoles[] usersInRoles = users.getChildrenInRoles(beanRole.getCode_name());
//                if (null != usersInRoles) {
//                    for (ChildrenInRoles usersInRole : usersInRoles) {
//                        //先看当前用户有没有
//                        EntityWrapper<EduEmployeeEntity> eduEmployeeEntityEntityWrapper = new EntityWrapper<>();
//                        eduEmployeeEntityEntityWrapper.eq("h4aUserGuid", usersInRole.getGuid());
//                        EduEmployeeEntity eduEmployeeEntity = eduEmployeeService.selectOne(eduEmployeeEntityEntityWrapper);
//                        String employeeId;
//                        if (null == eduEmployeeEntity) {
//                            //如果没有用户，直接新增一个用户
//                            eduEmployeeEntity = new EduEmployeeEntity();
//                            employeeId = IdUtil.simpleUUID();
//                            eduEmployeeEntity.setId(employeeId);
//                            eduEmployeeEntity.setH4aAllPathName(usersInRole.getAll_path_name());
//                            eduEmployeeEntity.setH4aUserGuid(usersInRole.getGuid());
//                            eduEmployeeEntity.setH4aViewGuid("");
//                            eduEmployeeEntity.setH4aDisplayName(usersInRole.getDisplay_name());
//                            eduEmployeeEntity.setH4aParentGuid(usersInRole.getParent_guid());
//                            eduEmployeeEntity.setEmployeeName(usersInRole.getDisplay_name());
//                            eduEmployeeEntity.setEmployeeCode(usersInRole.getPerson_id());//员工号
////                            eduEmployeeEntity.setRandCode(usersInRole.getRank_code());
////                            eduEmployeeEntity.setRankName(usersInRole.getRank_name());
////                            eduEmployeeEntity.setMobile(usersInRole.getSyscontent1()); //手机号
////                            eduEmployeeEntity.setEmail(usersInRole.getE_mail());//邮箱
//                            eduEmployeeService.insert(eduEmployeeEntity);
//                        } else {
//                            employeeId = eduEmployeeEntity.getId();
//                            //已经有用户了，更新信息
//                            eduEmployeeEntity.setId(IdUtil.simpleUUID());
//                            eduEmployeeEntity.setH4aAllPathName(usersInRole.getAll_path_name());
//                            eduEmployeeEntity.setH4aUserGuid(usersInRole.getGuid());
//                            eduEmployeeEntity.setH4aViewGuid("");
//                            eduEmployeeEntity.setH4aDisplayName(usersInRole.getDisplay_name());
//                            eduEmployeeEntity.setH4aParentGuid(usersInRole.getParent_guid());
//                            eduEmployeeEntity.setEmployeeName(usersInRole.getDisplay_name());
//                            eduEmployeeEntity.setEmployeeCode(usersInRole.getPerson_id());//员工号
//                            eduEmployeeService.updateById(eduEmployeeEntity);
//                        }
//
//                        //查看是否已经有 当前角色和当前用户关联了
//                        EntityWrapper<EduSystemRolesEmployeeEntity> eduSystemRolesEmployeeEntityEntityWrapper = new EntityWrapper<>();
//                        eduSystemRolesEmployeeEntityEntityWrapper.eq("h4aGuid", usersInRole.getGuid());
//                        eduSystemRolesEmployeeEntityEntityWrapper.eq("roleCode", usersInRole.getRole_value());
//                        EduSystemRolesEmployeeEntity eduSystemRolesEmployeeEntity = eduSystemRolesEmployeeService.selectOne(eduSystemRolesEmployeeEntityEntityWrapper);
//                        if (null == eduSystemRolesEmployeeEntity) {
//                            //如果没有就新增条记录
//                            EduSystemRolesEmployeeEntity eduSystemRolesEmployeeEntityTmp = new EduSystemRolesEmployeeEntity();
//                            eduSystemRolesEmployeeEntityTmp.setEmployeeId(employeeId);
//                            eduSystemRolesEmployeeEntityTmp.setH4aGuid(usersInRole.getGuid());
//                            eduSystemRolesEmployeeEntityTmp.setRolesId(roleId);
//                            eduSystemRolesEmployeeEntityTmp.setRoleCode(usersInRole.getRole_value());
//                            eduSystemRolesEmployeeEntityTmp.setLastUpdateTime(new Date());
//                            eduSystemRolesEmployeeService.insert(eduSystemRolesEmployeeEntityTmp);
//                        }
//
//                        //TODO 要删除的暂不处理
//
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.getMessage();
//            e.printStackTrace();
//        }
//
//        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
//    }
//
//    /**
//     * 同步权限
//     */
//    @Test
//    void syncAuthority() {
//        Functions func = new Functions();
//        String groupCode = "";// 选择权限组来获取，不传就是获取所有
//        try {
//            FunctionsInApplication[] allFunctions = func.getBeanFunctionsInApplication(groupCode);
//            if (null != allFunctions) {
//                //如果获取到了权限列表，直接遍历
//                for (FunctionsInApplication funcItem : allFunctions) {
//                    //查询当前权限code是否已经存在
//                    EntityWrapper<EduSystemAuthorityEntity> filter = new EntityWrapper<EduSystemAuthorityEntity>();
//                    filter.eq("code", funcItem.getCode_name());
//                    EduSystemAuthorityEntity eduSystemAuthorityEntity = eduSystemAuthorityService.selectOne(filter);
//                    int _type; //权限的类型
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
//                        //如果不存在，新增
//                        eduSystemAuthorityEntity = new EduSystemAuthorityEntity();
//                        eduSystemAuthorityEntity.setId(IdUtil.simpleUUID());
//                        eduSystemAuthorityEntity.setAuthorityType(_type);
//                        eduSystemAuthorityEntity.setCode(funcItem.getCode_name());
//                        eduSystemAuthorityEntity.setName(funcItem.getFunc_name());
//                        eduSystemAuthorityEntity.setLastUpdateTime(new Date());
//                        eduSystemAuthorityService.insert(eduSystemAuthorityEntity);
//                    } else {
//                        //已经存在，更新
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
//     * 同步角色和权限的关联（前提是角色和权限已经同步完成）
//     */
//    @Test
//    void syncAuthorityAndRole() {
//        Functions func = new Functions();
//        //先获取所有的角色
//        List<EduSystemRolesEntity> eduSystemRolesEntities = eduSystemRolesService.selectList(null);
//        //遍历
//        for (EduSystemRolesEntity rolesEntity : eduSystemRolesEntities) {
//            //调用接口，查询当前角色下面的权限
//            try {
//                FunctionsInRoles[] funcList = func.getBeanFunctionsInRoles(rolesEntity.getCode());
//                if (funcList == null) {
//                    //当前角色下满没有权限，那就直接 下一位
//                    continue;
//                }
//                for (FunctionsInRoles funcItem : funcList) {
//                    //查询是否已经有这个角色和权限的关联了
//                    EntityWrapper<EduSystemAuthorityRoleEntity> _filter = new EntityWrapper<EduSystemAuthorityRoleEntity>();
//                    _filter.eq("authorityCode", funcItem.getCode_name());
//                    _filter.eq("roleCode", rolesEntity.getCode());
//                    EduSystemAuthorityRoleEntity eduSystemAuthorityRoleEntity = eduSystemAuthorityRoleService.selectOne(_filter);
//                    if (null == eduSystemAuthorityRoleEntity) {
//                        //准备数据，获取权限信息
//                        EduSystemAuthorityEntity authorityInfo = eduSystemAuthorityService.selectOne(new EntityWrapper<EduSystemAuthorityEntity>().eq("code", funcItem.getCode_name()));
//                        if (null == authorityInfo) {
//                            //如果已经要保存关联了，但是权限却没有，那就不管，下一位
//                            continue;
//                        }
//                        //如果不存在关联，那就新增一个
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
//     * 不同南京海关节点下的所有机构和用户
//     */
//    @Test
//    void syncOrgAndUser() {
//        this.DepartmentOrderNumber = 0;
//        String rootPath = "中国海关\\南京海关";
//        Organization organization = new Organization();
//        try {
//            //根节点的数据先保存着
//            OrganizationChildren rootOrg = new OrganizationChildren();
//            //获取根节点中的所有 用户，用户组，机构
//            OrganizationChildren[] beanOrganizationChildrenByAllPath = organization.getBeanOrganizationChildrenByAllPath(rootPath);
//            for (OrganizationChildren children : beanOrganizationChildrenByAllPath) {
//                //南京海关是跟节点，保存了就进入下一个
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
//        //先判断当前节点是个啥类型 USERS / GROUPS / ORGANIZATIONS
//        switch (orgInfo.getObjectclass()){
//            case "USERS":
//                //记录人员的id
//                String employee_id;
//                //如果当前节点是个用户，那就新增或更新用户
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
//                    employee.setMobile(orgInfo.getSyscontent1()); //手机号
//                    employee.setEmail(orgInfo.getE_mail());//邮箱
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
//                    eduEmployeeEntity.setMobile(orgInfo.getSyscontent1()); //手机号
//                    eduEmployeeEntity.setEmail(orgInfo.getE_mail());//邮箱
//                    eduEmployeeService.updateById(eduEmployeeEntity);
//                }
//                System.out.println(" ====> 插入用户 结束 "+orgInfo.getDisplay_name());
//                //保存了用户之后，再记录用户和组织的关系
//                EntityWrapper<EduDepartmentEmployeeEntity> relationFilter = new EntityWrapper<>();
//                String parentDepartmentCode = "";
//                switch (parentInfo.getObjectclass()){
//                    case "USERS":
//                        //理论上人员不会有下一级，这种情况就错了
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
//                //先把对应的参数取出来 - department
//                EduDepartmentEntity departmentData = eduDepartmentService.selectOne(
//                        new EntityWrapper<EduDepartmentEntity>().eq("departmentCode", parentDepartmentCode));
//                if(null == eduDepartmentEmployeeEntity){
//                    //如果还没有关联存在，那就新建一个关联
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
//                    //如果已经有关联存在，那就更新关联
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
//                System.out.println(" ====> 插入用户组 开始 "+orgInfo.getDisplay_name());
//                //如果当前节点是个用户组，更新或新增用户组，然后继续往下走
//                //用户组的上一级通常应该是一个“组织”，所以 parent_id 应该是 org_guid
//                saveAndUpdateDepartment(orgInfo, parentInfo.getOrg_guid());
//                //取当前节点的下一级，便利往下走
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
//                System.out.println(" =====> 插入用户组 结束 "+orgInfo.getDisplay_name());
//                break;
//            case "ORGANIZATIONS":
//                System.out.println(" ====> 插入机构 开始 "+orgInfo.getDisplay_name());
//                //如果当前节点是组织机构，那就保存组织机构，然后往下走
//                try {
//                    saveAndUpdateDepartment(orgInfo, parentInfo.getOrg_guid());
//                    //取当前节点的下一级，便利往下走
//                    OrganizationChildren[] orgSonItems = organization.getBeanOrganizationChildrenByAllPath(orgInfo.getAll_path_name());
//                    for(OrganizationChildren sonItem : orgSonItems){
//                        //取出来的第一个是自己，要跳过
//                        if(sonItem.getAll_path_name().equals(orgInfo.getAll_path_name())){
//                            continue;
//                        }
//                        processCurrentOrg(sonItem, orgInfo);
//                    }
//                }catch (Exception e){
//                    System.out.println(e.getMessage());
//                    throw e;
//                }
//                System.out.println(" ====> 插入机构 结束 "+orgInfo.getDisplay_name());
//                break;
//        }
//    }
//
//    //保存department
//    private void saveAndUpdateDepartment(OrganizationChildren orgInfo, String parentId){
//        this.DepartmentOrderNumber++;
//        //判断一下是直属关还是隶属关
//        int slaveCustoms = 0;//直属海关
//        int _type = 1;
//        String currentCode = orgInfo.getOrg_guid();
//        if(orgInfo.getObjectclass().equals("GROUPS")){
//            _type = 2;
//            slaveCustoms = 2; //其他
//            currentCode = orgInfo.getGroup_guid();
//        }else{
//            //如果是南京海关根节点，那就算直属海关吧
//            //是组织的情况下，根据完整路径中是否同时包含两个海关判断
//            //比如 中国海关\南京海关\xx海关\办公室
//            if(!orgInfo.getAll_path_name().equals("中国海关\\南京海关") && !orgInfo.getAll_path_name().substring("中国海关\\南京海关\\".length()).contains("海关\\")){
//                slaveCustoms = 1;//隶属海关
//            }
//        }
//
//        EntityWrapper<EduDepartmentEntity> filter = new EntityWrapper<EduDepartmentEntity>();
//        filter.eq("departmentCode", currentCode);
//        filter.eq("type", _type);
//        EduDepartmentEntity eduDepartmentEntity = eduDepartmentService.selectOne(filter);
//        if(null == eduDepartmentEntity){
//            //如果没有，新增一个
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
//            //已经有了，更新信息
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
//        System.out.println(" =========> 新增或更新 机构 完结 "+orgInfo.getDisplay_name());
//    }
//
//    private void saveUserAndGroup(UsersInGroups user, OrganizationChildren org){
//        //记录人员的id
//        String employee_id;
//        //如果当前节点是个用户，那就新增或更新用户
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
////        System.out.println(" ====> 插入用户 结束 "+user.getDisplay_name());
//        //保存了用户之后，再记录用户和组织的关系
//        EntityWrapper<EduDepartmentEmployeeEntity> relationFilter = new EntityWrapper<>();
//        String parentDepartmentCode = "";
//        switch (org.getObjectclass()){
//            case "USERS":
//                //理论上人员不会有下一级，这种情况就错了
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
//        //先把对应的参数取出来 - department
//        EduDepartmentEntity departmentData = eduDepartmentService.selectOne(
//                new EntityWrapper<EduDepartmentEntity>().eq("departmentCode", parentDepartmentCode));
//        if(null == eduDepartmentEmployeeEntity){
//            //如果还没有关联存在，那就新建一个关联
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
//            //如果已经有关联存在，那就更新关联
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
