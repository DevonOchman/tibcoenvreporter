package com.logilabs.ssh;

import java.util.HashSet;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class sshConn {

	private JSch jsch = new JSch();

	private static sshConn instance = null;
	
	private HashSet<Session> sessions = new HashSet<Session>();
	private HashSet<Channel> channels = new HashSet<Channel>();

	public static sshConn getInstance() {
		if (instance == null) {
			instance = new sshConn();
		}
		return instance;
	}

	public Session openConnection(Host host) throws JSchException {
		JSch jsch = new JSch();
		Session session = jsch.getSession(host.username, host.hostName, host.port);
		session.setPassword(host.password);
		session.setConfig("StrictHostKeyChecking", "no");
		sessions.add(session);
		return session;
	}

	public ChannelExec openExecChannel(Session session) throws JSchException {
		if (session == null || !session.isConnected()) {
			return null;
		}
		ChannelExec channel = (ChannelExec) session.openChannel("exec");
		channels.add(channel);
		return channel;
	}

	public ChannelSftp openSFTPChannel(Session session) throws JSchException {
		if (session == null || !session.isConnected()) {
			return null;
		}
		ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
		channels.add(channel);
		return channel;
	}
	
	public void closeAll(){
		System.out.println("Closing all open connections.");
		for(Channel c : channels){
			c.disconnect();
		}
		for(Session s : sessions){
			s.disconnect();
		}
		System.out.println("Connections closed");
	}
}
