package com.hb.am;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Ex02_3 {
	
	ServerSocket ss;
	Socket s;
	
	public Ex02_3() {
		// 안드로이드 스레드 처리
		try {
			ss = new ServerSocket(7777);
			System.out.println("서버 대기중...");
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						s = ss.accept();
					} catch (IOException e) {
						e.printStackTrace();
					}
					System.out.println(s.getInetAddress());
					
				}
			}).start();
		} catch (Exception e) {
			
		}
	}
	
	public static void main(String[] args) {
		new Ex02_3();
		
	}

}
