package testing;

import java.util.List;

import jCiv.fileHandling.*;
import jCiv.map.*;

public class LoaderTest {
	public static void main(String[] args) {
		NationLoader nl = new NationLoader("resource/data/nations.xml");
		List<Nation> ns = nl.getNations();
		for (Nation n : ns) {
			System.out.println(n);
		}
	}
}
