package com.docencia.restejercicio.service;

import com.docencia.restejercicio.model.User;
import com.docencia.restejercicio.repository.UserRepository;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }
    
    public List<User> getAll() {
        return repository.findAll();
    }

    public User getById(Long userId) {
        return repository.findById(userId).orElse(null);
    }

    public User create(User user) {
        if (user.getId()== null){
            user.setId((long)repository.findAll().size());
        }
        return repository.save(user);
    }

    public User update(Long id, User update) {
        User user = update;
        user.setId(id);
        
        return user;
    }

    public void delete(Long id) {
        repository.deleteById(id);

    }
}
