package blog.config;

import blog.controllers.PostsController;
import blog.entity.Posts;
import blog.exceptionHandlers.PostNotFoundException;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PostsModelAssembler implements RepresentationModelAssembler<Posts, EntityModel<Posts>> {
    @Override
    public EntityModel<Posts> toModel(Posts entity) {
        try {
            return EntityModel.of(entity, linkTo(methodOn(PostsController.class).getPosts(entity.getPostId())).withSelfRel()
            , linkTo(methodOn(PostsController.class).getPosts()).withRel("Posts"));
        } catch (PostNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
