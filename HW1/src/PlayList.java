import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * @author Ben Lowmna brl2xx Homework 1 Lab Section 102
 */

public class PlayList {

	private String name;
	private ArrayList<Song> songList;

	/**
	 * constructor class no parameters. Name = "untitled"
	 */
	public PlayList() {
		name = "Untitled";
		songList = new ArrayList<Song>();
	}

	/**
	 * constructor class with name parameter
	 */
	public PlayList(String newName) {
		name = newName;
		songList = new ArrayList<Song>();
	}

	/**
	 * loads songs from file
	 */
	public boolean loadSongs(String fileName) {
		try {
			Scanner in = new Scanner(new File(fileName)).useDelimiter("\n");
			while (in.hasNextLine()) {
				String title = in.next().trim();
				String artist = in.next().trim();
				String[] time = in.next().trim().split(":");
				in.next();
				int secondsSum = Integer.parseInt(time[0]) * 60
						+ Integer.parseInt(time[1]);
				int minutes = secondsSum / 60;
				int seconds = secondsSum % 60;
				songList.add(new Song(artist, title, minutes, seconds));
			}

		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * clears playlist of all songs and then checks to be sure (this might be
	 * redundant
	 */
	public boolean clear() {
		songList.clear();
		return true;
	}

	/**
	 * adds song to playlist
	 */
	public boolean addSong(Song s) {
		return songList.add(s);
	}

	/**
	 * removes song from the list at index i and returns it
	 */
	public Song removeSong(int index) {
		try {
			return songList.remove(index);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * removes every occurrence of Song s from the list and returns s
	 */
	public Song removeSong(Song s) {
		boolean sWasPresent = false;
		while (songList.remove(s))
			sWasPresent = true;

		if (sWasPresent)
			return s;
		else
			return null;
	}

	/**
	 * returns song at specified index
	 */
	public Song getSong(int index) {
		try {
			return songList.get(index);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * sorts the class's song list by artist first, then by title if the artists
	 * are equal, then shortest first if both artist and title are equal
	 */
	public void sortByArtist() {
		Collections.sort(songList);
	}

	/**
	 * plays the play list by calling play() on each Song in the play list in
	 * order
	 */
	public void play() {
		int i;
		for (i = 0; i < songList.size(); i++)
			songList.get(i).play();
	}

	/**
	 * returns the number of songs in the play list
	 */
	public int size() {
		return songList.size();
	}

	/**
	 * returns the total time the play list will take in the format HH:MM:SS if
	 * there are hours, or MM:SS if there are no hours.
	 */
	public String totalPlayTime() {
		int secondsSum = 0;
		int i;
		for (i = 0; i < songList.size(); i++)
			secondsSum += songList.get(i).getMinutes() * 60
					+ songList.get(i).getSeconds();

		int hours = secondsSum / 3600;
		int minutes = (secondsSum % 3600) / 60;
		int seconds = (secondsSum % 3600) % 60;

		if (hours == 0)
			return String.format("%02d:%02d", minutes, seconds);
		else
			return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}

	/**
	 * returns the total time the play list will take as the number of seconds
	 */
	public int getPlayTimeSeconds() {
		int secondsSum = 0;
		int i;
		for (i = 0; i < songList.size(); i++)
			secondsSum += songList.get(i).getMinutes() * 60
					+ songList.get(i).getSeconds();

		return secondsSum;
	}

	/**
	 * returns default arraylist-to-string method
	 */
	public String toString() {
		return songList.toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Song> getSongList() {
		return songList;
	}

	public void setSongList(ArrayList<Song> songList) {
		this.songList = songList;
	}

}
