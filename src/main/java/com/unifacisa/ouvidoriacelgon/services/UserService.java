package com.unifacisa.ouvidoriacelgon.services;

import br.com.caelum.stella.validation.CPFValidator;
import com.unifacisa.ouvidoriacelgon.exceptions.InvalidCpfException;
import com.unifacisa.ouvidoriacelgon.exceptions.InvalidLoginCredentials;
import com.unifacisa.ouvidoriacelgon.models.UserModel;
import com.unifacisa.ouvidoriacelgon.repositories.UserRepository;
import com.unifacisa.ouvidoriacelgon.util.CpfValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private CpfValidator cpfValidator;
    @Autowired
    private UserRepository userRepository;

    public UserModel saveUser(UserModel userModel) {
        if (!cpfValidator.isValid(userModel.getCpf())) {
            throw new InvalidCpfException("Por favor insira um CPF válido");
        } else {
            return userRepository.save(userModel);
        }
    }

    public List<UserModel> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserModel> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public Boolean login(UserModel userModel) {
        List<UserModel> users = userRepository.findUserByUsernameContains(userModel.getUsername());
        if (users.size() != 0) {
            return users.get(0).getUsername().equals(userModel.getPassword()) &&
                    users.get(0).getPassword().equals(userModel.getPassword()) &&
                    users.get(0).getUserType() == userModel.getUserType();
        }
        else {
            throw new InvalidLoginCredentials("Credenciais de login inválidas!");
        }
    }
}
