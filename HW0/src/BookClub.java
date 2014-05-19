/*Ben Lowman (brl2xx)
 * Homework 0
 * Section 102
 */

import java.util.ArrayList;

public class BookClub {

	public static ArrayList<Book> commonBooks(Person a, Person b) {

		ArrayList<Book> common = new ArrayList<Book>();

		for (int i = 0; i < a.getRead().size(); i++)
			for (int j = 0; j < b.getRead().size(); j++)
				if (a.getRead().get(i).equals(b.getRead().get(j)))
					if (!common.contains(a.getRead().get(i)))
						common.add(a.getRead().get(i));

		return common;
	}

	public static double similarity(Person a, Person b) {

		int nBooksA = a.getRead().size();
		int nBooksB = b.getRead().size();
		int nCommon = commonBooks(a, b).size();

		if (nBooksA * nBooksB == 0)
			return (double) 0;

		else if (nBooksA < nBooksB)
			return nCommon / (double) nBooksA;

		else
			return nCommon / (double) nBooksB;

	}

}