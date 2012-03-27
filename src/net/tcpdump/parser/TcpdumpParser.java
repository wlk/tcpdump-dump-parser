package net.tcpdump.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TcpdumpParser {
	
	File outputFile;
	Scanner s;
	String MyIp;
	ServerDatabase db;

	public TcpdumpParser(File file) throws FileNotFoundException {
		s = new Scanner(file);
		MyIp = getMyIp();
		db = new ServerDatabase();
		parse();
	}

	public void setOutputFileName(String outputFileName) {
		
	}

	public void parse() {
		while(s.hasNext()){
			String line = s.nextLine();
			if(line.startsWith("IP")){
				String IpLine = s.nextLine();
				
				String host = getRemoteIpFromLine(IpLine);
				String port = getRemotePortFromLine(IpLine);
								
				db.add(host, Integer.parseInt(port));
			}
		}
		
	}
	
	private String getMyIp(){
		return "";
	}
	
	private String getRemoteIpFromLine(String line){
		return "";
	}
	
	private String getRemotePortFromLine(String line){
		return "";
	}

}
