package com.example.polycloudapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.polycloudapp.R;
import com.example.polycloudapp.activities.BaseActivity;
import com.example.polycloudapp.adapters.MaterialListAdapter;
import com.example.polycloudapp.adapters.PolymerGridAdapter;
import com.example.polycloudapp.beans.MainPolymerNameBean;
import com.example.polycloudapp.beans.SearchMaterialBean;
import com.example.polycloudapp.views.GridSpaceItemDecoration;
import com.github.florent37.awesomebar.ActionItem;
import com.github.florent37.awesomebar.AwesomeBar;
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
import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.SearchView;
import scut.carson_ho.searchview.bCallBack;

public class MainActivity extends BaseActivity {

    private RecyclerView mRvGrid, mRvList;
    private PolymerGridAdapter mGridAdapter;
    private MaterialListAdapter mListAdapter;
    private Context mContext;
    private SearchView searchView;
    private AwesomeBar awesomeBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        initView();
    }

    private void initView() {

        initNavBar(false, "PolyCloud", true);
        searchView = (SearchView) fd(R.id.search_view);
        awesomeBar = (AwesomeBar) fd(R.id.awesomeBar);
        awesomeBar.addAction(R.drawable.awsb_ic_edit_animated, "More");

        // 1、拿到okHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();

        // 2、构造Request
        Request.Builder builder = new Request.Builder();
        Request request = builder.get()
                .url("http://192.168.1.11:8000/PolyCloudService/GenPolymerData").build();

        // 3、将Request封装为Call
        Call call = okHttpClient.newCall(request);

        // 4、执行Call
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                System.out.println("onFailure : main activity get data http call!");
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
                        mRvGrid = fd(R.id.rv_grid);
                        mRvGrid.setLayoutManager(new GridLayoutManager(getBaseContext(), 3));
                        mRvGrid.addItemDecoration(new GridSpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.polymerMarginSize), mRvGrid));
                        mRvGrid.setNestedScrollingEnabled(false);
                        mGridAdapter = new PolymerGridAdapter(mContext, result.getGenPolymer());
                        mRvGrid.setAdapter(mGridAdapter);

                        /**
                         * mRvList.setNestedScrollingEnabled(false); 设置为false后导致滑动高度计算不准确
                         * 解决方法一：若已知列表高度，可直接在布局中设置RecyclerView的高度
                         * 解决方法二：若未知列表高度，可以在PolymerListAdapter中手动计算
                         */
                        mRvList = fd(R.id.rv_list);
                        mRvList.setLayoutManager(new LinearLayoutManager(mContext));
                        mRvList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
                        mRvList.setNestedScrollingEnabled(false);
                        mListAdapter = new MaterialListAdapter(mContext, mRvList, result.getSpecialPolymer(), "热敏性复合材料");
                        mRvList.setAdapter(mListAdapter);
                    }
                });
            }
        });

        // 设置点击键盘上的搜索按键后的操作（通过回调接口）
        // 参数 = 搜索框输入的内容
        searchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {

                OkHttpClient okHttpClient = new OkHttpClient();

                FormBody formBody = new FormBody.Builder()
                        .add("searchName", string).build();

                Request.Builder builder = new Request.Builder();
                Request request = builder.url("http://192.168.1.11:8000/PolyCloudService/SearchMaterial")
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
                    public void onResponse(@NotNull final Call call, @NotNull Response response) throws IOException {

                        final String res = Objects.requireNonNull(response.body()).string();

                        Gson gson = new Gson();
                        final SearchMaterialBean result = gson.fromJson(res, SearchMaterialBean.class);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                switch (result.getStatus()) {

                                    case "emptyError":
                                        Toast.makeText(mContext, "输入内容不能为空！", Toast.LENGTH_SHORT).show();
                                        break;
                                    case "emptyMaterial":
                                        Toast.makeText(mContext, "该材料未收录！", Toast.LENGTH_SHORT).show();
                                        break;
                                    case "success":
                                        Intent intent = new Intent(mContext, MaterialDetailActivity.class);
                                        intent.putExtra(MaterialDetailActivity.MATERIAL_NO, result.getMaterialName());
                                        mContext.startActivity(intent);
                                        break;
                                }
                            }
                        });
                    }
                });
            }
        });

        // 设置点击返回按键后的操作（通过回调接口）
        searchView.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
                return;
            }
        });

        awesomeBar.setActionItemClickListener(new AwesomeBar.ActionItemClickListener() {
            @Override
            public void onActionItemClicked(int position, ActionItem actionItem) {
                if (position == 0) {

                    startActivity(new Intent(mContext, MoreActivity.class));
                }
            }
        });
    }
}
