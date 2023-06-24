package com.frankzhou.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.frankzhou.community.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 针对user表的数据库操作
 * @date 2023-06-18
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




