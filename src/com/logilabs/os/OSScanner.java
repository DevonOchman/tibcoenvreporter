package com.logilabs.os;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.logilabs.os.model.OSDetails;

public class OSScanner {

	
	public static OSDetails report(){
		int numCores = getNumberOfCPUCores();
		InetAddress ip = null;
        String hostname = null;
		
		try {
			ip = InetAddress.getLocalHost();
			hostname = ip.getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
        
		return new OSDetails(hostname, ip.getHostAddress(), numCores);
	}
	
	private static int getNumberOfCPUCores() {
	    String command = "";
	    if(OSValidator.isMac()){
	        command = "sysctl -n machdep.cpu.core_count";
	    }else if(OSValidator.isUnix()){
	        command = "lscpu";
	    }else if(OSValidator.isWindows()){
	        command = "cmd /C WMIC CPU Get /Format:List";
	    }
	    Process process = null;
	    int numberOfCores = 0;
	    int sockets = 0;
	    try {
	        if(OSValidator.isMac()){
	            String[] cmd = { "/bin/sh", "-c", command};
	            process = Runtime.getRuntime().exec(cmd);
	        }else{
	            process = Runtime.getRuntime().exec(command);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    BufferedReader reader = new BufferedReader(
	            new InputStreamReader(process.getInputStream()));
	    String line;


	    try {
	        while ((line = reader.readLine()) != null) {
	            if(OSValidator.isMac()){
	                numberOfCores = line.length() > 0 ? Integer.parseInt(line) : 0;
	            }else if (OSValidator.isUnix()) {
	                if (line.contains("Core(s) per socket:")) {
	                    numberOfCores = Integer.parseInt(line.split("\\s+")[line.split("\\s+").length - 1]);
	                }
	                if(line.contains("Socket(s):")){
	                    sockets = Integer.parseInt(line.split("\\s+")[line.split("\\s+").length - 1]);
	                }
	            } else if (OSValidator.isWindows()) {
	                if (line.contains("NumberOfCores")) {
	                    numberOfCores = Integer.parseInt(line.split("=")[1]);
	                }
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    if(OSValidator.isUnix()){
	        return numberOfCores * sockets;
	    }
	    return numberOfCores;
	}
}
