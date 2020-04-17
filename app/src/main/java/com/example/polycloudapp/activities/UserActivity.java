package com.example.polycloudapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.polycloudapp.R;
import com.example.polycloudapp.helpers.UserHelper;
import com.example.polycloudapp.utils.UserUtils;

public class UserActivity extends BaseActivity {

    private TextView mTvUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        initView();
    }

    private void initView() {

        initNavBar(true, "个人中心", false);

        mTvUser = fd(R.id.tv_user);
        mTvUser.setText("当前用户: " + UserHelper.getInstance().getUsername());
    }

    /**
     * 修改密码点击事件
     */
    public void onChangePasswordClick(View view) {
        startActivity(new Intent(this, ChangePasswordActivity.class));
    }

    /**
     * 退出登录点击事件
     */
    public void onLogoutClick(View view) {
        UserUtils.logout(this);
    }
}
