package com.logilabs.files;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class DirectoryCrawler {

	private HashSet<String> targets = new HashSet<String>();
	
	private HashSet<String> omitRoots =  new HashSet<String>();

	private int peekDepth = 2;

	private int maxDepth = 10;

	public ArrayList<File> crawl() {
		HashSet<File> files = new HashSet<File>();
		File[] roots = File.listRoots();
		for(int i =0; i< roots.length; i++){
			File root = roots[i];
			if(root == null){
				continue;
			}
			for (File f : root.listFiles()) {
				if(isToOmit(f.getName())){
					continue;
				}
				files.addAll(crawlR(f, 0));
			}
		}
		
		ArrayList<File> finalList = new ArrayList<File>(files.size());
		finalList.addAll(files);
		return finalList;
	}        

	private HashSet<? extends File> crawlR(File f, int depth) {
		HashSet<File> files = new HashSet<File>();
		if (isTarget(f.getName())) {
			files.add(f);
			files.addAll(crawlH(f, 0));
		}

		File[] subF = f.listFiles();
		if (subF == null || depth >= maxDepth) {
			return files;
		}
		for (File sf : subF) {
			files.addAll(crawlR(sf, depth + 1));
		}

		return files;
	}

	private HashSet<? extends File> crawlH(File f, int depth) {
		HashSet<File> files = new HashSet<File>();
		File[] subF = f.listFiles();
		if (subF == null)
			return files;
		for (File sf : subF) {
			files.add(sf);
			if (depth < peekDepth) {
				files.addAll(crawlH(sf, depth + 1));
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
			else if (test.toLowerCase().contains(s.toLowerCase()))
				return true;
		}
		return false;
	}
	
	private boolean isToOmit(String test){
		if(test == null)
			return false;
		if(test.length() == 0)
			return false;
		for(String s : omitRoots){
			if(s.equalsIgnoreCase(test))
				return true;	
		}
		return false;
	}

	public void addTargets(String... target) {
		for (String t : target) {
			targets.add(t);
		}
	}
	public void addOmitRoots(String... target) {
		for (String t : target) {
			omitRoots.add(t);
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

	public HashSet<File> reduceFor(Collection<? extends File> files, String regex) {
		HashSet<File> newFiles = new HashSet<File>();
		for(File f : files){
			if(f.toString().matches(regex))
				newFiles.add(f);
		}
		return newFiles;
	}

}
