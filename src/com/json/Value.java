package com.json;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import com.json.StateValue.Matcher;

/**
 * json对应的数据结构
 * */
public class Value {
	private List<Attr> attrs = new ArrayList<>();
	private List<JsonObject> objs = new ArrayList<>();
	private List<JsonArray> arrays = new ArrayList<>();
	private List<Object> sequence = new ArrayList<>();//具体的json对象结构顺序
	private static Class[] cs = {String.class, Short.class, Byte.class, Integer.class, Long.class, Boolean.class, Character.class, Float.class, Double.class};
	
	public void generateValue(StateValue value) throws Exception{
		genAttrs(value);
		assembleObjectAndArray();
	}
	
	public String rootJsonString(){
		Object obj = sequence.get(0);
		if(obj instanceof JsonObject){
			JsonObject o = (JsonObject) obj;
			return o.jsonString();
		} else if(obj instanceof JsonArray){
			JsonArray a = (JsonArray) obj;
			return a.jsonString();
		} else {
			return null;
		}
	}
	
	public <T> List<T> parseList(Class<T> c) throws Exception{
		Object root = root();
		if(root instanceof JsonArray){
			return parseArray(c, JsonArray.class.cast(root));
		}
		return null;
	}
	public <T> T parseObject(Class<T> c) throws Exception{
		Object root = root();
		if(root instanceof JsonObject){
			return parseObject(c, JsonObject.class.cast(root));
		}
		return null;
	}
	
