package com.hb.am;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

// 자바통신 소켓통신이다.
// 서버는 ServerSocket 클래스
// 클라이언트 Socket 클래스
public class Ex01 {
	
	public Ex01() {
		try {
			ServerSocket ss = new ServerSocket(7777);
			System.out.println("서버 대기중...");
			// 클라이언트가 접속할때까지 기다린다.
			// 클라이언트가 접속을 하면 대리소켓 생성
			// Socket s 에는 접속자의 모든 정보가 들어있다.
			Socket s = ss.accept();
			String ip = s.getInetAddress().getHostAddress();
			String name = s.getInetAddress().getHostName();
			System.out.println("접속자 IP : "+ ip);
			System.out.println("접속자 컴퓨터이름 : "+ name);
			System.out.println("수고!");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Ex01();
	}
}
