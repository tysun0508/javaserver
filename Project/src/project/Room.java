package project;

import java.util.ArrayList;

//대화방
public class Room {
	
	private String quiz;
	int gamecount = 1;
	
	String r_name;
	// 대화방 참여자 리스트
	ArrayList<Player> j_list;
	Server s;
	boolean chk = false;
	
	public Room() {}
	public Room(String r_name, Server s) {
		this.r_name = r_name;
		this.s = s;
		j_list = new ArrayList<>();
	}
	
	
	public int gc(){
		return gamecount++;
	}
	
	public int gcover(){
		return gamecount =1;
	}
	
	
	
	// 방이름 반환하는 메소드
	public String getRooms(){
		return r_name;
	}
	
	// 게임시작 받아오는
	
	public String getQuiz() {
		return quiz;
	}
	
	public void setQuiz(String quiz) {
		this.quiz = quiz;
	}	

	// 방 참여자 이름 반환
	public String[] getJoinUsers(){
		String [] ar = new String[j_list.size()];
		int i = 0 ;
		for(Player k : j_list){
			ar[i++] = k.getNickName();
		}
		return ar;
	}
		
	// 방 참여자들에게 메세지 전달
	public void sendMessageRoom(Protocol protocol){
		for(Player k : j_list ){
			try {
				k.oos.writeObject(protocol);
				k.oos.flush();
			} catch (Exception e) {
			}
		}
	}
	
	
	// 방나가기
	public void outRoom(Player player){
		Protocol p = new Protocol();
		p.setCmd(600);
		p.setChat(getJoinUsers());
		p.setMsg(player.getNickName()+"님 퇴장");
		j_list.remove(player);
		p.setChat(getJoinUsers());
		sendMessageRoom(p);
	}
	
	// 대기실에서 방으로 참여하는 메소드
	public boolean joinRoom(Player player){
		if(j_list.size()<3){
			Protocol proto = new Protocol();
			j_list.add(player);
			proto.setCmd(500);
			if(chk == true)
				proto.setIndex(-1);
			else
				proto.setIndex(1);
			proto.setChat(getJoinUsers());
			proto.setMsg(player.getNickName()+" 님 입장");
			sendMessageRoom(proto);
			return true;
		}else{
			return false;
		}
	}
	
	// 방의 인원 세기
	public int getJoinCount(){
		return j_list.size();
	}

}