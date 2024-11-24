package org.moonzhou.springbootgz.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "t_user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
  
    @Column(nullable = false)
    private String username;
  
    @Column(nullable = false)
    private String email;
  
    @Column(nullable = false)
    private String trueName;
}
