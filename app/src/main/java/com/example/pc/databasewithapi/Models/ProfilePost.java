
package com.example.pc.databasewithapi.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfilePost {

    @SerializedName("caption")
    @Expose
    private String caption;
    @SerializedName("image")
    @Expose
    private Object image;
    @SerializedName("p_time")
    @Expose
    private String pTime;
    @SerializedName("is_public")
    @Expose
    private String isPublic;
    @SerializedName("post_id")
    @Expose
    private String postId;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
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

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

}
