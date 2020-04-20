package com.example.polycloudapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.polycloudapp.R;

public class MoreActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        initView();
    }

    private void initView () {

        initNavBar(true, "PloyCloud", false);
    }

    public void onCompositionMonitorClick (View view) {
        startActivity(new Intent(this, CompositionMonitorActivity.class));
    }
}
