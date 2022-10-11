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
	
	public static void UploadBook(){ // �ؽ�Ʈ ���Ͽ��� å ����� �޾ƿ�  
	    try {
	    	FileReader reader = new FileReader("Book.txt");
	    	BufferedReader buf = new BufferedReader(reader);
	    	String line;
	    	while((line = buf.readLine()) != null) {
	    		//������ ���Ͽ��� �о����
	    		StringTokenizer tokenizer = new StringTokenizer(line, ",");
	    		String title = tokenizer.nextToken();
	    		String author = tokenizer.nextToken();
	    		String publisher = tokenizer.nextToken();
	    		String loan = tokenizer.nextToken();
	    		
	    		//������ ���
	    		BookData bookdata = new BookData (title, author, publisher, loan);
	    		manager.add(bookdata);
	    	} 
	    	buf.close();
	    } catch (FileNotFoundException e) {
	    	System.out.println("������ ã�� �� �����ϴ�.");
	    	e.printStackTrace();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
	
	
	public static int IdentityUser(int sign) { //������ ���� ����
		int login = 0; //�α��� ����
		
		if(sign == 1) { //�α���
			System.out.print("���̵� : ");
			id = sc.next();
			System.out.print("��й�ȣ : ");
			String pw = sc.next();
			
			int idt = Umanager.identity(id, pw); //���� ��������� Ž���Ͽ� ������ ���� ������ 1 ��ȯ
			
			if(idt == 1) {
				System.out.print("�α��� ����!");
				login = 1;
			} else {
				System.out.println("�α��� ���� ");
			}
			
		} else if (sign == 2) { //ȸ������
			System.out.print("���̵� : ");
			id = sc.next();
			System.out.print("��й�ȣ : ");
			String pw = sc.next();
			
			
			File file = new File(id+".txt"); //�� ������ ���� ����� �����ϴ� ���� ����
			try {            
				if (file.createNewFile()) {                
					System.out.println("File created");
					Umanager.add(id, pw); //���� ��Ͽ� �߰�
					Umanager.save(); //���� ��� ���Ͽ��� �߰�
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
		UploadBook(); // å ��� ���ϰ� ����
		
		Umanager = new UserManager("user");
		Umanager.upload(); //���� ��� ���ϰ� ����
		
		while(true) {
		System.out.print("1. �α��� / 2. ȸ������   : ");
		int sign = sc.nextInt();
		int identity = IdentityUser(sign);
		
		userloan = new UserLoan(id);
		userloan.upload(id);
		
			while(identity == 1) { //�α��� �Ǿ��� ��
				
				System.out.println();
				System.out.println("----- ������ -----");
				System.out.println("1. ���� ��� ����");
				System.out.println("2. ���� ����");
				System.out.println("3. ���� ���� ��Ȳ");
				System.out.println("4. ���� �ݳ�");
				System.out.println("5. �α׾ƿ�");
				System.out.println("----------------");
				System.out.print("�Է� : ");
				int menu = sc.nextInt();
				System.out.println();
				
				if (menu == 1) { //���� ��� ����
					manager.showAllBook();
				}
				else if(menu == 2) { //���� ����
					System.out.print("���� ���� : ");
					String search = sc.next();
					int pos = manager.findLocation(search);//�������� ���� ��Ͽ��� ��ġ�� ��ȯ����
					System.out.println(manager.get(pos));
					
					//�������� ����
					if (manager.get(pos).getLoan().contains("o")) {
						if(userloan.getCnt() < 2) {
							System.out.println("�����Ͻðڽ��ϱ�? 1. ��  2. �ƴϿ�");
							System.out.print("�Է� : "); 
							int answer = sc.nextInt();
							
							if(answer == 1) { //�����ϴ� ���
								manager.get(pos).setLoan("(x)"); //å ��Ͽ��� ���� �Ұ��� ǥ��
								manager.save(); //�̸� å ��� ���Ͽ� ����
								userloan.add(manager.get(pos)); // �����ϱ�� �� å�� ������ ���⳻���� ����
								System.out.println("������ �Ϸ�Ǿ����ϴ�.");
								userloan.save(); //���� ���� ���Ͽ� ����
							}
						}
					}
				} else if (menu == 3) { //�α����� ���̵��� ���⳻�� ���		
					userloan.showAll();
					
				} else if (menu == 4) { //���� �ݳ�
					userloan.showAll();
					System.out.print("�ݳ��� ������ ��ȣ : ");
					int returnnum = sc.nextInt();
					
					manager.bookreturn(userloan.get(returnnum).getTitle()); //å ��Ͽ��� ���Ⱑ�ɿ��� ����
					userloan.bookreturn(returnnum); //���� �������� ����
					
					userloan.save();
					manager.save();
				} else if(menu == 5) {
					break;
				}
			}
	
		}
	}
}
