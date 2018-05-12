
package com.example.pc.databasewithapi.Models.getFriendRequestModel;

import java.util.List;

import com.example.pc.databasewithapi.FriendRequest;
import com.example.pc.databasewithapi.Models.Friend;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FriendRequestModel {

    @SerializedName("friend_requests")
    @Expose
    private List<Friend> friendRequests = null;
    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("total_count")
    @Expose
    private String totalCount;

    public List<Friend> getFriendRequests() {
        return friendRequests;
    }

    public void setFriendRequests(List<Friend> friendRequests) {
        this.friendRequests = friendRequests;
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
