package com.docencia.restejercicio.repository;

import com.docencia.restejercicio.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    List<User> users;

    public UserRepository() {
        this.users = new ArrayList<>();
    }

    /**
     * Retorna todos los usuarios
     * 
     * @return Lista de usuarios
     */
    public List<User> findAll() {
        return users;
    }

    /**
     * Busca a un usuario por id
     * 
     * @param id id del usuario
     * @return un usuario o null
     */
    public Optional<User> findById(Long id) {
        for (User user : users) {
            if (user.getId() == id) {
                return Optional.of(user);
            }
        }

        return null;
    }

    /**
     * Guarda un usuario
     * 
     * @param user usuario a guardar
     * @return el usuario
     */
    public User save(User user) {
        User user1 = new User(user.getId(), user.getUsername(), user.getEmail());
        users.add(user1);
        return user1;
    }

    /**
     * borra un usuario por id
     */
    public void deleteById(Long id) {
        try {
            User user = findById(id).orElse(null);
            users.remove(user);
        } catch (Exception e) {
            throw new RuntimeException(e);

        }

    }

    /**
     * Dice si existe un usuario por id
     * 
     * @param id id del usuario
     * @return true si existe false si no
     */
    public boolean existsById(Long id) {
        for (User user : users) {
            if (user.getId() == id) {
                return true;
            }
        }
        return false;
    }
}
