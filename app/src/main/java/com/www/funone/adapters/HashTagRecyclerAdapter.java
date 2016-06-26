package com.www.funone.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.www.funone.R;
import com.www.funone.model.HashTag;
import com.www.funone.util.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vitaliy.herasymchuk on 6/26/16.
 */
public class HashTagRecyclerAdapter extends RecyclerView.Adapter<HashTagRecyclerAdapter.HasTagViewHolder> {

    private List<HashTag> mHashTags = new ArrayList<>();
    private OnHashTagClickListener mOnHashTagClickListener = null;

    public HashTagRecyclerAdapter(List<HashTag> mHashTags) {
        this.mHashTags = mHashTags;
    }

    @Override
    public HasTagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hash_tag, parent, false);
        return new HasTagViewHolder(this, view);
    }

    @Override
    public void onBindViewHolder(HasTagViewHolder holder, int position) {
        holder.bind(mHashTags.get(position));
    }

    @Override
    public int getItemCount() {
        return mHashTags.size();
    }

    static class HasTagViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        HashTag tag;
        HashTagRecyclerAdapter adapter;
        TextView tvHashName;
        TextView tvPostsNumber;

        public HasTagViewHolder(HashTagRecyclerAdapter adapter, View itemView) {
            super(itemView);
            itemView.findViewById(R.id.lin_hashtag_wrapper).setOnClickListener(this);
            this.adapter = adapter;
            this.tvHashName = (TextView) itemView.findViewById(R.id.tv_hash_name);
            this.tvPostsNumber = (TextView) itemView.findViewById(R.id.tv_posts_number);
        }

        public void bind(HashTag hashTag) {
            this.tag = hashTag;
            if (Validator.isObjectValid(hashTag)) {
                tvHashName.setText(Validator.stringNotNull(hashTag.getName()));
                tvPostsNumber.setText(Validator
                        .stringNotNull(String.valueOf(hashTag.getPostsNumber())));
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.lin_hashtag_wrapper:
                    adapter.notifyHashTagSelected(tag);
                    break;
            }
        }
    }


    public void setOnHashTagClickListener(OnHashTagClickListener onHashTagClickListener) {
        this.mOnHashTagClickListener = onHashTagClickListener;
    }

    private void notifyHashTagSelected(HashTag tag) {
        if (Validator.isObjectValid(mOnHashTagClickListener)) {
            if (Validator.isObjectValid(tag)) {
                mOnHashTagClickListener.onHashTagClicked(tag);
            }
        }
    }

    public interface OnHashTagClickListener {
        void onHashTagClicked(HashTag tag);
    }
}
