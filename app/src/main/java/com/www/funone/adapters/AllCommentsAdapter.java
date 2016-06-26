package com.www.funone.adapters;

import android.content.Context;
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
public class AllCommentsAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;

    public AllCommentsAdapter(Context context){
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return 50;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.item_all_comments, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        return convertView;
    }

    public static class ViewHolder{

        private CircleImageView mUserAva;
        private TextViewFont mUserName;
        private TextViewFont mCommentText;
        private TextViewFont mCreatedTime;

        public ViewHolder(View view){
            mUserAva = (CircleImageView) view.findViewById(R.id.img_user);
            mUserName = (TextViewFont)view.findViewById(R.id.tv_comment_owner);
            mCommentText = (TextViewFont)view.findViewById(R.id.tv_comment_text);
            mCommentText.onSetAlpha(54);
            mCreatedTime = (TextViewFont)view.findViewById(R.id.tv_comment_time);
            mCreatedTime.onSetAlpha(37);
        }
    }
}
