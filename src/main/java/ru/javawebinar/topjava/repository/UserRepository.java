package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.User;

import java.util.Collection;



public interface UserRepository {
    public User save(User user);

    public boolean delete(int id);

    public User get(int id);

    public Collection<User> getAll();

    public User getByEmail(String email);
}
