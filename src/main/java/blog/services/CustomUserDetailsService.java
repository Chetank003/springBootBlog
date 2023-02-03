package blog.services;

import blog.entity.User;
import blog.models.JwtResponseModel;
import blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByEmail(username);
        return new CustomUserDetails(user);
    }

    public JwtResponseModel generateAuthenticationResponse(User user){
        return new JwtResponseModel(user);
    }
}
