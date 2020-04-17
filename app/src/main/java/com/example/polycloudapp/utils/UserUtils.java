package com.example.polycloudapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.example.polycloudapp.R;
import com.example.polycloudapp.activities.LoginActivity;
import com.example.polycloudapp.activities.MainActivity;
import com.example.polycloudapp.beans.LoginStatusBean;
import com.example.polycloudapp.beans.RegisterMessageBean;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserUtils {

    /**
     * 验证登录用户输入合法性
     */
    public static boolean validationLogin(Context context, String username, String password) {

        if (!RegexUtils.isUsername(username)) {
            Toast.makeText(context, "无效用户名！", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(context, "请输入密码！", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    /**
     * 退出登录
     */
    public static void logout(Context context) {

        // 删除SharedPreferences保存的用户标记
        boolean result = SPUtils.removeSPUser(context);
        if (!result) {
            Toast.makeText(context, "系统错误，请稍后重试！", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(context, LoginActivity.class);
        // 添加Intent标识符，清理task栈，并生成新的task栈
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        // 需要重新调用Activity跳转执行动画
        ((Activity)context).overridePendingTransition(R.anim.open_enter, R.anim.open_exit);
    }

    /**
     * 注册用户
     * @param context
     * @param userName
     * @param password
     * @param passwordConfirm
     */
    public static boolean registerUser (final Context context, String userName, String password, String passwordConfirm) {

        if (!RegexUtils.isUsername(userName)) {
            Toast.makeText(context, "请输入6-20位用户名，且不能以_结尾！", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (StringUtils.isEmpty(password) || !password.equals(passwordConfirm)) {
            Toast.makeText(context, "请确认密码！", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    /**
     * 验证是否存在已登录用户
     */
    public static boolean validateUserLogin (Context context) {
        return SPUtils.isLoginUser(context);
    }

    /**
     * 修改密码数据验证
     * @param context
     * @param oldPassword
     * @param newPassword
     * @param newPasswordConfirm
     * @return
     */
    public static boolean changePassword (Context context, String oldPassword, String newPassword, String newPasswordConfirm) {

        if (TextUtils.isEmpty(oldPassword)) {
            Toast.makeText(context, "请输入原密码！", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(newPassword) || !newPassword.equals(newPasswordConfirm)) {
            Toast.makeText(context, "请确认新密码输入正确！", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}

