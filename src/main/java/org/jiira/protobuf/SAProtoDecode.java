package org.jiira.protobuf;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
public class SAProtoDecode {
	public static final String SAString = "string";
	public static final String SAInt = "int";
	public static final String SAFloat = "float";
	public static final String SABoolean = "boolean";
	public static final String SAArray = "array";
	public static final String isBooleanStr = "1";
	public static final String splitStr = "\\[\\*]";
	public static final String decodeAssign = "[:]";
	public static final String decodeSplit = "[,]";
	public static final String decodeEnd = "[}]";
	public static final String decodeArrayAssign = "[:][{]";
	public static final String decodeArrayEnd = "[}][,]";
	public static final String decodeStrEnd = "\"";
	public static final String classNameEnd = "[}][]]\"";
	public SAProtoDecode(){
	}
	/**
	 * 解析一套数据表
	 * @param iostring
	 */
	public static void parsing(String iostring) {
		int x = 0, y = 0, len = iostring.length();
		String typeName;
		String endName;
		String fragmentStr;
		while(x < len){
			x = iostring.indexOf(decodeStrEnd, x) + 1;//查询类型名称首部
			if(x == -1)
				break;//解析完毕
			y = iostring.indexOf(decodeStrEnd, x);//查询类型名称尾部
			typeName = iostring.substring(x, y);//获得类型
			endName = classNameEnd + typeName + decodeStrEnd;//获得类型结尾(如果解析内容中包含了自身类型，无法解析)
			x = y + decodeAssign.length() + 1;//查询内容首部(跳过冒号)
			y = iostring.indexOf(endName, x);//查询内容尾部
			fragmentStr = iostring.substring(x, y);//获得内容
			x = y + endName.length();
			setIOString(typeName, fragmentStr);
		}
	}
	public static class ConvertModel{
		private int x, y, len;
		private String iostring;
		private static ConvertModel instance;
		public static ConvertModel getInstance(){
			if(null == instance){
				instance = new ConvertModel();
			}
			return instance;
		}
		public void setting(String iostring){
			setting(iostring, 0, 0);
		}
		public void setting(String iostring, int x, int y){
			len = iostring.length();
			this.iostring = iostring;
			this.x = x;
			this.y = y;
		}
		private String read() {
		int offset = decodeAssign.length();
			x = iostring.indexOf(decodeAssign, x) + offset;
			y = iostring.indexOf(decodeSplit, x);
			if(y == -1) {
				y = iostring.indexOf(decodeEnd, x);//结尾保护
				offset = decodeEnd.length();
			}
			String value = iostring.substring(x, y);
			x = y + offset;
			return value;
		}
		public int readInt(){
			return Integer.parseInt(read());
		}
		public boolean readBoolean() {
			return read().equals(isBooleanStr);
		}
		public float readFloat() {
			return Float.parseFloat(read());
		}
		public String readString() {
			return read();
		}
		public String[] readArray(){
			x = iostring.indexOf(decodeArrayAssign, x) + decodeArrayAssign.length();
			y = iostring.indexOf(decodeArrayEnd, x);
			String value = iostring.substring(x, y);
			x = y + decodeArrayEnd.length();
			return value.split(splitStr);
		}
		public boolean limit(){
			return x >= len;
		}
		public void flip() {
			x = 0;
			y = 0;
		}
	}
	public static final String STCardType = "sTCard";
	public static class STCard {
		private int Id;
		public int getId(){return Id;}
		private String Name;
		public String getName(){return Name;}
		private int Rare;
		public int getRare(){return Rare;}
		private int Up;
		public int getUp(){return Up;}
		private int Left;
		public int getLeft(){return Left;}
		private static ArrayList<STCard> list;
		private static Map<Integer, STCard> map;
		public STCard() {}
		public static ArrayList<STCard> getList(){
			if(null == list){
				list = new ArrayList<STCard>();
			}
			return list;
		}
		public static Map<Integer, STCard> getMap(){
			if(null == map){
				map = new ConcurrentHashMap<Integer, STCard>();
			}
			return map;
		}
		public static void parsing(String iostring) {
			ArrayList<STCard> list = getList();
			Map<Integer, STCard> map = getMap();
			list.clear();
			map.clear();
			ConvertModel buf = ConvertModel.getInstance();
			buf.setting(iostring);
			STCard sTCard;
			while(!buf.limit()) {
				sTCard= new STCard();
				sTCard.Id = buf.readInt();
				sTCard.Name = buf.readString();
				sTCard.Rare = buf.readInt();
				sTCard.Up = buf.readInt();
				sTCard.Left = buf.readInt();
				list.add(sTCard);
				map.put(sTCard.Id, sTCard);
			}
		}
	}
	public static void setIOString(String type, String iostring) {
		switch(type) {
			case SAProtoDecode.STCardType: {
				STCard.parsing(iostring);
				break;
			}//先看结果
		}
	}
}