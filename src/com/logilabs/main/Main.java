package com.logilabs.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.logilabs.files.DirectoryCrawler;
import com.logilabs.os.OSScanner;
import com.logilabs.os.ProcessScanner;
import com.logilabs.os.model.OSDetails;
import com.logilabs.write.Writer;
import com.logilabs.model.xml.*;

public class Main {

	public static void main(String[] args) {
		// UniversalInstallerHistory oo = (UniversalInstallerHistory)
		// XMLIntake.readXMLFromFileForClass(
		// UniversalInstallerHistory.class,
		// "C:\\Users\\DevonOchman\\.TIBCO\\UniversalInstallerHistory.xml");
		// int i = 0;
		// String ss =
		// UniversalInstallerHistoryParser.getInstalledProductList(oo);
		// i++;

		System.out.println("Initializing writer.");
		Writer writer = new Writer("report.txt");

		System.out.println("Reading file system.");
		OSDetails os = OSScanner.report();

		DirectoryCrawler dc = new DirectoryCrawler();
		dc.addTargets("tibco", ".TIBCO", ".TIBCOEnvInfo");

		List<File> files = dc.crawl();
		Collections.sort(files, new Comparator<File>() {

			@Override
			public int compare(File o1, File o2) {
				String one = o1.getAbsolutePath();
				String two = o2.getAbsolutePath();
				if (one == null)
					return -1;
				if (two == null)
					return 1;
				return one.compareTo(two);
			}
		});
		System.out.println("Reading active processes.");
		List<String> processes = new ArrayList<String>();

		ProcessScanner pc = new ProcessScanner();
		pc.addProcessStrings("tib", "ems", "bw", "bpm", "tea", "tra", "rv");
		processes.addAll(pc.getProcesses());

		System.out.println("Writing results.");
		try {
			writer.append("OS Details: \n");
			try {
				writer.append(os.toString());
			} catch (IOException e1) {
				System.out.println("An error occured writing OS details: " + os + " to file.");
				e1.printStackTrace();
			}
			System.out.println("Writing UniversalInstallerHistory details.");
			File uih = getFileByName(files, "UniversalInstallerHistory.xml");
			if (uih != null) {

				UniversalInstallerHistory oo = (UniversalInstallerHistory) XMLIntake
						.readXMLFromFileForClass(UniversalInstallerHistory.class, uih);
				writer.append("Universal Installer History: \n");
				try {
					writer.append(UniversalInstallerHistoryParser.getInstalledProductList(oo));
				} catch (IOException e2) {
					System.out.println(
							"An error occured writing UniversalInstallerHistory.xml details: " + oo + " to file.");
					e2.printStackTrace();
				}
			}else{
				writer.append("Universal Installer History not found.");
				System.out.println("Universal Installer History not found.");
			}
			writer.append("TIBCO Directory List: \n");
			for (File f : files) {
				try {
					writer.appendLine(f.getAbsoluteFile().toString());
				} catch (IOException e) {
					System.out.println("An error occured writing tibco directory list entry: " + f.getAbsolutePath()
							+ " to file.");
					e.printStackTrace();
				}
			}
			writer.append("Tibco-like process list: \n");
			for (String s : processes) {
				try {
					writer.appendLine(s);
				} catch (IOException e) {
					System.out.println("An error occured writing tibco process list entry " + s + " to file.");
					e.printStackTrace();
				}
			}
		} catch (IOException e3) {
			System.out.println("An error occured writing report details.");
			e3.printStackTrace();
		}

		System.out.println("Done. Goodbye.");
	}

	private static File getFileByName(List<File> files, String string) {
		for (File f : files) {
			if (f.getName().equalsIgnoreCase(string)) {
				return f;
			}
		}
		return null;
	}
}
