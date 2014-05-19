import org.junit.Test;
import static org.junit.Assert.*;

public class PlayListTest {

	@Test
	public void addPlayable() {
		PlayList p = new PlayList("p");
		
		//testing adding a playlist
		p.addPlayable(new PlayList("p2"));
		assertEquals("testing name of 0th element of playlist p", "p2", p
				.getPlayable(0).getName());

		//testing adding a song
		p.addPlayable(new Song(null, "s", null));
		assertEquals("testing name of 1st element of playlist p", "s", p
				.getPlayable(1).getName());

		//testing adding a video
		p.addPlayable(new Video(null, null, 0, 0,
				"http://www.youtube.com/embed/foo"));
		assertEquals("testing name of 2nd element of playlist p",
				"http://www.youtube.com/embed/foo", p.getPlayable(2).getName());
	}

	
	@Test
	public void addPlayList() {
		PlayList p = new PlayList("p");
		
		//testing adding a song
		p.addPlayList(new PlayList("p2"));
		assertEquals("testing name of 0th element of playlist p", "p2", p
				.getPlayable(0).getName());
	}

	@Test
	public void loadMedia() {
		PlayList p = new PlayList("default");
		//test.dat includes all examples from program spec
		p.loadMedia("test.dat");

		//to string method is used as mechanism to test all fields
		assertEquals("song 1",
				"{Song: title=Dancing Queen artist=Abba minutes=4 seconds=13}",
				p.getPlayable(0).toString());

		assertEquals("song 2",
				"{Song: title=Dancing Queen artist=Abba minutes=5 seconds=39}",
				p.getPlayable(1).toString());

		assertEquals("song 3",
				"{Song: title=Dancing Queen artist=Abba minutes=4 seconds=13}",
				p.getPlayable(2).toString());

		assertEquals("video 1",
				"Video [videoName=http://www.youtube.com/embed/k5mfcTfK9Nc, minutes=0, seconds=11, "
						+ "user=DunrovinYouthRetreat, title=Call Me Maybe - Polka Style]",
				p.getPlayable(3).toString());

		assertEquals("video 2",
				"Video [videoName=http://www.youtube.com/embed/JEdlIwGBNzw, minutes=0, seconds=30, "
						+ "user=UVA HARLEM SHAKE, title=OFFICIAL UVA Harlem Shake]",
				p.getPlayable(4).toString());

	}

}
