package org.jiira.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.jiira.protobuf.SAProtoDecode;

public class SADataTableManager {
	// D:/svn/serv/CardServer/config/local/saproto
	@SuppressWarnings("unused")
	private static SADataTableManager instance;

	public static SADataTableManager getInstance() {
		if (null == instance) {
			instance = new SADataTableManager();
		}
		return instance;
	}

	public void readDataTable() {
		File f = new File("config/" + SAAppConfig.getZone() + "/" + "saproto.u");
		InputStreamReader read;
		try {
			read = new InputStreamReader(new FileInputStream(f), "UTF-8");// 考虑到编码格�?
			@SuppressWarnings("resource")
			BufferedReader bufferedReader = new BufferedReader(read);
			StringBuffer proto = new StringBuffer();
			String lineTxt;
			while ((lineTxt = bufferedReader.readLine()) != null) {
				proto.append(lineTxt);
			}
			SAProtoDecode.parsing(proto.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
