package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;


import java.util.Collections;
import java.util.List;


/**
 * Created by grh on 11/28/17.
 */
public class MockUserRepository implements UserRepository {

    private static final Logger log = LoggerFactory.getLogger(MockUserRepository.class);

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return true;
    }

    @Override
    public User createAddSave(User user) {
        log.info("save {}", user);
        return user;
    }

    @Override
    public User read(int id) {
        log.info("get {}", id);
        return null;
    }

    @Override
    public List<User> getAllUsersList() {
        log.info("getAll");
        return Collections.emptyList();
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return null;
    }
}
