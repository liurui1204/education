package com.mohe.nanjinghaiguaneducation.common.crontab;

import cn.gov.customs.casp.sdk.h4a.entity.OrganizationChildren;
import cn.gov.customs.casp.sdk.h4a.entity.UsersInGroups;
import cn.gov.customs.casp.sdk.h4a.ogu.ws.IOguReaderGetOrganizationChildrenCupaaFaultArgsFaultFaultMessage;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mohe.nanjinghaiguaneducation.common.h4a.Organization;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduDepartmentEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduDepartmentEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduDepartmentEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduDepartmentService;
import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.employee.service.EduEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

public class H4ASyncDepartmentAndEmployee {

    private Integer DepartmentOrderNumber;

//    @Autowired
//    private EduDepartmentService eduDepartmentService;
//    @Autowired
//    private EduDepartmentEmployeeService eduDepartmentEmployeeService;
//    @Autowired
//    private EduEmployeeService eduEmployeeService;

//    void SyncDepartmentSchedule(){
//        // 先同步权限列表 （其实时菜单列表）
//        System.out.println(" ================================================");
//        System.out.println(" ======> 定时任务执行：同步部门和部门下的所有用户 =======");
//        System.out.println(" ================================================");
//        this.syncOrgAndUser();
//    }

    /**
     * 不同南京海关节点下的所有机构和用户
     */
    void syncOrgAndUser() {
        this.DepartmentOrderNumber = 0;
        String rootPath = "中国海关\\南京海关";
        Organization organization = new Organization();
        try {
            //根节点的数据先保存着
            OrganizationChildren rootOrg = new OrganizationChildren();
            //获取根节点中的所有 用户，用户组，机构
            OrganizationChildren[] beanOrganizationChildrenByAllPath = organization.getBeanOrganizationChildrenByAllPath(rootPath);
            for (OrganizationChildren children : beanOrganizationChildrenByAllPath) {
                //南京海关是跟节点，保存了就进入下一个
                if (children.getObjectclass().equals("ORGANIZATIONS") && children.getAll_path_name().equals(rootPath)) {
                    saveAndUpdateDepartment(children, "");
                    rootOrg = children;
                } else {
                    System.out.println(" => ROOT_PATH: " + rootPath + "  SON_PATH: " + children.getDisplay_name());
                    processCurrentOrg(children, rootOrg);
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void processCurrentOrg(OrganizationChildren orgInfo, OrganizationChildren parentInfo)
            throws IOguReaderGetOrganizationChildrenCupaaFaultArgsFaultFaultMessage {
        Organization organization = new Organization();
        //先判断当前节点是个啥类型 USERS / GROUPS / ORGANIZATIONS
        switch (orgInfo.getObjectclass()){
            case "USERS":
                //sideLine 是否主职（0:主职；1： 兼职）
                if(orgInfo.getSideline().equals("1")){
                    //如果当前机构中查出来的职位不是主职，那就不用保存了（不用保存用户信息，也不用保存部门和用户的对应关系）
                    System.out.println(" ====> 插入用户 跳过，因为是兼职 ");
                    return;
                }
                //记录人员的id
                String employee_id;
                //如果当前节点是个用户，那就新增或更新用户
                EntityWrapper<EduEmployeeEntity> filter = new EntityWrapper<EduEmployeeEntity>();
                filter.eq("h4aUserGuid", orgInfo.getUser_guid());
                EduEmployeeService eduEmployeeService = (EduEmployeeService)ApplicationContextUtil.getBean("eduEmployeeService");
                EduEmployeeEntity eduEmployeeEntity = eduEmployeeService.selectOne(filter);
                if(null == eduEmployeeEntity){
                    EduEmployeeEntity employee = new EduEmployeeEntity();
                    employee_id = IdUtil.simpleUUID();
                    employee.setEmployeeCode(orgInfo.getUser_guid());
                    employee.setH4aUserGuid(orgInfo.getUser_guid());
                    employee.setEmployeeName(orgInfo.getDisplay_name());
                    employee.setIsEnable(1);
                    employee.setH4aViewGuid(orgInfo.getView_guid());
                    employee.setH4aAllPathName(orgInfo.getAll_path_name());
                    employee.setH4aParentGuid(orgInfo.getParent_guid());
                    employee.setH4aDisplayName(orgInfo.getDisplay_name());
                    employee.setEmployeeCode(orgInfo.getPerson_id());
                    employee.setRankCode(orgInfo.getRank_code());
                    employee.setRankName(orgInfo.getRank_name());
                    employee.setMobile(orgInfo.getSyscontent1()); //手机号
                    employee.setEmail(orgInfo.getE_mail());//邮箱
//                    employee.setGender(orgInfo.get);
                    eduEmployeeService.insert(employee);
                }else{
                    employee_id = eduEmployeeEntity.getId();
                    eduEmployeeEntity.setEmployeeCode(orgInfo.getUser_guid());
                    eduEmployeeEntity.setH4aUserGuid(orgInfo.getUser_guid());
                    eduEmployeeEntity.setEmployeeName(orgInfo.getDisplay_name());
                    eduEmployeeEntity.setIsEnable(1);
                    eduEmployeeEntity.setH4aViewGuid(orgInfo.getView_guid());
                    eduEmployeeEntity.setH4aAllPathName(orgInfo.getAll_path_name());
                    eduEmployeeEntity.setH4aParentGuid(orgInfo.getParent_guid());
                    eduEmployeeEntity.setH4aDisplayName(orgInfo.getDisplay_name());
                    eduEmployeeEntity.setEmployeeCode(orgInfo.getPerson_id());
                    eduEmployeeEntity.setRankCode(orgInfo.getRank_code());
                    eduEmployeeEntity.setRankName(orgInfo.getRank_name());
                    eduEmployeeEntity.setMobile(orgInfo.getSyscontent1()); //手机号
                    eduEmployeeEntity.setEmail(orgInfo.getE_mail());//邮箱
                    eduEmployeeService.updateById(eduEmployeeEntity);
                }
                System.out.println(" ====> 插入用户 结束 "+orgInfo.getDisplay_name());
                //保存了用户之后，再记录用户和组织的关系
                EntityWrapper<EduDepartmentEmployeeEntity> relationFilter = new EntityWrapper<>();
                String parentDepartmentCode = "";
                switch (parentInfo.getObjectclass()){
                    case "USERS":
                        //理论上人员不会有下一级，这种情况就错了
                        parentDepartmentCode = parentInfo.getUser_guid();
                        break;
                    case "GROUPS":
                        parentDepartmentCode = parentInfo.getGroup_guid();
                        break;
                    case "ORGANIZATIONS":
                        parentDepartmentCode = parentInfo.getOrg_guid();
                        break;
                }
                relationFilter.eq("departmentCode", parentDepartmentCode);
                relationFilter.eq("h4aGuid", orgInfo.getUser_guid());
                EduDepartmentEmployeeService eduDepartmentEmployeeService = (EduDepartmentEmployeeService)ApplicationContextUtil.getBean("eduDepartmentEmployeeService");
                EduDepartmentEmployeeEntity eduDepartmentEmployeeEntity = eduDepartmentEmployeeService.selectOne(relationFilter);
                //先把对应的参数取出来 - department
                EduDepartmentService eduDepartmentService = (EduDepartmentService)ApplicationContextUtil.getBean("eduDepartmentService");
                EduDepartmentEntity departmentData = eduDepartmentService.selectOne(
                        new EntityWrapper<EduDepartmentEntity>().eq("departmentCode", parentDepartmentCode));
                if(null == eduDepartmentEmployeeEntity){
                    //如果还没有关联存在，那就新建一个关联
                    EduDepartmentEmployeeEntity relationData = new EduDepartmentEmployeeEntity();
                    relationData.setId(IdUtil.simpleUUID());
                    relationData.setDepartmentId(departmentData.getId());
                    relationData.setDepartmentCode(departmentData.getDepartmentCode());
                    relationData.setEmployee_id(employee_id);
                    relationData.setH4aGuid(orgInfo.getUser_guid());
                    relationData.setDelete(0);
                    relationData.setLast_update_time(new Date());
                    relationData.setIsMain(orgInfo.getSideline());
                    eduDepartmentEmployeeService.insert(relationData);
                }else{
                    //如果已经有关联存在，那就更新关联
                    eduDepartmentEmployeeEntity.setId(IdUtil.simpleUUID());
                    eduDepartmentEmployeeEntity.setDepartmentId(departmentData.getId());
                    eduDepartmentEmployeeEntity.setDepartmentCode(departmentData.getDepartmentCode());
                    eduDepartmentEmployeeEntity.setEmployee_id(employee_id);
                    eduDepartmentEmployeeEntity.setH4aGuid(orgInfo.getUser_guid());
                    eduDepartmentEmployeeEntity.setDelete(0);
                    eduDepartmentEmployeeEntity.setLast_update_time(new Date());
                    eduDepartmentEmployeeEntity.setIsMain(orgInfo.getSideline());
                    eduDepartmentEmployeeService.updateById(eduDepartmentEmployeeEntity);
                }
                break;
            case "GROUPS":
                System.out.println(" ====> 插入用户组 开始 "+orgInfo.getDisplay_name());
                //如果当前节点是个用户组，更新或新增用户组，然后继续往下走
                //用户组的上一级通常应该是一个“组织”，所以 parent_id 应该是 org_guid
                saveAndUpdateDepartment(orgInfo, parentInfo.getOrg_guid());
                //取当前节点的下一级，便利往下走
                try {
                    UsersInGroups[] users = organization.getBeanUsersInGroupsByAllPath(orgInfo.getAll_path_name());
                    if(null != users){
                        for(UsersInGroups user : users){
                            saveUserAndGroup(user, orgInfo);
                        }
                    }
                }catch (Exception ee){
                    System.out.println(ee.getMessage());
//                    throw ee;
                }
                System.out.println(" =====> 插入用户组 结束 "+orgInfo.getDisplay_name());
                break;
            case "ORGANIZATIONS":
                System.out.println(" ====> 插入机构 开始 "+orgInfo.getDisplay_name());
                //如果当前节点是组织机构，那就保存组织机构，然后往下走
                try {
                    saveAndUpdateDepartment(orgInfo, parentInfo.getOrg_guid());
                    //取当前节点的下一级，便利往下走
                    OrganizationChildren[] orgSonItems = organization.getBeanOrganizationChildrenByAllPath(orgInfo.getAll_path_name());
                    for(OrganizationChildren sonItem : orgSonItems){
                        //取出来的第一个是自己，要跳过
                        if(sonItem.getAll_path_name().equals(orgInfo.getAll_path_name())){
                            continue;
                        }
                        processCurrentOrg(sonItem, orgInfo);
                    }
                }catch (Exception e){
                    System.out.println(e.getMessage());
                    throw e;
                }
                System.out.println(" ====> 插入机构 结束 "+orgInfo.getDisplay_name());
                break;
        }
    }

    //保存department
    private void saveAndUpdateDepartment(OrganizationChildren orgInfo, String parentId){
        this.DepartmentOrderNumber++;
        //判断一下是直属关还是隶属关
        int slaveCustoms = 0;//直属海关
        int _type = 1;
        String currentCode = orgInfo.getOrg_guid();
        if(orgInfo.getObjectclass().equals("GROUPS")){
            _type = 2;
            slaveCustoms = 2; //其他
            currentCode = orgInfo.getGroup_guid();
        }else{
            //如果是南京海关根节点，那就算直属海关吧
            //是组织的情况下，根据完整路径中是否同时包含两个海关判断
            //比如 中国海关\南京海关\xx海关\办公室
            if(!orgInfo.getAll_path_name().equals("中国海关\\南京海关") && !orgInfo.getAll_path_name().substring("中国海关\\南京海关\\".length()).contains("海关\\")){
                slaveCustoms = 1;//隶属海关
            }
        }

        EntityWrapper<EduDepartmentEntity> filter = new EntityWrapper<EduDepartmentEntity>();
        filter.eq("departmentCode", currentCode);
        filter.eq("type", _type);
        EduDepartmentService eduDepartmentService = (EduDepartmentService) ApplicationContextUtil.getBean("eduDepartmentService");
        EduDepartmentEntity eduDepartmentEntity = eduDepartmentService.selectOne(filter);
        if(null == eduDepartmentEntity){
            //如果没有，新增一个
            EduDepartmentEntity  department = new EduDepartmentEntity();
            department.setId(IdUtil.simpleUUID());
            department.setDepartmentCode(currentCode);
            department.setDepartmentName(orgInfo.getDisplay_name());
            department.setParentId(parentId);
            department.setCreateTime(new Date());
            department.setIsEnable(1);
            department.setStatus(slaveCustoms);
            department.setDepartmentAllPath(orgInfo.getAll_path_name());
            department.setType(_type);
            department.setOrder(this.DepartmentOrderNumber);
            eduDepartmentService.insert(department);
        }else{
            //已经有了，更新信息
//            eduDepartmentEntity.setId(IdUtil.simpleUUID());
            eduDepartmentEntity.setDepartmentCode(currentCode);
            eduDepartmentEntity.setDepartmentName(orgInfo.getDisplay_name());
            eduDepartmentEntity.setParentId(parentId);
            eduDepartmentEntity.setCreateTime(new Date());
            eduDepartmentEntity.setUpdateTime(new Date());
            eduDepartmentEntity.setIsEnable(1);
            eduDepartmentEntity.setStatus(slaveCustoms);
            eduDepartmentEntity.setDepartmentAllPath(orgInfo.getAll_path_name());
            eduDepartmentEntity.setType(_type);
            eduDepartmentEntity.setOrder(this.DepartmentOrderNumber);
            eduDepartmentService.updateById(eduDepartmentEntity);
        }
        System.out.println(" =========> 新增或更新 机构 完结 "+orgInfo.getDisplay_name());
    }

    private void saveUserAndGroup(UsersInGroups user, OrganizationChildren org){
        //记录人员的id
        String employee_id;
        //如果当前节点是个用户，那就新增或更新用户
        EntityWrapper<EduEmployeeEntity> filter = new EntityWrapper<EduEmployeeEntity>();
        filter.eq("h4aUserGuid", user.getUser_guid());
        EduEmployeeService eduEmployeeService = (EduEmployeeService) ApplicationContextUtil.getBean("eduEmployeeService");
        EduEmployeeEntity eduEmployeeEntity = eduEmployeeService.selectOne(filter);
        if(null == eduEmployeeEntity){
            EduEmployeeEntity employee = new EduEmployeeEntity();
            employee_id = IdUtil.simpleUUID();
            employee.setEmployeeCode(user.getPerson_id());
            employee.setH4aUserGuid(user.getUser_guid());
            employee.setEmployeeName(user.getDisplay_name());
            employee.setIsEnable(1);
            employee.setH4aViewGuid(user.getView_guid());
            employee.setH4aAllPathName(user.getAll_path_name());
            employee.setH4aParentGuid(user.getParent_guid());
            employee.setH4aDisplayName(user.getDisplay_name());
            employee.setRankName(user.getRank_name());
            employee.setRankCode(user.getRank_code());
            eduEmployeeService.insert(employee);
        }else{
            employee_id = eduEmployeeEntity.getId();
            eduEmployeeEntity.setEmployeeCode(user.getPerson_id());
            eduEmployeeEntity.setH4aUserGuid(user.getUser_guid());
            eduEmployeeEntity.setEmployeeName(user.getDisplay_name());
            eduEmployeeEntity.setIsEnable(1);
            eduEmployeeEntity.setH4aViewGuid(user.getView_guid());
            eduEmployeeEntity.setH4aAllPathName(user.getAll_path_name());
            eduEmployeeEntity.setH4aParentGuid(user.getParent_guid());
            eduEmployeeEntity.setH4aDisplayName(user.getDisplay_name());
            eduEmployeeEntity.setRankCode(user.getRank_code());
            eduEmployeeEntity.setRankName(user.getRank_name());
            eduEmployeeService.updateById(eduEmployeeEntity);
        }
//        System.out.println(" ====> 插入用户 结束 "+user.getDisplay_name());
        //保存了用户之后，再记录用户和组织的关系
        EntityWrapper<EduDepartmentEmployeeEntity> relationFilter = new EntityWrapper<>();
        String parentDepartmentCode = "";
        switch (org.getObjectclass()){
            case "USERS":
                //理论上人员不会有下一级，这种情况就错了
                parentDepartmentCode = org.getUser_guid();
                break;
            case "GROUPS":
                parentDepartmentCode = org.getGroup_guid();
                break;
            case "ORGANIZATIONS":
                parentDepartmentCode = org.getOrg_guid();
                break;
        }
        relationFilter.eq("departmentCode", parentDepartmentCode);
        relationFilter.eq("h4aGuid", user.getUser_guid());
        EduDepartmentEmployeeService eduDepartmentEmployeeService = (EduDepartmentEmployeeService) ApplicationContextUtil.getBean("eduDepartmentEmployeeService");
        EduDepartmentEmployeeEntity eduDepartmentEmployeeEntity = eduDepartmentEmployeeService.selectOne(relationFilter);
        //先把对应的参数取出来 - department
        EduDepartmentService eduDepartmentService = (EduDepartmentService) ApplicationContextUtil.getBean("eduDepartmentService");
        EduDepartmentEntity departmentData = eduDepartmentService.selectOne(
                new EntityWrapper<EduDepartmentEntity>().eq("departmentCode", parentDepartmentCode));
        if(null == eduDepartmentEmployeeEntity){
            //如果还没有关联存在，那就新建一个关联
            EduDepartmentEmployeeEntity relationData = new EduDepartmentEmployeeEntity();
            relationData.setId(IdUtil.simpleUUID());
            relationData.setDepartmentId(departmentData.getId());
            relationData.setDepartmentCode(departmentData.getDepartmentCode());
            relationData.setEmployee_id(employee_id);
            relationData.setH4aGuid(user.getUser_guid());
            relationData.setDelete(0);
            relationData.setLast_update_time(new Date());
            eduDepartmentEmployeeService.insert(relationData);
        }else{
            //如果已经有关联存在，那就更新关联
            eduDepartmentEmployeeEntity.setId(IdUtil.simpleUUID());
            eduDepartmentEmployeeEntity.setDepartmentId(departmentData.getId());
            eduDepartmentEmployeeEntity.setDepartmentCode(departmentData.getDepartmentCode());
            eduDepartmentEmployeeEntity.setEmployee_id(employee_id);
            eduDepartmentEmployeeEntity.setH4aGuid(user.getUser_guid());
            eduDepartmentEmployeeEntity.setDelete(0);
            eduDepartmentEmployeeEntity.setLast_update_time(new Date());
            eduDepartmentEmployeeService.updateById(eduDepartmentEmployeeEntity);
        }
    }

}
