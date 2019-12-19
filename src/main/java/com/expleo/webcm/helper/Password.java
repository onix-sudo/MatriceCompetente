package com.expleo.webcm.helper;

/**
 * A helper class which is allows to verify and validate the password.
 * */
public class Password {

    private String oldPassword;
    private String confirmPassword;
    private String newPassword;

    public Password() {
    }

    public Password(String confirmPassword, String newPassword) {
        this.confirmPassword = confirmPassword;
        this.newPassword = newPassword;
        this.oldPassword = newPassword;
    }

    public Password(String oldPassword, String confirmPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.confirmPassword = confirmPassword;
        this.newPassword = newPassword;

    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
