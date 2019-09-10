package com.logilabs.files;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class DirectoryCrawler {

	private HashSet<String> targets = new HashSet<String>();

	private int peekDepth = 3;

	private int maxDepth = 10;

	public List<File> crawl() {
		List<File> files = new ArrayList<File>();
		File[] roots = File.listRoots();
		for (File f : roots) {
			files.addAll(crawlR(f, 0));
		}
		return files;
	}

	private Collection<? extends File> crawlR(File f, int depth) {
		System.out.println(depth + " " + f.getPath());
		List<File> files = new ArrayList<File>();
		String s = f.getName();
		if (isTarget(f.getName())) {
			files.add(f);
			files.addAll(crawlH(f, 0));
		}

		File[] subF = f.listFiles();
		if (subF == null || depth >= maxDepth) {
			return files;
		}
		for (File sf : subF) {
			files.addAll(crawlR(sf, ++depth));
		}

		return files;
	}

	private Collection<? extends File> crawlH(File f, int depth) {
		List<File> files = new ArrayList<File>();
		File[] subF = f.listFiles();
		if (subF == null)
			return files;
		for (File sf : subF) {
			files.add(sf);
			if (depth < peekDepth) {
				files.addAll(crawlH(sf, ++depth));
			}
		}

		return files;

	}

	private boolean isTarget(String test) {
		if (test == null)
			return false;
		if (test.length() == 0)
			return false;
		for (String s : targets) {
			if (s.equalsIgnoreCase(test))
				return true;
		}
		return false;
	}

	public void addTargets(String... target) {
		for (String t : target) {
			targets.add(t);
		}
	}

	public int getPrintDepth() {
		return peekDepth;
	}

	public void setPrintDepth(int printDepth) {
		this.peekDepth = printDepth;
	}

	public HashSet<String> getTargets() {
		return targets;
	}

}
