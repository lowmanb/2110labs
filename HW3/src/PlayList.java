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
	public boolean loadMedia(String fileName) {
		try {
			Scanner in = new Scanner(new File(fileName));
			while (in.hasNextLine()) {

				// array list holds all fields extracted from data file per
				// "pass"
				ArrayList<String> fields = new ArrayList<String>();

				// for loop keeps track of how many fields to add to fields
				// array list (loop only
				// executes at start or when something new is added to the
				// fields array list
				for (int i = 0; i < 4; i++) {

					// true loop scans for file and only breaks if it hits the
					// eof or exracts a field
					while (true) {

						if (in.hasNextLine()) {
							String line = in.nextLine();
							String form;

							if (line.contains("//")) {
								String[] splitLine = line.split("//");
								form = splitLine[0].trim();
							}

							else
								form = line.trim();

							// if no data was extracted on this line, the loop
							// continues
							if (form.equals(""))
								continue;

							// if data is extracted, then it is added to fields
							// array list for later processing and the
							// true loop restarts but only after the for loop
							// ticks over after four successes, the all
							// data has been collected and the for loop ends
							else {
								fields.add(form);
								break;
							}
						} else
							break;
					}
				}

				// If the true loop hits the end of the file before any more
				// data can be extracted (i.e. comments at end),
				// then the array list will not have four elements. This check
				// ensures no incomplete data is processed.
				if (fields.size() < 4)
					break;

				String title = fields.get(0);
				String author = fields.get(1);
				String[] time = fields.get(2).split(":");
				String fileLoc = fields.get(3);

				int secondsSum = Integer.parseInt(time[0]) * 60
						+ Integer.parseInt(time[1]);
				int minutes = secondsSum / 60;
				int seconds = secondsSum % 60;

				// checking if fileLoc points to youtube

				if (fileLoc.contains(":")) {
					String[] fileLocSplit = fileLoc.split(":");
					fileLocSplit[0] = "http://";
					this.addPlayable(new Video(author, title, minutes, seconds,
							fileLocSplit[0] + fileLocSplit[1]));
				}

				else {

					if (!(new File(fileLoc).exists()))
						return false;

					this.addPlayable(new Song(author, title, minutes, seconds,
							fileLoc));
				}

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
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
	 * adds playable to playable List
	 */
	public boolean addPlayable(Playable p) {
		return playableList.add(p);
	}

	/**
	 * adds song to playable list
	 */
	public boolean addSong(Song s) {
		return playableList.add(s);
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
