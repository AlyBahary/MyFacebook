
package com.example.pc.databasewithapi.Models.GetProfileModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetprofileModel {

    @SerializedName("user")
    @Expose
    private GetprofileUser user;
    @SerializedName("status")
    @Expose
    private boolean status;

    public GetprofileUser getUser() {
        return user;
    }

    public void setUser(GetprofileUser user) {
        this.user = user;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
