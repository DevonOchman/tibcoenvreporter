package com.logilabs.ssh;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class sshConn {

	private JSch jsch = new JSch();
	
	public Session openConnection(Host host) throws JSchException{
		JSch jsch = new JSch();
		Session session = jsch.getSession(host.username, host.hostName, host.port);
		session.setPassword(host.password);
		session.setConfig("StrictHostKeyChecking", "no");
		return session;
	}
	
	public ChannelExec openExecChannel(Session session) throws JSchException{
		if (session == null || !session.isConnected()) {
	        return null;
	    }
		ChannelExec channel = (ChannelExec) session.openChannel("exec");
		return channel;
	}
	
	public ChannelSftp openSFTPChannel(Session session) throws JSchException{
		if (session == null || !session.isConnected()) {
	        return null;
	    }
		ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
		return channel;
	}
}
