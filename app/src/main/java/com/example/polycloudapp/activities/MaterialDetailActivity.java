package com.example.polycloudapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.polycloudapp.R;
import com.example.polycloudapp.adapters.MaterialListAdapter;
import com.example.polycloudapp.beans.MainPolymerNameBean;
import com.example.polycloudapp.beans.MaterialDetailInfoBean;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MaterialDetailActivity extends BaseActivity {

    public static final String MATERIAL_NO = "materialNo";

    private String mPostMaterialNo;
    private Context mContext;

    private TextView mMaterialName, mMaterialNo, mManufacturerOrComponent, mProcessingLevel, mFeature, mApplication, mMaterialType;
    private TextView mDensity, mMeltPoint;
    private TextView mMeltIndex, mHDT, mGlassTraTemp;
    private TextView mTensileStrength, mTensileModulus, mElongationAtBreak, mBendingStrength, mBendingModulus, mImpactStrength, mOthers;

    private LineChart rlLineChart, utLineChart, nirLineChart, ramanLineChart;

    private TextView mNoOrMethod, mIsManufacturer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_detail);

        mContext = this;
        mPostMaterialNo = getIntent().getStringExtra(MATERIAL_NO);
        initView();
        // 隐藏StatusBar
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void initView() {

        initNavBar(true, mPostMaterialNo, false);

        mNoOrMethod = fd(R.id.tv_no_or_method);
        mIsManufacturer = fd(R.id.is_manufacturer);

        if (mPostMaterialNo.contains("PPC")) {

            mNoOrMethod.setText("方法");
            mIsManufacturer.setText("配方");
        }

        mMaterialName = fd(R.id.tb1_tv_row1);
        mMaterialNo = fd(R.id.tb1_tv_row2);
        mManufacturerOrComponent = fd(R.id.tb1_tv_row3);
        mProcessingLevel = fd(R.id.tb1_tv_row4);
        mFeature = fd(R.id.tb1_tv_row5);
        mApplication = fd(R.id.tb1_tv_row6);
        mMaterialType = fd(R.id.tb1_tv_row7);

        mDensity = fd(R.id.tb2_tv_row1);
        mMeltPoint = fd(R.id.tb2_tv_row2);

        mMeltIndex = fd(R.id.tb3_tv_row1);
        mHDT = fd(R.id.tb3_tv_row2);
        mGlassTraTemp = fd(R.id.tb3_tv_row3);

        mTensileStrength = fd(R.id.tb4_tv_row1);
        mTensileModulus = fd(R.id.tb4_tv_row2);
        mElongationAtBreak = fd(R.id.tb4_tv_row3);
        mBendingStrength = fd(R.id.tb4_tv_row4);
        mBendingModulus = fd(R.id.tb4_tv_row5);
        mImpactStrength = fd(R.id.tb4_tv_row6);
        mOthers = fd(R.id.tb4_tv_row7);

        rlLineChart = fd(R.id.rheology_line_chart);
        utLineChart = fd(R.id.ultrasonic_line_chart);
        nirLineChart = fd(R.id.nir_line_chart);
        ramanLineChart = fd(R.id.raman_line_chart);

        OkHttpClient okHttpClient = new OkHttpClient();

        FormBody formBody = new FormBody.Builder()
                .add("materialNo", mPostMaterialNo).build();

        Request.Builder builder = new Request.Builder();
        Request request = builder.url("http://192.168.1.11:8000/PolyCloudService/GetMaterialDetail")
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
                final MaterialDetailInfoBean result = gson.fromJson(res, MaterialDetailInfoBean.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mMaterialName.setText(result.getMaterialDetail().get(0).getMaterialName());
                        mMaterialNo.setText(result.getMaterialDetail().get(0).getMaterialNo());
                        mManufacturerOrComponent.setText(result.getMaterialDetail().get(0).getManuOrComp());
                        mProcessingLevel.setText(result.getMaterialDetail().get(0).getProcessingLevel());
                        mFeature.setText(result.getMaterialDetail().get(0).getFeature());
                        mApplication.setText(result.getMaterialDetail().get(0).getApplication());
                        mMaterialType.setText(result.getMaterialDetail().get(0).getMaterialType());
                        mDensity.setText(result.getMaterialDetail().get(0).getDensity() + " g/cm3");
                        mMeltPoint.setText(result.getMaterialDetail().get(0).getMeltingPoint() + " ℃");
                        mMeltIndex.setText(result.getMaterialDetail().get(0).getMeltIndex() + " g/10min");
                        mHDT.setText(result.getMaterialDetail().get(0).getDistorionTemp() + " ℃");
                        mGlassTraTemp.setText(result.getMaterialDetail().get(0).getGlassTraTemp() + " ℃");
                        mTensileStrength.setText(result.getMaterialDetail().get(0).getTensileStrength() + " MPa");
                        mTensileModulus.setText(result.getMaterialDetail().get(0).getTensileModulus() + " MPa");
                        mElongationAtBreak.setText(result.getMaterialDetail().get(0).getElongationAtBreak());
                        mBendingModulus.setText(result.getMaterialDetail().get(0).getBendingModulus() + " MPa");
                        mBendingStrength.setText(result.getMaterialDetail().get(0).getBendingStrength() + " MPa");
                        mImpactStrength.setText(result.getMaterialDetail().get(0).getImpactStrength());
                        mOthers.setText(result.getMaterialDetail().get(0).getOthers());

                        initChart(rlLineChart);
                        showLineChart(rlLineChart, result.getRlFitData(), "流变特性曲线", Color.CYAN, null);
                        initChart(utLineChart);
                        showLineChart(utLineChart, result.getMaterialUt(), "超声谱图", Color.CYAN, null);
                        initChart(nirLineChart);
                        showLineChart(nirLineChart, result.getMaterialNir(), "近红外谱图", Color.CYAN, LineDataSet.Mode.LINEAR);
                        initChart(ramanLineChart);
                        showLineChart(ramanLineChart, result.getMaterialRaman(), "拉曼光谱图", Color.CYAN, LineDataSet.Mode.LINEAR);
                    }
                });
            }
        });
    }

    /**
     * 初始化图表
     */
    public void initChart(LineChart lineChart) {

        XAxis xAxis;                //X轴
        YAxis leftYAxis;            //左侧Y轴
        YAxis rightYaxis;           //右侧Y轴
        Legend legend;              //图例
        LimitLine limitLine;        //限制线

        /**图表设置***/
        // 是否展示网格线背景
        lineChart.setDrawGridBackground(false);
        // 是否显示边界
        lineChart.setDrawBorders(true);
        // 是否可以拖动
        lineChart.setDragEnabled(false);
        // 缩放
        lineChart.setScaleEnabled(true);
        // XY同时缩放
        lineChart.setPinchZoom(true);
        // 双击缩放
        lineChart.setDoubleTapToZoomEnabled(true);
        // 是否可以触摸
        lineChart.setTouchEnabled(true);
        // 设置XY轴动画效果
        lineChart.animateY(1500);
        lineChart.animateX(1500);

        /***XY轴的设置***/
        xAxis = lineChart.getXAxis();
        leftYAxis = lineChart.getAxisLeft();
        rightYaxis = lineChart.getAxisRight();
        // X轴设置显示位置在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        // xAxis.setAxisMinimum(0f);
        // xAxis.setGranularity(1f);
        // 保证Y轴从0开始，不然会上移一点
        // leftYAxis.setAxisMinimum(0f);
        // rightYaxis.setAxisMinimum(0f);

        // 折线图例 标签 设置
        legend = lineChart.getLegend();
        legend.setEnabled(false);
    }

    /**
     * 曲线初始化设置 一个LineDataSet 代表一条曲线
     *
     * @param lineDataSet 线条
     * @param color       线条颜色
     * @param mode
     */
    public void initLineDataSet(LineDataSet lineDataSet, int color, LineDataSet.Mode mode) {

        lineDataSet.setColor(color);
        lineDataSet.setCircleColor(color);
        lineDataSet.setLineWidth(1f);
        lineDataSet.setCircleRadius(1f);
        // 设置曲线值的圆点是实心还是空心
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setValueTextSize(10f);
        // 设置折线图填充
        lineDataSet.setDrawFilled(false);
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setFormSize(15.f);
        lineDataSet.setDrawValues(true);
        if (mode == null) {
            // 设置曲线展示为圆滑曲线（如果不设置则默认折线）
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        } else {
            lineDataSet.setMode(mode);
        }
    }

    /**
     * 展示曲线
     *
     * @param dataList 数据集合
     * @param name     曲线名称
     * @param color    曲线颜色
     */
    public void showLineChart(LineChart lineChart, List<List<String>> dataList, String name, int color, LineDataSet.Mode mode) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {

            List<String> data = dataList.get(i);
            Entry entry = new Entry(Float.parseFloat(data.get(0)), Float.parseFloat(data.get(1)));
            entries.add(entry);
        }
        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries, name);
        initLineDataSet(lineDataSet, color, mode);
        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
    }
}
