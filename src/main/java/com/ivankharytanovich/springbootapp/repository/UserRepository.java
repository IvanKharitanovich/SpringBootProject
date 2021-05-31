package com.ivankharytanovich.springbootapp.repository;

import com.ivankharytanovich.springbootapp.model.User;
import com.ivankharytanovich.springbootapp.model.enums.Role;
import com.ivankharytanovich.springbootapp.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    User getUserById(Long id);

    User getUserByUsername(String username);

    @Modifying(clearAutomatically = true)
    @Query("update User u set u.username = :username, u.password = :password, u.firstName = :firstName, u.lastName = :lastName, u.status = :status, u.role = :role where u.id = :id")
    void queryUserById(@Param(value = "id") Long id, @Param(value = "username") String username, @Param(value = "password") String password,
                       @Param(value = "firstName") String firstName, @Param(value = "lastName") String lastName, @Param(value = "status") Status status,
                       @Param(value = "role") Role role);

}
