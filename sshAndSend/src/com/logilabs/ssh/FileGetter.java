package com.logilabs.ssh;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class FileGetter {

	public void sendFile(String filename, String target, Host host) throws JSchException, SftpException {
		System.out.println("Sending file " + filename);
		ChannelSftp channel = null;
		channel = openChannel(host);
		channel.connect();

		channel.put(filename, target);
		
		System.out.println("Sent " + filename + " to " + target + " on " + host.hostName);
		channel.disconnect();
		try {
			channel.getSession().disconnect();
		} catch (JSchException e) {
			e.printStackTrace();
		}
	}

	public void getFile(String filename, String target, Host host) throws SftpException {
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
