package com.mohe.nanjinghaiguaneducation.common.utils;

import cn.gov.customs.casp.sdk.h4a.accredit.ws.IAccreditReaderGetRolesCupaaFaultArgsFaultFaultMessage;
import cn.gov.customs.casp.sdk.h4a.entity.Roles;
import cn.gov.customs.casp.sdk.h4a.entity.UsersInRoles;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.mohe.nanjinghaiguaneducation.common.h4a.Users;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemRolesEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemRolesEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemRolesEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemRolesService;
import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.employee.service.EduEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


//@Configuration
//@EnableScheduling
public class StartTime {
    @Autowired
    private EduSystemRolesService eduSystemRolesService;
    @Autowired
    private EduSystemRolesEmployeeService eduSystemRolesEmployeeService;
    @Autowired
    private EduEmployeeService eduEmployeeService;

//    @Scheduled(cron = "0 10 23 * * ?")
//    private void configureTasks() {
//        Users users = new Users();
//        try {
//            Roles[] beanRoles = users.getBeanRoles();
//            for (Roles beanRole : beanRoles) {
//                EntityWrapper<EduSystemRolesEntity> entityEntityWrapper = new EntityWrapper<>();
//                Wrapper<EduSystemRolesEntity> eduSystemRolesEntityWrapper = entityEntityWrapper.eq("code", beanRole.getCode_name());
//                EduSystemRolesEntity systemRolesEntity = eduSystemRolesService.selectOne(eduSystemRolesEntityWrapper);
//                if (!ObjectUtils.isEmpty(systemRolesEntity)){
//                    systemRolesEntity.setName(beanRole.getRole_name());
//                    systemRolesEntity.setLastUpdateTime(new Date());
//                    systemRolesEntity.setMemo(beanRole.getRole_description());
//                    eduSystemRolesService.updateById(systemRolesEntity);
//                    continue;
//                }
//                EduSystemRolesEntity eduSystemRolesEntity = new EduSystemRolesEntity();
//                eduSystemRolesEntity.setCode(beanRole.getCode_name());
//                eduSystemRolesEntity.setName(beanRole.getRole_name());
//                eduSystemRolesEntity.setLastUpdateTime(new Date());
//                eduSystemRolesEntity.setMemo(beanRole.getRole_description());
//                String id = IdUtil.simpleUUID();
//                eduSystemRolesEntity.setId(id);
//                eduSystemRolesService.insert(eduSystemRolesEntity);
//                UsersInRoles[] usersInRoles = users.getUsersInRoles(beanRole.getCode_name());
//                for (UsersInRoles usersInRole : usersInRoles) {
//                    EntityWrapper<EduSystemRolesEntity> entityWrapper = new EntityWrapper<>();
//                    entityWrapper.eq("code", usersInRole.getRole_value());
//                    EduSystemRolesEntity entity = eduSystemRolesService.selectOne(entityWrapper);
//                    EntityWrapper<EduSystemRolesEmployeeEntity>eduSystemRolesEmployeeEntityEntityWrapper = new EntityWrapper<>();
//                    eduSystemRolesEmployeeEntityEntityWrapper.eq("h4aGuid",usersInRole.getUser_guid());
//                    EduSystemRolesEmployeeEntity eduSystemRolesEmployeeEntity = eduSystemRolesEmployeeService.selectOne(eduSystemRolesEmployeeEntityEntityWrapper);
//                    EntityWrapper<EduEmployeeEntity>eduEmployeeEntityEntityWrapper = new EntityWrapper<>();
//                    eduEmployeeEntityEntityWrapper.eq("h4aUserGuid",usersInRole.getUser_guid());
//                    EduEmployeeEntity eduEmployeeEntity = eduEmployeeService.selectOne(eduEmployeeEntityEntityWrapper);
//                    if (!ObjectUtils.isEmpty(eduSystemRolesEmployeeEntity)){
//                        eduSystemRolesEmployeeEntity.setRolesId(entity.getId());
//                        eduSystemRolesEmployeeEntity.setRoleCode(usersInRole.getRole_value());
//                        eduSystemRolesEmployeeEntity.setLastUpdateTime(new Date());
//                        eduSystemRolesEmployeeEntity.setVersion(1);
//                        eduSystemRolesEmployeeEntity.setEmployeeId(eduEmployeeEntity.getId());
//                        eduSystemRolesEmployeeService.updateById(eduSystemRolesEmployeeEntity);
//                        continue;
//                    }
//                    EduSystemRolesEmployeeEntity eduSystemRolesEmployee = new EduSystemRolesEmployeeEntity();
//                    eduSystemRolesEmployee.setRolesId(entity.getId());
//                    eduSystemRolesEmployee.setEmployeeId(eduEmployeeEntity.getId());
//                    eduSystemRolesEmployee.setVersion(1);
//                    eduSystemRolesEmployee.setH4aGuid(usersInRole.getUser_guid());
//                    String emId = IdUtil.simpleUUID();
//                    eduSystemRolesEmployee.setId(emId);
//                    eduSystemRolesEmployee.setRoleCode(usersInRole.getRole_value());
//                    eduSystemRolesEmployee.setLastUpdateTime(new Date());
//                    eduSystemRolesEmployee.setDelete(0);
//                    eduSystemRolesEmployeeService.insert(eduSystemRolesEmployee);
//                }
//
//            }
//            EntityWrapper<EduSystemRolesEmployeeEntity>eduSystemRolesEmployeeEntityEntityWrapper = new EntityWrapper<>();
//            eduSystemRolesEmployeeEntityEntityWrapper.eq("version",0);
//            List<EduSystemRolesEmployeeEntity> eduSystemRolesEmployeeEntities = eduSystemRolesEmployeeService.selectList(eduSystemRolesEmployeeEntityEntityWrapper);
//            for (EduSystemRolesEmployeeEntity eduSystemRolesEmployeeEntity : eduSystemRolesEmployeeEntities) {
//                eduSystemRolesEmployeeEntity.setDelete(1);
//                eduSystemRolesEmployeeService.updateById(eduSystemRolesEmployeeEntity);
//            }
//            EduSystemRolesEmployeeEntity entity = new EduSystemRolesEmployeeEntity();
//            entity.setVersion(0);
//            eduSystemRolesEmployeeService.update(entity,new EntityWrapper<EduSystemRolesEmployeeEntity>());
//        }catch (Exception e){
//            e.getMessage();
//        }
//
//        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
//    }


}
