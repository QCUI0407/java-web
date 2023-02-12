package dao;

import models.User;

import java.sql.SQLException;
import java.util.List;

public interface Users {
    User findByUsername(String username);
    Long insert(User user);
    void editProfile(String username, String password, long id);
    boolean validateUsername(String username) throws SQLException;
}