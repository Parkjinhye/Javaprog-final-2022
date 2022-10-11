
public class BookData {
	private String title, author, publisher, loan;

	
	public BookData() {}
	public BookData(String title, String author, String publisher, String loan) {
		//super();
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.loan = loan;
	}
	
	public String getTitle() {
		return title;
	}
	public BookData setTitle(String title) {
		this.title = title;
		return this;
	}
	
	public String getAuthor() {
		return author;
	}
	public BookData setAuthor(String author) {
		this.author = author;
		return this;
	}
	
	public String getPublisher() {
		return publisher;
	}
	public BookData setPublisher(String publisher) {
		this.publisher = publisher;
		return this;
	}
	
	public String getLoan() {
		return loan ;

	}
	public BookData setLoan(String loan) {
		this.loan = loan;
		return this;
	}

	
	public String toString() {
		return "[" + title + "] 저자=" + author + " 출판사=" + publisher + "  " + loan;
	}
}
