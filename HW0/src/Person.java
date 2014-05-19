/*Ben Lowman (brl2xx)
 * Homework 0
 * Section 102
 */

import java.util.ArrayList;

public class Person {

	private String name;
	private int id;
	private ArrayList<Book> read;

	public Person(String name, int id) {
		this.name = name;
		this.id = id;
		read = new ArrayList<Book>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public ArrayList<Book> getRead() {
		return read;
	}

	public boolean addBook(Book b) {
		if (!read.contains(b))
			return read.add(b);
		return false;
	}

	public boolean hasRead(Book b) {
		return read.contains(b);
	}

	public boolean forgetBook(Book b) {
		return read.remove(b);
	}

	public int numBooksRead() {
		return read.size();
	}

	public String toString() {
		return "Person [name=" + name + ", id=" + id + ", read=" + read + "]";
	}

	public boolean equals(Object o) {
		if (o instanceof Person)
			return (((Person) o).getId() == (id));
		return false;
	}

}
