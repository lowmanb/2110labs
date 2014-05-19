/*Ben Lowman (brl2xx)
 * Homework 0
 * Section 102
 */

public class Book {

	private String title;
	private String author;

	public Book(String title, String author) {
		this.title = title;
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String toString() {
		return "Book [title=" + title + ", author=" + author + "]";
	}

	public boolean equals(Object o) {
		if (o instanceof Book)
			return (((Book) o).getTitle().equals(title) && ((Book) o)
					.getAuthor().equals(author));
		return false;
	}
}
