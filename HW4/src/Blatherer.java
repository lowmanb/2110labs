import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 * 
 * @author Ben Lowman brl2xx
 * 
 */
public class Blatherer implements Iterable<String> {

	/**
	 * main method testing various project gutenberg texts
	 */
	public static void main(String[] args) {

		/*
		 * Blatherer b = new Blatherer(4);
		 * b.feed(scannerForWebPage("http://www.gutenberg.org/ebooks/98.txt.utf-8"
		 * )); // Tale of Two Cities System.out.println("Fed pages");
		 * b.feed(scannerForWebPage
		 * ("http://www.gutenberg.org/ebooks/1400.txt.utf-8")); // Great
		 * Expectations System.out.println("Fed pages");
		 * b.feed(scannerForWebPage
		 * ("http://www.gutenberg.org/ebooks/730.txt.utf-8")); // Oliver Twist
		 * System.out.println("Fed pages");
		 * b.feed(scannerForWebPage("http://www.gutenberg.org/ebooks/766.txt.utf-8"
		 * )); // David Copperfield System.out.println("Fed pages");
		 * b.feed(scannerForWebPage
		 * ("http://www.gutenberg.org/ebooks/1023.txt.utf-8")); // Bleak House
		 * System.out.println("Fed pages");
		 * b.feed(scannerForWebPage("http://www.gutenberg.org/ebooks/580.txt.utf-8"
		 * )); // The Pickwick Papers System.out.println("Fed pages");
		 * b.feed(scannerForWebPage
		 * ("http://www.gutenberg.org/ebooks/967.txt.utf-8")); // Nicolas
		 * Nickleby System.out.println("Fed pages");
		 */
	}

	/**
	 * delimiter that seperates words in keys of masterGrams
	 */
	public static final char GRAM_DELIMITER = '~';

	/**
	 * size of n-grams
	 */
	private int n;

	/**
	 * map that holds the n-grams
	 */
	private Map<String, ArrayList<String>> masterGrams;

	/**
	 * map that holds the run length of each n-gram
	 */
	private Map<String, Integer> runLengths;

	/**
	 * Creates a new Blatherer with the given value of n. This value is used by
	 * the feed method below to decide how many words go into a single gram.
	 */
	public Blatherer(int n) {
		masterGrams = new LinkedHashMap<String, ArrayList<String>>();
		runLengths = new LinkedHashMap<String, Integer>();
		this.n = n;
	}

	/**
	 * Adds the contents of the Scanner to the map of n-grams. Note that it does
	 * not clear contents that might already have been there, it just adds more.
	 */
	public void feed(Scanner s) {

		ArrayList<String> words = new ArrayList<String>();
		while (s.hasNext())
			words.add(s.next());

		for (int i = 0; i <= words.size() - (n - 1); i++) {

			String key = "";

			for (int j = 0; j < n - 1; j++)
				key += words.get(i + j) + "~";

			key = key.substring(0, key.length() - 1);

			String value = "";

			try {
				value = words.get(i + (n - 1));
			} catch (Exception e) {
				value = null;
			}

			if (masterGrams.containsKey(key)) {
				if (!masterGrams.get(key).contains(value) && value != null)
					masterGrams.get(key).add(value);

			}

			else {
				ArrayList<String> valueList = new ArrayList<String>();
				if (value != null)
					valueList.add(value);
				masterGrams.put(key, valueList);
			}

		}

	}

	/**
	 * returns key with first word removed
	 */
	public static String removeFirstWord(String key) {
		return key.replaceFirst("\\w+" + GRAM_DELIMITER, "");
	}
	
	/**
	 * returns true if given key returns a dead end
	 */
	public boolean isDeadEnd(String key) {
		return !masterGrams.containsKey(key);
	}

	/**
	 * Removes all dead-ends from the Blatherer's map of n-grams. Also removes
	 * any values that lead to no valid key, as discussed above.
	 */
	public void removeDeadEnds() {

		List<String> keyList = new ArrayList<String>(masterGrams.keySet());

		// looping backwards through the arraylist of keys
		for (int i = keyList.size() - 1; i >= 0; i--) {

			String key = keyList.get(i);
			int valueListSize = masterGrams.get(key).size();

			// for each key in the arraylist, loop through all the corresponding
			// values
			for (int j = 0; j < valueListSize; j++) {
				// create a new "transformed" key using the given algorithm and
				// test to see if it is contained in the map
				String newKey = removeFirstWord(key) + GRAM_DELIMITER
						+ getMasterGramValues(key).get(j);

				// check to see if newKey is a dead end. If so, remove the
				// corresponding value in the list of values
				if (isDeadEnd(newKey))
					getMasterGramValues(key).remove(j);
			}

			// if the lists of values for the corresponding key is empty,
			// remove the key from the masterGrams map
			if (getMasterGramValues(key).size() == 0)
				masterGrams.remove(key);
		}

	}

	/**
	 * Returns the run length of each key in the current map. It is possible for
	 * runLengths to get stuck in a loop if a key only leads in to itself or one
	 * of its parents. If the run length gets up to 100 or more, just use 100.
	 */
	public Map<String, Integer> runLengths() {

		for (String key : new ArrayList<String>(masterGrams.keySet())) {

			int runLength = 0;
			String keyTransform = key;

			while (true) {

				int valueListSize = getMasterGramValues(keyTransform).size();
				if (valueListSize == 0 || valueListSize > 1)
					break;

				runLength++;

				if (runLength == 100)
					break;

				keyTransform = removeFirstWord(keyTransform) + GRAM_DELIMITER
						+ getMasterGramValues(keyTransform).get(0);
			}

			runLengths.put(key, runLength);

		}
		return runLengths;
	}

