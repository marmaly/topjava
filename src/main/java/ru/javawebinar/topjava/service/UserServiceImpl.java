package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

/**
 * Created by grh on 11/28/17.
 */
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    @Override
    public User createAddSave(User user) {
        return null;
    }

    @Override
    public void delete(int id) throws NotFoundException {

    }

    @Override
    public User read(int id) throws NotFoundException {
        return null;
    }

    @Override
    public User getByEmail(String email) throws NotFoundException {
        return null;
    }

    @Override
    public void update(User user) {

    }

    @Override
    public List<User> getAll() {
        return null;
    }
}
