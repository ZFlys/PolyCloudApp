package com.example.polycloudapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.polycloudapp.R;
import com.example.polycloudapp.activities.MaterialListActivity;
import com.example.polycloudapp.activities.PolymerListActivity;
import com.example.polycloudapp.beans.MainPolymerNameBean;

import java.util.List;

public class PolymerGridAdapter extends RecyclerView.Adapter <PolymerGridAdapter.ViewHolder> {

    private Context mContext;
    private List<MainPolymerNameBean.GenPolymerBean> mPolymerName;

    public PolymerGridAdapter (Context context, List<MainPolymerNameBean.GenPolymerBean> polymerName) {
        mContext = context;
        this.mPolymerName = polymerName;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_grid_polymer, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final MainPolymerNameBean.GenPolymerBean mGenPolymerName = mPolymerName.get(position);

        Glide.with(mContext)
                .load(getResource(mContext, mGenPolymerName.getPoster()))
                .into(holder.ivIcon);

        holder.mTvPolymerName.setText(mGenPolymerName.getPolymerName());
        holder.mTvMaterialNum.setText(mGenPolymerName.getKindnum());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MaterialListActivity.class);
                intent.putExtra(MaterialListActivity.MATERIAL_NAME, mGenPolymerName.getPolymerName());
                mContext.startActivity(intent);
                /*
                Intent intent = new Intent(mContext, PolymerListActivity.class);
                intent.putExtra(PolymerListActivity.POLYMER_NAME, mGenPolymerName.getPolymerName());
                mContext.startActivity(intent);
                 */
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPolymerName.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivIcon;
        View itemView;
        TextView mTvMaterialNum, mTvPolymerName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView = itemView;
            ivIcon = itemView.findViewById(R.id.iv_icon);
            mTvPolymerName = itemView.findViewById(R.id.tv_polymer_name);
            mTvMaterialNum = itemView.findViewById(R.id.tv_material_num);
        }
    }

    /**
     * 将字符串转换为图片资源id
     * @param context
     * @param imageName
     * @return
     */
    public int getResource(Context context, String imageName){
        int resId = context.getResources().getIdentifier(imageName, "mipmap", context.getPackageName());
        //如果没有在"mipmap"下找到imageName,将会返回0
        return resId;
    }
}
