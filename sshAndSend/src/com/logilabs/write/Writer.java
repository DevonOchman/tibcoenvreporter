package com.logilabs.write;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {
	
	private String fileName;
	
	private File outputFile;
	
	private BufferedWriter bw;
	
	public Writer(String fileName){
		this.fileName = fileName;
		recreateFile();
	}
	
	public void appendLine(String s) throws IOException{
		append("\n");
		append(s);
	}
	
	public void append(String s) throws IOException{
		bw = new BufferedWriter(new FileWriter(fileName, true));
		bw.append(s);
		bw.close();
	}
	
	private void recreateFile() {
		outputFile = new File(fileName);
		if(outputFile.exists()){
			outputFile.delete();
			try {
				outputFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String getReportPath() {
		return this.outputFile.getPath();
	}

}
