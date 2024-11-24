package org.moonzhou.springbootgz.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.moonzhou.springbootgz.entity.User;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserParam {
    private Long id;
    private String username;
    private String email;
    private String trueName;
}
