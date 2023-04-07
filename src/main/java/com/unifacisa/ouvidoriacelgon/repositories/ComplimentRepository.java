package com.unifacisa.ouvidoriacelgon.repositories;

import com.unifacisa.ouvidoriacelgon.models.ComplimentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplimentRepository extends JpaRepository<ComplimentModel, Long> {

    List<ComplimentModel> findComplimentByUserIdContains(Long userId);
}
