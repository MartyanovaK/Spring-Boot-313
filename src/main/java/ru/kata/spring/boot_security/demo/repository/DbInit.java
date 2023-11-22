package ru.kata.spring.boot_security.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.User;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@Component
@Transactional
public class DbInit {

    @Autowired
    private UserDao userDao;

    @PostConstruct
    private void postConstruct() {
        User admin = new User("Boris", "admin", "Ivanov", "mail.ru", "admin");
        User normalUser = new User("Ivan", "user", "Borisov", "gmail.ru","user password");
        userDao.add(admin);
        userDao.add(normalUser);
    }
}