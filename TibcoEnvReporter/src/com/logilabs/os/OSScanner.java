package com.logilabs.os;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.logilabs.os.model.OSDetails;

public class OSScanner {

	public static String report() {
		String OSd = getNumberOfCPUCores();
		InetAddress ip = null;
		String hostname = null;

		try {
			ip = InetAddress.getLocalHost();
			hostname = ip.getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		String ret = "";
		ret += "Hostname:\t\t" + hostname;
		ret += "\nIP:\t\t\t\t" + ip;
		ret += "\n" + OSd;
		return ret;
	}

	private static String getNumberOfCPUCores() {
		String ret = "";
		String command = "";
		if (OSValidator.isMac()) {
			command = "sysctl -n machdep.cpu.core_count";
		} else if (OSValidator.isUnix()) {
			command = "lscpu";
		} else if (OSValidator.isWindows()) {
			command = "cmd /C WMIC CPU Get /Format:List";
		}
		Process process = null;
		int numberOfCores = 0;
		int sockets = 0;
		try {
			if (OSValidator.isMac()) {
				String[] cmd = { "/bin/sh", "-c", command };
				process = Runtime.getRuntime().exec(cmd);
			} else {
				process = Runtime.getRuntime().exec(command);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line;

		try {
			while ((line = reader.readLine()) != null) {
				if (OSValidator.isMac()) {
					numberOfCores = line.length() > 0 ? Integer.parseInt(line) : 0;
					ret += "Number of Cores:\t" + numberOfCores;
				} else if (OSValidator.isUnix()) {
					if (line.contains("CPU(s):")) {
						ret += line + "\n";
					}
					if (line.contains("Thread(s) per core:")) {
						ret += line + "\n";
					}
					if (line.contains("Core(s) per socket:")) {
						ret += line + "\n";
					}
					if (line.contains("Socket(s):")) {
						ret += line + "\n";
					}
					if (line.contains("Model name:")) {
						ret += line + "\n";
					}
					if (line.contains("Hypervisor vendor:")) {
						ret += line + "\n";
					}
					if (line.contains("Virtualization type:")) {
						ret += line + "\n";
					}
				} else if (OSValidator.isWindows()) {
					if (line.contains("NumberOfCores")) {
						numberOfCores = Integer.parseInt(line.split("=")[1]);
						ret += "Number of Cores:\t" + numberOfCores;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ret;
	}
}
