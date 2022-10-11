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
        list.add(raindata); //�����迭�� ���� �߰��ϴ� �żҵ�
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
		// ���� ����, ���Ͽ� ��ü ����ϱ�, ���ϴݱ�
		 try {
			FileWriter writer = new FileWriter("Book.txt"); //FileWriter ��ü �����ϸ鼭 ������ ����. ������ ��������
			BufferedWriter buf = new BufferedWriter(writer); //���� ��Ͽ� ���� ����� �߰�
			
			//���Ͽ� ����ϱ�
			for(BookData bookdata : list) {
				buf.write(bookdata.getTitle() + ",");
				buf.write(bookdata.getAuthor() + ",");
				buf.write(bookdata.getPublisher() + ",");
				buf.write(bookdata.getLoan());
				buf.newLine(); //����. �����ٷ� ����
			}
			buf.close(); //���� �ݱ�

		} catch (IOException e) {
	    	e.printStackTrace();
	    }
	}

	public void bookreturn(String title) { //å ��Ͽ��� ���Ⱑ�ɿ��� ����
		for(int i = 0; i<list.size(); i++) 
            if(list.get(i).getTitle().equals(title)) list.get(i).setLoan("(o)");
        
		
	}
}
