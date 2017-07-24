package com.hb.am;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable{
	
	ServerSocket ss;
	Socket s;
	
	// 대기실 리스트
	ArrayList<Player> list;
	
	// 채팅방 리스트
	ArrayList<Room> r_list;
	
	public Server() {
		r_list = new ArrayList<>();
		list = new ArrayList<>();
		try {
			ss = new ServerSocket(8989);
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
				Player player = new Player(s, this);
				list.add(player);
				player.start();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	// 대기실에 있는 사람들의 이름을 반환하는 메소드
	public String[] getUsers(){
		String[] arr = new String[list.size()];
		int i = 0;
		for(Player k : list){
			arr[i++] = k.getNickName();
		}
		return arr;
	}
	
	// 현재 대기실에 있는 방의 제목을 반환하는 메소드
	public String[] getRooms(){
		String[] arr = new String[r_list.size()];
		int i = 0;
		for(Room k : r_list){
			arr[i++] = k.getRoom();
		}
		return arr;
	}
	
	// 대기실에 있는 사람들에게 Protocol 전달
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
	public void del(Player player){
		list.remove(player);
	}
	
	// 방리스트 인덱스를 받아서 해당 방을 리턴한다
	public Room changeRoom(int index){
		return r_list.get(index);
	}
	
	// 방삭제
	public void delRoom(Room room){
		r_list.remove(room);
	}
	
	public static void main(String[] args) {
		new Server();
	}
}
