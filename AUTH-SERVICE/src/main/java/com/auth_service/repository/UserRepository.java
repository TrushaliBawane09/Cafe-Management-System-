package com.auth_service.repository;

import com.auth_service.dto.UserWrapper;
import com.auth_service.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    public Optional<User> findByEmail(String email);

    //@Query("SELECT new com.auth_service.dto.UserWrapper(u.id, u.name , u.email, u.contact, u.status) FROM User u WHERE u.role = :role")
    public List<User> findAllByRole(@Param("role") String role);


}
