package com.example.polycloudapp.helpers;

/**
 * 1、用户登录
 *  1.1、当用户登录时，利用SharedPreferences保存用户的用户标记（username）
 *  1.2、利用全局单例类UserHelper保存登录用户信息
 *      1.2.1、用户登录之后保存信息
 *      1.2.2、用户打开应用程序，检测SharedPreferences中是否存在登录用户标记，
 *              若存在，则为UserHelper进行赋值，并进入主页
 *              若不存在，则进入登录页面
 * 2、用户退出
 *  2.1、删除SharedPreferences保存的用户标记，退出到登录页面
 */
public class UserHelper {

    private static UserHelper instance;

    private UserHelper () {};

    public static UserHelper getInstance() {

        if (instance == null) {

            synchronized (UserHelper.class) {

                if (instance == null) {

                    instance = new UserHelper();
                }
            }
        }

        return instance;
    }

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
