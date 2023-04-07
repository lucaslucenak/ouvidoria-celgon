package com.unifacisa.ouvidoriacelgon.repositories;

import com.unifacisa.ouvidoriacelgon.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    List<UserModel> findUserByUsernameContains(String username);
}
