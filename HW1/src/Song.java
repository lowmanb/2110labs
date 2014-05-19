/**
 * @author Ben Lowman brl2xx Homework 1 Lab Section 102
 */
public class Song implements Comparable<Song> {

	private String artist;
	private String title;
	private int minutes;
	private int seconds;

	/**
	 * constructor class with artist and title parameters
	 */
	public Song(String artist, String title) {
		this.artist = artist;
		this.title = title;
	}

	/**
	 * constructor class with all parameters
	 */
	public Song(String artist, String title, int minutes, int seconds) {
		this.artist = artist;
		this.title = title;
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
					&& ((Song) o).getMinutes() == minutes && ((Song) o)
						.getSeconds() == seconds);
		else
			return false;
	}

	/**
	 * supplied to String method
	 */
	public String toString() {
		return "{Song: title=" + title + " artist=" + artist + "}";
	}

	/**
	 * supplied play method
	 */
	public void play() {
		System.out.printf("Playing Song: artist=%-20s title=%s\n", artist,
				title);
	}

	/**
	 * compares lexigraphically by artist, then by title, then by time
	 */
	@Override
	public int compareTo(Song o) {
		if (artist.compareTo(o.getArtist()) == 0) {
			if (title.compareTo(o.getTitle()) == 0) {
				if (minutes * 60 + seconds < o.getMinutes() * 60
						+ o.getSeconds())
					return -1;
				else if (minutes * 60 + seconds == o.getMinutes() * 60
						+ o.getSeconds())
					return 0;
				else
					return 1;
			}

			return title.compareTo(o.getTitle());
		}
		return artist.compareTo(o.getArtist());

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

	/**
	 * setter updates minutes is seconds >=60
	 */
	public void setSeconds(int seconds) {
		this.seconds = seconds % 60;
		this.minutes += seconds / 60;
	}

}
