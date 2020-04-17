package com.example.polycloudapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.polycloudapp.R;
import com.example.polycloudapp.beans.LoginStatusBean;
import com.example.polycloudapp.helpers.UserHelper;
import com.example.polycloudapp.utils.SPUtils;
import com.example.polycloudapp.utils.UserUtils;
import com.example.polycloudapp.views.InputView;
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

// NavigationBar
public class LoginActivity extends BaseActivity {

    private InputView mInputUsername, mInputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    /**
     * 初始化View
     * */
    private void initView() {

        initNavBar(false, "登录", false);

        mInputUsername = fd(R.id.input_username);
        mInputPassword = fd(R.id.input_password);
    }


    /**
     * 登录按钮点击事件
     */
    public void onCommitClick(final View view) {

        final String username = mInputUsername.getInputStr();
        String password = mInputPassword.getInputStr();

        // 验证用户输入是否合法
        if (!UserUtils.validationLogin(this, username, password)) {
            return;
        }

        /*
        // 1、拿到okHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();

        // 2、构造Request
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url("http://192.168.1.11:8000/PolyCloudService/login").build();

        // 3、将Request封装为Call
        Call call = okHttpClient.newCall(request);

        // 4、执行Call
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                System.out.println("onFailure : login http call!");
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                final String res = Objects.requireNonNull(response.body()).string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mOkHttpTest.setText(res);
                    }
                });
            }
        });
        */

        // 1、拿到okHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();

        // 2、构造Request
        // 2.1 构造requestBody
        FormBody formBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password).build();

        Request.Builder builder = new Request.Builder();
        Request request = builder.url("http://192.168.1.11:8000/PolyCloudService/login")
                .post(formBody).build();

        // 3、将Request封装为Call
        Call call = okHttpClient.newCall(request);

        // 4、执行Call
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                System.out.println("onFailure : login http call!");
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(view.getContext(), "网络错误，请稍后重试！", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                final String res = Objects.requireNonNull(response.body()).string();

                Gson gson = new Gson();
                final LoginStatusBean result = gson.fromJson(res, LoginStatusBean.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!result.getStatus().equals("用户未注册")) {
                            if (result.getStatus().equals("验证失败")) {

                                Toast.makeText(view.getContext(), "密码错误！", Toast.LENGTH_SHORT).show();
                            } else if (result.getStatus().equals("验证成功")) {

                                // 保存用户登录标记
                                boolean isSave = SPUtils.saveUser(view.getContext(), username);
                                if (!isSave) {
                                    Toast.makeText(view.getContext(), "系统错误，请稍后重试！", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                UserHelper.getInstance().setUsername(username);

                                // 跳转到应用主页
                                Intent intent = new Intent(view.getContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        } else {

                            Toast.makeText(view.getContext(), "用户未注册！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    /**
     * 立即注册按钮跳转事件
     * @param view
     */
    public void onRegisterClick(View view) {

        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
