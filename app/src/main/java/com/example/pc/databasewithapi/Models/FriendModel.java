
package com.example.pc.databasewithapi.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FriendModel {

    @SerializedName("friends")
    @Expose
    private List<Friend> friends = null;
    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("total_count")
    @Expose
    private String totalCount;

    public List<Friend> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

}