	/**
	 * Removes all keys for which the run-length is larger than the specified
	 * number. (observation: this will often create new dead ends.)
	 */
	public void removeRunsLongerThan(int i) {
		runLengths();
		for (String key : new ArrayList<String>(runLengths.keySet())) {
			if (runLengths.get(key) > i)
				masterGrams.remove(key);
		}

	}

	/**
	 * Returns an unmodifiable map of key to Collections of Strings. This will
	 * probably be a single-line method: return Collections.unmodifiableMap(
	 * this.nameOfYourMap ); You are welcome to have this return a more specific
	 * type, such as Map<String, List<String>> or HashMap<String,
	 * TreeSet<String>>, if you wish.
	 */
	public Map<String, ? extends Collection<String>> nGrams() {
		return Collections.unmodifiableMap(this.masterGrams);
	}

	/**
	 * Returns a key whose collection of values has the most entries. In the
	 * example above, it would return "Sam~I~am" because it has 3 entries in its
	 * value while other keys have only 1. If there is a tie, any of the tied
	 * keys may be returned (we don't care which one).
	 */
	public String mostCommonKey() {
		int maxValueSize = 0;
		String maxKeyValue = "";

		for (String key : masterGrams.keySet()) {
			if (getMasterGramValues(key).size() > maxValueSize) {
				maxValueSize = getMasterGramValues(key).size();
				maxKeyValue = key;
			}
		}

		return maxKeyValue;

	}

	/**
	 * Returns an iterator that will generate the specified number of words
	 * (unless it hits a dead-end first), starting at the given key. Simply
	 * returning a new BlatherIter should be sufficient.
	 */
	public BlatherIter iterator(int i, String s) {
		return new BlatherIter(this, s, i);
	}

	/**
	 * Returns an iterator that will generate the specified number of words
	 * (unless it hits a dead-end first), starting at the mostCommonKey. Simply
	 * returning a new BlatherIter should be sufficient.
	 */
	public BlatherIter iterator(int i) {
		return new BlatherIter(this, this.mostCommonKey(), i);
	}

	/**
	 * Returns an iterator that will generate 100 words, starting at the
	 * mostCommonKey. Simply returning a new BlatherIter should be sufficient.
	 */
	public BlatherIter iterator() {
		return new BlatherIter(this, this.mostCommonKey(), 100);
	}

	/**
	 * This helper method can print text neatly wrapped to 80 columns with
	 * indented paragraphs.
	 * 
	 * @param iter
	 *            an iterator that gives an empty string for a paragraph break
	 * @author Luther Tychonievich (lat7h)
	 */
	public static void prettyPrint(Iterator<String> iter) {
		int col = 0;
		while (iter.hasNext()) {
			String w = iter.next();
			col += 1 + w.length();
			if (w.length() == 0) {
				System.out.print("\n    ");
				col = 0;
			} else if (col > 80) {
				System.out.print(w + "\n");
				col = 0;
			} else {
				System.out.print(w + " ");
			}
		}
	}

	/**
	 * This helper method can print text neatly wrapped to 80 columns with
	 * indented paragraphs.
	 * 
	 * @param iter
	 *            an iterable that gives an empty string for a paragraph break
	 * @author Luther Tychonievich (lat7h)
	 */
	public static void prettyPrint(Iterable<String> iter) {
		prettyPrint(iter.iterator());
	}

	/**
	 * A helper method to download pages only once, reducing network traffic. It
	 * also returns only words, ignoring non-word stuff. It returns
	 * Gutenberg.org-style paragraph breaks (1+ blank lines) the empty string ""
	 * This is already finished; you don't need to tweak it.
	 * 
	 * @author Luther Tychonievich (lat7h)
	 * @param page
	 *            the URL to open
	 * @return a Scanner ready to read from a local copy of that page
	 */
	public static Scanner scannerForWebPage(String page) {
		// A regular expression, or Pattern in Java-speak, for splitting text
		// and finding paragraph breaks
		final String notWordOrPunctuationOrParagraphBreak = "[^-A-Za-z,.?!\"']*\\n[^-A-Za-z,.?!\"']*(?=\\n)" // 1+
																												// newline
																												// followed
																												// by
																												// newline
				+ "|" + "[^-A-Za-z,.?!\"'\\n]*\\n[^-A-Za-z,.?!\"'\\n]*" // one
																		// new
																		// line
				+ "|" + "[^-A-Za-z,.?!\"'\\n]+" // no new line
		;
		try {
			String filename = page.substring(page.lastIndexOf('/') + 1);
			File local = new File(filename);
			if (local.isFile())
				return new Scanner(local)
						.useDelimiter(notWordOrPunctuationOrParagraphBreak);
			URL u = new URL(page);
			FileOutputStream save = new FileOutputStream(local);
			InputStream read = u.openStream();
			byte[] got = new byte[1024];
			int have = read.read(got);
			while (have > 0) {
				save.write(got, 0, have);
				have = read.read(got);
			}
			save.close();
			read.close();
			return new Scanner(local)
					.useDelimiter(notWordOrPunctuationOrParagraphBreak);
		} catch (IOException e) {
			System.err.println("ERROR reading " + page);
			e.printStackTrace();
			return null;
		}
	}

	public Map getMasterGrams() {
		return masterGrams;
	}

	public Map getRunLengths() {
		return runLengths;
	}

	/**
	 * returns the value list of masterGrams
	 */
	public ArrayList<String> getMasterGramValues(String key) {
		return masterGrams.get(key);
	}
}
