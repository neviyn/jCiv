package testing;

import jCiv.fileHandling.NationLoader;
import jCiv.map.Nation;
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class LoaderTest {
	public static void main(String[] args) {
		try {
                    NationLoader nl = new NationLoader("resource/data/nations.xml");
                    List<Nation> ns = nl.getNations();
                    for (Nation n : ns) {
                    	System.out.println(n);
                    }
                }
                catch(IOException | SAXException | ParserConfigurationException e) {
                    //Deal with them all
                    if(e instanceof IOException) {
                        System.err.println("No file found.");
                    }
                    else if(e instanceof SAXException) {
                        System.err.println("I don't know what this error is.");
                    }
                    else if(e instanceof ParserConfigurationException) {
                        System.err.println("I'm guessing this is invalid xml");
                    }
                }
        }
}

