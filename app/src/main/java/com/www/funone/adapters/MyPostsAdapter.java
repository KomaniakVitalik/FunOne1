package com.www.funone.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.www.funone.R;
import com.www.funone.view.TextViewFont;

/**
 * Created by vitaliy.komaniak on 6/26/16.
 */
public class MyPostsAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;

    public MyPostsAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 100;
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
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_my_post, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    public static class ViewHolder {

        private TextViewFont mPostTitle;
        private TextViewFont mPostMessage;
        private TextViewFont mPostCreatedTime;

        public ViewHolder(View view) {
            mPostTitle = (TextViewFont) view.findViewById(R.id.tv_post_title);
            mPostMessage = (TextViewFont) view.findViewById(R.id.tv_post_text);
            mPostCreatedTime = (TextViewFont) view.findViewById(R.id.tv_post_created);
            mPostCreatedTime.onSetAlpha(30);
        }
    }
}
