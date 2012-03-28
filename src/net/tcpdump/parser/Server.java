package net.tcpdump.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Server {
	//private String ip;
	//ArrayList<Application> applications;//unused
	HashMap<Integer, Application> hm;//Port -> Application

	public Server(){
		hm = new HashMap<Integer, Application>();
		//applications = new ArrayList<Application>();
	}

	public Application getApplicationByPort(int port){
		return hm.get(port);
	}

	public void addApplication(Application a){
		hm.put(a.getPort(), a);
	}
	
	public String getApplications(){
		Iterator<Entry<Integer, Application>> it = hm.entrySet().iterator();
		//Integer port;
		Application app;
		
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		
		while(it.hasNext()){
            Map.Entry<Integer, Application> pairs = (Entry<Integer, Application>)it.next();
            //port = pairs.getKey();
            app = pairs.getValue();
            
            sb.append("app: " + app.getName() + ", ");
		}
		sb.append("]");
		return sb.toString();
	}
	

}