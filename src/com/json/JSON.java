package com.json;

import java.lang.reflect.Field;

public class JSON {
	
	public static <T> T parseObject(String json, Class<T> cls) throws Exception{
		T t = cls.newInstance();
		Field[] fileds = cls.getFields();
		Value v = Analysis.analysis(json);
		for(Field f : fileds){
			
		}
		return null;
	}
	
	public static boolean equals(String content1, String content2) throws Exception{
		Value v1 = Analysis.analysis(content1);
		Value v2 = Analysis.analysis(content2);
		return v1.equals(v2);
	}
	
}
