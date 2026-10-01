package com.dado.project24162025.service;

import com.dado.project24162025.model.Users_24162025;
import java.util.List;

public interface IUserService_24162025 {
    List<Users_24162025> getAllUsers();
    List<Users_24162025> getUsersPaged(int page, int pageSize);
    int countUsers();
    int totalPages(int pageSize);
    Users_24162025 getUserById(int id);

    String register(String username, String email, String fullname, String rawPassword, String phone);

    boolean verifyOtp(String email, String otpInput);

    Users_24162025 login(String username, String rawPassword);

    boolean updateUser(Users_24162025 user);
    boolean deleteUser(int id);
}
