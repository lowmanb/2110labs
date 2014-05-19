import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * 
 * @author Ben Lowman brl2xx
 * 
 */
public class BlatherIter implements Iterator<String> {
	/**
	 * blatherer that is being iterated over
	 */
	private Blatherer b;

	/**
	 * pos of iterator
	 */
	private int pos;

	/**
	 * max number of words to iterate over
	 */
	private int maxWords;

	/**
	 * the current key of the iterator
	 */
	private String key;

	/**
	 * Can generate the given number of words from the given Blatherer. The
	 * first word it produces will be one of the values for the provided key.
	 * (e.g., in the example above if created with key "I~am~Sam" it would
	 * produce "Sam" the first time next is invoked)
	 */
	public BlatherIter(Blatherer b, String s, int i) {
		this.b = b;
		pos = 0;
		maxWords = i;
		key = s;
	}

	/**
	 * True if the number of words generated is not yet the given number, and
	 * the current key is in the Blatherer, and the collection of values for the
	 * current key is not empty.
	 */
	public boolean hasNext() {

		return (pos < maxWords && b.getMasterGrams().containsKey(key) && b
				.getMasterGramValues(key).size() > 0);
	}

	/**
	 * return a random element of the current key's collection, and advance the
	 * key to reflect what is returned. If the iteration is over, throw a
	 * NoSuchElementException instead.
	 */
	public String next() throws NoSuchElementException {

		if (!hasNext())
			throw new NoSuchElementException();

		pos++;

		Random rand = new Random();
		int r = rand.nextInt(b.getMasterGramValues(key).size());

		String oldKey = key;
		
		key = b.removeFirstWord(key) + b.GRAM_DELIMITER
				+ b.getMasterGramValues(key).get(r);
		
		return b.getMasterGramValues(oldKey).get(r);
	}

	/**
	 * throw an UnsupportedOperationException
	 */
	public void remove() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Increase the number of words left to generate by the given number. Note
	 * that this method is not part of the Iterator interface.
	 */
	public void moreWords(int i) {
		maxWords += i;
	}

}
