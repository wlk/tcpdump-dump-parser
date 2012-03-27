package net.tcpdump.parser;

import java.util.HashMap;

public class ServerDatabase {
	
	static HashMap<String, Server> hm;
	
	public ServerDatabase(){
		hm = new HashMap<String, Server>();
	}
	
	public void add(String ip, int port){
		Server s = hm.get(ip);
		if(s != null){
			Application a = s.getApplicationByPort(port);
			if(a != null){
				s.addApplication(new Application(ApplicationDatabase.getApplicationNameByPort(port), port));
			}
		}
	}

}
