package blog.services;

import blog.entity.User;
import blog.entity.UserEnums;
import blog.exceptionHandlers.UserNotFoundException;
import blog.models.UserModel;
import blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found for the given userId"));
    }

    @Override
    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User updateUserById(Long userId, User user) {
        User oldUser = userRepository.findById(userId).get();
        if(oldUser != null){
            if(oldUser.getUserName() != null && !oldUser.getUserName().equalsIgnoreCase("")){
                oldUser.setUserName(user.getUserName());
            }
            if(oldUser.getFullName() != null && !oldUser.getFullName().equalsIgnoreCase("")){
                oldUser.setFullName(user.getFullName());
            }
            if(oldUser.getEmail() != null && !oldUser.getEmail().equalsIgnoreCase("")){
                oldUser.setEmail(user.getUserName());
            }
            return userRepository.save(oldUser);
        } else {
            return saveUser(user);
        }
    }

    @Override
    public User registerUser(UserModel userModel) {
        User user = new User();
        user.setFullName(userModel.getFullName());
        user.setUserName(userModel.getUserName());
        user.setEmail(userModel.getEmail());
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        user.setRole(UserEnums.USER);
        user.setEnabled(true);
        userRepository.save(user);
        return user;
    }

    @Override
    public User
    findByEmailId(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found for the given email"));
    }

    @Override
    public User findByUserName(String userName) throws UserNotFoundException {
        return userRepository.findByUserName(userName).orElseThrow(() -> new UserNotFoundException("User not found for the given user name"));
    }
}
