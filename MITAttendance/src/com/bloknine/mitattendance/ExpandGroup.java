package com.bloknine.mitattendance;

import java.util.ArrayList;

public class ExpandGroup {

	private String name;
	private String perc;
	private ArrayList<ExpandChild> items;
	
	public String getName(){
		return name;
	}
	
	public void setName(String s){
		name=s;
	}
	
	public String getPerc(){
		return perc;
	}
	
	public void setPerc(String s){
		perc=s;
	}
	
	public ArrayList<ExpandChild> getItems(){
		return items;
	}
	
	public void setItems(ArrayList<ExpandChild> i){
		items=i;
	}
	
}
