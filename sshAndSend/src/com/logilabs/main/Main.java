package com.logilabs.main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.logilabs.ssh.HostManager;

public class Main {
	
	static ArrayList<String> hostnames;
	
	static HostManager hm = new HostManager();;

	public static void main(String[] args) {
		 String user = "root";//"3033519";
		 String pwd = "h1TQy27DeE";//"3FbGPk28s";
		 String host = "192.168.43.147";//"lqc90990esbdvp01";
		 int port = 22;//2022;
//		String user = "3033519";
//		String pwd = "3FbGPk28s";
//		String host = "lqc90990esbdvp01";
//		int port = 2022;
//		
//		try {
//			readHosts(args[0]);
//		} catch (IOException e) {
//			System.out.println("An error occured trying to read the conts of the file: " + args[0]);
//			e.printStackTrace();
//			System.exit(0);
//		}

		String[] commands = { 
				"chmod 777 /tmp/TibcoEnvReporter.sh",
				"sudo /bin/su - tibco -c /tmp/TibcoEnvReporter.sh", 
				};

		hostnames = new ArrayList<String>();
		hostnames.add(host);   
		
		addHosts(hostnames, user, pwd, port);
		
		
//		hm.testHostConns();
		hm.executeCommandsAgainstHosts(commands);
	}

	private static void addHosts(ArrayList<String> hostnames2, String user, String pwd, int port) {
		
		for(String s : hostnames2){
			hm.addHost(s, port, user, pwd);
		}
		
	}

	private static void readHosts(String string) throws IOException {
		hostnames = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(string));
		String curr;
		
		while ((curr = br.readLine()) != null) {
				hostnames.add(curr);
		}
		
	}

}
