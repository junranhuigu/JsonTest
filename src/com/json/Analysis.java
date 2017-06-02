package com.json;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;


class Analysis {
	
	// json解析器逻辑：由于 JSON 语法特别简单 我们不需要写分词器（tokenizer） 只需检测下一个字符 便可以知道它是哪种类型的值 然后调用相关的分析函数
	//	n ➔ null
	//	t ➔ true
	//	f ➔ false
	//	" ➔ string
	//	0-9/- ➔ number
	//	[ ➔ array
	//	{ ➔ object
	public static Value analysis(String content) throws Exception{
		State state = new State();
		char c = 0;
		try {
			for(int i = 0; i < content.length(); ++ i){
				c = content.charAt(i);
				switch (c) {
				case '[':
					if(state.state() == State.ARRAY){
						state.addState(State.ELEMENT);
					}
					state.addState(State.ARRAY);
					state.addState(State.ELEMENT);
					break;
				case ']':
					state.subState(State.ELEMENT);
					state.subState(State.ARRAY);
					break;
				case '{':
					if(state.state() == State.ARRAY){
						state.addState(State.ELEMENT);
					}
					state.addState(State.OBJECT);
					state.addState(State.MEM_KEY);
					break;
				case '}':
					if(state.state() == State.MEM_KEY){
						state.subState(State.MEM_KEY);
					} else if(state.state() == State.MEM_VALUE){
						state.subState(State.MEM_VALUE);
					}
					state.subState(State.OBJECT);
					break;
				case ':':
					state.subState(State.MEM_KEY);
					state.addState(State.MEM_VALUE);
					break;
				case ',':
					if(state.state() == State.ELEMENT){
						state.subState(State.ELEMENT);
					} else if(state.state() == State.MEM_VALUE){
						state.subState(State.MEM_VALUE);
					}
					break;
				default:
					if(isBlank(c)){
						break;
					}
					if(state.state() == State.ARRAY){
						state.addState(State.ELEMENT);
					} else if(state.state() == State.OBJECT){
						state.addState(State.MEM_KEY);
					}
					if(state.state() == State.ELEMENT || state.state() == State.MEM_VALUE || state.state() == State.MEM_KEY){
						if(c == '"'){
							//获取字符串
							String k = analysisString(content, i);
							i += k.length() + 1;//还差一个"号
//							System.out.println("String " + k + ", state : " + state.stateName());
							state.addStateContent(k);
						} else {
							String v = analysisOthers(content, i);
							i += v.length() - 1;//已经读了一个数字
//							System.out.println("Other " + v + ", state : " + state.stateName());
							state.addStateContent(v);
						}
					}
					break;
				}
			}
		} finally {
//			System.out.println("nowChar : " + c + ", " + state);
		}
//		System.out.println(state.getValue());
		Value value = new Value(state.getValue());
//		System.out.println(value);
//		System.out.println(value.rootJsonString());
		return value;
	}
	
	private static String analysisOthers(String content, int index) throws Exception {
		String _ready = null;
		for(int i = index + 1; i < content.length(); ++ i){
			char n = content.charAt(i);
			if(n == ',' || n == ']' || n == '}'){
				_ready = removeBlank(content.substring(index, i));
				break;
			}
		}
		String result = null;
		char c = content.charAt(index);
		switch (c) {
		case 'f':
			result = "false";
			break;
		case 't':
			result = "true";
			break;
		case 'n':
			result = "null";
			break;
		default://数值
			result = "-?[0-9]{1,}(\\.[0-9]{1,})?";
			break;
		}
		//验证合法性
		if(result.equals(_ready) || Pattern.matches(result, _ready)){
			return _ready;
		} else {
			throw new Exception(_ready + "非法值存在");
		}
	}

	private static String analysisString(String content, int index) {
		for(int i = index + 1; i < content.length(); ++ i){
			char c = content.charAt(i);
			if(c == '"' && content.charAt(i - 1) != '\\'){
				return content.substring(index + 1, i);
			}
		}
		return null;
	}

	private static String removeBlank(String content){
		int begin = 0;
		int end = 0;
		for(int i = 0; i < content.length(); ++ i){
			char c = content.charAt(i);
			if(!isBlank(c)){
				begin = i;
				break;
			}
		}
		for(int i = content.length() - 1; i >= 0; -- i){
			char c = content.charAt(i);
			if(!isBlank(c)){
				end = i;
				break;
			}
		}
		if(end + 1 >= content.length()){
			return content.substring(begin);
		} else {
			return content.substring(begin, end + 1);
		}
	}
	
	private static boolean isBlank(char c){
		List<Integer> blank = Arrays.asList(32, 9, 10, 13);
		for(int i : blank){
			if(i == c){
				return true;
			}
		}
		return false;
	}
}
