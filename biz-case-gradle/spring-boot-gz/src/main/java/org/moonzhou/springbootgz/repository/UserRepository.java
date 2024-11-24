package org.moonzhou.springbootgz.repository;

import org.moonzhou.springbootgz.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
