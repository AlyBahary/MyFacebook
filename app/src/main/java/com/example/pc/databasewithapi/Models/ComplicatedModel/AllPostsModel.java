
package com.example.pc.databasewithapi.Models.ComplicatedModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllPostsModel {

    @SerializedName("posts")
    @Expose
    private List<AllPostsPost> posts = null;
    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("total_count")
    @Expose
    private int totalCount;

    public List<AllPostsPost> getPosts() {
        return posts;
    }

    public void setPosts(List<AllPostsPost> posts) {
        this.posts = posts;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

}
