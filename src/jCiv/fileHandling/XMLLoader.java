package jCiv.fileHandling;

import java.io.File;
import java.io.IOException;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public abstract class XMLLoader {

	protected Document doc;
	
	protected Document loadDocument(String filename)
		throws SAXException, IOException, ParserConfigurationException
	{
		doc = null;
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(filename));
		} catch (SAXException se) {
			System.err.println("Failed to parse document: " + filename);
			throw se;
		} catch (IOException ie) {
			System.err.println("Failed to load document: " + filename);
			throw ie;
		} catch (ParserConfigurationException pe) {
			System.err.println("The parser configuration has failed, while attempting to parse document:" + filename);
			throw pe;
		} 
		doc.getDocumentElement().normalize();
		return doc;
	}
	
	protected abstract void parse();
}
