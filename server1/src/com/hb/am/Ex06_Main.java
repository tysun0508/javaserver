package com.hb.am;

import java.net.ServerSocket;
import java.net.Socket;

public class Ex06_Main {
	public static void main(String[] args) {
		ServerSocket ss = null;
		Socket s = null;
		try {
			ss = new ServerSocket(7777);
			System.out.println("서버대기중...");
			
			s = ss.accept();
			
			new Thread(new Ex06_Input(s)).start();
			new Thread(new Ex06_Output(s)).start();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
