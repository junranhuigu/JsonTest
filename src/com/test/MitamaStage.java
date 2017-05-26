package com.test;
/**
 * 御魂关卡
 * */
public class MitamaStage {
	private int id;//御魂关卡id
	private int productCount;//今日产出次数
	public MitamaStage() {
		// TODO Auto-generated constructor stub
	}
	public MitamaStage(int id) {
		this.id = id;
	}
	
	/**
	 * 刷新数据
	 * */
	public void refresh(){
		this.productCount = 0;
	}
	/**
	 * 记录一次的产出
	 * */
	public void recordProduct(){
		++ productCount;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProductCount() {
		return productCount;
	}
	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}
}
