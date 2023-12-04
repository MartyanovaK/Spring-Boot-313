package ru.kata.spring.boot_security.demo.repository;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Arrays;

@Component
public class DbInit {

    private final UserService userDao;

    private final RoleService roleService;

    public DbInit(UserService userDao, RoleService roleService) {
        this.userDao = userDao;
        this.roleService = roleService;
    }


    @PostConstruct
    @Transactional
    public void postConstruct() {
        User admin = new User("Boris", "admin", "Ivanov", "mail.ru", "admin");
        roleService.addRoleAdmin();
        User normalUser = new User("Ivan", "user", "Borisov", "gmail.ru","user password");
        roleService.addRoleUser();
        userDao.add(admin);
        userDao.add(normalUser);
    }
}