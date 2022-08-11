package com.mohe.nanjinghaiguaneducation.common.crontab;

import cn.gov.customs.casp.config.ConfigPathUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.modules.common.dto.EduTreeDto;
import com.mohe.nanjinghaiguaneducation.modules.performance.service.EduOnlinePerformanceImportDetailService;
import com.mohe.nanjinghaiguaneducation.modules.performance.service.EduStudyPerformanceImportDetailService;
import com.mohe.nanjinghaiguaneducation.modules.queue.entity.EduQueueEntity;
import com.mohe.nanjinghaiguaneducation.modules.queue.service.EduQueueService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.ObjectUtils;

import javax.naming.spi.DirStateFactory;
import javax.xml.transform.Result;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class MainPlanScheduleRun {

    @Autowired
    private EduStudyPerformanceImportDetailService eduStudyPerformanceImportDetailService;

    @Autowired
    private EduOnlinePerformanceImportDetailService eduOnlinePerformanceImportDetailService;
    @Autowired
    private EduQueueService eduQueueService;

    //秒分时日月周年
    @Scheduled(cron = "* * 2 * * ?") //先配置成每天凌晨2点启动
    public void RunJob(){
//        System.out.println("12313132123123123132123");
        // 先同步权限列表 （其实时菜单列表）
        System.out.println(" ================================================");
        System.out.println(" ==========> 定时任务执行：先同步权限列表 ============");
        System.out.println(" ================================================");
        H4ASyncAuthority h4ASyncAuthority = new H4ASyncAuthority();
        h4ASyncAuthority.syncAuthority();

        //同步角色和权限的关联
        System.out.println(" ================================================");
        System.out.println(" ==========> 定时任务执行：同步角色和权限的关联 ========");
        System.out.println(" ================================================");
        h4ASyncAuthority.syncAuthorityAndRole();
    }

    @Scheduled(fixedDelay = 10*60*1000)
    public void RunEmployee(){
        EduQueueEntity entity = eduQueueService.selectOne(new EntityWrapper<EduQueueEntity>()
                .eq("object", 0).eq("type", 0).eq("status", 0).orderBy("id", false).last("limit 1"));
        if (!ObjectUtils.isEmpty(entity)){
            entity.setStatus(1);
            eduQueueService.updateById(entity);
            System.out.println(" ================================================");
            System.out.println(" ======> 定时任务执行：同步部门和部门下的所有用户 =======");
            System.out.println(" ================================================");
            H4ASyncDepartmentAndEmployee h4ASyncDepartmentAndEmployee = new H4ASyncDepartmentAndEmployee();
            h4ASyncDepartmentAndEmployee.syncOrgAndUser();
            entity.setStatus(2);
            eduQueueService.updateById(entity);
        }
    }

    //秒分时日月周年
    @Scheduled(fixedDelay = 1000*60*30)
    public void RunJobFast(){
        H4ASyncAuthority h4ASyncAuthority = new H4ASyncAuthority();

        // 再同步角色 列表
        System.out.println(" ================================================");
        System.out.println(" ============> 定时任务执行：同步角色列表 ============");
        System.out.println(" ================================================");
        String configPathUtil = ConfigPathUtil.getConfigPathUtil();
        String sdkConfigPath = ConfigPathUtil.getSdkConfigPath();
        System.out.println("configPathUtil:"+configPathUtil);
        System.out.println("sdkConfigPath:"+sdkConfigPath);

        h4ASyncAuthority.syncRoleAndUsers();

    }

    @Scheduled(fixedDelay = 10*60*1000)
    public void RunStudyImport(){
        System.out.println(" ================================================");
        System.out.println(" =====> 定时任务执行：处理绩效考核-学时学分导入数据 =====");
        System.out.println(" ================================================");
        eduStudyPerformanceImportDetailService.processImportStudy();
    }

    @Scheduled(fixedDelay = 10*60*1000)
    public void RunOnlineImport(){
        System.out.println(" ================================================");
        System.out.println(" ====> 定时任务执行：处理绩效考核-网络培训班导入数据 ====");
        System.out.println(" ================================================");
        eduOnlinePerformanceImportDetailService.processImportOnline();
    }

    @Scheduled(fixedDelay = 15*60*1000)
    public void RunStudyDetailUpdate(){
        System.out.println(" ================================================");
        System.out.println(" =====> 定时任务执行：处理绩效考核-学时学分明细更新 =====");
        System.out.println(" ================================================");
        eduStudyPerformanceImportDetailService.processImportStudyDetail();
    }

    @Scheduled(fixedDelay = 15*60*1000)
    public void RunOnlineDetailUpdate(){
        System.out.println(" ================================================");
        System.out.println(" ====> 定时任务执行：处理绩效考核-网络培训班明细更新 ====");
        System.out.println(" ================================================");
        eduOnlinePerformanceImportDetailService.processImportOnlineDetail();
    }


}
