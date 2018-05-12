
package com.example.pc.databasewithapi.Models.ComplicatedModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllPostsLike {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("f_name")
    @Expose
    private String fName;
    @SerializedName("l_name")
    @Expose
    private String lName;
    @SerializedName("n_name")
    @Expose
    private String nName;
    @SerializedName("p_picrure")
    @Expose
    private String pPicrure;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getLName() {
        return lName;
    }

    public void setLName(String lName) {
        this.lName = lName;
    }

    public String getNName() {
        return nName;
    }

    public void setNName(String nName) {
        this.nName = nName;
    }

    public String getPPicrure() {
        return pPicrure;
    }

    public void setPPicrure(String pPicrure) {
        this.pPicrure = pPicrure;
    }

}
