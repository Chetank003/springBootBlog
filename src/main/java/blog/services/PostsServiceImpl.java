package blog.services;

import blog.entity.Posts;
import blog.entity.User;
import blog.exceptionHandlers.PostNotFoundException;
import blog.exceptionHandlers.UserNotFoundException;
import blog.models.PostModel;
import blog.repository.PostsRepository;
import blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostsServiceImpl implements PostsService{

    @Autowired
    private PostsRepository postsRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Posts addPost(PostModel postModel) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(postModel.getAuthorId());
        if(user.isPresent()){
            Posts post = new Posts();
            post.setBody(postModel.getPostBody());
            post.setTitle(postModel.getPostTitle());
            post.setUser(user.get());
            return postsRepository.save(post);
        }
        else
            throw new UserNotFoundException("Invalid author Id");
    }

    @Override
    public List<Posts> getPosts() {
        return postsRepository.findAll();
    }

    @Override
    public Posts getPostById(long id) throws PostNotFoundException {
        return postsRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Post not found for the given postId"));
    }

    @Override
    public void deletePostById(long id) {
        postsRepository.deleteById(id);
    }
}
