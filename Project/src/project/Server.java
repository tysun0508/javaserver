package project;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server implements Runnable{
	ServerSocket ss;
	Socket s;

	// 비로그인 유저 리스트
	// 대기실 리스트
	ArrayList<Player> list;
    // 채팅방 리스트
	ArrayList<Room> r_list;
	PlayDAO dao;
	
	String[] quiz = 
		{"기린","강사님","바나나","제철소","식인종","베토벤","박명수","용의자","개인기","핸드폰","컴퓨터","취직","상하이","햄버거"
		,"오라클","자바","프로젝트","개미","휴지","이어폰","가격표","샴푸","퓨마","나이키","인쇄소","김경호","개회식"
		,"철학","사시사철","공고문","잡지","우거지","삽살개","모피"
		,"카레이서","소방관","위기","게릴라","돈다발","가로수","중고생"
		,"총총걸음","마술","바둑","인형","스타벅스","자동차","텀블러","책상","마우스","후레쉬","네이버"
		,"비상구","빔프로젝트","아파트","시계","안경","공백","흰색","스마트폰","톰캣","css","오버워치"
		,"안드로메다","시간","인생","배고파","컨닝","빨강","혈액","운전면허","람보르기니","액션","왕건"
		,"1호선","2호선","3호선","4호선","5호선","6호선","7호선"};

	
	

	
	public Server() {
		dao = new PlayDAO();
		r_list = new ArrayList<>();
		list = new ArrayList<>();
		try {
			ss = new ServerSocket(3456);
			System.out.println("서버 대기중...");
			new Thread(this).start();
		} catch (Exception e) {
		}
	}
	
	@Override
	public void run() {
		while(true){
			try {
				s = ss.accept();
				Player player = new Player(s,this);
				list.add(player);
				player.start();
			} catch (Exception e) {
			}
		}
	}
	
	
	public String showquiz(){
		int change = (int)(Math.random()*quiz.length);
		
		System.out.println(change);
		return quiz[change];		
	}
	

	
	// 대기실의 있는 사람들의 이름을 반환하는 메소드
	public String[] getUsers(){
		String[] arr = new String[list.size()];
		int i = 0 ;
		for(Player k : list){
			arr[i++] = k.getNickName();
		}
		return arr;
	}
	
	public boolean validUser(String id){
		String[] users = getUsers();
		for (String string : users) {
			if(string.equals(id))
				return false;
		}
		return true;
	}
	
	// 현재 대기실에 있는 방의 제목을 반환하는 메소드
	public String[] getRooms(){
		String[] arr = new String[r_list.size()];
		int i = 0 ;
		for(Room k : r_list){
			arr[i++] = k.getRooms() + "  (" + k.getJoinCount() + "/" + 3 + ")";
		}
		return arr;
	}
	
	// 대기실에 있는 사람들에게 프로토콜 전달
	public void sendMsg(Protocol p){
		try {
			for(Player k : list){
				k.oos.writeObject(p);
				k.oos.flush();
			}
		} catch (Exception e) {
		}
	}

	// 만들어진 방 r_list에 추가 
	public void addRoom(Room room){
		r_list.add(room);
	}
	
	// 대기실(list)에서 자기자신 빼기
	public void delplayer(Player player){
		list.remove(player);
	}
	

	public void addPlayer(Player player) {
		list.add(player);
	}

	
	
	
	// 방리스트 인덱스를 받아서 해당 방을 리턴한다.
	public Room changeRoom(int index){
		return r_list.get(index);
	}
	
	// 방삭제
	public void delRoom(Room room){
		r_list.remove(room);
	}
	
	//dao 사용
		public boolean useDao(Object obj, int select){
			if(select == 0)
				return dao.idChk(String.valueOf(obj));
			else if(select == 1)
				return dao.Addmember((UserVO)obj);
			else if(select == 2)
				return dao.loginSuccess((UserVO)obj);
			return false;
		}
		
		
		public static void main(String[] args) {
			new Server();
		}
}

