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

public class CommandExecutor{

	private List<String> commands = new ArrayList<String>();

	public void executeCommands(String username, String password, String host, int port) {
		ChannelExec channel = null;
		try {
			channel = openChannel(username, password, host, port);
			if (channel == null) {
				System.out.println("An error occured trying to open channel to  " + host);
				return;
			}
			for (String command : commands) {
				try {
					executeCommand(command, channel);
				} catch (Exception e) {
					System.out.println("An error occred trying to run the command " + command + " against " + host);
					e.printStackTrace();
					break;
				}
			}
		} finally {
			try {
				channel.disconnect();
				channel.getSession().disconnect();
			} catch (JSchException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void executeCommand(String command, ChannelExec channel) throws IOException, JSchException {
		channel.setCommand(command);
		channel.setInputStream(null);
		InputStream output = channel.getInputStream();
		System.out.println("Executing command: " + command);
		channel.connect();
		String result = CharStreams.toString(new InputStreamReader(output));
		channel.disconnect();
		System.out.println("Command result: " + result);
	}

	private ChannelExec openChannel(String username, String password, String host, int port) {
		sshConn ssh = new sshConn();
		Session session;
		try {
			session = ssh.openConnection(username, password, host, port);
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
