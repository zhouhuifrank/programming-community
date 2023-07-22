package com.frankzhou.community.task;

import com.frankzhou.community.common.util.oss.MinioTemplate;
import com.frankzhou.community.mapper.OssFileMapper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 对象存储文件清理定时任务
 * @date 2023-07-22
 */
@EnableScheduling
@Component
public class OssFileClearTask {

    @Resource
    private OssFileMapper ossFileMapper;

    @Resource
    private MinioTemplate minioTemplate;

    @Scheduled(cron = "0 0 0/1 * * ?")
    public void clearExpireFile() {

    }

    @Scheduled(cron = "0 10 0/1 * * ?")
    public void clearOssFile() {

    }
}
