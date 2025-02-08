package com.bchat.model.po;

import com.bchat.model.dto.RegisterDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Data
@Entity
@Table(name = "users")
@Repository
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles;

    public User(RegisterDTO registerDTO) {
        this.email = registerDTO.getEmail();
        this.name = registerDTO.getName();
        this.password = registerDTO.getPassword();
        this.username = registerDTO.getUsername();
    }
}