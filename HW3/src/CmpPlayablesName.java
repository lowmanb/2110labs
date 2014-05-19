import java.util.Comparator;

/**
 * @author Ben Lowman brl2xx
 */
public class CmpPlayablesName implements Comparator<Playable> {

	/**
	 * comparator class compares two playable objects by name
	 */
	public int compare(Playable p1, Playable p2) {
		return p1.getName().compareTo(p2.getName());
	}

}
