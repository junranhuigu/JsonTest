package com.json;

import java.lang.reflect.Field;
import java.util.Stack;

/**
 * 状态机记录
 * */
class State {
	private Stack<Integer> stack;
	private int nowState;
	
	public static final int OBJECT = 1;
	public static final int ARRAY = 2;
	public static final int MEM_KEY = 5;
	public static final int MEM_VALUE = 7;
	public static final int ELEMENT = 8;
//	public static final int WAIT_FEN = 6;
//	public static final int MEM_OVER = 9;
//	public static final int ELEMENT_OVER = 10;
	
	public State() {
		this.stack = new Stack<>();
		this.nowState = 0;
	}
	
	public void addState(int key){
		if(nowState == 0){
			nowState = key;
		} else {
			stack.push(nowState);
			nowState = key;
		}
	}
	
	public void subState(int key) throws Exception{
		if(nowState == key){
			if(!stack.isEmpty()){
				nowState = stack.pop();
			} else {
				nowState = 0;
			}
		} else {
			throw new Exception("状态不对 nowState : " + nowState + " key :" + key);
		}
	}
	
	public int state(){
		return nowState;
	}
	
	public String stateName(){
		return name(nowState);
	}
	
	private String name(int key){
		for(Field field : this.getClass().getFields()){
			if(!field.isSynthetic()){
				try {
					int k = field.getInt(this);
					if(k == key){
						return field.getName();
					}
				} catch (Exception e) {}
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		StringBuilder stackInfo = new StringBuilder("");
		for(int i = stack.size() - 1; i >= 0; -- i){
			stackInfo.append(name(stack.get(i))).append(",");
		}
		return "nowState : " + name(nowState) + ", readyState : [" + stackInfo + "]";
	}
}
