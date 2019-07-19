package org.jiira.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import core.utils.sys.BaseConfig;

public class SAAppConfig {
    private static final Logger log = LoggerFactory.getLogger(SAAppConfig.class);
	
	private static String zone;
	private static String host;
	public static boolean InitializeConfiguration(){
		ObjectMapper mapper = new ObjectMapper();
		Path zonePath = Paths.get("config/zone.json");
		try {
			zone = mapper.readValue(Files.readAllBytes(zonePath), ZoneConfig.class).zone;
            Path systemConfig = Paths.get("config/" + zone + "/" + "configuration.json");
			SystemConfig.setValue(mapper.readValue(Files.readAllBytes(systemConfig), SystemConfig.class));
			BaseConfig.instance().setBaseThreadDelay(SystemConfig.instance().getBaseThreadDelay());
            DOMConfigurator.configure(SystemConfig.instance().getLog4jPath());
            //获取地址
            host = InetAddress.getLocalHost().getHostAddress();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage(), e);
            return false;
		}
		log.info("初始化成完毕");
		return true;
	}
    public static String getZone() {
        return zone;
    }
    public static String getHost(){
    	return host;
    }
}

class ZoneConfig{
	public String zone;
}

