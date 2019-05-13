package edwy.lugo.ninjachallenge.entidades;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Post implements Serializable {
    @SerializedName("userId")
    private int userId;
    @SerializedName("id")
    private int postId;
    @SerializedName("title")
    private String title;
    @SerializedName("body")
    private String body;

    public Post () {}

    public Post(int userId, int postId, String title, String body) {
        this.userId = userId;
        this.postId = postId;
        this.title = title;
        this.body = body;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        if (userId != post.userId) return false;
        if (postId != post.postId) return false;
        if (!title.equals(post.title)) return false;
        return body.equals(post.body);

    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + postId;
        result = 31 * result + title.hashCode();
        result = 31 * result + body.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "USER_ID = " + userId +
                "\nPOST_ID = " + postId +
                "\nTITLE = " + title +
                "\nBODY = " + body;
    }
}

