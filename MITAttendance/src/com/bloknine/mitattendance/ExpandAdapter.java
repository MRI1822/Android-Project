package com.bloknine.mitattendance;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ExpandAdapter extends BaseExpandableListAdapter {
	
	private Context context;
	private ArrayList<ExpandGroup> groups;
	
	public ExpandAdapter(Context c , ArrayList<ExpandGroup> g){
		context = c;
		groups = g;
		
	}
	
	public void addItem(ExpandChild i , ExpandGroup g){
		groups.add(g);
		int index = groups.indexOf(g);
		ArrayList<ExpandChild> ch = groups.get(index).getItems();
		ch.add(i);
		groups.get(index).setItems(ch);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		ArrayList<ExpandChild> chList = groups.get(groupPosition).getItems();
		return chList.get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		ExpandChild eChild = (ExpandChild) getChild(groupPosition , childPosition);
		if(convertView == null){
			LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = lInflater.inflate(R.layout.expand_child, null);
		}
		TextView tViewC = (TextView) convertView.findViewById(R.id.tViewChild);
		tViewC.setText(eChild.getName());
		TextView tViewT = (TextView) convertView.findViewById(R.id.tViewTag);
		tViewT.setText(eChild.getTag());
		
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		ArrayList<ExpandChild> chList = groups.get(groupPosition).getItems();
		return chList.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groups.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return groups.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		ExpandGroup eGroup = (ExpandGroup) getGroup(groupPosition);
		if(convertView == null){
			LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = lInflater.inflate(R.layout.expand_group, null);
		}
		TextView tViewG = (TextView) convertView.findViewById(R.id.tViewGroup);
		tViewG.setText(eGroup.getName());
		TextView tViewP = (TextView) convertView.findViewById(R.id.tViewPerc);
		String perc = eGroup.getPerc();
		int mod = Integer.parseInt(perc);
		if(mod>=75)
			convertView.setBackgroundColor(Color.parseColor("#33B5E5"));
		else
			convertView.setBackgroundColor(Color.parseColor("#FF8800"));
		tViewP.setText(perc+"%");
		return convertView;
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
