package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.User;

import java.util.Collection;



public interface UserRepository {
    public User createAddSave(User user);

    public boolean delete(int id);

    public User read(int id);

    public Collection<User> getAllUsersList();

    public User getByEmail(String email);
}
