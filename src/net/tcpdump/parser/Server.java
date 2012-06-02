package net.tcpdump.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.plaf.SliderUI;

import com.google.gson.Gson;

public class Server {
	HashMap<Integer, Application> hm;// Port -> Application
	int maxTTL;
	String os;
	String ip;

	public Server() {
		hm = new HashMap<Integer, Application>();
		maxTTL = 0;
		os = "";
		ip = "";
	}
	
	public void setIp(String ip){
		this.ip = ip;
	}

	private String getCommand() {
		String s1 = "nmap -O -oX - " + ip;
		return s1;
	}

	public String getOS() {

		String command = getCommand();

		Runtime runtime = Runtime.getRuntime();
		Process process = null;
		try {
			//System.out.println("running command: " + command);
			process = runtime.exec(command);
			process.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		InputStream is = process.getInputStream();
		InputStream is_err = process.getErrorStream();
		InputStreamReader isr = new InputStreamReader(is);
		InputStreamReader isr_err = new InputStreamReader(is_err);
		BufferedReader br = new BufferedReader(isr);
		BufferedReader br_err = new BufferedReader(isr_err);
		String line;
		try {
			while ((line = br.readLine()) != null) {
				if(line.matches(".*osclass.*")){
					return getOSProperties(line);
				}
			   }
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			while ((line = br_err.readLine()) != null) {
				System.out.println(line);
			   }
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return "";
	}
	
	private String getOSProperties(String s){
		s = s.replace("<osclass", "");
		s = s.replace(" />", "");
		//s = s.substring(9);
		//s = s.replace(" />", "");
		return s;
	}

	public void addApplication(Application a) {
		hm.put(a.getPort(), a);
	}

	public String getApplications() {
		Iterator<Entry<Integer, Application>> it = hm.entrySet().iterator();
		// Integer port;
		Application app;

		StringBuffer sb = new StringBuffer();
		sb.append("[");

		while (it.hasNext()) {
			Map.Entry<Integer, Application> pairs = (Entry<Integer, Application>) it.next();
			// port = pairs.getKey();
			app = pairs.getValue();

			sb.append("app: " + app.getName() + ", ");
		}
		sb.append("]");
		return sb.toString();
	}

	public void setMaxTTL(int ttl) {
		if (ttl > maxTTL) {
			maxTTL = ttl;
		}
	}

	public int getMaxTTL() {
		return maxTTL;
	}

}