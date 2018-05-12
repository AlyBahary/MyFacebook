
package com.example.pc.databasewithapi.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("post_id")
    @Expose
    private String postId;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("caption")
    @Expose
    private String caption;
    @SerializedName("is_public")
    @Expose
    private String isPublic;
    @SerializedName("p_time")
    @Expose
    private String pTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(String isPublic) {
        this.isPublic = isPublic;
    }

    public String getPTime() {
        return pTime;
    }

    public void setPTime(String pTime) {
        this.pTime = pTime;
    }

}
