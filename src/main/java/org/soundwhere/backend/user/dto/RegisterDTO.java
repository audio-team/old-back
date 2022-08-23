package org.soundwhere.backend.user.dto;

public class RegisterDTO {
    public String username;
    public String password;
    public String email;

    public RegisterDTO(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public RegisterDTO() {}

    @Override
    public String toString() {
        return "RegisterDto{" +
            "username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", email='" + email + '\'' +
            '}';
    }
}
