package com.hb.am;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Player extends Thread{
	
	Socket s;
	Server server;
	ObjectInputStream ois;
	ObjectOutputStream oos;
	Room room;
	String nickName;
	
	public Player() {}
	public Player(Socket s, Server server){
		try {
			this.s = s;
			this.server = server;
			
			ois = new ObjectInputStream(s.getInputStream());
			oos = new ObjectOutputStream(s.getOutputStream());
			
		} catch (Exception e) {
		}
	}
	
	public String getNickName(){
		return nickName;
	}
	
	@Override
	public void run() {
		while(true){
			try {
				Protocol p = (Protocol)ois.readObject();
				
				switch (p.getCmd()) {
				case 100: // 로그인
					nickName = p.getMsg();
					// 현재 대기실에 존재하는 모든 닉네임 받기
					p.setUsers(server.getUsers());
					// 현재 대기실에 존재하는 모든 방 제목을 받기
					p.setRooms(server.getRooms());
					
					server.sendMsg(p);
					
					break;
				
				case 200: // 방 만들기
					String roomName = p.getMsg();
					room = new Room(roomName);
					
					// 자기자신이 방에 참여한다.
					room.joinRoom(this);
					
					// 생성된 방 객체를 서버에 등록(방 추가)
					server.addRoom(room);
					
					// 자기자신은 대기실에서 삭제 되고
					server.del(this);
					
					// 남은 인원들 보여주기
					// 방제목은 남은 인원들에게 보여야 된다.
					Protocol p2 = new Protocol();
					p2.setCmd(100);
					p2.setUsers(server.getUsers());
					p2.setRooms(server.getRooms());
					server.sendMsg(p2);
					
					break;
					
				case 300: // 방 참여
					room = server.changeRoom(p.getIndex());
					room.joinRoom(this);
					server.del(this);
					
					Protocol p3 = new Protocol();
					p3.setCmd(100);
					p3.setUsers(server.getUsers());
					p3.setRooms(server.getRooms());
					server.sendMsg(p3);
					
					break;
					
				case 400: // 쪽지 보내기
					Player player = server.list.get(p.getIndex());
					String msg = p.getMsg();
					p.setMsg(nickName + "님의 쪽지 : \r\n" + msg);
					player.oos.writeObject(p);
					player.oos.flush();
					
					break;
				
				case 500:
					p.setMsg(nickName+":"+p.getMsg());
					p.setChat(room.getJoinUsers());
					room.sendMessageRoom(p);
					break;
					
				case 600:
					room.outRoom(this);
					// 방에 인원이 하나도 없으면 방 삭제
					if(room.getJoinCount()==0){
						server.delRoom(room);
					}
					server.list.add(this);
					Protocol p4 = new Protocol();
					p4.setCmd(100);
					p4.setUsers(server.getUsers());
					p4.setRooms(server.getRooms());
					server.sendMsg(p4);
					break;
				}
			} catch (Exception e) {
			}
		}
	}
}






















