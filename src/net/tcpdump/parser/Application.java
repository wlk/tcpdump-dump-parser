package net.tcpdump.parser;

public class Application {

	public Application(String name, int port) {
		this.name = name;
		this.port = port;
	}


	private int port;
	private String name;
	
	public Integer getPort() {
		return port;
	}

	public String getName() {
		return name;
	}
}
