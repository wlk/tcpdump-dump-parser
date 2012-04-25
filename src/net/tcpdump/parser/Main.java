package net.tcpdump.parser;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {
	
	public static void main(String[] args){
		String filename = args[0];
		String type = args[1];
		//String outputFileName = args[1];
		ParserInterface parser = null;
		
		//String MyIp;
		ServerDatabase db;
		ApplicationDatabase adb;
		
		//MyIp = getMyIp();
		db = new ServerDatabase();
		adb = new ApplicationDatabase();
		
		try {
			if(type.equals("dump")){
				parser = new TcpdumpParser(new File(filename), adb, db);
			}
			else{
				parser = new TSVParser(new File(filename), adb, db);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
		
		parser.parse();
		
		System.out.println(db.getAllServers());
	}
}
