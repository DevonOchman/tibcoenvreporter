package com.logilabs.ssh;

import java.io.IOException;
import java.util.ArrayList;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.logilabs.write.Writer;

public class HostManager {
	
	private ArrayList<Host> hosts = new ArrayList<Host>();
	
	public void executeCommandsAgainstHosts(String... commands){
		CommandExecutor cme;
		cme = new CommandExecutor();
		
		System.out.println("Begin executing commands: "+ commands + " against " + hosts.size() + " hosts");
		for(Host h : hosts){
			System.out.println("Executing against " + h.hostName + " on port " + h.port);
			cme.executeCommands(h);
			System.out.println("Commmands excuted fetching /home/tibco/report.txt to report_" + h.hostName + ".txt");
			FileGetter fg;
			fg= new FileGetter();
			String fileContent = "";
			try {
				fileContent = fg.getFile("/tmp/report.txt", h);
			} catch (SftpException e) {
				System.out.println("An error occured fetching contents of /tmp/report.txt from " + h.hostName);
				e.printStackTrace();
			}
			Writer w = new Writer("report_" + h.hostName + ".txt");
			try {
				w.append(fileContent);
			} catch (IOException e) {
				System.out.println("An error occured writing to file: " + w.getReportPath());
				e.printStackTrace();
			}
		}
	}
	
	public void addHost(String inHost, int port, String username, String password){
		hosts.add(new Host(inHost, port, username, password));
	}
	public ArrayList<Host> getHosts(){
		return hosts;
	}

	public void testHostConns() {
		sshConn ssh = new sshConn();
		for(Host h : hosts){
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
			}finally {
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