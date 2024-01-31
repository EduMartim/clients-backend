package com.edu.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;

import com.edu.models.Task;
import com.edu.models.User;
import com.edu.repositories.TaskRepository;

import jakarta.transaction.Transactional;

public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    // Este metodo é responsavel por buscar uma tarefa pelo ID
    public Task findByID(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        java.util.Optional<Task> task = this.taskRepository.findById(id);
        return task.orElseThrow(() -> new RuntimeException(
                "Task not found! id: " + id +
                        ", Tipo: " + Task.class.getName()));
    }

    // Este metodo é responsavel por criar uma tarefa

    @Transactional
    public Task create(Task obj) {
        User user = this.userService.findByID(obj.getUser().getId());
        obj.setId(null);
        obj.setUser(user);
        obj = this.taskRepository.save(obj);
        return obj;
    }

    // Este método é responsável por atualizar uma tarefa
    @Transactional
    public Task update(Task obj) {
        Task newObj = this.findByID(obj.getId());
        newObj.setDescription(obj.getDescription());
        return this.taskRepository.save(newObj);
    }

    // Este método é responsável por deletar uma tarefa
    public void delete(@NonNull Long id) {
        findByID(id);
        try {
            this.taskRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("It is not possible to delete a task that may be associated to other users");
        }
    }
}
