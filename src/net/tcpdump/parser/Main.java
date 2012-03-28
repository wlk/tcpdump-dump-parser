package net.tcpdump.parser;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {
	public static void main(String[] args){
		String filename = args[0];
		//String outputFileName = args[1];
		TcpdumpParser parser = null;
		try {
			parser = new TcpdumpParser(new File(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//parser.setOutputFileName(outputFileName);
		parser.parse();
	}
}
