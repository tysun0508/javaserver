package com.hb.am;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Ex02_1 implements Runnable {
	ServerSocket ss;
	Socket s;
	
	public Ex02_1() {
		
		try {
			System.out.println("1"+Thread.currentThread().getName());
			ss = new ServerSocket(7777);
			System.out.println("서버 대기중...");
		} catch (Exception e) {
			
		}
	}
	
	@Override
	public void run() {
		try {
			System.out.println("2"+Thread.currentThread().getName());
			s = ss.accept();
			System.out.println(s.getInetAddress());
		} catch (IOException e) {
			
		}
		
	}
	
	public static void main(String[] args) {
		new Thread(new Ex02_1()).start(); 
		System.out.println("3"+Thread.currentThread().getName());
		System.out.println("수고~!");
	}

}
