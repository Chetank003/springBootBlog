package blog.entity;


import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Table(
        name = "user_tbl",
        uniqueConstraints = @UniqueConstraint(
                name = "uniqueEmailConstraint",
                columnNames = "email_id"
        )
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String fullName;
    private String userName;
    @Column(
            name = "email_id",
            nullable = false
    )
    @Email(message = "Please enter valid email address")
    private String email;
    @Length(min = 8, max = 60, message = "Please enter valid password")
    private String password;
    private boolean enabled;
    @Enumerated(EnumType.STRING)
    private UserEnums role;

    public User() {
    }

    public User(String fullName, String userName, String email, String password, boolean enabled, UserEnums role) {
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public UserEnums getRole() {
        return role;
    }

    public void setRole(UserEnums role) {
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
