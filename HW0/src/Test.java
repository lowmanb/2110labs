/*Ben Lowman (brl2xx)
 * Homework 0
 * Section 102
 */

public class Test {
	
	public static void  main (String[] args){
	Person p1 = new Person("bob", 0);
	Person p2 = new Person("bob", 0);
	Book b1 = new Book("english", "sue");
	Book b2 = new Book("math", "jane");
	Book b3 = new Book("calc", "joe");
	
	p1.addBook(b1);
	p1.addBook(b2);
	p1.addBook(b3);
	p2.addBook(b1);
	p2.addBook(new Book("a", "b"));
	//p2.addBook(new Book("b", "c"));
	
	
	System.out.print(p1.forgetBook(b1));
	}
}
