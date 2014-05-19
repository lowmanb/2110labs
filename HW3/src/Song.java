import edu.virginia.cs2110.Mp3FilePlayer;

/**
 * @author Ben Lowman brl2xx Homework 1 Lab Section 102
 */
public class Song implements Playable {

	private String artist;
	private String title;
	private int minutes;
	private int seconds;
	private String fileName;

	/**
	 * constructor class with artist and title parameters
	 */
	public Song(String artist, String title, String fileName) {
		this.artist = artist;
		this.title = title;
		this.fileName = fileName;
	}

	/**
	 * constructor class with all parameters
	 */
	public Song(String artist, String title, int minutes, int seconds,
			String fileName) {
		this.artist = artist;
		this.title = title;
		this.fileName = fileName;
		int secondSum = seconds + 60 * minutes;
		this.minutes = secondSum / 60;
		this.seconds = secondSum % 60;
	}

	/**
	 * constructor class with song parameter (effectively duplicates attributes
	 */
	public Song(Song s) {
		artist = s.getArtist();
		title = s.getTitle();
		fileName = s.getFileName();
		minutes = s.getMinutes();
		seconds = s.getSeconds();
	}

	/**
	 * compares if two songs' fields are equal
	 */
	public boolean equals(Object o) {
		if (o instanceof Song)
			return (((Song) o).getArtist().equals(artist)
					&& ((Song) o).getTitle().equals(title)
					&& ((Song) o).getMinutes() == minutes
					&& ((Song) o).getSeconds() == seconds && ((Song) o)
					.getFileName().equals(fileName));
		else
			return false;
	}

	/**
	 * supplied to String method
	 */
	public String toString() {
		return "{Song: title=" + title + " artist=" + artist + " minutes="
				+ minutes + " seconds=" + seconds + "}";
	}

	/**
	 * playable interface play method implementation
	 */
	public void play() {
		new Mp3FilePlayer(fileName).playAndBlock();
	}

	/**
	 * playable interface play(double) method implementation
	 */
	public void play(double sec) {
		new Mp3FilePlayer(fileName).playAndBlock(sec);
	}

	/**
	 * playable interface getName method implementation
	 */
	public String getName() {
		return title;
	}

	/**
	 * playable interface getPlayTimeSeconds implementation
	 */
	public int getPlayTimeSeconds() {
		return minutes * 60 + seconds;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getFileName() {
		return fileName;
	}

	/**
	 * setter updates minutes is seconds >=60
	 */
	public void setSeconds(int seconds) {
		this.seconds = seconds % 60;
		this.minutes += seconds / 60;

	}

}
