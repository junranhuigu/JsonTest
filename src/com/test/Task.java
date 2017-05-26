package com.test;

import java.util.HashSet;
import java.util.Set;

/**
 * 任务信息
 * */
public class Task {
	private int id;//任务id
	private int mapId;//对应大地图id
	private Position position;//地图坐标
	private Set<Integer> stageDetails;//已通关卡
	private int state;//关卡状态 0未开通 1可以打 2已完成
	
	public static final int STATE_NOT_OPEN = 0;
	public static final int STATE_CAN_ATTACK = 1;
	public static final int STATE_FINISHED = 2;
	
	public Task() {
		this.stageDetails = new HashSet<>();
	}
	
	/**
	 * 主线任务初始
	 * */
	public Task(int mainStageId){
		this();
		this.id = mainStageId;
		this.mapId = 0;
	}
	
	/**
	 * 支线任务初始
	 * */
	public Task(int branchStageId, Position position){
		this.id = branchStageId;
		this.position = position;
		this.mapId = position.getMapId();
	}
	
	/**
	 * 记录已通过关卡id
	 * */
	public void recordPassCheckPoint(int checkpointId){
		this.stageDetails.add(checkpointId);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMapId() {
		return mapId;
	}
	public void setMapId(int mapId) {
		this.mapId = mapId;
	}
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	public void setStageDetails(Set<Integer> stageDetails) {
		this.stageDetails = stageDetails;
	}
	public Set<Integer> getStageDetails() {
		return stageDetails;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", mapId=" + mapId + ", position=" + position
				+ ", stageDetails=" + stageDetails + ", state=" + state + "]";
	}
}
