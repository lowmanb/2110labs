import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import edu.virginia.cs2110.Mp3FilePlayer;

/**
 * @author Ben Lowman brl2xx
 * @author David Philpott dlp9er Contains all methods needed for the user
 *         interface. Each function of the program was compartmentalized as much
 *         as possible in different methods. This makes debugging much easier
 *         and the logic of the program more clear
 */
public class MediaPlayer {

	/**
	 * Highest level list only contains playlists
	 */
	private ArrayList<PlayList> masterList;

	/**
	 * since a scanner is used so frequently in the methods of this class, it
	 * was made a field
	 */
	private Scanner in;

	/**
	 * constuctor only creates instance of the masterList and scanner, and
	 * creates the default playlist
	 */
	public MediaPlayer() {
		masterList = new ArrayList<PlayList>();
		masterList.add(new PlayList("playlist"));
		in = new Scanner(System.in);
	}

	/**
	 * static method used to check if playlist name exists in an arbitrary list
	 * of playlists. This algorithm was needed many times in the methods of this
	 * class, so it is implemented here.
	 */
	static boolean containsName(ArrayList<PlayList> p, String name) {

		boolean flag = false;

		for (int i = 0; i < p.size(); i++)
			if (p.get(i).getName().equals(name))
				flag = true;
		return flag;
	}

	/**
	 * adds a new playlist to the highest level of playlists, the masterList
	 */
	public void newPlaylist() {

		System.out.println("Enter name of new playlist");
		String name = in.nextLine();

		if (containsName(masterList, name)) {
			System.out.println("Must be a new playlist");
			return;
		}

		masterList.add(new PlayList(name));
		System.out.println("Playlist " + name + " added!");
	}

	/**
	 * loads the data from a specified file and adds it to a specified playlist
	 */
	public void loadPlayListData() {

		System.out.println("Enter name of playlist");
		String name = in.nextLine();

		if (!containsName(masterList, name)) {
			System.out.println("Playlist does not exist");
			return;
		}

		System.out.println("Enter file name");
		String fileName = in.nextLine();

		if (!(new File(fileName).exists())) {
			System.out.println("File does not exist");
			return;
		}

		for (int i = 0; i < masterList.size(); i++)
			if (masterList.get(i).getName().equals(name))
				masterList.get(i).loadMedia(fileName);

		System.out.println("Data from " + fileName + " added to " + name
				+ " playlist!");
	}

	/**
	 * add a playlist to another playlist
	 */
	public void appendPlaylist() {

		System.out.println("Enter name of old playlist");
		String nameOld = in.nextLine();

		if (!containsName(masterList, nameOld)) {
			System.out.println("Playlist does not exist");
			return;
		}

		System.out.println("Enter name of new playlist");
		String nameNew = in.nextLine();

		if (nameOld.equals(nameNew)) {
			System.out.println("Same name not allowed.");
			return;
		}

		for (int i = 0; i < masterList.size(); i++)
			if (masterList.get(i).getName().equals(nameOld))
				masterList.get(i).addPlayable(new PlayList(nameNew));

		System.out.println("Playlist " + nameNew + " appended to playlist "
				+ nameOld + "!");
	}

	/**
	 * plays all playables inside a playlist, either for their full length or a
	 * specified amount of time
	 */
	public void playPlaylist() {

		System.out.println("Enter PlayList name");
		String name = in.nextLine();

		if (!containsName(masterList, name)) {
			System.out.println("playlist does not exist");
			return;
		}

		System.out.println("Seconds per song? For whole song, enter '0'");
		String line = in.nextLine();
		int time = Integer.parseInt(line);

		for (int i = 0; i < masterList.size(); i++)
			if (masterList.get(i).getName().equals(name)) {
				if (time == 0)
					masterList.get(i).play();
				else
					masterList.get(i).play(time);
			}
		System.out.println("Playing playlist " + name + "!");
	}

	/**
	 * plays an MP3 file given by its data file location. This method does not
	 * interact with masterList
	 */
	public void playMP3() {

		System.out.println("Enter MP3 file name");
		String fileName = in.nextLine();

		if (!(new File(fileName).exists())) {
			System.out.println("MP3 file not found");
			return;
		}

		System.out
				.println("Number of seconds to play? For whole song, enter '0'");
		String line = in.nextLine();
		int time = Integer.parseInt(line);

		if (time == 0) {
			new Mp3FilePlayer(fileName).playAndBlock();
		} else {
			new Mp3FilePlayer(fileName).playAndBlock(time);
		}

		System.out.println("MP3 Playback completed!");
	}

	/**
	 * loops through and prints the names of all the playlists stored in
	 * masterList
	 */
	public void printPlaylistNames() {
		for (int i = 0; i < masterList.size(); i++)
			System.out.println(masterList.get(i).getName());
	}

	/**
	 * prints the names of all playables store in the default playlist
	 */
	public void printNamesDefault() {

		String s = "";
		for (int i = 0; i < masterList.get(0).size(); i++)
			System.out.println(masterList.get(0).getPlayable(i).getName());
	}

	/**
	 * prints ALL the information of an arbitrary playlist
	 */
	public void printArbPlaylist() {
		System.out.println("Enter name of playlist");
		String name = in.nextLine();

		if (!containsName(masterList, name)) {
			System.out.println("Playlist does not exist");
			return;
		}

		for (int i = 0; i < masterList.size(); i++)
			if (masterList.get(i).getName().equals(name))
				System.out.println(masterList.get(i).toString());
	}

	/**
	 * starts and manages user interactivity
	 */
	public void start() {

		System.out.println("1. Quit the program\n" + "2. Add new playlist\n"
				+ "3. Add media to existing playlist\n"
				+ "4. Add playlist to another playlist\n"
				+ "5. Play playlist\n" + "6. Play song\n"
				+ "7. Print list of all playlist names\n"
				+ "8. Print playable names in the default playlist\n"
				+ "9. Print information of a playlist\n\n"
				+ "Default Playlist Added! (name 'playlist')");

		while (true) {

			System.out.println("\nEnter option:");

			String option = in.nextLine();

			if (option.equals("1")) {
				break;
			}

			else if (option.equals("2")) {
				newPlaylist();
				continue;
			}

			else if (option.equals("3")) {
				loadPlayListData();
				continue;
			}

			else if (option.equals("4")) {
				appendPlaylist();
				continue;
			}

			else if (option.equals("5")) {
				playPlaylist();
				continue;
			}

			else if (option.equals("6")) {
				playMP3();
				continue;
			}

			else if (option.equals("7")) {
				printPlaylistNames();
				continue;
			}

			else if (option.equals("8")) {
				printNamesDefault();
				continue;
			}

			else if (option.equals("9")) {
				printArbPlaylist();
				continue;
			}

			else {
				System.out.println("Invalid Option");
				continue;
			}

		}
		System.out.print("[Process completed]");
		System.exit(0);
	}

	/**
	 * returns the default playlist
	 */
	public PlayList getDefaultPlayList() {
		for (int i = 0; i < masterList.size(); i++)
			if (masterList.get(i).getName().equals("playlist"))
				return masterList.get(i);
		return null;
	}

	/**
	 * returns masterList of playlists
	 */
	public ArrayList<PlayList> getPlayLists() {
		return masterList;
	}

	/**
	 * main method simply creates a new instance of MediaPlayer and calls
	 * start().
	 */
	public static void main(String[] args) {
		new MediaPlayer().start();
	}

}
