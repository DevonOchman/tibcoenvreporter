package com.logilabs.os;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProcessScanner {

	private List<String> processStrings = new ArrayList<String>();

	private List<String> users = new ArrayList<String>();

	public List<String> getProcesses() {
		List<String> processes = new ArrayList<String>();
		String command = "";
		if (OSValidator.isUnix()) {
			command = "ps -ef";
		} else if (OSValidator.isWindows()) {
			command = "tasklist";
		}
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

		String line;
		try {
			if (OSValidator.isWindows()) {
				reader.readLine();
				reader.readLine();
				int width = reader.readLine().split(" ")[0].length();
				while ((line = reader.readLine()) != null) {
					String p = line.substring(0, width).trim();
					processes.add(p);
				}
			} else if (OSValidator.isUnix()) {
				ProcessRefList prs = new ProcessRefList();
				line = reader.readLine();
				int width = line.indexOf(" TIME ") + " TIME ".length();
				while ((line = reader.readLine()) != null) {
					String user = line.split(" ")[0];
					String cmd = line.substring(width);
					prs.addProcessRef(new ProcessRef(user, cmd));
				}
				Collections.sort(prs.getPrs(), new Comparator<ProcessRef>() {
					@Override
					public int compare(ProcessRef arg0, ProcessRef arg1) {
						return arg0.compareTo(arg1);
					}
				});
				prs.filterAgainst(processStrings);
				prs.filterAgainstUser(users);
				processes = prs.printCommands();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return processes;
	}

	public void addProcessStrings(String... target) {
		for (String t : target) {
			processStrings.add(t);
		}
	}
	public void addUserStrings(String... target) {
		for (String t : target) {
			users.add(t);
		}
	}
}

class ProcessRefList {
	public List<ProcessRef> getPrs() {
		return prs;
	}

	public void filterAgainstUser(List<String> users) {
		List<ProcessRef> newPrs = new ArrayList<ProcessRef>();
		for (ProcessRef pr : prs) {
			if (pr.hasUserLike(users)) {
				newPrs.add(pr);
			}
		}
		prs = newPrs;
	}

	public void filterAgainst(List<String> processStrings) {
		List<ProcessRef> newPrs = new ArrayList<ProcessRef>();
		for (ProcessRef pr : prs) {
			if (pr.hasCommandLike(processStrings)) {
				newPrs.add(pr);
			}
		}
		prs = newPrs;
	}

	private List<ProcessRef> prs = new ArrayList<ProcessRef>();

	public void addProcessRef(ProcessRef pr) {
		if (containsUser(pr.getUser())) {
			getProcessRefForUser(pr.getUser()).addCommand(pr.getCommands());
		} else {
			prs.add(pr);
		}
	}

	private ProcessRef getProcessRefForUser(String user) {
		for (ProcessRef pr : prs) {
			if (pr.getUser().equals(user))
				return pr;
		}
		return null;

	}

	public boolean containsUser(String user) {
		for (ProcessRef pr : prs) {
			if (pr.getUser().equals(user))
				return true;
		}
		return false;
	}

	public List<String> printCommands() {
		List<String> commandStrings = new ArrayList<String>();
		for (ProcessRef pr : prs) {
			for (String command : pr.getCommands()) {
				commandStrings.add(pr.getUser() + "\t" + command);
			}
		}
		return commandStrings;
	}
}

class ProcessRef implements Comparable<ProcessRef> {
	private String user;

	public String getUser() {
		return user;
	}

	public boolean hasUserLike(List<String> users) {

		for (String u : users) {
			if (user.equalsIgnoreCase(u))
				return true;
		}

		return false;
	}

	public boolean hasCommandLike(List<String> processStrings) {
		for (String s : command) {
			for (String ps : processStrings) {
				if (s.contains(ps))
					return true;
			}
		}
		return false;
	}

	public List<String> getCommands() {
		return command;
	}

	private List<String> command = new ArrayList<String>();

	public ProcessRef(String user, String command) {
		super();
		this.user = user;
		this.command.add(command);
	}

	public void addCommand(String command) {
		this.command.add(command);
	}

	public void addCommand(List<String> command) {
		for (String s : command) {
			if (!containsCommand(s))
				addCommand(s);
		}
	}

	public boolean containsCommand(String command) {
		return this.command.contains(command);
	}

	@Override
	public int compareTo(ProcessRef arg0) {
		if (!user.equals(arg0.getUser())) {
			return user.compareTo(arg0.getUser());
		} else {
			return new Integer(command.size()).compareTo(arg0.getCommands().size());
		}
	}
}
