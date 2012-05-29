package jCiv.fileHandling;

import java.io.File;
import java.io.IOException;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public abstract class XMLLoader {

	protected Document doc;
	
	protected void loadDocument(String filename)
		throws XMLParseException, SAXException, IOException, ParserConfigurationException
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
		
		try {
			parse();
		} catch (XMLParseException xe) {
			System.err.println("Incorrect structure found while parsing document: " + filename);
			throw xe;
		} 
	}
	
	protected abstract void parse() throws XMLParseException;
}
