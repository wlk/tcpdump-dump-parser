package net.tcpdump.parser;

import java.io.File;

public class Main {
	public static void main(String[] args){
		String filename = args[0];
		String outputFileName = args[1];
		TcpdumpParser parser = new TcpdumpParser(new File(filename));
		parser.setOutputFileName(outputFileName);
		parser.parse();
	}
}
