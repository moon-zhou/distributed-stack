package org.moonzhou.transaction.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.moonzhou.transaction.entity.User;

/**
 * @author moon zhou
 * @since 2023-09-01
 */
@Data
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String name;

    private Integer age;

    public UserDTO(User entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.age = entity.getAge();
    }
}
