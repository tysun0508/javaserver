package com.hb.cm;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable{
	
	ServerSocket ss;
	Socket s;
	ArrayList<Player> list = new ArrayList<>();
	public Server() {
		try {
			ss = new ServerSocket(7777);
			System.out.println("서버대기중....");
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
				new Thread(player).start();
			} catch (Exception e) {
			}
		}
	}
	
	public void sendMsg(Protocol p){
		try {
			for(Player k : list){
				k.oos.writeObject(p);
				k.oos.flush();
			}
			
		} catch (Exception e) {
		}
	}
	
	public void del(Player s){
		try {
			list.remove(s);
			Protocol p3 = new Protocol(200, s.name+"님 퇴장");
			for(Player k : list){
				k.oos.writeObject(p3);
				k.oos.flush();
			}
			
		} catch (Exception e) {
		}
	}
	
	public static void main(String[] args) {
		new Server();
	}
}










