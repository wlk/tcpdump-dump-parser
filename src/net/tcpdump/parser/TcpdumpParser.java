package net.tcpdump.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TcpdumpParser {

	File outputFile;
	Scanner s;
	String MyIp;
	ServerDatabase db;
	ApplicationDatabase adb;
	
	public TcpdumpParser(File file) throws FileNotFoundException {
		s = new Scanner(file);
		MyIp = getMyIp();
		System.out.println("my ip: " + MyIp);
		db = new ServerDatabase();
		adb = new ApplicationDatabase();
		
		parse();
		
		System.out.println(db.getAllServers());
	}

	public void setOutputFileName(String outputFileName) {

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

	private String getMyIp() {
		String myIP = "";
		try {
			InetAddress addr = InetAddress.getLocalHost();
			myIP = addr.getHostAddress();
		} catch (UnknownHostException e) {
			System.out.println("unable to determine my ip " + e);
		}
		return myIP;
	}

	/*
	 * example 2 lines:
IP (tos 0x0, ttl 64, id 29503, offset 0, flags [DF], proto TCP (6), length 52)
    192.168.1.101.59636 > 92.122.217.74.80: tcp 0
     
    first line: IP - means this is IP protocol
    second line: source_host.source_port > remote_host.remote_port
	 */
	
	private void getRemoteIpAndPortFromLine(String line) {
		
		line = line.trim();
		line = line.replace(":", "");
		String[] tokens = line.split(" ");
		int[] shifts = {0,2};
		String[] toReturn = { "", "" };
		
		for(int j = 0; j < shifts.length; ++j){
			String IpAndPort = tokens[0+shifts[j]];
			
			//System.out.println("IpAndPort: " + IpAndPort);
			
			String[] tokens2 = IpAndPort.split("\\.");
			
			StringBuffer sb = new StringBuffer();
			sb.append(tokens2[0] + ".");
			sb.append(tokens2[1] + ".");
			sb.append(tokens2[2] + ".");
			sb.append(tokens2[3]);
			
			String ip = sb.toString();

			toReturn[0] = ip;
			if(tokens2.length < 5){//no port given
				continue;
			}
			toReturn[1] = tokens2[4];
				
			db.add(ip, Integer.parseInt(tokens2[4]));
			
			//System.out.println("host: " + ip + " port: " + tokens2[4]);
			
		}
	}

}
