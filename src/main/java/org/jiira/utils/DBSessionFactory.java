package org.jiira.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.utils.SALog;
import core.utils.sys.SADBConfigType;

public class DBSessionFactory {
	// 日志记录
	private static final Logger logger = LoggerFactory
			.getLogger(DBSessionFactory.class);

	private static Map<String, Boolean> useSlaveDB = new HashMap<String, Boolean>();
	// dbname,url
	private static Map<String, String> connectionUrlMap = new HashMap<String, String>();
	// dbname,sqlSessionFactory
	private static Map<String, SqlSessionFactory> sqlSessionFactoryMap = new HashMap<String, SqlSessionFactory>();

	private static final String CONFIG_BASEDIR = "config/";
	// 测试标识
	private static boolean DEBUG_FLAG = false;

	public static void init() {
		useSlaveDB.clear();
		sqlSessionFactoryMap.clear();
		InputStream is = null;
		try {
			// local
			String zone = SAAppConfig.getZone();
			Properties properties = new Properties();

			Map<String, SADBConfigType> dbConfigMap = SystemConfig.instance().getDbConfig();
			for (Map.Entry<String, SADBConfigType> entry : dbConfigMap.entrySet()) {
                useSlaveDB.put(entry.getKey(), false);
                //主数据库
                StringBuilder propertiesFilePath = new StringBuilder();
                propertiesFilePath.append(CONFIG_BASEDIR);
                propertiesFilePath.append(zone);
                propertiesFilePath.append("/");
                propertiesFilePath.append(entry.getValue().propertiesFilename);
                
                try {
                    is = new FileInputStream(propertiesFilePath.toString());
                    properties.load(is);
                    connectionUrlMap.put(entry.getKey(), properties.getProperty("url"));
                    is = Resources.getResourceAsStream(CONFIG_BASEDIR + entry.getValue().configXmlFilename);
                    sqlSessionFactoryMap.put(entry.getKey(), new SqlSessionFactoryBuilder().build(is, properties));
                    if(sqlSessionFactoryMap.get(entry.getKey()) == null) {
                        logger.error("Failed to connect the session : {}", entry.getKey());
                    }
                } catch (FileNotFoundException e) {
                    logger.error("Not exists master DB config file.", e);
                    continue;
                }

                SALog.info("数据库 : " + entry.getKey() + " 加在完毕");
            }
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	public static SqlSessionFactory getSqlSessionFactory(String dbId) {

		if (DEBUG_FLAG == true || SystemConfig.instance().isEnableDBShard() == false)
			return sqlSessionFactoryMap.get("gamedb");

		return sqlSessionFactoryMap.get(dbId);
	}

	public static SqlSessionFactory getSqlSessionFactoryForce(String dbId) {

		return sqlSessionFactoryMap.get(dbId);
	}
	
	public static SqlSessionFactory getSqlSessionFactoryShard(String gsn) {
		// 测试服默认走这 目前测试的时候
		if (DEBUG_FLAG || SystemConfig.instance().isEnableDBShard() == false) {
			return sqlSessionFactoryMap.get("gamedb");
		}

		BigInteger gsnNumber = new BigInteger(gsn);
		int resultHash = gsnNumber.mod(new BigInteger("2")).intValue();
		// 至少为两位10进制数
		String dbName = String.format("gamedb%02d", resultHash + 1);
		return sqlSessionFactoryMap.get(dbName);
	}

	public static boolean isUseSlaveDB(String dbId) {
		return useSlaveDB.get(dbId);
	}

	public static Map<String, Boolean> getUseSlaveDB() {
		return useSlaveDB;
	}

	public static Map<String, String> getConnectionUrlMap() {
		return connectionUrlMap;
	}
}
