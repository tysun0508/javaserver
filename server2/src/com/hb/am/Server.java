package com.hb.am;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable {
	
	ServerSocket ss;
	Socket s;
	ArrayList<Player> list;
	public Server() {
		try {
			list = new ArrayList<>();
			ss = new ServerSocket(7777);
			System.out.println("서버대기중...");
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
			}
		}
	}
	
	// 받은 메세지를 가지고 list 안에 존재하는 클라이언트들에게 각각 메세지를 보낸다.
	public void sendMsg(String msg){
		try {
			msg = msg + System.getProperty("line.separator");
			for(Player k : list){
				k.bw.write(msg);
				k.bw.flush();
			}
		} catch (Exception e) {
		}
	}
	
	// 삭제
	public void delPlayer(Player player){
		list.remove(player);
	}
	
	
	public static void main(String[] args) {
		new Server();
	}

}
