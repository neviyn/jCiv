package testing;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import jCiv.fileHandling.*;
import jCiv.map.*;

public class LoaderTest {
	public static void main(String[] args) {
		NationLoader nl;
		List<Nation> ns;
		try {
			nl = new NationLoader("resource/data/nations.xml");
			ns = nl.getNations();
			for (Nation n : ns) {
				System.out.println(n);
			}
		} catch (IOException | SAXException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
