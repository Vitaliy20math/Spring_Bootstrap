package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.HashSet;
import java.util.Set;
@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final RoleDao roleDao;
    private final UserDao userDao;
    @Autowired
    public CommandLineRunnerImpl(RoleDao roleDao, UserDao userDao) {
        this.roleDao = roleDao;
        this.userDao = userDao;
    }

    @Override
    public void run(String... args) throws Exception {

        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");

        Set<Role> rolesMoreOne = new HashSet<>();
        Set<Role> rolesAdmin = new HashSet<>();
        Set<Role> rolesUser = new HashSet<>();

        roleDao.save(roleAdmin);
        roleDao.save(roleUser);

        rolesAdmin.add(roleAdmin);//if user have only one role
        rolesUser.add(roleUser);//here too
        //rolesMoreOne it is Set that have more one role, so inside sets roleUser and roleAdmin
        rolesMoreOne.add(roleUser);
        rolesMoreOne.add(roleAdmin);

        User user1 = new User("user2", "surname2", "$2a$12$8w9TE21bJCwBg3X6ycgSk.X7SI3i2jH5hCyLdX/sD/dDnRsKia8I.", rolesUser);//1234 - password
        User user2 = new User("user3", "surname3", "$2a$12$so0/Z3SRfE1F9UetI.VkUewPxMfM.7yszNMMV/Yj64VyZy6qnMMjm", rolesAdmin);//1215 - password
        User user3 = new User("user4", "surname4", "$2a$12$tlUpdEM69sFGpk.rf8lqBuhyd6.daqu6PYpjfOnszAfbpNP8UVajS", rolesMoreOne);//0909 - password

        userDao.save(user1);
        userDao.save(user2);
        userDao.save(user3);

    }
}
