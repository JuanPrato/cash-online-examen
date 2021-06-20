package com.cashonline.examen.repository;

import com.cashonline.examen.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
