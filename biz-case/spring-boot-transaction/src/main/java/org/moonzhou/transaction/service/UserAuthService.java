package org.moonzhou.transaction.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.moonzhou.transaction.common.exception.NotFoundException;
import org.moonzhou.transaction.dto.UserAuthDTO;
import org.moonzhou.transaction.entity.UserAuth;
import org.moonzhou.transaction.mapper.UserAuthMapper;
import org.moonzhou.transaction.param.UserAuthParam;
import org.moonzhou.transaction.param.UserAuthQueryParam;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author moon zhou
 * @since 2023-09-06
 */
@Service
@Transactional
public class UserAuthService extends ServiceImpl<UserAuthMapper, UserAuth> implements IService<UserAuth> {

    public UserAuthDTO getById(Long id) {
        return super.getOptById(id)
                .map(UserAuthDTO::new)
                .orElseThrow(() -> new NotFoundException("UserAuth", id));
    }

    public Long save(UserAuthParam param) {
        UserAuth entity = new UserAuth();
        BeanUtils.copyProperties(param, entity);
        super.save(entity);
        return entity.getId();
    }

    public Long saveFail(UserAuthParam param) {
        UserAuth entity = new UserAuth();
        BeanUtils.copyProperties(param, entity);
        super.save(entity);
        exception();
        return entity.getId();
    }

    public void delete(Long id) {
        super.removeById(id);
    }

    public void update(Long id, UserAuthParam param) {
        super.getOptById(id).ifPresent(entity -> {
            BeanUtils.copyProperties(param, entity);
            super.updateById(entity);
        });
    }

    public List<UserAuthDTO> list(UserAuthQueryParam param) {
        return super.lambdaQuery()
                .list().stream()
                .map(UserAuthDTO::new)
                .collect(Collectors.toList());
    }

    private void exception() {
        throw new RuntimeException("test exception...");
    }
}
