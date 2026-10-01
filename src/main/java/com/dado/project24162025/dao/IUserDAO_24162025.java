package com.dado.project24162025.dao;

import com.dado.project24162025.model.Users_24162025;
import java.util.List;

public interface IUserDAO_24162025 {
    List<Users_24162025> getAll();
    List<Users_24162025> getPaged(int page, int pageSize);
    int countAll();
    Users_24162025 getById(int userId);
    Users_24162025 getByUsername(String username);
    Users_24162025 getByEmail(String email);
    boolean insert(Users_24162025 user);
    boolean update(Users_24162025 user);
    boolean delete(int userId);
    boolean activateUser(int userId);
}
