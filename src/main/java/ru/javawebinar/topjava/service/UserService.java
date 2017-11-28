package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.User;

import java.util.List;

/**
 * Created by grh on 11/28/17.
 */
public interface UserService {


    User createAddSave(User user);

    void delete(int id) throws NotFoundException;

    User read(int id) throws NotFoundException;

    User getByEmail(String email) throws NotFoundException;

    void update(User user);

    List<User> getAll();
}
