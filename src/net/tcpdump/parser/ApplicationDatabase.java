package net.tcpdump.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;

public class ApplicationDatabase {
	static HashMap<Integer, Application> hm; // Port -> Application Name
	
	public ApplicationDatabase(){
		hm = new HashMap<Integer, Application>();
		String properties = "";
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("port-mapping.txt");
		try {
			properties = convertStreamToString(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		generateMappersFromProperties(properties);
	}
	
	public static Application getApplicationByPort(int port){
		return hm.get(port);
	}
	
	public String convertStreamToString(InputStream is) throws IOException {
		if (is != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		} else {
			return "";
		}
	}

	private void generateMappersFromProperties(String properties) {
		String[] propertiesLines = properties.split("\n"); //each line contains next service
		for (int i =0; i < propertiesLines.length; ++i) {
			//System.out.println("read line: " + propertiesLines[i]);
			String[] propertiesArray = propertiesLines[i].split("\t");
			
			String port = propertiesArray[0];
			String serviceName = propertiesArray[1];

			hm.put(Integer.parseInt(port), new Application(serviceName, Integer.parseInt(port)));
		}

	}
	
	public static String getApplicationNameByPort(int port){
		if(hm.containsKey(port)){
			return hm.get(port).getName();
		}
		else{
			return "unknown " + port;
		}
	}

}
