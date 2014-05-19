import java.util.Iterator;
import java.util.Scanner;

public class TestCanvas {

	public static void main(String[] args) {

		Blatherer b = new Blatherer(4);
		Scanner s = new Scanner(
				"I am Sam Sam I am That Sam I am that Sam I am I do not like")
				.useDelimiter(" ");

		b.feed(s);
		// System.out.println(b.getMasterGrams());

		Iterator i = b.iterator(4, "I~am~Sam");

		while(i.hasNext())
			System.out.println(i.next());

	}

}
