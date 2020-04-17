package com.example.polycloudapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.polycloudapp.constants.SPConstants;
import com.example.polycloudapp.helpers.UserHelper;

public class SPUtils {

    /**
     * 当用户登录时，利用SharedPreferences保存用户的用户标记（username）
     */
    public static boolean saveUser (Context context, String username) {

        SharedPreferences sp = context.getSharedPreferences(SPConstants.SP_NAME_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(SPConstants.SP_KEY_USERNAME, username);
        boolean result = editor.commit();

        return result;
    }

    /**
     * 验证是否存在已登录用户
     */
    public static boolean isLoginUser (Context context) {

        boolean result = false;

        SharedPreferences sp = context.getSharedPreferences(SPConstants.SP_NAME_USER, Context.MODE_PRIVATE);
        String username = sp.getString(SPConstants.SP_KEY_USERNAME, "");

        if (!TextUtils.isEmpty(username)) {

            result = true;
            UserHelper.getInstance().setUsername(username);
        }

        return result;
    }

    /**
     * 删除用户标记
     */
    public static boolean removeSPUser(Context context) {

        SharedPreferences sp = context.getSharedPreferences(SPConstants.SP_NAME_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(SPConstants.SP_KEY_USERNAME);
        boolean result = editor.commit();
        return result;
    }
}
