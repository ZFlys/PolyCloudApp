package com.example.polycloudapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
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

    private LineChart lineChart;
    private XAxis xAxis;                //X轴
    private YAxis leftYAxis;            //左侧Y轴
    private YAxis rightYaxis;           //右侧Y轴
    private Legend legend;              //图例
    private LimitLine limitLine;        //限制线

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_detail);

        mContext = this;
        initView();
        // 隐藏StatusBar
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void initView() {

        initNavBar(true, "材料详情", false);

        mPostMaterialNo = getIntent().getStringExtra(MATERIAL_NO);

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
                        mDensity.setText(result.getMaterialDetail().get(0).getDensity());
                        mMeltPoint.setText(result.getMaterialDetail().get(0).getMeltingPoint());
                        mMeltIndex.setText(result.getMaterialDetail().get(0).getMeltIndex());
                        mHDT.setText(result.getMaterialDetail().get(0).getDistorionTemp());
                        mGlassTraTemp.setText(result.getMaterialDetail().get(0).getGlassTraTemp());
                        mTensileStrength.setText(result.getMaterialDetail().get(0).getTensileStrength());
                        mTensileModulus.setText(result.getMaterialDetail().get(0).getTensileModulus());
                        mElongationAtBreak.setText(result.getMaterialDetail().get(0).getElongationAtBreak());
                        mBendingModulus.setText(result.getMaterialDetail().get(0).getBendingModulus());
                        mBendingStrength.setText(result.getMaterialDetail().get(0).getBendingStrength());
                        mImpactStrength.setText(result.getMaterialDetail().get(0).getImpactStrength());
                        mOthers.setText(result.getMaterialDetail().get(0).getOthers());
                    }
                });
            }
        });
    }

    /**
     * 初始化图表
     */
    private void initChart(LineChart lineChart) {
        /**图表设置***/
        // 是否展示网格线
        lineChart.setDrawGridBackground(false);
        // 是否显示边界
        lineChart.setDrawBorders(true);
        // 是否可以拖动
        lineChart.setDragEnabled(false);
        // 是否有触摸事件
        lineChart.setTouchEnabled(true);
        // 设置XY轴动画效果
        lineChart.animateY(2500);
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

        /***折线图例 标签 设置**
        legend = lineChart.getLegend();
        //设置显示类型，LINE CIRCLE SQUARE EMPTY 等等 多种方式，查看LegendForm 即可
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(12f);
        //显示位置 左下方
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //是否绘制在图表里面
        legend.setDrawInside(false);
         */
    }

    /**
     * 曲线初始化设置 一个LineDataSet 代表一条曲线
     *
     * @param lineDataSet 线条
     * @param color       线条颜色
     * @param mode
     */
    private void initLineDataSet(LineDataSet lineDataSet, int color, LineDataSet.Mode mode) {

        lineDataSet.setColor(color);
        lineDataSet.setCircleColor(color);
        lineDataSet.setLineWidth(1f);
        lineDataSet.setCircleRadius(3f);
        // 设置曲线值的圆点是实心还是空心
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setValueTextSize(10f);
        // 设置折线图填充
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setFormSize(15.f);
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
    public void showLineChart(List<?> dataList, String name, int color) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            // IncomeBean data = dataList.get(i);
            /**
             * 在此可查看 Entry构造方法，可发现 可传入数值 Entry(float x, float y)
             * 也可传入Drawable， Entry(float x, float y, Drawable icon) 可在XY轴交点 设置Drawable图像展示
             */
            // Entry entry = new Entry(i, (float) data.getValue());
            // entries.add(entry);
        }
        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries, name);
        initLineDataSet(lineDataSet, color, LineDataSet.Mode.LINEAR);
        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
    }
}
