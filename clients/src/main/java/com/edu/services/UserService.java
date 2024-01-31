package com.edu.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.models.Task;
import com.edu.models.User;
import com.edu.repositories.TaskRepository;
import com.edu.repositories.UserRepository;

import jakarta.transaction.Transactional;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    // Este metodo é responsavel por buscar um usuario pelo ID
    public User findByID(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new RuntimeException(
                "User not found! id: " + id +
                        ", Tipo: " + User.class.getName()));
    }

    // Este metodo é responsavel por criar um usuario
    @Transactional
    public User create(User obj) {
        obj.setId(null);
        obj = this.userRepository.save(obj);
        List<Task> tasks = obj.getTasks();
        if (tasks == null) {
            throw new IllegalArgumentException("Tasks cannot be null");
        }
        this.taskRepository.saveAll(tasks);
        return obj;
    }

    // Este método é responsável por atualizar um usuario
    @Transactional
    public User update(User obj) {
        User newObj = this.findByID(obj.getId());
        newObj.setPassword(obj.getPassword());
        return this.userRepository.save(newObj);
    }

    // Este método é responsável por deletar um usuario
    public void delete(Long id) {
        findByID(id);
        if (id != null) {
            try {
                this.userRepository.deleteById(id);
            } catch (Exception e) {
                throw new RuntimeException("Error deleting user as it may be associated to another entity!");
            }
        }
    }
}
