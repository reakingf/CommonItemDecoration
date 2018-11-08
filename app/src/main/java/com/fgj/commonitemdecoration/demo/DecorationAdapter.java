package com.fgj.commonitemdecoration.demo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fgj.commonitemdecoration.R;

import java.util.List;

/**
 * Created by FGJ on 2018/8/27.
 */
public class DecorationAdapter extends RecyclerView.Adapter<DecorationAdapter.ViewHolder> {

    private final LayoutInflater mLayoutInflater;
    private List<DecorationEnum> mData;

    private OnItemClickListener itemClickListener;

    public DecorationAdapter(Context context, List<DecorationEnum> data) {
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.main_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(mData.get(position).name);
        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public DecorationEnum getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_item);
        }
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

}

