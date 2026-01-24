package com.docencia.aed.repository;

import com.docencia.aed.entity.User;
import java.util.Optional;
import java.util.List;

public interface UserRepository {
    User save(User user);
    
    Optional<User> findById(Long id);
    
    Optional<User> findByEmail(String email);
    
    List<User> findAll();
    
    void deleteById(Long id);
}