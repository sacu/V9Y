package org.jiira.utils;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
/**
 * 
 * @author time
 * Mybatis Impl To Service Factory MITSF
 *
 */
public class MITSF {
	private static Map<Class<?>, Object> services;
	private final static String IService = "org/jiira/data/mybatis/service";
	private final static String DIServiceImp = "org.jiira.data.mybatis.service.";
	private final static String DServiceImpl = "org.jiira.data.mybatis.service.impl.";

	public static void init() {
		services = new HashMap<Class<?>, Object>();
		Class<?> c = MITSF.class;
		ClassLoader loader = c.getClassLoader();
		URL url = loader.getResource(IService);
		URI uri = null;
		try {
			uri = url.toURI();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 2. 通过File获得uri下的所有文件
		File file = new File(uri);
		File[] files = file.listFiles();
		Class<? extends Object> iface;
		Constructor<?> cons;
		for (File f : files) {
			String fName = f.getName();
			if (!fName.endsWith(".class")) {
				continue;
			}
			fName = fName.substring(0, fName.length() - 6);
			// 3. 通过反射加载类
			try {
				iface = Class.forName(DIServiceImp + fName);
				c = Class.forName(DServiceImpl + fName.substring(1) + "Impl");
				cons = c.getConstructor();
				services.put(iface, cons.newInstance());
			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
					| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	@SuppressWarnings("unchecked")
	public static <T> T getIService(Class<T> c) {
		if(services.containsKey(c)) {
			return (T)services.get(c);
		}
		return null;
	}
}
