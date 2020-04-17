package com.example.polycloudapp.activities;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.polycloudapp.R;
import com.example.polycloudapp.adapters.PolymerListAdapter;
import com.example.polycloudapp.beans.MainPolymerNameBean;
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

public class PolymerListActivity extends BaseActivity {

    public static final String  POLYMER_NAME = "polymerId";

    private Context mContext;
    private RecyclerView mRvList;
    private PolymerListAdapter mListAdapter;
    private String mPolymerName;
    private String mMaterialName;
    private TextView mTvMaterialTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polymer_list);

        mContext = this;
        initView();
    }

    private void initView () {

        initNavBar(true, "材料列表", false);

        mPolymerName = getIntent().getStringExtra(POLYMER_NAME);
        mMaterialName = getIntent().getStringExtra(MaterialListActivity.MATERIAL_NAME);

        mTvMaterialTitle = fd(R.id.tv_material_title);
        mTvMaterialTitle.setText(mMaterialName);

        OkHttpClient okHttpClient = new OkHttpClient();

        FormBody formBody = new FormBody.Builder()
                .add("polymerName", mPolymerName).build();

        Request.Builder builder = new Request.Builder();
        Request request = builder.url("http://192.168.1.11:8000/PolyCloudService/GetMaterialNo")
                .post(formBody).build();

        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                System.out.println("onFailure : http call!");
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mContext, "网络错误，请稍后重试！", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                final String res = Objects.requireNonNull(response.body()).string();

                Gson gson = new Gson();
                final MainPolymerNameBean result = gson.fromJson(res, MainPolymerNameBean.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        mRvList = fd(R.id.rv_list);
                        mRvList.setLayoutManager(new LinearLayoutManager(mContext));
                        mRvList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
                        mListAdapter = new PolymerListAdapter(mContext, null, result.getSpecialPolymer(), mPolymerName);
                        mRvList.setAdapter(mListAdapter);
                    }
                });
            }
        });
/*
        mRvList = fd(R.id.rv_list);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mListAdapter = new PolymerListAdapter(this, null);
        mRvList.setAdapter(mListAdapter);

 */
    }

}
