package com.unifacisa.ouvidoriacelgon.models;

import com.unifacisa.ouvidoriacelgon.enums.UserTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_user")
public class UserModel {

    public UserModel(UserTypeEnum userType, String cpf, String username, String password) {
        this.userType = userType;
        this.cpf = cpf;
        this.username = username;
        this.password = password;
    }

    public UserModel(UserTypeEnum userType, String username, String password) {
        this.userType = userType;
        this.username = username;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "userType", nullable = false)
    private UserTypeEnum userType;

    @Column(name = "cpf", nullable = false)
    private String cpf;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;
}
