package com.dado.project24162025.model;

public class UserRoles_24162025 {
    private int roleId;
    private String roleName;

    public UserRoles_24162025() {}

    public UserRoles_24162025(int roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public int getRoleId() { return roleId; }
    public void setRoleId(int roleId) { this.roleId = roleId; }

    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }
}
