package com.logilabs.main;

import com.logilabs.ssh.CommandExecutor;

public class Main {

	public static void main(String[] args) {
		String user = "root";//"3033519";
		String pwd = "h1TQy27DeE";//"3FbGPk28s";
		String host = "192.168.43.147";//"lqc90990esbdvp01";
		int port = 22;//2022;

		CommandExecutor cme = new CommandExecutor();
		cme.addCommands("ps");
		cme.executeCommands(user, pwd, host, port);
	}

}
