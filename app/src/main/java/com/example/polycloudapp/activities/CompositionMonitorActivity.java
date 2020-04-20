package com.example.polycloudapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.example.polycloudapp.R;
import com.example.polycloudapp.beans.MonitorDataBean;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CompositionMonitorActivity extends BaseActivity {

    private static int TAG = 1;

    private Context mContext;

    private LineChart nirOnlineChart, compositionOnlineMonitorChart;
    private TextView mMonitorResult;

    private Thread mThread;
    private Handler mHandler;
    private List<Entry> mEntries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composition_monitor);

        mContext = this;
        initView();
    }

    private void initView () {

        initNavBar(true, "组分含量监测", false);

        mMonitorResult = fd(R.id.tb_result_tv_row1);

        nirOnlineChart = fd(R.id.nir_online_chart);
        compositionOnlineMonitorChart = fd(R.id.composition_online_monitor_chart);

        initChart(nirOnlineChart);
        initChart(compositionOnlineMonitorChart);
    }

    /**
     * 获取实时监测数据按钮
     * @param view
     */
    public void onGetMonitorDataClick(View view) {

        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int xPoint = 0;
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    OkHttpClient okHttpClient = new OkHttpClient();
                    FormBody formBody = new FormBody.Builder().add("xIndex", Integer.toString(xPoint)).build();
                    Request.Builder builder = new Request.Builder();
                    Request request = builder.url("http://192.168.1.11:8000/PolyCloudService/GetMonitorData")
                            .post(formBody).build();
                    Call call = okHttpClient.newCall(request);
                    try {
                        Response response = call.execute();
                        final String res = Objects.requireNonNull(response.body()).string();
                        if (res != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Gson gson = new Gson();
                                    MonitorDataBean result = gson.fromJson(res, MonitorDataBean.class);
                                    showLineChart(nirOnlineChart, result.getNirMonitorData(), "近红外谱图", Color.CYAN, LineDataSet.Mode.LINEAR);

                                    mEntries.add(new Entry(Integer.parseInt(result.getCompositionMonitorData().get(0)),
                                            Float.parseFloat(result.getCompositionMonitorData().get(1))));
                                    LineDataSet lineDataSet = new LineDataSet(mEntries, "实时监测曲线");
                                    initLineDataSet(lineDataSet, Color.CYAN, LineDataSet.Mode.LINEAR);
                                    LineData lineData = new LineData(lineDataSet);
                                    compositionOnlineMonitorChart.setData(lineData);
                                    compositionOnlineMonitorChart.invalidate();

                                    mMonitorResult.setText(result.getCompositionMonitorData().get(1));
                                }
                            });
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    xPoint += 1;
                }
            }
        });

        mThread.start();
    }

    /**
     * 初始化图表
     */
    public void initChart(LineChart lineChart) {

        XAxis xAxis;                //X轴
        Legend legend;              //图例

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
        // X轴设置显示位置在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
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
        lineChart.invalidate();
    }
}
