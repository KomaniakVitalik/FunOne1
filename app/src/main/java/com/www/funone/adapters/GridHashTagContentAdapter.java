package com.www.funone.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.www.funone.R;

/**
 * Created by vitaliy.herasymchuk on 6/26/16.
 */
public class GridHashTagContentAdapter extends
        RecyclerView.Adapter<GridHashTagContentAdapter.ImageItemHolder> {

    @Override
    public ImageItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ImageItemHolder(this, view);
    }

    @Override
    public void onBindViewHolder(ImageItemHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 25;
    }

    static class ImageItemHolder extends RecyclerView.ViewHolder {
        GridHashTagContentAdapter adapter;

        public ImageItemHolder(GridHashTagContentAdapter adapter, View itemView) {
            super(itemView);
            this.adapter = adapter;
        }
    }

}
