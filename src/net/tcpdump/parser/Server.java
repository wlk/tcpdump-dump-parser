package net.tcpdump.parser;

import java.util.ArrayList;
import java.util.HashMap;

public class Server {
	private String ip;
	ArrayList<Application> applications;//unused
	HashMap<Integer, Application> hm;//Port -> Application

	public Server(){
		hm = new HashMap<Integer, Application>();
		applications = new ArrayList<Application>();
	}

	public Application getApplicationByPort(int port){
		return hm.get(port);
	}

	public void addApplication(Application a){
		hm.put(a.getPort(), a);
	}
	
	public String getApplications(){
		//TODO
		return "";
	}
	
	public String getPorts(){
		//TODO
		return "";
	}

}