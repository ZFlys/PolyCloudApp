package com.example.polycloudapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.blankj.utilcode.util.StringUtils;
import com.example.polycloudapp.R;
import com.example.polycloudapp.beans.RegisterMessageBean;
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

public class RegisterActivity extends BaseActivity {

    private InputView mInputUserName, mInputPassword, mInputPasswordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {

        initNavBar(true, "注册", false);

        mInputUserName = fd(R.id.input_username);
        mInputPassword = fd(R.id.input_password);
        mInputPasswordConfirm = fd(R.id.input_password_confirm);
    }

    /**
     * 注册按钮点击事件
     * 1、用户输入合法性验证
     *  1.1、用户输入的用户名是否合法
     *  1.2、用户输入的用户名是否已经被注册
     *  1.3、用户是否输入密码和确认密码，两个密码是否相同
     * 2、保存用户输入的用户名和密码至数据库（密码使用加密）
     */
    public void onRegisterClick(final View view) {

        String userName = mInputUserName.getInputStr();
        String password = mInputPassword.getInputStr();
        String passwordConfirm = mInputPasswordConfirm.getInputStr();

        boolean result = UserUtils.registerUser(this, userName, password, passwordConfirm);
        if (!result) return;

        OkHttpClient okHttpClient = new OkHttpClient();

        FormBody formBody = new FormBody.Builder()
                .add("username", userName)
                .add("password", password).build();

        Request.Builder builder = new Request.Builder();
        Request request = builder.url("http://192.168.1.11:8000/PolyCloudService/register")
                .post(formBody).build();

        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                System.out.println("onFailure : register http call!");
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
                final RegisterMessageBean result = gson.fromJson(res, RegisterMessageBean.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (result.getMessage()) {
                            case "userExisted":

                                Toast.makeText(view.getContext(), "用户已经存在，请重新输入用户名！", Toast.LENGTH_SHORT).show();
                                break;
                            case "registerSuccess":

                                Toast.makeText(view.getContext(), "注册成功！", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                                break;
                            case "registerError":

                                Toast.makeText(view.getContext(), "注册失败，请稍后重试！", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
            }
        });
    }
}
