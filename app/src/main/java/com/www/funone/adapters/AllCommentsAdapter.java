package com.www.funone.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.www.funone.R;
import com.www.funone.view.TextViewFont;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by vitaliy.komaniak on 6/25/16.
 */
public class AllCommentsAdapter extends RecyclerView.Adapter<AllCommentsAdapter.ViewHolderComment> {

    private Context mContext;
    private LayoutInflater mInflater;

    public AllCommentsAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolderComment onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_comments, parent, false);
        return new ViewHolderComment(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderComment holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }


    public static class ViewHolderComment extends RecyclerView.ViewHolder {

        private CircleImageView mUserAva;
        private TextViewFont mUserName;
        private TextViewFont mCommentText;
        private TextViewFont mCreatedTime;

        public ViewHolderComment(View view) {
            super(view);
            mUserAva = (CircleImageView) view.findViewById(R.id.img_user);
            mUserName = (TextViewFont) view.findViewById(R.id.tv_comment_owner);
            mCommentText = (TextViewFont) view.findViewById(R.id.tv_comment_text);
            mCommentText.onSetAlpha(54);
            mCreatedTime = (TextViewFont) view.findViewById(R.id.tv_comment_time);
            mCreatedTime.onSetAlpha(37);
        }
    }
}
