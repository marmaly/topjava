package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.User;

import java.util.Collection;



public interface UserRepository {
    public User createAddSave(User user);

    public void delete(int id);

    public User read(int id);

    public Collection<User> getAllUsersList();
}
