package com.unifacisa.ouvidoriacelgon.services;

import com.unifacisa.ouvidoriacelgon.models.ComplimentModel;
import com.unifacisa.ouvidoriacelgon.models.ProtestModel;
import com.unifacisa.ouvidoriacelgon.repositories.ComplimentRepository;
import com.unifacisa.ouvidoriacelgon.repositories.ProtestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ComplimentService {

    @Autowired
    private ComplimentRepository complimentRepository;

    public ComplimentModel saveCompliment(ComplimentModel complimentModel) {
        return complimentRepository.save(complimentModel);
    }

    public List<ComplimentModel> findAllCompliments() {
        return complimentRepository.findAll();
    }

    public List<ComplimentModel> findAllComplimentsByUserId(Long id) {
        List<ComplimentModel> complimentModelsByUserId = new ArrayList<>();
        for (ComplimentModel i : findAllCompliments()) {
            if (i.getUser().getId().equals(id)) {
                complimentModelsByUserId.add(i);
            }
        }
        return complimentModelsByUserId;
    }

    public Optional<ComplimentModel> findComplimentById(Long id) {
        return complimentRepository.findById(id);
    }

    public void deleteComplimentById(Long id) {
        complimentRepository.deleteById(id);
    }
}
