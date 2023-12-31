package ru.kata.spring.boot_security.demo.repository;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Role> findAll() {
        return entityManager.createQuery("from Role", Role.class).getResultList();
    }

    @Override
    public Role findRoleById(Long id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public Role findRoleByRoleName(String roleName) {
        return entityManager.createQuery("select r from Role r where r.role =: roleName", Role.class)
                .setParameter("roleName", roleName).getSingleResult();
    }

    @Override
    public void addRoleUser() {

        String query = "insert into users_roles (user_id, role_id) values ((SELECT id FROM users WHERE username = 'user'), (SELECT id FROM roles WHERE role = 'ROLE_USER'))";
        entityManager.createQuery(query);
    }

    @Override
    public void addRoleAdmin() {
        String query = "insert into users_roles (user_id, role_id) values ((SELECT id FROM users WHERE username = 'admin'), (SELECT id FROM roles WHERE role = 'ADMIN_USER'))";
        entityManager.createQuery(query);
    }
}
