package com.logilabs.ssh;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class FileGetter {

	public void getFile(String filename, String target, Host host)
			throws SftpException {
		ChannelSftp channel = null;
		try {
			channel = openChannel(host);
			channel.connect();
			channel.get(filename, target);

		} catch (JSchException e1) {
			System.out.println("Failed to open channel to " + host.hostName);
			e1.printStackTrace();
		} finally {
			try {
				channel.disconnect();
				channel.getSession().disconnect();
			} catch (JSchException e) {
				e.printStackTrace();
			}
		}
	}

	private ChannelSftp openChannel(Host host) {
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
		ChannelSftp channel;
		try {
			channel = ssh.openSFTPChannel(session);
		} catch (JSchException e) {
			System.out.println("Failed to open a session to " + host);
			e.printStackTrace();
			return null;
		}
		return channel;
	}
}
