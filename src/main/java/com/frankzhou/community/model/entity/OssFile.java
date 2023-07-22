package com.frankzhou.community.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.frankzhou.community.common.base.BaseEntity;
import lombok.Data;


/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 对象存储文件信息
 * @date 2023-06-18
 */
@Data
@TableName(value ="oss_file")
public class OssFile extends BaseEntity implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 上传人id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 关联业务类型
     */
    @TableField(value = "business_type")
    private Integer businessType;

    /**
     * 关联业务id
     */
    @TableField(value = "business_id")
    private Long businessId;

    /**
     * 关联业务编号
     */
    @TableField(value = "business_no")
    private String businessNo;

    /**
     * 文件名称
     */
    @TableField(value = "file_name")
    private String fileName;

    /**
     * 文件类型
     */
    @TableField(value = "file_type")
    private Integer fileType;

    /**
     * 文件目录
     */
    @TableField(value = "file_category")
    private String fileCategory;

    /**
     * 保留天数
     */
    @TableField(value = "reserve_day")
    private Integer reserveDay;

    /**
     * 存储桶目录
     */
    @TableField(value = "bucket")
    private String bucket;

    /**
     * 文件路径(上传OSS时的文件名)
     */
    @TableField(value = "file_path")
    private String filePath;

    /**
     * 文件id(md5码)
     */
    @TableField(value = "file_id")
    private String fileId;

    /**
     * 文件访问路径
     */
    @TableField(value = "file_url")
    private String fileUrl;

}