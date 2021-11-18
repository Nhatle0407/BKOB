package com.example.bkob.Singleton;

import com.example.bkob.models.UserModel;

public class UserSingleton {
    private static UserModel userModel;

    public static UserModel getUserModel() {
        return userModel;
    }

    public static void setUserModel(UserModel userModel) {
        UserSingleton.userModel = userModel;
    }
}
