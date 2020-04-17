package com.example.polycloudapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.polycloudapp.R;
import com.example.polycloudapp.beans.LoginStatusBean;
import com.example.polycloudapp.beans.RegisterMessageBean;
import com.example.polycloudapp.helpers.UserHelper;
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

public class ChangePasswordActivity extends BaseActivity {

    private InputView mOldPassword, mNewPassword, mNewPasswordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        initView();
    }

    private void initView() {

        initNavBar(true, "修改密码", false);

        mOldPassword = fd(R.id.input_old_password);
        mNewPassword = fd(R.id.input_password);
        mNewPasswordConfirm = fd(R.id.input_password_confirm);
    }

    public void onChangePasswordCommitClick (final View view) {

        String oldPassword = mOldPassword.getInputStr();
        String newPassword = mNewPassword.getInputStr();
        String newPasswordConfirm = mNewPasswordConfirm.getInputStr();

        boolean result = UserUtils.changePassword(this, oldPassword, newPassword, newPasswordConfirm);
        if (!result) return;

        OkHttpClient okHttpClient = new OkHttpClient();

        FormBody formBody = new FormBody.Builder()
                .add("username", UserHelper.getInstance().getUsername())
                .add("oldPassword", oldPassword)
                .add("newPassword", newPassword).build();

        Request.Builder builder = new Request.Builder();
        Request request = builder.url("http://192.168.1.11:8000/PolyCloudService/ChangePassword")
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
                        Toast.makeText(view.getContext(), "网络错误！", Toast.LENGTH_SHORT).show();
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
                        switch (result.getStatus()) {
                            case "userInfoError":

                                Toast.makeText(view.getContext(), "用户信息错误，请稍后重试！", Toast.LENGTH_SHORT).show();
                                break;
                            case "changeSuccess":

                                Toast.makeText(view.getContext(), "密码修改成功！", Toast.LENGTH_SHORT).show();
                                UserUtils.logout(view.getContext());
                                break;
                            case "oldPasswordValidateError":

                                Toast.makeText(view.getContext(), "原密码错误，请重新输入！", Toast.LENGTH_SHORT).show();
                                break;
                            case "changeFail":
                                Toast.makeText(view.getContext(), "密码修改失败，请稍后重试！", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
            }
        });

    }
}
