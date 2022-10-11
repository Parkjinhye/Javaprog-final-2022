import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LibraryManager {
	private ArrayList<BookData> list;
	private String label;
	
	public LibraryManager() {}
	
	public LibraryManager(String label) {
        this.label = label;
        list = new ArrayList<BookData>();
    }
	
	public void add (BookData raindata) {
        list.add(raindata); //가변배열에 원소 추가하는 매소드
    }
	
	public void showAllBook() {
		for(BookData bookdata : list) System.out.println(bookdata);
	}
	
	
	public int findLocation(String search) {
        for(int i = 0; i<list.size(); i++) 
            if(list.get(i).getTitle().contains(search)) return i;
        return -1;
    }
	
	public BookData get(int pos) {
        return list.get(pos);
    }
	
	public void save() {
		// 파일 열기, 파일에 객체 기록하기, 파일닫기
		 try {
			FileWriter writer = new FileWriter("Book.txt"); //FileWriter 객체 생성하면서 파일을 오픈. 없으면 생성해줌
			BufferedWriter buf = new BufferedWriter(writer); //파일 기록에 버퍼 기능을 추가
			
			//파일에 기록하기
			for(BookData bookdata : list) {
				buf.write(bookdata.getTitle() + ",");
				buf.write(bookdata.getAuthor() + ",");
				buf.write(bookdata.getPublisher() + ",");
				buf.write(bookdata.getLoan());
				buf.newLine(); //엔터. 다음줄로 내림
			}
			buf.close(); //파일 닫기

		} catch (IOException e) {
	    	e.printStackTrace();
	    }
	}

	public void bookreturn(String title) { //책 목록에서 대출가능여부 수정
		for(int i = 0; i<list.size(); i++) 
            if(list.get(i).getTitle().equals(title)) list.get(i).setLoan("(o)");
        
		
	}
}
