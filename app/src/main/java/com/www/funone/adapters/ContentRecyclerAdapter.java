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

    private static final int LIKED = 1;
    private static final int OPEN_COMMENT_DETAILS_PAGE = 2;
    private static final int OPEN_SHARE_LAYOUT = 3;
    private static final int OPEN_ALL_COMMENTS = 4;

    private OnPostInteractionListener listener;
    private List<Post> mPostsList = new ArrayList<>();

    public ContentRecyclerAdapter(List<Post> mPostsList) {
        this.mPostsList = mPostsList;
    }

    @Override
    public ContentPostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new ContentPostViewHolder(this, view);
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

        ContentRecyclerAdapter adapter;
        Context context;
        TextView tvCommentsCount;
        TextView tvLikesCount;
        TextView tvReplaysAndDaysCount;
        TextView tvRelatedHashTags;
        ImageView ivLike;
        Post post;

        public ContentPostViewHolder(ContentRecyclerAdapter adapter, View itemView) {
            super(itemView);
            context = itemView.getContext();
            this.adapter = adapter;
            itemView.findViewById(R.id.iv_btn_share).setOnClickListener(this);
            itemView.findViewById(R.id.lin_comments_wrapper).setOnClickListener(this);
            itemView.findViewById(R.id.iv_post_image).setOnClickListener(this);

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
                case R.id.iv_btn_share:
                    adapter.notifyPostInteraction(post, ContentRecyclerAdapter.OPEN_SHARE_LAYOUT);
                    break;
                case R.id.iv_like:
                    if (!post.isLiked()) {
                        post.setLiked(true);
                        setPostLiked(true);
                    } else {
                        post.setLiked(false);
                        setPostLiked(false);
                    }
                    adapter.notifyPostInteraction(post, ContentRecyclerAdapter.LIKED);
                    break;
                case R.id.lin_comments_wrapper:
                    adapter.notifyPostInteraction(post, ContentRecyclerAdapter.OPEN_ALL_COMMENTS);
                    break;
                case R.id.iv_post_image:
                    adapter.notifyPostInteraction(post, ContentRecyclerAdapter.OPEN_COMMENT_DETAILS_PAGE);
                    break;
            }
        }
    }


    public void setOnPostInteractionListener(OnPostInteractionListener listener) {
        this.listener = listener;
    }

    private void notifyPostInteraction(Post post, int type) {
        if (Validator.isObjectValid(listener)) {
            if (Validator.isObjectValid(post)) {
                switch (type) {
                    case LIKED:
                        listener.onPostLiked(post);
                        break;
                    case OPEN_COMMENT_DETAILS_PAGE:
                        listener.onOpenBestCommentsActivity(post);
                        break;
                    case OPEN_SHARE_LAYOUT:
                        listener.onOpenShareLayout(post);
                        break;
                    case OPEN_ALL_COMMENTS:
                        listener.onOpenALLComments(post);
                        break;
                }
            }
        }
    }

    public interface OnPostInteractionListener {
        void onPostLiked(Post post);

        void onOpenBestCommentsActivity(Post post);

        void onOpenShareLayout(Post post);

        void onOpenALLComments(Post post);
    }


}
