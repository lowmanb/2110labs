/**
 * @author Ben Lowman brl2xx
 */
public interface Playable {
	/**
	 * play playable object (implemented by song and playlist)
	 */
	public void play();

	/**
	 * play s seconds of playable object (implemented by song and playlist)
	 */
	public void play(double seconds);

	/**
	 * returns name of playable object (implemented by song and playlist)
	 */
	public String getName();

	/**
	 * returns total play time of playable object (implemented by song and
	 * playlist)
	 */
	public int getPlayTimeSeconds();

}
