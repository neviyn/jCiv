package jCiv.testing;

import jCiv.fileHandling.*;
import jCiv.map.*;

import java.io.IOException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 * Tests the XML file handling classes. Not JUnit tests at the moment - just runs the loader
 * then prints the results.
 * 
 * @author jdl & BenG
 */
public class LoaderTest {
	public static void main(String[] args) {
		String filename = "resource/data/nations.xml";
		try {
            NationLoader nl = new NationLoader(filename);
            List<Nation> ns = nl.getNations();
            for (Nation n : ns) {
            	System.out.println(n);
            }
        } catch (XMLParseException xe) {
        	System.err.println("Unexpected XML structure found while parsing document: " + filename);
        	xe.printStackTrace(System.err);
        } catch (IOException ie) {
        	System.err.println("Failed to load document: " + filename);
        	ie.printStackTrace(System.err);
        } catch (SAXException se) {
        	System.err.println("Failed to load document: " + filename);
        	se.printStackTrace(System.err);
        } catch (ParserConfigurationException pe) {
        	System.err.println("Sax parser configuration not correct, while parsing document: " + filename);
        	pe.printStackTrace(System.err);
        }
		
		filename = "resource/data/testMap.xml";
		try {
            MapLoader ml = new MapLoader(filename);
            JCivMap map = ml.getMap();
            System.out.println(map);
        } catch (XMLParseException xe) {
        	System.err.println("Unexpected XML structure found while parsing document: " + filename);
        	xe.printStackTrace(System.err);
        } catch (IOException ie) {
        	System.err.println("Failed to load document: " + filename);
        	ie.printStackTrace(System.err);
        } catch (SAXException se) {
        	System.err.println("Failed to load document: " + filename);
        	se.printStackTrace(System.err);
        } catch (ParserConfigurationException pe) {
        	System.err.println("Sax parser configuration not correct, while parsing document: " + filename);
        	pe.printStackTrace(System.err);
        }
    }
}