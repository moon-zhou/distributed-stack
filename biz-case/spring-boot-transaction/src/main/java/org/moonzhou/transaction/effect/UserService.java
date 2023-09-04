package org.moonzhou.transaction.effect;

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
        super.save(entity);
        return entity.getId();
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
}
