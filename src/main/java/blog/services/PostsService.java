package blog.services;

import blog.entity.Posts;
import blog.exceptionHandlers.PostNotFoundException;
import blog.exceptionHandlers.UserNotFoundException;
import blog.models.PostModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostsService {
    Posts addPost(PostModel postModel) throws UserNotFoundException;

    List<Posts> getPosts();

    Posts getPostById(long id) throws PostNotFoundException;

    void deletePostById(long id);
}
