package blog.services;

import blog.entity.User;
import blog.exceptionHandlers.UserNotFoundException;
import blog.models.UserModel;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public interface UserService {
    public User saveUser(User user);

    public List<User> getUsers();

    User getUserById(Long userId) throws UserNotFoundException;

    void deleteUserById(Long userId);

    User updateUserById(Long userId, User user);

    User registerUser(UserModel userModel);

    User findByEmailId(String email) throws UserNotFoundException;

    User findByUserName(String userName) throws UserNotFoundException;
}
