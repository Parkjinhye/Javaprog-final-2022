import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.File;

public class LibraryMain {
	static Scanner sc = new Scanner(System.in);
	private static LibraryManager manager;
	private static UserManager Umanager;
	private static UserLoan userloan;
	static String id;
	
	public static void UploadBook(){ // 텍스트 파일에서 책 목록을 받아옴  
	    try {
	    	FileReader reader = new FileReader("Book.txt");
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
	    		manager.add(bookdata);
	    	} 
	    	buf.close();
	    } catch (FileNotFoundException e) {
	    	System.out.println("파일을 찾을 수 없습니다.");
	    	e.printStackTrace();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
	
	
	public static int IdentityUser(int sign) { //유저에 대해 받음
		int login = 0; //로그인 여부
		
		if(sign == 1) { //로그인
			System.out.print("아이디 : ");
			id = sc.next();
			System.out.print("비밀번호 : ");
			String pw = sc.next();
			
			int idt = Umanager.identity(id, pw); //유저 목록파일을 탐색하여 동일한 것이 있으면 1 반환
			
			if(idt == 1) {
				System.out.print("로그인 성공!");
				login = 1;
			} else {
				System.out.println("로그인 실패 ");
			}
			
		} else if (sign == 2) { //회원가입
			System.out.print("아이디 : ");
			id = sc.next();
			System.out.print("비밀번호 : ");
			String pw = sc.next();
			
			
			File file = new File(id+".txt"); //이 유저의 대출 기록을 저장하는 파일 생성
			try {            
				if (file.createNewFile()) {                
					System.out.println("File created");
					Umanager.add(id, pw); //유저 목록에 추가
					Umanager.save(); //유저 목록 파일에도 추가
				} else {                
					System.out.println("File already exists");            
				}        
			} catch (IOException e) {            
				e.printStackTrace();        
			}
		}
		
		return login;
	}
	
	
	public static void main(String[] args) {
		manager = new LibraryManager("book");
		UploadBook(); // 책 목록 파일과 연결
		
		Umanager = new UserManager("user");
		Umanager.upload(); //유저 목록 파일과 연결
		
		while(true) {
		System.out.print("1. 로그인 / 2. 회원가입   : ");
		int sign = sc.nextInt();
		int identity = IdentityUser(sign);
		
		userloan = new UserLoan(id);
		userloan.upload(id);
		
			while(identity == 1) { //로그인 되었을 때
				
				System.out.println();
				System.out.println("----- 도서관 -----");
				System.out.println("1. 도서 목록 보기");
				System.out.println("2. 도서 대출");
				System.out.println("3. 나의 대출 현황");
				System.out.println("4. 도서 반납");
				System.out.println("5. 로그아웃");
				System.out.println("----------------");
				System.out.print("입력 : ");
				int menu = sc.nextInt();
				System.out.println();
				
				if (menu == 1) { //도서 목록 보기
					manager.showAllBook();
				}
				else if(menu == 2) { //도서 대출
					System.out.print("도서 제목 : ");
					String search = sc.next();
					int pos = manager.findLocation(search);//제목으로 도서 목록에서 위치를 반환받음
					System.out.println(manager.get(pos));
					
					//대출의향 묻기
					if (manager.get(pos).getLoan().contains("o")) {
						if(userloan.getCnt() < 2) {
							System.out.println("대출하시겠습니까? 1. 예  2. 아니오");
							System.out.print("입력 : "); 
							int answer = sc.nextInt();
							
							if(answer == 1) { //대출하는 경우
								manager.get(pos).setLoan("(x)"); //책 목록에서 대출 불가로 표기
								manager.save(); //이를 책 목록 파일에 저장
								userloan.add(manager.get(pos)); // 대출하기로 한 책의 정보를 대출내역에 저장
								System.out.println("대출이 완료되었습니다.");
								userloan.save(); //대출 내역 파일에 저장
							}
						}
					}
				} else if (menu == 3) { //로그인한 아이디의 대출내역 출력		
					userloan.showAll();
					
				} else if (menu == 4) { //도서 반납
					userloan.showAll();
					System.out.print("반납할 도서의 번호 : ");
					int returnnum = sc.nextInt();
					
					manager.bookreturn(userloan.get(returnnum).getTitle()); //책 목록에서 대출가능여부 변경
					userloan.bookreturn(returnnum); //대출 내역에서 삭제
					
					userloan.save();
					manager.save();
				} else if(menu == 5) {
					break;
				}
			}
	
		}
	}
}
