package org.jiira.logic.update;

import org.jiira.utils.CommandCollection.SingleUpdateTypeEnum;

public class SingleUpdateENV {
	public Integer changeId;//需要更新的ID
	public Integer acceptId;//接收消息的ID
	public SingleUpdateTypeEnum[] types;//消费类型
	public int[] ids;//值
	public int[] counts;//值
	
	private SingleUpdateENV(Integer changeId, Integer acceptId, SingleUpdateTypeEnum[] types, int[] ids, int[] counts){
		this.changeId = changeId;//需要更新的ID
		this.acceptId = acceptId;//接收消息的ID
		this.types = types;//消费类型
		this.ids = ids;//值
		this.counts = counts;
	}
	
	public static SingleUpdateENV getSingle(Integer changeId, Integer acceptId, SingleUpdateTypeEnum[] types, int[] ids, int[] counts){
		return new SingleUpdateENV(changeId, acceptId, types, ids, counts);
	}
}
