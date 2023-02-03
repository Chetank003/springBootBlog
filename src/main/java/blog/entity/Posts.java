package blog.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(
        name = "posts_tbl",
        uniqueConstraints = @UniqueConstraint(
                name = "uniqueAuthorIdConstraint",
                columnNames = "author_id"
        )
)
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long postId;
    @ManyToOne
    @JoinColumn(
            name = "author_id",
            referencedColumnName = "userId",
            nullable = false
    )
    private User user;
    @Column(
            name = "blog_title",
            nullable = false
    )
    private String title;
    @Column(
            name = "blog_body",
            nullable = false
    )
    private String body;
    Date postDate = new Date();

    public Posts() {}

    public Posts(long id, User user, String title, String body) {
        this.postId = id;
        this.user = user;
        this.title = title;
        this.body = body;
        this.postDate = new Date();
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    @Override
    public String toString() {
        return "blog.models.Posts{" +
                "postId=" + postId +
                ", user=" + user +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", postDate=" + postDate +
                '}';
    }
}
