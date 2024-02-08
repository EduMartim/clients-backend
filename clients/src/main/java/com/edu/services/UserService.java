package com.edu.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.models.User;
import com.edu.repositories.TaskRepository;
import com.edu.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository; // Injeção de dependência para o UserRepository

    // Este método é responsável por buscar um usuario pelo ID
    @Autowired
    private TaskRepository taskRepository;

    @SuppressWarnings("null")
    public User findById(Long id) {
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new RuntimeException(
                "Usuário não encontrado! Id: " + id + ", Tipo: " + User.class.getName()));
    }

    // Este método é responsável por criar um usuario
    @SuppressWarnings("null")
    @Transactional
    public User create(User obj) {
        obj.setId(null);
        obj = this.userRepository.save(obj);
        this.taskRepository.saveAll(obj.getTasks());
        return obj;
    }

    @Transactional
    public User update(User obj) {
        User newObj = findById(obj.getId());
        newObj.setPassword(obj.getPassword());
        return this.userRepository.save(newObj);
    }

    // Este método é responsável por deletar um usuario
    @SuppressWarnings("null")
    public void delete(Long id) {
        findById(id);
        try {
            this.userRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar usuário, pois pode estar associado a outra entidade!");
        }
    }
}
