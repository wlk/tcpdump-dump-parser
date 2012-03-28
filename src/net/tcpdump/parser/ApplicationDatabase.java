package net.tcpdump.parser;

import java.util.HashMap;

public class ApplicationDatabase {
	static HashMap<Integer, Application> hm; // Port -> Application Name
	
	public static void init(){
		hm = new HashMap<Integer, Application>();
		hm.put(80, new Application("HTTP Server", 80));
		hm.put(443, new Application("HTTPS Server", 443));
		hm.put(22, new Application("SSH Server", 22));
		//TODO add reading this from file
	}
	
	public static Application getApplicationByPort(int port){
		return hm.get(port);
	}
	
	public static String getApplicationNameByPort(int port){
		return hm.get(port).getName();
	}

}
