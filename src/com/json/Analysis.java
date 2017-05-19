package com.json;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;


class Analysis {
	public static void main(String[] args) throws Exception{
		String json = "{\"advantureExp\":0,\"advantureInfo\":{\"advantureCount\":0,\"advantureFreeDeployCount\":0,\"refreshTime\":1477633389173},\"advantureLevel\":1,\"advantureStageId\":0,\"battleInfo\":{\"battlecd\":0,\"enemy\":[{\"attacked\":0,\"id\":\"10092\",\"name\":\"漂泊的卡尔\",\"vipLevel\":0},{\"attacked\":0,\"id\":\"10071\",\"name\":\"树上的赫特\",\"vipLevel\":0},{\"attacked\":0,\"id\":\"10085\",\"name\":\"勇敢的加登\",\"vipLevel\":0},{\"attacked\":0,\"id\":\"10001\",\"name\":\"可爱的阿瑟\",\"vipLevel\":0},{\"attacked\":0,\"id\":\"10043\",\"name\":\"消失的杜威\",\"vipLevel\":0}],\"id\":\"1025103053777301\",\"lastAttackTime\":0,\"refreshTime\":1477362666458,\"remainRefreshCount\":5},\"defenceRecords\":[],\"id\":\"1025103053777301\",\"lastProductCalTime\":1477362666456,\"openProductTeamPosition\":1,\"productTeam\":[{\"beginProductTime\":0,\"id\":1,\"members\":[],\"state\":-1,\"typeId\":0}],\"stages\":[{\"backpack\":[],\"deployCount\":0,\"process\":0,\"stageId\":1,\"typeCounts\":{}}]}";
		analysis(json);
	}
	
	// json解析器逻辑：由于 JSON 语法特别简单 我们不需要写分词器（tokenizer） 只需检测下一个字符 便可以知道它是哪种类型的值 然后调用相关的分析函数
	//	n ➔ null
	//	t ➔ true
	//	f ➔ false
	//	" ➔ string
	//	0-9/- ➔ number
	//	[ ➔ array
	//	{ ➔ object
	public static void analysis(String content) throws Exception{
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
//			case '"':
//				break;
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
							System.out.println("String " + k + ", state : " + state.stateName());
						} else {
							String v = analysisOthers(content, i);
							i += v.length() - 1;//已经读了一个数字
							System.out.println("Other " + v + ", state : " + state.stateName());
						}
					} 
					break;
				}
//				System.out.println("nowChar : " + c + ", " + state);
			}
		} finally {
			System.out.println("nowChar : " + c + ", " + state);
		}
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
//		for(int i = index; i < content.length(); ++ i){
//			if(content.charAt(i) == first_c){
//				++ index;
//			} else if(content.charAt(i) == last_c){
//				-- index;
//			}
//			if(index == 0){
//				return content.substring(index, i);
//			}
//		}
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
