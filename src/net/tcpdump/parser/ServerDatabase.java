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
			Server s = new Server();
			s.setIp(ip);
			hm.put(ip, s);
			//System.out.println("new server: " + ip);
		}
		Server s = hm.get(ip);
		
		Application a = ApplicationDatabase.getApplicationByPort(port);
		if(a != null){
			//System.out.println("app is not null " + port);
			s.addApplication(new Application(ApplicationDatabase.getApplicationNameByPort(port), port));
			//System.out.println("app: " + ApplicationDatabase.getApplicationNameByPort(port));
		}
		else{
			//System.out.println("app is null " + port);
			s.addApplication(new Application("unknown " + port, port));
		}
	}
	
	public void setTTL(String ip, int ttl){
		Server s = hm.get(ip);
		s.setMaxTTL(ttl);
		//System.out.println("for server: " + ip + " ttl was set to: " + ttl);
		//System.out.println("max ttl for server: " + ip + " " + s.getMaxTTL());
	}
	
	public String getOSForTTL(int ttl){
		if(ttl > 64){
			return "Windows";
		}
		return "Linux";
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
            
            sb.append("server: " + host + "\t" + "os: " + getOSForTTL(s.getMaxTTL()) +  "\t" + s.getApplications() + "\tos (nmap):" + s.getOS() + "\n");
		}
		return sb.toString();
	}

}
