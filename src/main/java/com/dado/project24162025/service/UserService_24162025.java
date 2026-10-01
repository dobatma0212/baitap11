package com.dado.project24162025.service;

import com.dado.project24162025.dao.IUserDAO_24162025;
import com.dado.project24162025.dao.UserDAO_24162025;
import com.dado.project24162025.model.Users_24162025;
import com.dado.project24162025.util.MailUtil_24162025;
import com.dado.project24162025.util.PasswordUtil_24162025;

import java.util.List;

public class UserService_24162025 implements IUserService_24162025 {

    private final IUserDAO_24162025 userDAO = new UserDAO_24162025();

    @Override
    public List<Users_24162025> getAllUsers() {
        return userDAO.getAll();
    }

    @Override
    public List<Users_24162025> getUsersPaged(int page, int pageSize) {
        return userDAO.getPaged(page, pageSize);
    }

    @Override
    public int countUsers() {
        return userDAO.countAll();
    }

    @Override
    public int totalPages(int pageSize) {
        int total = countUsers();
        return (int) Math.ceil(total / (double) pageSize);
    }

    @Override
    public Users_24162025 getUserById(int id) {
        return userDAO.getById(id);
    }

    @Override
    public String register(String username, String email, String fullname, String rawPassword, String phone) {
        if (username == null || username.trim().isEmpty()) return "Vui long nhap username.";
        if (email == null || email.trim().isEmpty()) return "Vui long nhap email.";
        if (rawPassword == null || rawPassword.length() < 4) return "Mat khau phai co it nhat 4 ky tu.";
        if (userDAO.getByUsername(username) != null) return "Username da ton tai.";
        if (userDAO.getByEmail(email) != null) return "Email da duoc dang ky.";

        String otp = MailUtil_24162025.generateOtp();

        Users_24162025 u = new Users_24162025();
        u.setUsername(username);
        u.setEmail(email);
        u.setFullname(fullname);
        u.setPassword(PasswordUtil_24162025.hash(rawPassword));
        u.setImages("");
        u.setPhone(phone);
        u.setStatus(0);
        u.setCode(otp);
        u.setRoleId(3);
        u.setSellerid(null);

        boolean ok = userDAO.insert(u);
        if (!ok) return "Dang ky that bai, vui long thu lai.";

        MailUtil_24162025.sendOtpMail(email, otp);
        return null;
    }

    @Override
    public boolean verifyOtp(String email, String otpInput) {
        Users_24162025 u = userDAO.getByEmail(email);
        if (u == null) return false;
        if (u.getStatus() == 1) return true;
        if (u.getCode() != null && u.getCode().equals(otpInput)) {
            return userDAO.activateUser(u.getUserId());
        }
        return false;
    }

    @Override
    public Users_24162025 login(String username, String rawPassword) {
        Users_24162025 u = userDAO.getByUsername(username);
        if (u == null) return null;
        if (u.getStatus() != 1) return null;
        String hashed = PasswordUtil_24162025.hash(rawPassword);
        if (hashed.equals(u.getPassword())) return u;
        return null;
    }

    @Override
    public boolean updateUser(Users_24162025 user) {
        return userDAO.update(user);
    }

    @Override
    public boolean deleteUser(int id) {
        return userDAO.delete(id);
    }
}
