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
	    		//데이터 파일에서 읽어오기
	    		StringTokenizer tokenizer = new StringTokenizer(line, ",");
	    		String id = tokenizer.nextToken();
	    		String pw = tokenizer.nextToken();
	    		
	    		//데이터 등록
	    		UserData userdata = new UserData(id, pw);
				users.add(userdata);
	    	} 
	    	buf.close();
	    } catch (FileNotFoundException e) {
	    	System.out.println("파일을 찾을 수 없습니다.");
	    	e.printStackTrace();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
		
	}
	public void save() {
		// 파일 열기, 파일에 객체 기록하기, 파일닫기
		 try {
			FileWriter writer = new FileWriter("users.txt"); //FileWriter 객체 생성하면서 파일을 오픈. 없으면 생성해줌
			BufferedWriter buf = new BufferedWriter(writer); //파일 기록에 버퍼 기능을 추가
			
			//파일에 기록하기
			for(UserData userdata : users) {
				buf.write(userdata.getId() + ",");
				buf.write(userdata.getPw());
				buf.newLine(); //엔터. 다음줄로 내림
			}
			buf.close(); //파일 닫기

		} catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
	
	public int identity(String id, String pw) {//user 정보에서 아이디와 비번이 일치하는지 확인
		for(int i = 0; i<users.size(); i++) {
            if(users.get(i).getId().equals(id)) { //아이디와 비번이 일치
            	if(users.get(i).getPw().equals(pw)) return 1;
            }
		} return 0;
	}

}
