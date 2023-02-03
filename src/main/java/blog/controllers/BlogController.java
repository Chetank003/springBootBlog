package blog.controllers;

import blog.entity.Posts;
import blog.entity.User;
import blog.exceptionHandlers.UserNotFoundException;
import blog.models.JwtResponseModel;
import blog.models.LoginModel;
import blog.models.ResponseModel;
import blog.models.UserModel;
import blog.repository.PostsRepository;
import blog.security.JWTAuthenticationUtil;
import blog.services.CustomUserDetailsService;
import blog.services.UserService;
import blog.utils.BindingResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private PostsRepository postsRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTAuthenticationUtil jwtAuthenticationUtil;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute UserModel userModel, BindingResult bindingResult, Model model) throws UserNotFoundException {
        if (userModel.getUserName().isEmpty() || userModel.getFullName().isEmpty() || userModel.getEmail().isEmpty() || userModel.getPassword().isEmpty() || userModel.getConfirmPassword().isEmpty()){
            model.addAttribute("errorMessage", "No fields can be empty");
            return "/registration";
        }
        try {
            if (userService.findByEmailId(userModel.getEmail()) != null) {
                bindingResult

                        .rejectValue("email", "error.email",
                                "There is already a user registered with the email provided");
            }
            if (userService.findByUserName(userModel.getUserName()) != null) {
                bindingResult
                        .rejectValue("userName", "error.userName",
                                "There is already a user registered with the username provided");
            }
        } catch (UserNotFoundException e){}
        if (!bindingResult.hasErrors()) {
            // Registration successful, save user
            // Set user role to USER and set it as active
            userService.registerUser(userModel);

            model.addAttribute("successMessage", "User has been registered successfully");
            model.addAttribute("userModel", new UserModel());
        }
        return "registration";
    }

    @GetMapping("/register")
    public String registerUserPage(Model model){
        model.addAttribute("userModel", new UserModel());
        return "registration";
    }

    @GetMapping("/login")
    public String userLogin(Model model){
        model.addAttribute("loginModel", new LoginModel());
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginModel loginModel, BindingResult bindingResult) throws UserNotFoundException {
        /*
        * Below snippet uses different secret for creating a token
        * */
        /*Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginModel.getEmail(), loginModel.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        String jwt = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, "sample")
                .compact();
        return ResponseEntity.ok(new JwtResponseModel(jwt, userDetails.getUsername(), roles));*/

        /*
        * JWT validation
        * */
        if(bindingResult.hasErrors()){
            return ResponseModel.response(HttpStatus.BAD_REQUEST, false, BindingResultHandler.getErrorData(bindingResult));
        }
        User user = userService.findByEmailId(loginModel.getEmail());
        boolean isValidCredentials = jwtAuthenticationUtil.isUserCredentialsValid(user, loginModel.getPassword());
        if(!isValidCredentials){
            throw new UserNotFoundException("Invalid credentials");
        }

        JwtResponseModel jwtResponseModel = userDetailsService.generateAuthenticationResponse(user);
        String token = jwtAuthenticationUtil.createToken(loginModel.getEmail());
        jwtResponseModel.setToken(token);
        return ResponseModel.response(HttpStatus.OK, true, jwtResponseModel);
    }

    @GetMapping("/")
    public String getAllPosts(@CurrentSecurityContext(expression = "authentication.name") String name, Model model){
        model.addAttribute("userName", name);
        List<Posts> allPosts = postsRepository.findAll();
        model.addAttribute("allPosts", allPosts);
        return "blog";
    }

}
