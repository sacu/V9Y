package org.jiira;


import org.jiira.data.netty.tcp.SocketDataWorker;
import org.jiira.protobuf.SAProtoDecode;
import org.jiira.protobuf.SAProtoDecode.STCard;
import org.jiira.utils.DBSessionFactory;
import org.jiira.utils.SAAppConfig;
import org.jiira.utils.SADataTableManager;

import core.utils.SALog;
import core.utils.sys.SAIOCManager;


public class Launch {
	private static Launch _instance;
	public static void main(String[] args) {
		Launch.getInstance().launch();	
	}
	
	public static Launch getInstance(){
		if(null == _instance){
			_instance = new Launch();
		}
		return _instance;
	}
	
	public Launch(){
	}
	
	public void launch(){
		SAIOCManager.IC = 19870818;
		SALog.IsRelease = false;
		SAAppConfig.InitializeConfiguration();//配置文件初始化
		//sa proto
		SADataTableManager.getInstance().readDataTable();//读取数据表
//		DBSessionFactory.init();//数据库链接初始化
		//读取数据库
//		CacheFactory.getInstance().readCache();//初始化缓存数据(需要在IOC之前)
		SAIOCManager.getInstance().loadIOC("config/" + SAAppConfig.getZone() + "/ioc.xml");//ioc 初始化
		SocketDataWorker sdw = (SocketDataWorker)SAIOCManager.getInstance().datas.get("org.jiira.data.netty.tcp.SocketDataWorker");
		sdw.startup();
//		STCard card = STCard.getMap().get(1000);
//		SALog.info(card.getName());//米诺斯战士（牛头怪）
		//测试sa proto
//		SAProtoDecode
	}
}
