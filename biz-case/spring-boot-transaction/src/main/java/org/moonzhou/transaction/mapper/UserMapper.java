package org.moonzhou.transaction.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.moonzhou.transaction.entity.User;

/**
 * @author moon zhou
 * @since 2023-09-01
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
