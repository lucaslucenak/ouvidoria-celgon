package com.unifacisa.ouvidoriacelgon.repositories;

import com.unifacisa.ouvidoriacelgon.models.ComplimentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplimentRepository extends JpaRepository<ComplimentModel, Long> {

    @Query(value = "SELECT * FROM tb_compliment WHERE user_id = ?1", nativeQuery = true)
    List<ComplimentModel> findComplimentByUserIdContains(@Param("userId") Long userId);
}
