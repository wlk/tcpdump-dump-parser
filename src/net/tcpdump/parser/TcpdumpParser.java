package net.tcpdump.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TcpdumpParser implements ParserInterface{

	File outputFile;
	Scanner s;
	String MyIp;
	ServerDatabase db;
	ApplicationDatabase adb;
	
	public TcpdumpParser(File file, ApplicationDatabase adb, ServerDatabase db) throws FileNotFoundException {
		this.adb = adb;
		this.db = db;
		s = new Scanner(file);
		parse();		
	}

	public void parse() {
		while (s.hasNext()) {
			String line = s.nextLine();
			if (line.startsWith("IP")) {
				String IpLine = s.nextLine();
				//System.out.println("IP line: " + IpLine);
				
				getRemoteIpAndPortFromLine(IpLine);

			}
		}

	}

	/*
	 * example 2 lines:
IP (tos 0x0, ttl 64, id 29503, offset 0, flags [DF], proto TCP (6), length 52)
    192.168.1.101.59636 > 92.122.217.74.80: tcp 0
     
    first line: IP - means this is IP protocol
    second line: source_host.source_port > remote_host.remote_port
	 */
	
	//TODO add parsing TTL (+ get which IP is SOURCE, first probably)
	//TODO add parsing protocol (TCP or UDP)
	private void getRemoteIpAndPortFromLine(String line) {
		
		line = line.trim();
		line = line.replace(":", "");
		String[] tokens = line.split(" ");
		int[] shifts = {0,2};
		String[] toReturn = { "", "" };
		
		for(int j = 0; j < shifts.length; ++j){
			if(tokens.length <= shifts[j]){
				continue;
			}
			String IpAndPort = tokens[0+shifts[j]];
			
			//System.out.println("IpAndPort: " + IpAndPort);
			
			String[] tokens2 = IpAndPort.split("\\.");
			if(tokens2.length < 5 || tokens2[4].equals("")){//no port given
				continue;
			}
			
			StringBuffer sb = new StringBuffer();
			sb.append(tokens2[0] + ".");
			sb.append(tokens2[1] + ".");
			sb.append(tokens2[2] + ".");
			sb.append(tokens2[3]);
			
			String ip = sb.toString();

			toReturn[0] = ip;

			toReturn[1] = tokens2[4];
			
			try{
				db.add(ip, Integer.parseInt(tokens2[4]));
			}
			catch(NumberFormatException e){
				//do nothing
			}
			
			//System.out.println("host: " + ip + " port: " + tokens2[4]);
			
		}
	}

}
