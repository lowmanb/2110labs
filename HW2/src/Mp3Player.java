import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Ben Lowman brl2xx
 */
public class Mp3Player {

	/**
	 * ArrayList of PlayLists (NOT PLAYABLES) that holds all the playlists user
	 * can add (NO SONGS)
	 */
	private ArrayList<PlayList> allPlayLists;

	/**
	 * Constructor initialized allPlayLists
	 */
	public Mp3Player() {
		allPlayLists = new ArrayList<PlayList>();
	}

	/**
	 * This method starts the text gui and prompts user for default playlist
	 * file information, and then continuously prompts the user to add
	 * additional playlists
	 */
	public void start() {

		// creating default playlist
		allPlayLists.add(new PlayList("playlist"));
		Scanner keyboard = new Scanner(System.in);

		// loop throws error until PlayList.loadSongs() returns true
		while (true) {
			System.out.print("Default playlist MP3 information filename:  ");
			if (!allPlayLists.get(0).loadSongs(keyboard.next())) {
				throwFileError();
				continue;
			}
			break;
		}
		allPlayLists.get(0).toString();
		allPlayLists.get(0).play();
		// of the next lab.
	}

	/**
	 * Simple method that prints file error message
	 */
	public void throwFileError() {
		System.out.println("Error loading file");
	}

	/**
	 * simple method that prints name error (name given matches name of playlist
	 * in allPlayLists). This is to be implemented in tandem with
	 * hasPlayListName() in future homework assignments
	 */
	public void throwNameError() {
		System.out
				.println("Playlist with same name already exists. Enter different name.");
	}

	/**
	 * method returns true if allPlayLists contain "name" and false if it does
	 * not. This method was included because the assignment instructions
	 * mentioned checking the names of PlayLists to add. There is no
	 * functionality in the program, however, to add playlists. Instructions
	 * were very vague in that regard.
	 */
	public boolean hasPlayListName(String name) {
		for (int i = 0; i < allPlayLists.size(); i++)
			return allPlayLists.get(i).getName().equals(name);
		return false;
	}

	/**
	 * returns default playlist (does not assume it is at index 0 - for future
	 * implementations).
	 */
	public PlayList getDefaultPlayList() {
		for (int i = 0; i < allPlayLists.size(); i++)
			if (allPlayLists.get(i).getName().equals("playlist"))
				return allPlayLists.get(i);
		return null;
	}

	/**
	 * prints names of all the playlists in allPlayLists
	 */
	public ArrayList getPlayLists() {
		return allPlayLists;
	}

	/**
	 * Main method creates new instance of MP3Player and calls start() method
	 */
	public static void main(String[] args) {
		new Mp3Player().start();

	}

}
