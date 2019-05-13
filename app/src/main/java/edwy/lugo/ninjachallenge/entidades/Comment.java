package edwy.lugo.ninjachallenge.entidades;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Comment implements Serializable {

    @SerializedName("postId")
    private int postId;
    @SerializedName("id")
    private int commentId;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("body")
    private String body;

    public Comment () {}

    public Comment(int postId, int commentId, String name, String email, String body) {
        this.postId = postId;
        this.commentId = commentId;
        this.name = name;
        this.email = email;
        this.body = body;
    }

    public Comment(int postId, int commentId, String name, String body) {
        this.postId = postId;
        this.commentId = commentId;
        this.name = name;
        this.body = body;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        if (postId != comment.postId) return false;
        if (commentId != comment.commentId) return false;
        if (!name.equals(comment.name)) return false;
        if (email != null ? !email.equals(comment.email) : comment.email != null) return false;
        return body.equals(comment.body);

    }

    @Override
    public int hashCode() {
        int result = postId;
        result = 31 * result + commentId;
        result = 31 * result + name.hashCode();
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + body.hashCode();
        return result;
    }
}
