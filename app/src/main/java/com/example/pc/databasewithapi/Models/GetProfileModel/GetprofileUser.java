
package com.example.pc.databasewithapi.Models.GetProfileModel;

import java.util.List;

import com.example.pc.databasewithapi.Models.Post;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetprofileUser {

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
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("birth_date")
    @Expose
    private String birthdate;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    @SerializedName("p_picture")
    @Expose
    private String pPicture;
    @SerializedName("posts")
    @Expose
    private List<Post> posts = null;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPPicture() {
        return pPicture;
    }

    public void setPPicture(String pPicture) {
        this.pPicture = pPicture;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

}
