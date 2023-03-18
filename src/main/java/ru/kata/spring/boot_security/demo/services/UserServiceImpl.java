package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.models.User;


import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' is not found!", username));
        }
        return user;
    }
    @Transactional
    public User findUserById(Long userId) {
        return userDao.findById(userId).get();
    }
    @Transactional
    public List<User> allUsers() {
        return userDao.findAll();
    }
    @Transactional
    public boolean saveUser(User user) {
        User userFromDB = userDao.findByUsername(user.getUsername());
        if (userFromDB != null) {
            return false;
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDao.save(user);
        return true;
    }
    @Transactional
    public boolean deleteUser(Long userId) {
        if (userDao.findById(userId).isPresent()) {
            userDao.deleteById(userId);
            return true;
        }
        return false;
    }
    @Transactional
    public void update(User user, Long id) {
        User userFromDb = userDao.findById(id).get();
        userFromDb.setUsername(user.getUsername());
        userFromDb.setSurname(user.getSurname());
        userFromDb.setRoles(user.getRoles());
    }

    @Override
    public User findByUsername(String name) {
        return userDao.findByUsername(name);
    }


}
