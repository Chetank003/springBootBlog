package blog.config;

import blog.controllers.UserController;
import blog.entity.User;
import blog.exceptionHandlers.UserNotFoundException;
import blog.services.UserServiceImpl;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {
    @Override
    public EntityModel<User> toModel(User entity) {
        try {
            return EntityModel.of(entity, linkTo(methodOn(UserController.class).getUserById(entity.getUserId())).withSelfRel()
            , linkTo(methodOn(UserController.class).getUsers()).withRel("users"));
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
