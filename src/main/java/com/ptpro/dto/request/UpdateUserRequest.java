package com.ptpro.dto.request;

public class UpdateUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private Long roleId;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstNAme) {
        this.firstName = firstNAme;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
