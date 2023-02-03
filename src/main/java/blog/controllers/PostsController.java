package blog.controllers;

import blog.config.PostsModelAssembler;
import blog.entity.Posts;
import blog.exceptionHandlers.PostNotFoundException;
import blog.exceptionHandlers.UserNotFoundException;
import blog.models.PostModel;
import blog.services.PostsService;
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
public class PostsController {

    @Autowired
    private PostsService postsService;
    private final PostsModelAssembler postsModelAssembler;

    public PostsController(PostsModelAssembler postsModelAssembler) {
        this.postsModelAssembler = postsModelAssembler;
    }

    @PostMapping("/posts")
    ResponseEntity<?> addPost(@RequestBody PostModel postModel) throws UserNotFoundException {
        EntityModel<Posts> newPost = postsModelAssembler.toModel(postsService.addPost(postModel));
        return ResponseEntity.created(newPost.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(newPost);
    }

    @GetMapping("/posts")
    public CollectionModel<EntityModel<Posts>> getPosts(){
        List<EntityModel<Posts>> allPosts = postsService.getPosts().stream().map(postsModelAssembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(allPosts, linkTo(methodOn(PostsController.class).getPosts()).withSelfRel());
    }

    @GetMapping("/posts/{id}")
    public EntityModel<Posts> getPosts(@PathVariable("id") long id) throws PostNotFoundException {
        return postsModelAssembler.toModel(postsService.getPostById(id));
    }

    @DeleteMapping("/posts/{id}")
    ResponseEntity<?> deletePostsById(@PathVariable("id") long id){
        postsService.deletePostById(id);
        return ResponseEntity.noContent().build();
    }
}
