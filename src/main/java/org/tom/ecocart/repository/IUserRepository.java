package org.tom.ecocart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tom.ecocart.Entities.User;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
}