	/**
	 * 判断某个类是否为基础类的封装类或者String
	 * */
	private static boolean inSimpleEncapsolution(Class<?> cls){
		for(Class<?> c : cs){
			if(c == cls){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 将自身转为封装类
	 * */
	private <T> T parseObject(Class<T> cls, JsonObject obj) throws Exception{
		T t = cls.newInstance();
		for(Field f : cls.getDeclaredFields()){
			if(Modifier.isFinal(f.getModifiers()) || Modifier.isStatic(f.getModifiers())){
				continue;
			}
			Method method = t.getClass().getMethod("set" + StringUtil.upperFirstLetter(f.getName()), f.getType());
			if(f.getType().isPrimitive() || inSimpleEncapsolution(f.getType())){//Attr
				Attr attr = null;
				for(Attr a : obj.attrs){
					if(f.getName().equals(a.name)){
						attr = a;
						obj.attrs.remove(a);
						break;
					}
				}
				if(attr != null){
					parsePrimitiveAttr(t, method, attr);
				}
			} else if(Collection.class.isAssignableFrom(f.getType()) || f.getType().isArray()){//Array
				JsonArray array = null;
				for(JsonArray a : obj.arrays){
					if(f.getName().equals(a.name)){
						array = a;
						obj.arrays.remove(a);
						break;
					}
				}
				if(array != null){
					if(f.getType().isArray()){//数组
						Class<?> typeCls = f.getType().getComponentType();
						List list = parseArray(typeCls, array);
						Object o = list2Array(list, typeCls);
						method.invoke(t, o);
					} else {//列表
						ParameterizedType type = (ParameterizedType) f.getGenericType();
						List list = parseList(type, array);
						if(f.getType() == List.class){
							method.invoke(t, list);//初始化列表
						} else if(f.getType() == Set.class){
							method.invoke(t, new HashSet<>(list));//初始化列表
						} else {
							method.invoke(t, f.getType().newInstance());//初始化列表
							method = t.getClass().getMethod("get" + StringUtil.upperFirstLetter(f.getName()), null);
							Collection collection = (Collection<?>) method.invoke(t, null);
							collection.addAll(list);
						}
					}
				}
			} else {//object
				JsonObject _obj = null;
				for(JsonObject o : obj.objs){
					if(f.getName().equals(o.name)){
						_obj = o;
						obj.objs.remove(o);
						break;
					}
				}
				if(_obj != null){
					if(Map.class.isAssignableFrom(f.getType())){//Map
						ParameterizedType pt = (ParameterizedType) f.getGenericType();
						Type[] types = pt.getActualTypeArguments();
						Class keyCls = (Class) types[0];
						Class valueCls = (Class) types[1];
						Map map = parseMap(keyCls, valueCls, _obj);
						method.invoke(t, map);
					} else {//Object
						method.invoke(t, parseObject(f.getType(), _obj));
					}
				}
			}
		}
		return t;
	}
	
	private <T> List<T> parseArray(Class<T> cls, JsonArray array) throws Exception{
		List<T> list = new ArrayList<>();
		if(cls.isPrimitive() || inSimpleEncapsolution(cls)){
			for(int i = 0; i < array.attrs.size(); ++ i){
				T t = (T) getValue(array.attrs.get(i), cls);
				list.add(t);
			}
		} else if(Collection.class.isAssignableFrom(cls) || cls.isArray()){//Array
			if(cls.isArray()){//数组
				for(int i = 0; i < array.arrays.size(); ++ i){
					T t = (T) parseArray(cls.getComponentType(), array.arrays.get(i));
					list.add(t);
				}
			} else {//列表
				
			}
		} else {//Object
			for(int i = 0; i < array.objs.size(); ++ i){
				T t = parseObject(cls, array.objs.get(i));
				list.add(t);
			}
		}
		return list;
	}
	
	private List parseList(ParameterizedType type, JsonArray array) throws Exception{
		List list = new ArrayList<>();
		try {//最后一层
			Class typeCls = (Class) type.getActualTypeArguments()[0];
			if(inSimpleEncapsolution(typeCls)){
				for(Attr attr : array.attrs){
					list.add(getValue(attr, typeCls));
				}
			} else {//Object
				for(JsonObject obj : array.objs){
					list.add(parseObject(typeCls, obj));
				}
			}
		} catch (Exception e) {//还有列表嵌套 
			for(JsonArray a : array.arrays){
				ParameterizedType pt = (ParameterizedType) type.getActualTypeArguments()[0];
				List l = parseList(pt, a);
				list.add(l);
			}
		}
		return list;
	} 
	
	private <T> void parsePrimitiveAttr(T t, Method method, Attr attr) throws Exception{
		Object value = getValue(attr, method.getParameterTypes()[0]);
		method.invoke(t, value);
	}
	
	private Object getValue(Attr attr, Class<?> paramClass){
		Object value = null;
		if(paramClass == int.class || paramClass == Integer.class){
			value = Integer.parseInt(attr.value);
		} else if(paramClass == byte.class || paramClass == Byte.class){
			value = Byte.parseByte(attr.value);
		} else if(paramClass == short.class || paramClass == Short.class){
			value = Short.parseShort(attr.value);
		} else if(paramClass == long.class || paramClass == Long.class){
			value = Long.parseLong(attr.value);
		} else if(paramClass == float.class || paramClass == Float.class){
			value = Float.parseFloat(attr.value);
		} else if(paramClass == double.class || paramClass == Double.class){
			value = Double.parseDouble(attr.value);
		} else if(paramClass == short.class || paramClass == Boolean.class){
			value = Boolean.parseBoolean(attr.value);
		} else if(paramClass == char.class || paramClass == Character.class){
			value = attr.value.charAt(0);
		} else {//字符串
			value = attr.value;
		}
		return value;
	}
	
	/**
	 * @return 一个是数组的Object
	 * */
	private Object list2Array(List list, Class<?> cls){
		Object o = Array.newInstance(cls, list.size());
		if(cls.isArray()){
			for(int i = 0; i < list.size(); ++ i){
				List _l = (List) list.get(i);
				Object _o = list2Array(_l, cls.getComponentType());
				Array.set(o, i, _o);
			}
		} else {//最后一层
			for(int i = 0; i < list.size(); ++ i){
				Array.set(o, i, list.get(i));
			}
		}
		return o;
	}
	
	private Map parseMap(Class<?> keyCls, Class<?> valueCls, JsonObject obj) throws Exception{
		if(!inSimpleEncapsolution(keyCls)){
			throw new Exception(keyCls.getName() + "作为Map类型key值无法解析，目前key值仅支持基础类型");
		}
		Map map = new HashMap<>();
		for(Attr attr : obj.attrs){
			Attr a = new Attr();
			a.value = attr.name;
			Object key = getValue(a, keyCls);
			Object value = getValue(attr, valueCls);
			if(key == null && value == null){
				continue;
			} else {
				map.put(key, value);
			}
		}
		for(JsonArray array : obj.arrays){
			Attr a = new Attr();
			a.value = array.name;
			Object key = getValue(a, keyCls);
			Object value = parseArray(valueCls, array);
			if(key == null && value == null){
				continue;
			} else {
				map.put(key, value);
			}
		}
		for(JsonObject o : obj.objs){
			Attr a = new Attr();
			a.value = o.name;
			Object key = getValue(a, keyCls);
			Object value = parseObject(valueCls, o);
			if(key == null && value == null){
				continue;
			} else {
				map.put(key, value);
			}
		}
		return map;
	}
	
	private Object root(){
		return sequence.get(0);
	}
	
	public Object parse(String content, Class<?> cls) throws Exception{
		Object o = null;
		if(inSimpleEncapsolution(cls)){
			Attr a = new Attr();
			a.value = content;
			o = getValue(a, cls);
		} else {//key是个复合类
			Value v = Analysis.analysis(content);
			Object root = v.root();
			if(root instanceof JsonObject){
				JsonObject ro = (JsonObject) root;
				o = parseObject(cls, ro);
			} else if(root instanceof JsonArray){
				JsonArray ra = (JsonArray) root;
				o = parseArray(cls, ra);
			}
		}
		return o;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Value){
			Value v = (Value) obj;
			Object o1 = root();
			Object o2 = v.root();
			if(o1.getClass() == o2.getClass()){
				return o1.equals(o2);
			}
		}
		return false;
	}
	
	/**
	 * 生成属性对象数据
	 * */
	private void genAttrs(StateValue value){
		for(int i = 0; i < value.getContents().size(); ++ i){
			Matcher matcher = value.getContents().get(i);
			switch (matcher.state) {
			case State.MEM_KEY:
				if(matcher.value == null){
					continue;
				}
				String attrName = matcher.value;
				matcher = value.getContents().get(++ i);
				if(matcher.value != null){//属性
					Attr attr = new Attr();
					attr.name = attrName;
					attr.value = matcher.value;//值都是字符串
					attrs.add(attr);
					sequence.add(attr);
				} else {//对象或者数组
					matcher = value.getContents().get(++ i);
					switch (matcher.state) {
					case State.OBJECT:
						JsonObject obj = new JsonObject();
						obj.name = attrName;
						this.objs.add(obj);
						sequence.add(obj);
						break;
					case State.ARRAY:
						JsonArray array = new JsonArray();
						array.name = attrName;
						this.arrays.add(array);
						sequence.add(array);
						break;
					}
				}
				break;
			case State.ARRAY_FINISH:
//				lastArray().open = false;
				sequence.add(State.ARRAY_FINISH);
				break;
			case State.OBJECT_FINISH:
//				lastObject().open = false;
				sequence.add(State.OBJECT_FINISH);
				break;
			case State.OBJECT://初始对象
				JsonObject obj = new JsonObject();
				this.objs.add(obj);
				sequence.add(obj);
				break;
			case State.ARRAY://初始队列
				JsonArray array = new JsonArray();
				this.arrays.add(array);
				sequence.add(array);
				break;
			case State.ELEMENT://队列的基础值
				if(matcher.value != null){
					Attr attr = new Attr();
					attr.value = matcher.value;
					this.attrs.add(attr);
					sequence.add(attr);
				}
				break;
			}
		}
	}
	
	/**
	 * 装配属性对象数据，即分配那个类或数组中包含哪些数据
	 * */
	private void assembleObjectAndArray(){
		Stack<JsonObject> so = new Stack<>();
		Stack<JsonArray> sa = new Stack<>();
		Stack<Integer> sf = new Stack<>();
		JsonObject nowObj = null;
		JsonArray nowArray = null;
		int flag = -1;//表示当前为JsonObject还是JsonArray
		for(int i = 0; i < sequence.size(); ++ i){
			Object obj = sequence.get(i);
			if(obj instanceof JsonObject){
				JsonObject o = (JsonObject) obj;
				if(nowObj != null){
					so.push(nowObj);
				}
				switch (flag) {//添加属性
				case State.OBJECT:
					nowObj.objs.add(o);
					break;
				case State.ARRAY:
					nowArray.objs.add(o);
					break;
				}
				//当前对象置换
				nowObj = o;
				if(flag != -1){
					sf.push(flag);
				}
				flag = State.OBJECT;
			} else if(obj instanceof JsonArray){
				JsonArray a = (JsonArray) obj;
				if(nowArray != null){
					sa.push(nowArray);
				}
				switch (flag) {//添加属性
				case State.OBJECT:
					nowObj.arrays.add(a);
					break;
				case State.ARRAY:
					nowArray.arrays.add(a);
					break;
				}
				//当前对象置换
				nowArray = a;
				if(flag != -1){
					sf.push(flag);
				}
				flag = State.ARRAY;
			} else if(obj instanceof Attr){
				Attr attr = (Attr) obj;
				switch (flag) {//添加属性
				case State.OBJECT:
					nowObj.attrs.add(attr);
					break;
				case State.ARRAY:
					nowArray.attrs.add(attr);
					break;
				}
			} else {//终结
				int state = (int) obj;
				if(sf.isEmpty()){
					flag = -1;
				} else {
					flag = sf.pop();
				}
				switch (state) {
				case State.ARRAY_FINISH:
					if(sa.isEmpty()){
						nowArray = null;
					} else {
						nowArray = sa.pop();
					}
					break;
				case State.OBJECT_FINISH:
					if(so.isEmpty()){
						nowObj = null;
					} else {
						nowObj = so.pop();
					}
					break;
				}
			}
		}
	}
	
	
	private class Attr{
		String name;//属性名
		String value;//属性值
		
		public String jsonString() {
			StringBuilder builder = new StringBuilder();
			builder.append("\"").append(name).append("\" : \"").append(value).append("\"");
			return builder.toString();
		}
		
		public String simpleMsg(){
			return name + " : " + value;
		}
		
		@Override
		public String toString() {
			return simpleMsg();
		}
		
		@Override
		public boolean equals(Object obj) {
			if(obj instanceof Attr){
				Attr a = (Attr) obj;
				if(name != null && a.name != null && name.equals(a.name) && value.equals(a.value)){
					return true;
				} else if(name == null && a.name == null && value.equals(a.value)){
					return true;
				}
			}
			return false;
		}
	}
	private class JsonArray{
		String name;//属性名
		List<JsonObject> objs;//属性值-复合封装类
		List<JsonArray> arrays;//属性值-数组
		List<Attr> attrs;//属性值-基础封装类
		
		public JsonArray() {
			this.objs = new ArrayList<>();
			this.arrays = new ArrayList<>();
			this.attrs = new ArrayList<>();
		}
		
		public String simpleMsg(){
			int childCount = objs.size() + arrays.size() + attrs.size();
			return "Array " + name + "(" + childCount + ")";
		}
		
		public String jsonString() {
			StringBuilder builder = new StringBuilder();
			if(name == null){
				builder.append("[");
			} else {
				builder.append("\"").append(name).append("\" : [");
			}
			for(Attr attr : attrs){
				builder.append(attr.jsonString()).append(",");
			}
			for(JsonObject o : objs){
				builder.append(o.jsonString()).append(",");
			}
			for(JsonArray a : arrays){
				builder.append(a.jsonString()).append(",");
			}
			if(builder.charAt(builder.length() - 1) == ','){
				builder.deleteCharAt(builder.length() - 1);
			}
			builder.append("]");
			return builder.toString();
		}
		
		@Override
		public String toString() {
			return simpleMsg();
		}
		
		@Override
		public boolean equals(Object obj) {
			if(obj instanceof JsonArray){
				JsonArray a = (JsonArray) obj;
				if(!StringUtil.isEmpty(name) && !StringUtil.isEmpty(a.name) && !name.equals(a.name)){
					return false;
				} else if(!StringUtil.isEmpty(name) && StringUtil.isEmpty(a.name)){
					return false;
				} else if(StringUtil.isEmpty(name) && !StringUtil.isEmpty(a.name)){
					return false;
				}
				for(JsonObject o : objs){
					boolean isSame = false;
					for(JsonObject oa : a.objs){
						if(oa.equals(o)){
							isSame = true;
							break;
						}
					}
					if(!isSame){
						return false;
					}
				}
				for(JsonArray o : arrays){
					boolean isSame = false;
					for(JsonArray oa : a.arrays){
						if(oa.equals(o)){
							isSame = true;
							break;
						}
					}
					if(!isSame){
						return false;
					}
				}
				for(Attr o : attrs){
					boolean isSame = false;
					for(Attr oa : a.attrs){
						if(oa.equals(o)){
							isSame = true;
							break;
						}
					}
					if(!isSame){
						return false;
					}
				}
				return true;
			}
			return false;
		}
	}
	private class JsonObject{
		String name;//属性名
		List<JsonObject> objs;//属性值-复合封装类
		List<JsonArray> arrays;//属性值-数组
		List<Attr> attrs;//属性值-基础封装类
		
		public JsonObject() {
			this.objs = new ArrayList<>();
			this.arrays = new ArrayList<>();
			this.attrs = new ArrayList<>();
		}
		
		public String simpleMsg(){
			int childCount = objs.size() + arrays.size() + attrs.size();
			return "Object " + name + "(" + childCount + ")";
		}
		
		public String jsonString() {
			StringBuilder builder = new StringBuilder();
			if(name == null){
				builder.append("{");
			} else {
				builder.append("\"").append(name).append("\" : {");
			}
			for(Attr attr : attrs){
				builder.append(attr.jsonString()).append(",");
			}
			for(JsonObject o : objs){
				builder.append(o.jsonString()).append(",");
			}
			for(JsonArray a : arrays){
				builder.append(a.jsonString()).append(",");
			}
			if(builder.charAt(builder.length() - 1) == ','){
				builder.deleteCharAt(builder.length() - 1);
			}
			builder.append("}");
			return builder.toString();
		}
		
		@Override
		public String toString() {
			return simpleMsg();
		}
		
		@Override
		public boolean equals(Object obj) {
			if(obj instanceof JsonObject){
				JsonObject a = (JsonObject) obj;
				if(!StringUtil.isEmpty(name) && !StringUtil.isEmpty(a.name) && !name.equals(a.name)){
					return false;
				} else if(!StringUtil.isEmpty(name) && StringUtil.isEmpty(a.name)){
					return false;
				} else if(StringUtil.isEmpty(name) && !StringUtil.isEmpty(a.name)){
					return false;
				}
				for(JsonObject o : objs){
					boolean isSame = false;
					for(JsonObject oa : a.objs){
						if(oa.equals(o)){
							isSame = true;
							break;
						}
					}
					if(!isSame){
						return false;
					}
				}
				for(JsonArray o : arrays){
					boolean isSame = false;
					for(JsonArray oa : a.arrays){
						if(oa.equals(o)){
							isSame = true;
							break;
						}
					}
					if(!isSame){
						return false;
					}
				}
				for(Attr o : attrs){
					boolean isSame = false;
					for(Attr oa : a.attrs){
						if(oa.equals(o)){
							isSame = true;
							break;
						}
					}
					if(!isSame){
						return false;
					}
				}
				return true;
			}
			return false;
		}
	}
	
	public String attrString(){
		return "attrs : " + attrs.toString() + "\nobjects : " + objs.toString() + "\narrays : " + arrays.toString();
	}
	
	public String sequenceString(){
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < sequence.size(); ++ i){
			Object obj = sequence.get(i);
			if(obj instanceof Integer){
				int state = (int) obj;
				builder.append(State.name(state)).append("\n");
			} else {
				builder.append(obj.toString()).append("\n");
			}
		}
		return builder.toString();
	}
	
	@Override
	public String toString() {
		return attrString();
	}
}
