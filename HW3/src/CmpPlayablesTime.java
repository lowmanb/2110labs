import java.util.Comparator;

/**
 * @author Ben Lowman brl2xx
 */
public class CmpPlayablesTime implements Comparator<Playable> {

	/**
	 * comparator class
	 */
	public int compare(Playable p1, Playable p2) {
		if (p1.getPlayTimeSeconds() > p2.getPlayTimeSeconds())
			return 1;
		else if (p1.getPlayTimeSeconds() < p2.getPlayTimeSeconds())
			return -1;
		return 0;
	}

}
