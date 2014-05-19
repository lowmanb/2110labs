import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * @author Ben Lowmna brl2xx Homework 1 Lab Section 102
 */

public class PlayList implements Playable {

	/**
	 * name of playlist
	 */
	private String name;
	/**
	 * playlist implemented as playable list - meaning it can also itself hold
	 * playlists
	 */
	private ArrayList<Playable> playableList;

	/**
	 * constructor class no parameters. Name = "untitled"
	 */
	public PlayList() {
		name = "Untitled";
		playableList = new ArrayList<Playable>();
	}

	/**
	 * constructor class with name parameter
	 */
	public PlayList(String newName) {
		name = newName;
		playableList = new ArrayList<Playable>();
	}

	/**
	 * loads songs from file
	 * 
	 */
	public boolean loadSongs(String fileName) {
		try {
			Scanner in = new Scanner(new File(fileName));
			while (in.hasNextLine()) {

				String title = in.nextLine().trim();
				String artist = in.nextLine().trim();
				String[] time = in.nextLine().trim().split(":");
				String mp3FileName = in.nextLine().trim();

				if (!(new File(mp3FileName).exists()))
					return false;

				in.nextLine();

				int secondsSum = Integer.parseInt(time[0]) * 60
						+ Integer.parseInt(time[1]);
				int minutes = secondsSum / 60;
				int seconds = secondsSum % 60;

				playableList.add(new Song(artist, title, minutes, seconds,
						mp3FileName));

			}

		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * clears playableList of all playables and then checks to be sure (this
	 * might be redundant
	 */
	public boolean clear() {
		playableList.clear();
		return true;
	}

	/**
	 * adds song to playable List
	 */
	public boolean addSong(Song p) {
		return playableList.add(p);
	}

	/**
	 * returns playable at specified index
	 */
	public Playable getPlayable(int index) {
		try {
			return playableList.get(index);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * sorts playable list lexigraphically by name
	 */
	public void sortByName() {
		Collections.sort(playableList, new CmpPlayablesName());
	}

	/**
	 * sorts playables by total play time (ascending)
	 */
	public void sortByTime() {
		Collections.sort(playableList, new CmpPlayablesTime());
	}

	/**
	 * implementation of play method in playable interface
	 */
	public void play() {
		int i;
		for (i = 0; i < playableList.size(); i++)
			playableList.get(i).play();
	}

	/**
	 * implementation of play(double) method in playable interface
	 */
	public void play(double seconds) {
		int i;
		for (i = 0; i < playableList.size(); i++)
			playableList.get(i).play(seconds);
	}

	/**
	 * returns the number of playables in the play list
	 */
	public int size() {
		return playableList.size();
	}

	/**
	 * returns the total time the play list will take in the format HH:MM:SS if
	 * there are hours, or MM:SS if there are no hours.
	 */

	/**
	 * implementation of getPlayTimeSeconds in playable interface
	 */
	public int getPlayTimeSeconds() {
		int secondsSum = 0;
		for (int i = 0; i < playableList.size(); i++)
			secondsSum += playableList.get(i).getPlayTimeSeconds();
		return secondsSum;
	}

	/**
	 * adds playlist to playable
	 */
	public boolean addPlayList(PlayList pl) {
		if (!playableList.contains(pl))
			return playableList.add(pl);
		return false;
	}

	/**
	 * returns default playable-to-string method
	 */
	public String toString() {
		String s = "";
		for (int i = 0; i < playableList.size(); i++)
			s += playableList.get(i).toString();
		return s;
	}

	/**
	 * equals method compares the name of two playlists
	 */
	public boolean equals(Object o) {
		return ((Playable) o).getName().equals(name);
	}

	/**
	 * implementation of getName in Playable interface
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Playable> getPlayableList() {
		return playableList;
	}

	public void setPlayableList(ArrayList<Playable> playableList) {
		this.playableList = playableList;
	}

}
