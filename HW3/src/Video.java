import java.net.URI;
import java.awt.Desktop;
import java.lang.Thread;

/**
 * @author Ben Lowman brl2xx and David Philpott dlp9er Homework 3 Lab 102
 */
public class Video implements Playable {

	private String videoName;
	private int minutes;
	private int seconds;
	private String user;
	private String title;

	/**
	 * BLOCK_ADJUSTMENT used to increase time we block when playing a song to
	 * allow for time it takes to get video to start up in browser. Adjust for
	 * your system if needed.
	 */
	private static final int BLOCK_ADJUSTMENT = 3; // units are seconds

	/**
	 * constructor class with all parameters
	 */
	public Video(String user, String title, int min, int sec, String videoName) {
		this.user = user;
		this.title = title;
		this.minutes = min;
		this.seconds = sec;
		this.videoName = videoName; // must in this form:
									// http://www.youtube.com/embed/FzRH3iTQPrk

		if (!videoName.toLowerCase()
				.startsWith("http://www.youtube.com/embed/")) {
			System.out.println("* Constructor given videoName " + videoName
					+ " which is not the proper form.");
			System.out.println("* This video will almost certainly not play.");
		}
	}

	/**
	 * playable interface play method implementation
	 */
	public void play() {
		this.play(this.minutes * 60 + this.seconds);
	}

	/**
	 * playable interface play(double) method implementation
	 */
	public void play(double s) {
		try {
			Desktop.getDesktop().browse(new URI(videoName + "?autoplay=1"));
			Thread.sleep((int) (1000 * (s + BLOCK_ADJUSTMENT))); // block
																	// for
																	// length
																	// of
																	// song
		} catch (Exception e) {
			System.out.println("* Error: " + e + " when playing YouTube video "
					+ videoName);
		}
	}

	/**
	 * playable interface getName method implementation
	 */
	@Override
	public String getName() {
		return videoName;
	}

	/**
	 * playable interface getPlayTimeSeconds implementation
	 */
	@Override
	public int getPlayTimeSeconds() {
		return (int) (minutes * 60 + seconds);
	}

	/**
	 * compares video object to another video object
	 */
	public boolean equals(Object o) {
		if (o instanceof Video)
			return (((Video) o).getUser().equals(user)
					&& ((Video) o).getTitle().equals(title)
					&& ((Video) o).getMinutes() == minutes
					&& ((Video) o).getSeconds() == seconds && ((Video) o)
					.getVideoName().equals(videoName));
		else
			return false;
	}

	@Override
	public String toString() {
		return "Video [videoName=" + videoName + ", minutes=" + minutes
				+ ", seconds=" + seconds + ", user=" + user + ", title="
				+ title + "]";
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public int getSeconds() {
		return seconds;
	}

	/**
	 * changed default seconds(double) to seconds(int)
	 */
	public void setSeconds(int seconds) {
		this.seconds = seconds % 60;
		this.minutes += seconds / 60;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * main method for the class
	 */
}