package com.logilabs.ssh;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.google.common.io.CharStreams;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class CommandExecutor {

	private List<String> commands = new ArrayList<String>();

	public void executeCommands(Host host) throws JSchException {

		
		for (String command : commands) {
			try {
				executeCommand(command, host);
			} catch (Exception e) {
				System.out.println("An error occred trying to run the command " + command + " against " + host);
				e.printStackTrace();
				break;
			}
		}

	}

	private void executeCommand(String command, Host host) throws IOException, JSchException {
		ChannelExec channel = openChannel(host);
		channel.setCommand(command);
		channel.setInputStream(null);
		InputStream output = channel.getInputStream();
		System.out.println("Executing command: " + command);
		channel.connect();
		String result = CharStreams.toString(new InputStreamReader(output));
		channel.disconnect();
		System.out.println("Command result: " + result);
	}

	private ChannelExec openChannel(Host host) {
		sshConn ssh = new sshConn();
		Session session;
		try {
			session = ssh.openConnection(host);
			session.connect();
		} catch (JSchException e) {
			System.out.println("Failed to open a session to " + host);
			e.printStackTrace();
			return null;
		}
		ChannelExec channel;
		try {
			channel = ssh.openExecChannel(session);
		} catch (JSchException e) {
			System.out.println("Failed to open a session to " + host);
			e.printStackTrace();
			return null;
		}
		return channel;
	}

	public void addCommands(String... inCommands) {
		for (String s : inCommands) {
			commands.add(s);
		}
	}
}
