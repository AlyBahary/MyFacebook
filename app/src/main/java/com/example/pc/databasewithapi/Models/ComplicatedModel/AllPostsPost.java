
package com.example.pc.databasewithapi.Models.ComplicatedModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllPostsPost {

    @SerializedName("post_id")
    @Expose
    private String postId;
    @SerializedName("caption")
    @Expose
    private String caption;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("p_time")
    @Expose
    private String pTime;
    @SerializedName("is_public")
    @Expose
    private String isPublic;
    @SerializedName("user")
    @Expose
    private AllPostsUser user;
    @SerializedName("likes")
    @Expose
    private List<AllPostsLike> likes = null;
    @SerializedName("comments")
    @Expose
    private List<AllPostsComment> comments = null;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPTime() {
        return pTime;
    }

    public void setPTime(String pTime) {
        this.pTime = pTime;
    }

    public String getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(String isPublic) {
        this.isPublic = isPublic;
    }

    public AllPostsUser getUser() {
        return user;
    }

    public void setUser(AllPostsUser user) {
        this.user = user;
    }

    public List<AllPostsLike> getLikes() {
        return likes;
    }

    public void setLikes(List<AllPostsLike> likes) {
        this.likes = likes;
    }

    public List<AllPostsComment> getComments() {
        return comments;
    }

    public void setComments(List<AllPostsComment> comments) {
        this.comments = comments;
    }

}
