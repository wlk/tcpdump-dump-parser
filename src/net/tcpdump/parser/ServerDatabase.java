package net.tcpdump.parser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class ServerDatabase {
	
	static HashMap<String, Server> hm;
	
	public ServerDatabase(){
		hm = new HashMap<String, Server>();//IP->Server
	}
	
	public void add(String ip, int port){
		
		if(! hm.containsKey(ip)){//no server found
			hm.put(ip, new Server());
		}
		Server s = hm.get(ip);
		
		Application a = s.getApplicationByPort(port);
		if(a != null){
			System.out.println("app is not null");
			s.addApplication(new Application(ApplicationDatabase.getApplicationNameByPort(port), port));
		}
		else{
			System.out.println("app is null");
			s.addApplication(new Application("unknown", port));
		}
	}
	
	
	public String getAllServers(){
		Iterator<Entry<String, Server>> it = hm.entrySet().iterator();
		Server s;
		String host;
		
		StringBuffer sb = new StringBuffer();
		
		while(it.hasNext()){
			
            Map.Entry<String, Server> pairs = (Entry<String, Server>)it.next();
            host = pairs.getKey();
            s = pairs.getValue();
            
            sb.append("server: " + host + "\t" + s.getApplications() + "\n");
		}
		return sb.toString();
	}

}
