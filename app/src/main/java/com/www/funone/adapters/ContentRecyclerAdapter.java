package com.www.funone.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.www.funone.R;
import com.www.funone.model.Post;
import com.www.funone.util.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vitaliy.herasymchuk on 6/27/16.
 */
public class ContentRecyclerAdapter extends RecyclerView.Adapter<ContentRecyclerAdapter.ContentPostViewHolder> {

    private List<Post> mPostsList = new ArrayList<>();

    public ContentRecyclerAdapter(List<Post> mPostsList) {
        this.mPostsList = mPostsList;
    }

    @Override
    public ContentPostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new ContentPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContentPostViewHolder holder, int position) {
        holder.bind(mPostsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mPostsList.size();
    }

    static class ContentPostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Context context;
        TextView tvCommentsCount;
        TextView tvLikesCount;
        TextView tvReplaysAndDaysCount;
        TextView tvRelatedHashTags;
        ImageView ivLike;
        Post post;

        public ContentPostViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            itemView.findViewById(R.id.iv_btn_more).setOnClickListener(this);
            itemView.findViewById(R.id.lin_comments_wrapper).setOnClickListener(this);

            tvRelatedHashTags = (TextView) itemView.findViewById(R.id.tv_related_hash_tags);
            tvReplaysAndDaysCount = (TextView) itemView.findViewById(R.id.tv_plays_days_count);
            tvLikesCount = (TextView) itemView.findViewById(R.id.tv_likes_count);
            tvCommentsCount = (TextView) itemView.findViewById(R.id.tv_comments_count);

            ivLike = (ImageView) itemView.findViewById(R.id.iv_like);
            ivLike.setOnClickListener(this);
        }

        public void bind(Post post) {
            this.post = post;
            if (Validator.isObjectValid(post)) {
                setPostLiked(post.isLiked());
            }
        }

        private void setPostLiked(boolean liked) {
            if (liked) {
                ivLike.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.like_active));
            } else {
                ivLike.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.like));
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_btn_more:
                    break;
                case R.id.iv_like:
                    if (!post.isLiked()) {
                        post.setLiked(true);
                        setPostLiked(true);
                    } else {
                        post.setLiked(false);
                        setPostLiked(false);
                    }
                    break;
                case R.id.lin_comments_wrapper:
                    break;
            }
        }
    }


}
