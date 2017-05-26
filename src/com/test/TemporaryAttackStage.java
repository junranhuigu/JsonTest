package com.test;
/**
 * 存储临时数据——玩家选择进攻的关卡信息
 * */
public class TemporaryAttackStage {
	private int type;//关卡类型 1主线 2支线 3挑战
	private int stageId;//关卡id
	private int checkPointId;//关卡点id
	public TemporaryAttackStage() {
		// TODO Auto-generated constructor stub
	}
	public TemporaryAttackStage(int type, int stageId, int checkPointId) {
		this.type = type;
		this.stageId = stageId;
		this.checkPointId = checkPointId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getStageId() {
		return stageId;
	}
	public void setStageId(int stageId) {
		this.stageId = stageId;
	}
	public int getCheckPointId() {
		return checkPointId;
	}
	public void setCheckPointId(int checkPointId) {
		this.checkPointId = checkPointId;
	}
}
