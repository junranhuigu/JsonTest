package com.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 地图信息
 * */
public class WorldMap {
	private String id;//角色id
	private Task mainTask;//主线任务
	private Task[] branchTasks;//随机任务列表
	private List<Task> challengeTasks;//挑战任务列表
	private List<MitamaStage> caves;//已开放御魂槽
	private long refreshTime;//刷新时间
	private int finishBranchTaskCount;//当日完成支线任务数
	private int finishChallengeTaskCount;//当日完成挑战任务数
	private int[][] rewardBoxIds;//已领取宝箱id
	private List<List<List<Integer>>> branchPassIds;//已通关支线任务id 再此队列中的支线任务可扫荡
	private TemporaryAttackStage temporaryStage;//角色进攻关卡信息
	private HashMap<Integer, Task> map;
	
	public WorldMap() {
		// TODO Auto-generated constructor stub
	}
	
	public WorldMap(String id) {
		this.id = id;
		this.mainTask = new Task(1);
		this.branchTasks = new Task[3];
		this.challengeTasks = new ArrayList<>();
		this.caves = new ArrayList<>();
		this.refreshTime = System.currentTimeMillis();
		this.rewardBoxIds = new int[3][];
		this.map = new HashMap<>();
//		this.branchPassIds = new ArrayList<>();
	}
	

	public Task[] getBranchTasks() {
		return branchTasks;
	}

	public void setBranchTasks(Task[] branchTasks) {
		this.branchTasks = branchTasks;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<Task> getChallengeTasks() {
		return challengeTasks;
	}
	public void setChallengeTasks(List<Task> challengeTasks) {
		this.challengeTasks = challengeTasks;
	}
	public List<MitamaStage> getCaves() {
		return caves;
	}
	public void setCaves(List<MitamaStage> caves) {
		this.caves = caves;
	}
	public long getRefreshTime() {
		return refreshTime;
	}
	public void setRefreshTime(long refreshTime) {
		this.refreshTime = refreshTime;
	}
	public int getFinishBranchTaskCount() {
		return finishBranchTaskCount;
	}
	public void setFinishBranchTaskCount(int finishBranchTaskCount) {
		this.finishBranchTaskCount = finishBranchTaskCount;
	}
	public int getFinishChallengeTaskCount() {
		return finishChallengeTaskCount;
	}
	public void setFinishChallengeTaskCount(int finishChallengeTaskCount) {
		this.finishChallengeTaskCount = finishChallengeTaskCount;
	}
	public int[][] getRewardBoxIds() {
		return rewardBoxIds;
	}

	public void setRewardBoxIds(int[][] rewardBoxIds) {
		this.rewardBoxIds = rewardBoxIds;
	}

	public Task getMainTask() {
		return mainTask;
	}
	public void setMainTask(Task mainTask) {
		this.mainTask = mainTask;
	}
	public List<List<List<Integer>>> getBranchPassIds() {
		return branchPassIds;
	}

	public void setBranchPassIds(List<List<List<Integer>>> branchPassIds) {
		this.branchPassIds = branchPassIds;
	}

	public TemporaryAttackStage getTemporaryStage() {
		return temporaryStage;
	}
	public void setTemporaryStage(TemporaryAttackStage temporaryStage) {
		this.temporaryStage = temporaryStage;
	}

	public HashMap<Integer, Task> getMap() {
		return map;
	}

	public void setMap(HashMap<Integer, Task> map) {
		this.map = map;
	}
}
