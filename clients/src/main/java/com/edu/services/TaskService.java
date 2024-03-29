package com.edu.services;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edu.models.Task;
import com.edu.models.User;
import com.edu.repositories.TaskRepository;
import com.edu.services.exeptions.DataBindingViolationException;
import com.edu.services.exeptions.ObjectNotFoundException;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    // Este metodo é responsavel por buscar uma tarefa pelo ID
    public Task findById(Long id) {
        @SuppressWarnings("null")
        Optional<Task> task = this.taskRepository.findById(id);
        return task.orElseThrow(() -> new ObjectNotFoundException(
                "Task not found! id: " + id + ", Tipo: " + Task.class.getName()));
    }

    // Este metodo é responsavel por buscar todas as tarefas de um usuário
    public List<Task> findAllByUserId(Long userId) {
        List<Task> tasks = this.taskRepository.findByUser_Id(userId);
        return tasks;
    }

    // Este metodo é responsavel por criar uma tarefa
    @Transactional
    public Task create(Task obj) {
        User user = this.userService.findById(obj.getUser().getId());
        obj.setId(null);
        obj.setUser(user);
        obj = this.taskRepository.save(obj);
        return obj;
    }

    // Este metodo é responsavel por atualizar uma tarefa
    @Transactional
    public Task update(Task obj) {
        Task newObj = this.findById(obj.getId());
        newObj.setDescription(obj.getDescription());
        return this.taskRepository.save(newObj);
    }

    // Este metodo é responsavel por deletar uma tarefa
    public void delete(@NonNull Long id) {
        findById(id);
        try {
            this.taskRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindingViolationException(
                    "It is not possible to delete a task that may be associated to other users");
        }
    }
}
