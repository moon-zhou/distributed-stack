package org.moonzhou.transaction.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.moonzhou.transaction.common.exception.NotFoundException;
import org.moonzhou.transaction.dto.UserDTO;
import org.moonzhou.transaction.entity.User;
import org.moonzhou.transaction.mapper.UserMapper;
import org.moonzhou.transaction.param.UserParam;
import org.moonzhou.transaction.param.UserQueryParam;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author moon zhou
 * @since 2023-09-01
 */
@Service
@Transactional
public class UserService extends ServiceImpl<UserMapper, User> implements IService<User> {

    public UserDTO getById(Long id) {

        return super.getOptById(id)
                .map(UserDTO::new)
                .orElseThrow(() -> new NotFoundException("User", id));
    }

    public Long save(UserParam param) {
        User entity = new User();
        BeanUtils.copyProperties(param, entity);
        entity.setUserNo(UUID.randomUUID().toString()); // 实际业务中，可以实现对应生成器，加入机房信息，机器信息等，该核心字段可用于数据分库分表的字段
        super.save(entity);
        return entity.getId();
    }

    public Long saveFail(UserParam param) {
        User entity = new User();
        BeanUtils.copyProperties(param, entity);
        entity.setUserNo(UUID.randomUUID().toString()); // 实际业务中，可以实现对应生成器，加入机房信息，机器信息等，该核心字段可用于数据分库分表的字段
        super.save(entity);
        exception();
        return entity.getId();
    }

    public void delete(Long id) {
        super.removeById(id);
    }

    public void update(Long id, UserParam param) {
        super.getOptById(id).ifPresent(entity -> {
            BeanUtils.copyProperties(param, entity);
            super.updateById(entity);
        });
    }

    public List<UserDTO> list(UserQueryParam param) {
        return super.lambdaQuery()
                .list().stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    private void exception() {
        throw new RuntimeException("test exception...");
    }
}
