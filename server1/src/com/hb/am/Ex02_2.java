package com.hb.am;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Ex02_2 implements Runnable {
	
	ServerSocket ss;
	Socket s;
	
	public Ex02_2() {
		
		try {
			ss = new ServerSocket(7777);
			System.out.println("서버 대기중...");
			new Thread(this).start();
		} catch (Exception e) {
			
		}
	}
	
	@Override
	public void run() {
		try {
			s = ss.accept();
			System.out.println(s.getInetAddress());
		} catch (IOException e) {
			
		}
		
	}
	
	public static void main(String[] args) {
		new Ex02_2();
		
	}

}
