package ru.kata.spring.boot_security.demo.repository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;



    @Override
    public List<User> allUsers() {
        return entityManager.createQuery("select u from User u",User.class).getResultList();

    }


    @Override
    public void add(User user) {
        entityManager.persist(user);
    }


    @Override
    public void delete(Long id) {
        User user = entityManager.find(User.class,  id);
        entityManager.remove(user);
    }

    @Override
    public void edit(User user) {
        entityManager.merge(user);
    }

    @Override
    public User getById(Long id) {
        return entityManager.find(User.class,  id);
    }

    @Override
    public User findByUserName(String userName) {
        User user = entityManager.createQuery("Select u from User u left join fetch u.roles where u.username=:username", User.class).setParameter("username", userName).getSingleResult();
        if (user == null) {
            throw new UsernameNotFoundException("User " + userName + " not found");
        }
        return user;

    }

}