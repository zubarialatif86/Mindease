package com.example.mindease;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

 class BadgeAdapter extends BaseAdapter {

    private Context context;
    private List<BadgeItem> badgeList;

    public BadgeAdapter(Context context, List<BadgeItem> badgeList) {
        this.context = context;
        this.badgeList = badgeList;
    }

    @Override
    public int getCount() {
        return badgeList.size();
    }

    @Override
    public Object getItem(int position) {
        return badgeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_badge, parent, false);
        }

        ImageView badgeImage = convertView.findViewById(R.id.badgeImage);
        TextView badgeTitle = convertView.findViewById(R.id.badgeTitle);

        BadgeItem item = badgeList.get(position);
        badgeImage.setImageResource(item.getImageRes());
        badgeTitle.setText(item.getTitle());

        return convertView;
    }
}
