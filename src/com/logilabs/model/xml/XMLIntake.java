package com.logilabs.model.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class XMLIntake {
	
	public static <T> Object readXMLFromFileForClass(Class<T> class1, File uih) {
		return readXMLFromFileForClass(class1, uih.getAbsolutePath());
	}
	@SuppressWarnings("unchecked")
	public static <T> Object readXMLFromFileForClass(Class<T> target, String fileUri) {
		String xml = "";
		try {
			xml = fetchFileContent(fileUri);
		} catch (URISyntaxException  e) {
			System.out.println("An error occured reading data from: " + fileUri);
			e.printStackTrace();
			return null;
		} catch( IOException e){
			System.out.println("An error occured reading data from: " + fileUri);
			e.printStackTrace();
			return null;
		}
		
		JAXBContext jaxbContext;
		T targetObject = null;
		 try {
			jaxbContext = JAXBContext.newInstance(target);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			targetObject = (T) jaxbUnmarshaller.unmarshal(new StringReader(xml));
		} catch (JAXBException e) {
			System.out.println("");
			e.printStackTrace();
		}
		
		return targetObject;
	}
	
	private static String fetchFileContent(String fileName) throws URISyntaxException, IOException {
		File file = new File(fileName);
		FileInputStream fis = new FileInputStream(file);
		byte[] data = new byte[(int) file.length()];
		fis.read(data);
		fis.close();

        return new String(data, "UTF-8");
	}

	

}
