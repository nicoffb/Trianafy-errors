package com.salesianostriana.dam.trianafy.validation.repo;

import net.openwebinars.springboot.validation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByUsername(String username);
}
