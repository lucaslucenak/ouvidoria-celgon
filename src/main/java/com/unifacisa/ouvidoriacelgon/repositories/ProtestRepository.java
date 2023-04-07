package com.unifacisa.ouvidoriacelgon.repositories;

import com.unifacisa.ouvidoriacelgon.models.ProtestModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProtestRepository extends JpaRepository<ProtestModel, Long> {

    List<ProtestModel> findProtestByUserIdContains(Long userId);
}
