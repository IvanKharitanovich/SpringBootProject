package com.ivankharytanovich.springbootapp.model;

import com.ivankharytanovich.springbootapp.model.enums.Role;
import com.ivankharytanovich.springbootapp.model.enums.Status;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "user_username")
    @Pattern(regexp = "([a-z A-Z]){3,16}",
            message = "Please enter a valid username (More than 3 latter and less then 16)")
    private String username;
    @Column(name = "user_password")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}",
            message = "Please enter a valid password ( at lest 8 chars with one number, one lowercase character, one uppercase character)")
    private String password;
    @Column(name = "user_firstname")
    @Pattern(regexp = "([a-z A-Z]){1,16}",
            message = "Please enter a valid firstName (More than 1 latter and less then 16)")
    private String firstName;
    @Column(name = "user_lastname")
    @Pattern(regexp = "([a-z A-Z]){1,16}",
            message = "Please enter a valid lastName (More than 1 latter and less then 16)")
    private String lastName;
    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "user_status")
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "user_creation_date")
    private LocalDate creationDate;

    public User() {
        role = Role.USER;
        status = Status.ACTIVE;
        creationDate = LocalDate.now();
    }

    public User(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        role = Role.USER;
        status = Status.ACTIVE;
        creationDate = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean isAccountNonExpired() {
        return status.booleanValue();
    }

    @Override
    public boolean isAccountNonLocked() {
        return status.booleanValue();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return status.booleanValue();
    }

    @Override
    public boolean isEnabled() {
        return status.booleanValue();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List <Role> roleList = new ArrayList<>();
        roleList.add(this.role);
        return roleList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && role == user.role && status == user.status && Objects.equals(creationDate, user.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, firstName, lastName, role, status, creationDate);
    }
}
