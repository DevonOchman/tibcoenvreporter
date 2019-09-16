package com.logilabs.os;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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

			if (OSValidator.isUnix()) {
				System.out.println("Failed lscpu, getting from /proc/cpuinfo");
//				e.printStackTrace();
				File file = new File("/proc/cpuinfo");
//				System.out.println(file.exists());
				BufferedReader br = null;
				try {
					br = new BufferedReader(new FileReader("/proc/cpuinfo"));
					String curr;
					String r = "";
					int i = 1;
					while ((curr = br.readLine()) != null && i < 18) {
						r += curr + "\n";
						System.out.println(i);
						i++;
					}

					return r;
				} catch (FileNotFoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

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
