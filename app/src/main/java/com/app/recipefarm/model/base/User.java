package com.app.recipefarm.model.base;

import static com.app.recipefarm.utility.Constants.PROFILE_IMAGES;

public class User {
    public String userId;
    public String email;
    public String username;
    public String password;
    public String bio;
    public String profileImage;
    public String profileImageExt;

    public User(String userId, String email, String username, String password, String bio, String profileImage, String profileImageExt) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.password = password;
        this.bio = bio;
        this.profileImage = profileImage;
        this.profileImageExt = profileImageExt;
    }

    public String getImagePath() {
        return PROFILE_IMAGES + "/" + profileImage + "." + profileImageExt;
    }
}
