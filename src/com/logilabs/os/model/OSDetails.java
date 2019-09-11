package com.logilabs.os.model;


public class OSDetails {
	private String hostname;
	private String ip;
	private int cores;

	public OSDetails(String hostname, String ip, int cores) {
		this.hostname = hostname;
		this.ip = ip;
		this.cores = cores;
	}

	@Override
	public String toString(){
		String ret = "";
		ret += "Hostname:\t\t" + hostname;
		ret += "\nIP:\t\t\t\t" + ip;
		ret += "\nNum cores:\t\t" + cores;

		return ret;
	}
}
