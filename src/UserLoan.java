import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class UserLoan {
	private BookData[] loans;
	private String id;
	private int cnt; //대출 수
	
	public UserLoan(String id) {
		this.id = id;
		loans = new BookData[2];
		cnt = 0;
	}
	
	public void upload(String id) {//파일에서 가져오기
		try {
	    	FileReader reader = new FileReader(id+".txt");
	    	BufferedReader buf = new BufferedReader(reader);
	    	String line;
	    	while((line = buf.readLine()) != null) {
	    		//데이터 파일에서 읽어오기
	    		StringTokenizer tokenizer = new StringTokenizer(line, ",");
	    		String title = tokenizer.nextToken();
	    		String author = tokenizer.nextToken();
	    		String publisher = tokenizer.nextToken();
	    		String loan = tokenizer.nextToken();
	    		
	    		//데이터 등록
	    		BookData bookdata = new BookData (title, author, publisher, loan);
	    		loans[cnt++]=bookdata;
	    	} 
	    	buf.close();
	    } catch (FileNotFoundException e) {
	    	System.out.println("파일을 찾을 수 없습니다.");
	    	e.printStackTrace();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
	
	public void save() { //파일에 저장
		try {
			FileWriter writer = new FileWriter( id + ".txt"); //FileWriter 객체 생성하면서 파일을 오픈. 없으면 생성해줌
			BufferedWriter buf = new BufferedWriter(writer); //파일 기록에 버퍼 기능을 추가
			
			//파일에 기록하기
			if(loans[0] != null) {
				buf.write(loans[0].getTitle() + ",");
				buf.write(loans[0].getAuthor() + ",");
				buf.write(loans[0].getPublisher() + ",");
				buf.write(loans[0].getLoan());
				buf.newLine(); //엔터. 다음줄로 내림
			}
			if(loans[1] != null) {
				buf.write(loans[1].getTitle() + ",");
				buf.write(loans[1].getAuthor() + ",");
				buf.write(loans[1].getPublisher() + ",");
				buf.write(loans[1].getLoan());
				buf.newLine(); //엔터. 다음줄로 내림
			}
			
			buf.close(); //파일 닫기
			//System.out.println("완료");
		} catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
	
	public void add(BookData bookdata) {
		loans[cnt++] = bookdata; //객체의 주소가 대입(복사)

	}
	public BookData get(int i) {
		return loans[--i];
	}
	
	public void showAll() {
		System.out.println("1. " + loans[0]);
		System.out.println("2. " + loans[1]);
	}
	
	public int getCnt() {
		return cnt;
	}

	public void bookreturn(int returnnum) {// 책 반납
		
		if(returnnum == 1 && loans[1] == null) { //1.반납할 책  2.null
			loans[0] = null;
		} else if (returnnum == 1 && loans[1] != null) {  //1.반납할 책   2.대출중인 책
			loans[0] = loans[1];
			loans[1] = null;
		} else if(returnnum == 2) { //1.대출중인 책   2.반납할 책
			loans[1] = null;
		}
		
	}

	

}
