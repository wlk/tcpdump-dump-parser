package net.tcpdump.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * file format (all fields are separated by TABs):
 * SOURCE_IP	DESTINATION_IP	TTL	SOURCE_PORT	DESTINATION_PORT	PROTOCOL 
 */

public class TSVParser implements ParserInterface {

	Scanner s;
	ServerDatabase db;
	ApplicationDatabase adb;

	public TSVParser(File file, ApplicationDatabase adb, ServerDatabase db) throws FileNotFoundException {
		this.adb = adb;
		this.db = db;
		s = new Scanner(file);
		parse();
	}

	@Override
	public void parse() {
		while (s.hasNext()) {
			String line = s.nextLine();
			String[] lineArr = line.split("\\t");
			if (lineArr.length != 6) {// not complete line
				continue;
			} else {// complete line
				try {
					//System.out.println(lineArr[0] + " " + lineArr[1] + " " + lineArr[2] + " " + lineArr[3] + " " + lineArr[4] + " " + lineArr[5]);
					db.add(lineArr[0], Integer.parseInt(lineArr[3]));
					db.add(lineArr[1], Integer.parseInt(lineArr[4]));
					
					db.setTTL(lineArr[0], Integer.parseInt(lineArr[2]));
	
				} catch (NumberFormatException e) {
					// do nothing
				}
			}

		}

	}

}
