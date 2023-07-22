package com.frankzhou.community.common.util.oss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 读取Oss的配置文件
 * @date 2023-07-22
 */
@Data
@ConfigurationProperties(prefix = "oss")
public class OssProperties {

    /**
     * 是否开启对象存储
     */
    Boolean enabled;

    /**
     * 对象存储服务类型
     */
    OssType type;

    /**
     * Oss访问端口，集群时必须提供统一入口
     */
    String endpoint;

    /**
     * 用户名
     */
    String accessKey;

    /**
     * 密码
     */
    String secretKey;

    /**
     * 默认存储桶
     */
    String defaultBucketName;
}
