package com.www.funone.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by vitaliy.herasymchuk on 6/26/16.
 */
public class HashTagRecyclerAdapter extends RecyclerView.Adapter<HashTagRecyclerAdapter.HasTagViewHolder>{

    @Override
    public HasTagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate();
        return null;
    }

    @Override
    public void onBindViewHolder(HasTagViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class HasTagViewHolder extends RecyclerView.ViewHolder{

        public HasTagViewHolder(View itemView) {
            super(itemView);
        }
    }
}
