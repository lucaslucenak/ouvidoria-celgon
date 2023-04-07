package com.unifacisa.ouvidoriacelgon.services;

import com.unifacisa.ouvidoriacelgon.enums.UserTypeEnum;
import com.unifacisa.ouvidoriacelgon.models.ProtestModel;
import com.unifacisa.ouvidoriacelgon.models.UserModel;
import com.unifacisa.ouvidoriacelgon.repositories.ProtestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProtestService {

    @Autowired
    private ProtestRepository protestRepository;

    public ProtestModel saveProtest(ProtestModel protestModel) {
        return protestRepository.save(protestModel);
    }

    public List<ProtestModel> findAllProtests() {
        return protestRepository.findAll();
    }

    public List<ProtestModel> findAllProtestsByUserId(Long id) {
        return protestRepository.findProtestByUserIdContains(id);
    }

    public Optional<ProtestModel> findProtestById(Long id) {
        return protestRepository.findById(id);
    }

    public void deleteProtestById(Long id) {
        protestRepository.deleteById(id);
    }
}
