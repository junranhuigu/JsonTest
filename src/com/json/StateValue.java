package com.json;

import java.util.ArrayList;
import java.util.List;
/**
 * 初步解析获取状态机对应内容
 * */
class StateValue {
	private List<Matcher> contents = new ArrayList<>();
	
	public void add(int state, String stateName){
		contents.add(new Matcher(state, stateName, null));
	}
	
	public void addContent(String value){
		this.contents.get(this.contents.size() - 1).value = value;
	}
	
	public class Matcher{
		int state;
		String stateName;
		String value;//值
		public Matcher(int state, String stateName, String value) {
			this.state = state;
			this.stateName = stateName;
			this.value = value;
		}
		@Override
		public String toString() {
			return stateName + " " + value;
		}
	}
	
	public List<Matcher> getContents() {
		return contents;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < contents.size(); ++ i){
			builder.append(contents.get(i).toString()).append("\n");
		}
		return builder.toString();
	}
}
