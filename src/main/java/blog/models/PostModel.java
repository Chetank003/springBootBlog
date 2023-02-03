package blog.models;

public class PostModel {
    private Long authorId;
    private String postTitle;
    private String postBody;

    public PostModel() {
    }

    public PostModel(Long authorId, String postTitle, String postBody) {
        this.authorId = authorId;
        this.postTitle = postTitle;
        this.postBody = postBody;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }
}
