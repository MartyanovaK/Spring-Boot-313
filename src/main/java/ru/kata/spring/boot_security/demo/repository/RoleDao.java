package ru.kata.spring.boot_security.demo.repository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Set;

@Repository
public interface RoleDao {
    List<Role> findAll();

    Role findRoleById(Long id);

    Role findRoleByRoleName(String role);

    void addRoleUser();
    public void addRoleAdmin();

}
