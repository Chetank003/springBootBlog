package blog.controllers;

import blog.config.UserModelAssembler;
import blog.entity.User;
import blog.exceptionHandlers.UserNotFoundException;
import blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {
    private final UserModelAssembler userModelAssembler;

    public UserController(UserModelAssembler userModelAssembler) {
        this.userModelAssembler = userModelAssembler;
    }

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    ResponseEntity<?> saveUser(@RequestBody User user){
        EntityModel<User> savedUser = userModelAssembler.toModel(userService.saveUser(user));
        return ResponseEntity.created(savedUser.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(savedUser);
    }

    @GetMapping("/users")
    public CollectionModel<EntityModel<User>> getUsers(){
        List<EntityModel<User>> users = userService.getUsers().stream().map(userModelAssembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(users, linkTo(methodOn(UserController.class).getUsers()).withSelfRel());
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> getUserById(@PathVariable("id") Long userId) throws UserNotFoundException {
        User user = userService.getUserById(userId);
        return userModelAssembler.toModel(user);
    }

    @DeleteMapping("/users/{id}")
    ResponseEntity<?> deleteUserById(@PathVariable("id") Long userId){
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/{id}")
    ResponseEntity<?> updateUserById(@PathVariable("id") Long userId, @RequestBody User user){
        EntityModel<User> updatedUser = userModelAssembler.toModel(userService.updateUserById(userId, user));
        return ResponseEntity.created(updatedUser.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(updatedUser);
    }

    @GetMapping(value = "/users", params = "email")
    public EntityModel<User> getUserByEmail(@RequestParam("email") String email) throws UserNotFoundException {
        return userModelAssembler.toModel(userService.findByEmailId(email));
    }

    @GetMapping(value = "/users", params = "userName")
    public EntityModel<User> getUserByUserName(@RequestParam("userName") String userName) throws UserNotFoundException {
        return userModelAssembler.toModel(userService.findByUserName(userName));
    }
}
