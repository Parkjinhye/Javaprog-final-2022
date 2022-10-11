import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class UserManager {
	private ArrayList<UserData> users;
	String label;
	
	public UserManager() {}
	public UserManager(String label) {
		users = new ArrayList<UserData>();
		this.label = label;
	}
	
	
	public void add(UserData userdata) {
		users.add(userdata);
	}
	public void add(String id, String pw) {
		UserData userdata = new UserData(id,pw);
		users.add(userdata);
	}
	
	
	
	public void upload() {
		try {
	    	FileReader reader = new FileReader("users.txt");
	    	BufferedReader buf = new BufferedReader(reader);
	    	String line;
	    	while((line = buf.readLine()) != null) {
	    		//������ ���Ͽ��� �о����
	    		StringTokenizer tokenizer = new StringTokenizer(line, ",");
	    		String id = tokenizer.nextToken();
	    		String pw = tokenizer.nextToken();
	    		
	    		//������ ���
	    		UserData userdata = new UserData(id, pw);
				users.add(userdata);
	    	} 
	    	buf.close();
	    } catch (FileNotFoundException e) {
	    	System.out.println("������ ã�� �� �����ϴ�.");
	    	e.printStackTrace();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
		
	}
	public void save() {
		// ���� ����, ���Ͽ� ��ü ����ϱ�, ���ϴݱ�
		 try {
			FileWriter writer = new FileWriter("users.txt"); //FileWriter ��ü �����ϸ鼭 ������ ����. ������ ��������
			BufferedWriter buf = new BufferedWriter(writer); //���� ��Ͽ� ���� ����� �߰�
			
			//���Ͽ� ����ϱ�
			for(UserData userdata : users) {
				buf.write(userdata.getId() + ",");
				buf.write(userdata.getPw());
				buf.newLine(); //����. �����ٷ� ����
			}
			buf.close(); //���� �ݱ�

		} catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
	
	public int identity(String id, String pw) {//user �������� ���̵�� ����� ��ġ�ϴ��� Ȯ��
		for(int i = 0; i<users.size(); i++) {
            if(users.get(i).getId().equals(id)) { //���̵�� ����� ��ġ
            	if(users.get(i).getPw().equals(pw)) return 1;
            }
		} return 0;
	}

}
