package com.logilabs.ssh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class FileGetter {

	public String getFile(String filename, String username, String password, String host, int port)
			throws SftpException {
		String fileContent = "";
		ChannelSftp channel = null;
		try {
			channel = openChannel(username, password, host, port);
			InputStream stream = channel.get(filename);
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(stream));
				String curr;
				try {
					while ((curr = br.readLine()) != null) {
						fileContent += curr + "\n";
					}
				} catch (IOException e) {
					System.out.println("An error occured reading content of file: " + filename + "from " + host);
					e.printStackTrace();
				}
			} finally {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} finally {
			try {
				channel.disconnect();
				channel.getSession().disconnect();
			} catch (JSchException e) {
				e.printStackTrace();
			}
		}
		return fileContent;
	}

	private ChannelSftp openChannel(String username, String password, String host, int port) {
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
