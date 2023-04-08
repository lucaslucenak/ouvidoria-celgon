package com.unifacisa.ouvidoriacelgon.util;

import com.unifacisa.ouvidoriacelgon.models.ComplimentModel;
import com.unifacisa.ouvidoriacelgon.models.ProtestModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PrintFormatter {

    public PrintFormatter() {
    }

    public void formatComplimentsOutput(List<ComplimentModel> complimentModels) {
        for (ComplimentModel i : complimentModels) {
            System.out.println("Id: " + i.getId() + " Usuário: " + i.getUser().getUsername() + " Descrição: " + i.getComplimentDescription());
        }
    }

    public void formatProtestsOutput(List<ProtestModel> protestModels) {
        for (ProtestModel i : protestModels) {
            System.out.println("Id: " + i.getId() + " Usuário: " + i.getUser().getUsername() + " Descrição: " + i.getProtestDescription());
        }
    }
}
