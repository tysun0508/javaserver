package com.hb.am;

import java.util.ArrayList;

// 대화방
public class Room {
	
	String r_name;
	
	// 대화방 참여자 list
	ArrayList<Player> j_list = new ArrayList<>();
	
	public Room() {}
	public Room(String r_name) {
		this.r_name = r_name;
	}
	
	// 방이름 반환하는 메소드
	public String getRoom(){
		return r_name;
	}
	
	// 대기실에서 방으로 참여하는 메소드
	public void joinRoom(Player player){
		j_list.add(player);
		Protocol protocol = new Protocol();
		protocol.setCmd(500);
		protocol.setChat(getJoinUsers());
		protocol.setMsg(player.getNickName()+" 님 입장");
		sendMessageRoom(protocol);
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
		j_list.remove(player);
		Protocol p = new Protocol();
		p.setCmd(600);
		p.setChat(getJoinUsers());
		p.setMsg(player.getNickName()+"님 퇴장");
		sendMessageRoom(p);
	}
	
	public int getJoinCount(){
		return j_list.size();
		
	}
	
}












