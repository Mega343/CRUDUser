package ru.javarush.task.dao;


import ru.javarush.task.model.User;
import java.util.List;

public interface UserDao {
    public void add(User user);
    public void edit(User user);
    public void delete(int id);
    public User getUser(int id);
    public List<User> search(String name);
    public List<User> getAllUsers(Long page);

}
