package com.json;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class JSON {
	
	public static <T> T parseObject(String json, Class<T> cls) throws Exception{
		Value value = Analysis.analysis(json);
		return value.parseObject(cls);
	}
	
	public static <T> List<T> parseArray(String json, Class<T> cls) throws Exception{
		Value value = Analysis.analysis(json);
		return value.parseList(cls);
	}
	
	public static boolean equals(String content1, String content2) throws Exception{
		Value v1 = Analysis.analysis(content1);
		Value v2 = Analysis.analysis(content2);
		return v1.equals(v2);
	}
	
	/**
	 * 提取json中的所有key值
	 * @return key key值 value 该key值出现的次数
	 * */
	public static Map<String, Integer> jsonKeys(String json) throws Exception{
		Value value = Analysis.analysis(json);
		return value.jsonKeys();
	}
	
	/**
	 * 提取json中的所有value值
	 * @return key value值 value 该value值出现的次数
	 * */
	public static Map<String, Integer> jsonValues(String json) throws Exception{
		Value value = Analysis.analysis(json);
		return value.jsonValues();
	}
	
	/**
	 * json数据结构的初步解析结果
	 * @param detail 显示详细信息
	 * */
	public static String jsonStructure(String json) throws Exception{
		Value value = Analysis.analysis(json);
		List<String> list = new ArrayList<>(value.jsonStructure());
		Collections.sort(list);
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < list.size(); ++ i){
			builder.append(list.get(i)).append("\n");
		}
		return builder.toString();
	}
	
	/**
	 * 使用语句获取json中的数据
	 * @param executeLanguage 执行语句
	 * */
	public static List<Object> select(String executeLanguage, String json){
		return GroovyService.execute(executeLanguage, json);
	}
}
