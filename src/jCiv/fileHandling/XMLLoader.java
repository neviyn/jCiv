package jCiv.fileHandling;

import java.io.File;
import java.io.IOException;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException; 

public abstract class XMLLoader {

	protected static Document loadDocument(String filename)
	{
		Document doc = null;
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(filename));
		} catch (SAXException se) {
			System.err.println("Failed to parse document: " + filename);
			se.printStackTrace(System.err);
		} catch (IOException ie) {
			System.err.println("Failed to load document: " + filename);
			ie.printStackTrace(System.err);
		} catch (ParserConfigurationException pe) {
			System.err.println("The parser configuration has failed, while attempting to parse " + filename);
			pe.printStackTrace(System.err);
		} 
		doc.getDocumentElement().normalize();
		return doc;
	}
	
	protected abstract void parse();
}
