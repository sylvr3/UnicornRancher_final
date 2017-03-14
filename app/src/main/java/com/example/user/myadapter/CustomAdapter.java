package com.example.user.myadapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<LocationInfo> unicornList;

    public CustomAdapter(Context context, ArrayList<LocationInfo> uniList) {
        this.context = context;
        this.unicornList = uniList;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<UnicornInfo> productList = unicornList.get(groupPosition).getUnicornLocation();
        return productList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View view, ViewGroup parent) {

        UnicornInfo detailInfo = (UnicornInfo) getChild(groupPosition, childPosition);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.list_item, null);
        }
        TextView childItem = (TextView) view.findViewById(R.id.childItem);
        childItem.setText(detailInfo.getName());

        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<UnicornInfo> productList = unicornList.get(groupPosition).getUnicornLocation();
        return productList.size();

    }

    @Override
    public Object getGroup(int groupPosition) {
        return unicornList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return unicornList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isLastChild, View view,
                             ViewGroup parent) {

        LocationInfo headerInfo = (LocationInfo) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.group_item, null);
        }

        TextView heading = (TextView) view.findViewById(R.id.heading);
        heading.setText(headerInfo.getLoc());

        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
