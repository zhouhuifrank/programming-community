package com.frankzhou.community.config;

import com.frankzhou.community.common.factory.MyThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 统一管理线程池
 * @date 2023-06-11
 */
@Configuration
@EnableAsync
public class ThreadPoolConfig implements AsyncConfigurer {
    /**
     * 项目通用线程池
     */
    public static final String COMMON_EXECUTOR = "commonExecutor";

    /**
     * 消息发送线程池
     */
    public static final String MESSAGE_EXECUTOR = "messageExecutor";

    /**
     * 定时任务专用线程池
     */
    public static final String TASK_EXECUTOR = "taskExecutor";

    @Override
    public Executor getAsyncExecutor() {
        return commonExecutor();
    }

    @Primary
    @Bean(COMMON_EXECUTOR)
    public ThreadPoolTaskExecutor commonExecutor() {
        // 设置线程池参数
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(200);
        executor.setThreadNamePrefix("common-executor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setThreadFactory(new MyThreadFactory(executor));
        executor.setAwaitTerminationMillis(10000);
        executor.initialize();
        return executor;
    }

    @Bean(MESSAGE_EXECUTOR)
    public ThreadPoolTaskExecutor messageExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(100);
        executor.setMaxPoolSize(100);
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix("message-executor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        executor.setThreadFactory(new MyThreadFactory(executor));
        executor.setAwaitTerminationMillis(10000);
        executor.initialize();
        return executor;
    }

    @Bean(TASK_EXECUTOR)
    public ThreadPoolTaskExecutor cacheRebuildExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(30);
        executor.setMaxPoolSize(30);
        executor.setQueueCapacity(200);
        executor.setThreadNamePrefix("task-executor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        executor.setThreadFactory(new MyThreadFactory(executor));
        executor.setAwaitTerminationMillis(10000);
        executor.initialize();
        return executor;
    }
}
