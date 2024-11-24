package org.moonzhou.springbootgz.service;

import lombok.AllArgsConstructor;
import org.moonzhou.springbootgz.dto.UserDto;
import org.moonzhou.springbootgz.entity.User;
import org.moonzhou.springbootgz.param.UserParam;
import org.moonzhou.springbootgz.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream().map(UserDto::new).toList();
    }

    public List<UserDto> queryBigData() {
        return LongStream.range(1, 1000).mapToObj(x -> new UserDto(x, "moon" + x, x + "+email@q63.com", "zhou" + x)).collect(Collectors.toList());
    }

    public UserDto findById(Long id) {
        return userRepository.findById(id).map(UserDto::new).orElse(null);
    }

    public UserDto save(UserParam userParam) {
        User user = new User();
        BeanUtils.copyProperties(userParam, user);
        User saved = userRepository.save(user);
        return new UserDto(saved);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
