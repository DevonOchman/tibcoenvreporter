package com.logilabs.ssh;

import java.util.ArrayList;
import java.util.Arrays;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class HostManager {

	private ArrayList<Host> hosts = new ArrayList<Host>();

	public void executeCommandsAgainstHosts(String... commands) {
		CommandExecutor cme;
		cme = new CommandExecutor();
		cme.addCommands(commands);
		System.out.println("Begin executing commands: " + Arrays.toString(commands) + " against " + hosts.size() + " hosts");
		for (Host h : hosts) {
			System.out.println("Sending TibcoEnvReporter.sh to " + h.hostName);
			FileGetter fg;
			fg = new FileGetter();
			try {
				fg.sendFile("TibcoEnvReporter.sh", "/tmp/TibcoEnvReporter.sh", h);
			} catch (JSchException e2) {
				System.out.println("An error occured sending TibcoEnvReporter.sh to /tmp/TibcoEnvReporter.sh");
				e2.printStackTrace();
				continue;
			} catch (SftpException e2) {
				System.out.println("An error occured sending TibcoEnvReporter.sh to /tmp/TibcoEnvReporter.sh");
				e2.printStackTrace();
				continue;
			}
			System.out.println("Executing against " + h.hostName + " on port " + h.port);
			try {
				cme.executeCommands(h);
			} catch (JSchException e1) {
				System.out.println("An error occured running commands againsts host: " + h.hostName);
				e1.printStackTrace();
				continue;
			}
			System.out.println("Commmands excuted.");
			System.out.println("waiting 20 seconds for script tp run.");
//			try {
//				Thread.sleep(20000);
//			} catch (InterruptedException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
			System.out.println("Fetching /tmp/report.txt to report_" + h.hostName + ".txt");
			
			try {
				fg.getFile("/tmp/report.txt", "reports/report_" + h.hostName + ".txt", h);
			} catch (SftpException e) {
				System.out.println("An error occured fetching contents of /tmp/report.txt from " + h.hostName);
				e.printStackTrace();
				continue;
			}
			sshConn.getInstance().closeAll();
		}
	}

	public void addHost(String inHost, int port, String username, String password) {
		hosts.add(new Host(inHost, port, username, password));
	}

	public ArrayList<Host> getHosts() {
		return hosts;
	}

	public void testHostConns() {
		sshConn ssh = sshConn.getInstance();
		for (Host h : hosts) {
			System.out.println("Creating session to host: " + h.hostName + " for user " + h.username);
			Session session = null;
			try {
				session = ssh.openConnection(h);
			} catch (JSchException e) {
				System.out.println("An error occured connecting to host: " + h.hostName + " for user " + h.username);
				e.printStackTrace();
				continue;
			}
			System.out.println("Session created. Connecting...");
			try {
				session.connect();
				System.out.println("Session connected.");
			} catch (JSchException e) {
				System.out.println("An error occured connecting to host: " + h.hostName + " for user " + h.username);
				e.printStackTrace();
				continue;
			} finally {
				session.disconnect();
			}
			System.out.println("Seesion and connection OK for host: " + h.hostName);
		}

	}
}

class Host {

	String hostName;
	int port;
	String username;
	String password;

	public Host(String hostName, int port, String username, String password) {
		super();
		this.hostName = hostName;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	@Override
	public String toString() {
		return "Host [hostName=" + hostName + ", port=" + port + ", username=" + username + ", pasword=" + password
				+ "]";
	}

}