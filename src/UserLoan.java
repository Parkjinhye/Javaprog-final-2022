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
	private int cnt; //���� ��
	
	public UserLoan(String id) {
		this.id = id;
		loans = new BookData[2];
		cnt = 0;
	}
	
	public void upload(String id) {//���Ͽ��� ��������
		try {
	    	FileReader reader = new FileReader(id+".txt");
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
	    		loans[cnt++]=bookdata;
	    	} 
	    	buf.close();
	    } catch (FileNotFoundException e) {
	    	System.out.println("������ ã�� �� �����ϴ�.");
	    	e.printStackTrace();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
	
	public void save() { //���Ͽ� ����
		try {
			FileWriter writer = new FileWriter( id + ".txt"); //FileWriter ��ü �����ϸ鼭 ������ ����. ������ ��������
			BufferedWriter buf = new BufferedWriter(writer); //���� ��Ͽ� ���� ����� �߰�
			
			//���Ͽ� ����ϱ�
			if(loans[0] != null) {
				buf.write(loans[0].getTitle() + ",");
				buf.write(loans[0].getAuthor() + ",");
				buf.write(loans[0].getPublisher() + ",");
				buf.write(loans[0].getLoan());
				buf.newLine(); //����. �����ٷ� ����
			}
			if(loans[1] != null) {
				buf.write(loans[1].getTitle() + ",");
				buf.write(loans[1].getAuthor() + ",");
				buf.write(loans[1].getPublisher() + ",");
				buf.write(loans[1].getLoan());
				buf.newLine(); //����. �����ٷ� ����
			}
			
			buf.close(); //���� �ݱ�
			//System.out.println("�Ϸ�");
		} catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
	
	public void add(BookData bookdata) {
		loans[cnt++] = bookdata; //��ü�� �ּҰ� ����(����)

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

	public void bookreturn(int returnnum) {// å �ݳ�
		
		if(returnnum == 1 && loans[1] == null) { //1.�ݳ��� å  2.null
			loans[0] = null;
		} else if (returnnum == 1 && loans[1] != null) {  //1.�ݳ��� å   2.�������� å
			loans[0] = loans[1];
			loans[1] = null;
		} else if(returnnum == 2) { //1.�������� å   2.�ݳ��� å
			loans[1] = null;
		}
		
	}

	

}
