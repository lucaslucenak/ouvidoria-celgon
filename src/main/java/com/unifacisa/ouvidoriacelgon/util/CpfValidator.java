package com.unifacisa.ouvidoriacelgon.util;

import br.com.caelum.stella.validation.CPFValidator;
import org.springframework.stereotype.Component;

@Component
public class CpfValidator {

    public CpfValidator() {
    }

    public boolean isValid(String cpf) {
        CPFValidator cpfValidator = new CPFValidator();

        try {
            cpfValidator.assertValid(cpf);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
