package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.RoleDao;
import ru.kata.spring.boot_security.demo.repository.UserDao;
import java.util.ArrayList;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final RoleDao roleDao;
    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder, UserDao userDao, RoleDao roleDao) {
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Override
    public List<User> allUsers() {
        return userDao.allUsers();
    }

    @Transactional
    @Override
    public void add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(getMyListRoles(user));
        userDao.add(user);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        userDao.delete(id);
    }

    @Transactional
    @Override
    public void edit(User userUp, Long id) {
        User user = userDao.getById(id);
        user.setName(userUp.getName());
        user.setUserName(userUp.getUserName());
        user.setLastName(userUp.getLastName());
        user.setEmail(userUp.getEmail());
        user.setRoles(getMyListRoles(userUp));
        if (!user.getPassword().equals(userUp.getPassword())) {
            user.setPassword(passwordEncoder.encode(userUp.getPassword()));
        }
        userDao.edit(user);
    }

    private List<Role> getMyListRoles(User user) {
        List<Role> usersRoles = user.getRoles();
        List<Role> allRoles = roleDao.findAll();
        List<Role> myRoleList = new ArrayList<>();
        for (Role role : allRoles) {
            for (Role userRole : usersRoles) {
                if (role.getRole().equals(userRole.getRole())) {
                    myRoleList.add(role);
                }
            }
        }
        return myRoleList;
    }

    @Override
    public User getById(Long id) {
        User userDB = userDao.getById(id);
        if (userDB == null) {
            throw new UsernameNotFoundException(String.format("Пользователь с id = %d не найден", id));
        }
        return userDB;
    }

}