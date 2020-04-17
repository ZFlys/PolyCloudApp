package com.example.polycloudapp.views;

import android.graphics.Rect;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int mSpace;

    public GridSpaceItemDecoration (int space, RecyclerView parent) {

        mSpace = space;
        getRecyclerViewOffsets(parent);
    }

    /**
     *
     * @param outRect Item的矩形边界
     * @param view ItemView
     * @param parent RecyclerView
     * @param state RecyclerView的状态
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.left = mSpace;

        /*
        // 判断当前Item在每一行中是否为第一个,设置左边界为0
        if (parent.getChildLayoutPosition(view) % 3 == 0) {
            outRect.left = 0;
        }
         */
    }

    private void getRecyclerViewOffsets(RecyclerView parent) {

        // View margin: margin > 0,View与边界产生一个距离； margin < 0,View超出边界一个距离
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) parent.getLayoutParams();
        layoutParams.leftMargin = -mSpace;
        parent.setLayoutParams(layoutParams);
    }
}
