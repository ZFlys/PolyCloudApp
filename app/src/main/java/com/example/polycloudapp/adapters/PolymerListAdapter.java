package com.example.polycloudapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.polycloudapp.R;
import com.example.polycloudapp.activities.MaterialDetailActivity;
import com.example.polycloudapp.beans.MainPolymerNameBean;

import java.util.List;

public class PolymerListAdapter extends RecyclerView.Adapter<PolymerListAdapter.ViewHolder> {

    private Context mContext;
    private View mItemView;
    private RecyclerView mRv;
    private boolean isCalculationRvHeight;
    private String mMaterialTag;
    private List<MainPolymerNameBean.SpecialPolymerBean> mSpecialPolymerName;

    public PolymerListAdapter (Context context, RecyclerView recyclerView, List<MainPolymerNameBean.SpecialPolymerBean> specialPolymerName, String materialTag) {
        mContext = context;
        mRv = recyclerView;
        mMaterialTag = materialTag;
        this.mSpecialPolymerName = specialPolymerName;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mItemView = LayoutInflater.from(mContext).inflate(R.layout.item_list_polymer, parent,false);
        return new ViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        setRecyclerViewHeight();

        final MainPolymerNameBean.SpecialPolymerBean mSpecialPolymer = mSpecialPolymerName.get(position);

        holder.mTvMethod.setText(mSpecialPolymer.getMethod());
        holder.mTvPolyCategory.setText(mMaterialTag);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, MaterialDetailActivity.class);
                intent.putExtra(MaterialDetailActivity.MATERIAL_NO, mSpecialPolymer.getMethod());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSpecialPolymerName.size();
    }

    /**
     * 1、获取ItemView的高度
     * 2、获取itemView的数量
     * 3、recyclerViewHeight = itemViewHeight * itemViewNum
     */
    private void setRecyclerViewHeight() {

        // 只需要计算一次
        if (isCalculationRvHeight || mRv == null) return;

        isCalculationRvHeight = true;

        // 获取ItemView的高度
        RecyclerView.LayoutParams itemViewLp = (RecyclerView.LayoutParams) mItemView.getLayoutParams();
        // 获取itemView的数量
        int itemCount = getItemCount();
        // recyclerViewHeight = itemViewHeight * itemViewNum
        int recyclerViewHeight = itemViewLp.height * itemCount;
        // 设置RecyclerView的高度
        LinearLayout.LayoutParams rvLp = (LinearLayout.LayoutParams) mRv.getLayoutParams();
        rvLp.height = recyclerViewHeight;
        mRv.setLayoutParams(rvLp);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        ImageView ivIcon;
        TextView mTvMethod;
        TextView mTvPolyCategory;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView = itemView;
            ivIcon = itemView.findViewById(R.id.iv_icon);
            mTvMethod = itemView.findViewById(R.id.tv_method);
            mTvPolyCategory = itemView.findViewById(R.id.tv_polymer_category);
        }
    }
}
