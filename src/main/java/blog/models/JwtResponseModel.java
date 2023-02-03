package blog.models;

import blog.entity.User;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
public class JwtResponseModel {
    private String token;
    private String email;
    private String userName;
    private String role;
    private boolean isActive;


    public JwtResponseModel(User user) {
        this.email = user.getEmail();
        this.role = user.getRole().toString();
        this.userName = user.getUserName();
        this.isActive = user.isEnabled();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
