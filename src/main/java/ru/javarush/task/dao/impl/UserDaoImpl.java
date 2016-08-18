package ru.javarush.task.dao.impl;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javarush.task.dao.UserDao;
import ru.javarush.task.model.User;

import java.util.List;


@Repository
public class UserDaoImpl implements UserDao{

    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
    private SessionFactory session;
    private static final int limitPages = 10;

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
            Query query = session.getCurrentSession().createQuery("select u from User u where name LIKE :name");
            return query.setParameter("name", "%" + name + "%").list();
        }

    @Override
    public List<User> getAllUsers(Long page) {
        Session session = this.session.getCurrentSession();
        Query query = session.createQuery("FROM User");
        query.setFirstResult((int)(page - 1) * limitPages);
        query.setMaxResults(limitPages);
        List<User> usersList = query.list();
        return usersList;
    }

    public void setSession(SessionFactory session) {
        this.session = session;
    }
}
