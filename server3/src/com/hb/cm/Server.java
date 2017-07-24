package com.hb.cm;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable {
	
	ServerSocket ss;
	Socket s;
	ArrayList<Player> list ;
	
	public Server() {
		try {
			list = new ArrayList<>();
			ss = new ServerSocket(3333);
			System.out.println("서버대기중...");
			new Thread(this).start();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public void run() {
		while(true){
			try {
				s = ss.accept();
				Player player = new Player(s, this);
				list.add(player);
				new Thread(player).start();
			} catch (Exception e) {
			}
		}
	}
	// 그리기
	public void sendMsg(VO vo){
		try {
			for(Player k : list){
				k.oos.writeObject(vo);
				k.oos.flush();
			}
		} catch (Exception e) {
		}
	}
	// 삭제
	public void delMsg(Player player){
		list.remove(player);
	}
	public static void main(String[] args) {
		new Server();
	}
}
