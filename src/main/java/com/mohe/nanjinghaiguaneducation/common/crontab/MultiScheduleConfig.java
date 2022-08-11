package com.mohe.nanjinghaiguaneducation.common.crontab;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;

@Configuration
@EnableScheduling
public class MultiScheduleConfig implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("scheduled@");// 设置定时任务线程名称的前缀
        int corePoolSize  = 10; // 设置定时任务的核心线程数
        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(corePoolSize,executor));
    }
}
