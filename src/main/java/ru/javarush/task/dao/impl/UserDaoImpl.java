package ru.javarush.task.dao.impl;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javarush.task.dao.UserDao;
import ru.javarush.task.model.User;

import java.util.ArrayList;
import java.util.List;


@Repository
public class UserDaoImpl implements UserDao{

    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
    private SessionFactory session;

    @Override
    public void add(User user) {
        session.getCurrentSession().save(user);
        logger.info("User add successful" + user);
    }

    @Override
    public void edit(User user) {
        session.getCurrentSession().update(user);
        logger.info("User update successful" + user);
    }

    @Override
    public void delete(int id) {
        session.getCurrentSession().delete(getUser(id));
        logger.info("User delete successful by id = " + id);
    }

    @Override
    public User getUser(int id) {
        return (User) session.getCurrentSession().get(User.class, id);
    }

    @Override
    public List<User> search(String name) {
        List<User> searchUsers = new ArrayList<>();
        for(User user : getAllUsers()){
            if(user.getName().equals(name)){
                searchUsers.add(user);
                logger.info("User found" + user);
            }
        }
        if(searchUsers.isEmpty()){
            logger.info("User didn't find");
        }
        return searchUsers;
    }

    @Override
    public List<User> getAllUsers() {
        Session session = this.session.getCurrentSession();
        List<User> usersList = session.createQuery("from User").list();
        return usersList;
    }

    public void setSession(SessionFactory session) {
        this.session = session;
    }
}
