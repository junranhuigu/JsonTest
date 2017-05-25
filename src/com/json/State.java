package com.json;

import java.lang.reflect.Field;
import java.util.Stack;

/**
 * 状态机记录
 * */
class State {
	private Stack<Integer> stack;
	private int nowState;
	private StateValue value;
	
	public static final int OBJECT = 1;
	public static final int ARRAY = 2;
	public static final int OBJECT_FINISH = 3;
	public static final int ARRAY_FINISH = 4;
	public static final int MEM_KEY = 5;
	public static final int MEM_VALUE = 7;
	public static final int ELEMENT = 8;
	
	public State() {
		this.stack = new Stack<>();
		this.nowState = 0;
		this.value = new StateValue();
	}
	
	public void addState(int key){
		if(nowState == 0){
			nowState = key;
		} else {
			stack.push(nowState);
			nowState = key;
		}
		this.value.add(nowState, stateName());
	}
	
	/**
	 * 补全内容
	 * */
	public void addStateContent(String value){
		if(state() == State.ELEMENT || state() == State.MEM_VALUE || state() == State.MEM_KEY){
			this.value.addContent(value);
		}
	}
	
	public void subState(int key) throws Exception{
		if(nowState == key){
			switch (nowState) {
			case OBJECT:
				this.value.add(OBJECT_FINISH, name(OBJECT_FINISH));
				break;
			case ARRAY:
				this.value.add(ARRAY_FINISH, name(ARRAY_FINISH));
				break;
			}
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
	
	public static String name(int key){
		for(Field field : State.class.getFields()){
			if(!field.isSynthetic()){
				try {
					int k = field.getInt(null);
					if(k == key){
						return field.getName();
					}
				} catch (Exception e) {}
			}
		}
		return null;
	}
	
	public StateValue getValue() {
		return value;
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
