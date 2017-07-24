package com.hb.am;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Ex04_1 {
	ServerSocket ss;
	Socket s;
	
	public Ex04_1() {
		try {
			ss = new ServerSocket(7777);
			System.out.println("서버 대기중...");
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					while(true){
						try {
							s = ss.accept();
							// 클라이언트가 바이트스트림(out)했다고 가정
							// 서버도 바이트 스트림 하자
							InputStream in = s.getInputStream();
							BufferedInputStream bis = new BufferedInputStream(in);
							
							// 아스키코드가 들어온다(형변환해야 한다)
							//int b = 0;
							//b = bis.read();
							//System.out.println("출력 : " + (char)b);
							
							// 여러글자 출력가능(but 한글처리 못함)
							//while ((b=bis.read()) != -1){
							//	System.out.println("출력 : " + (char)b);
							//}
							
							// 여러글자 출력 한글처리 까지
							byte[] b = new byte[1024];
							bis.read(b);
							//byte[]를 String으로 변경하자
							String msg = new String(b).trim();
							System.out.println(msg);
							
						} catch (Exception e) {
						}
					}
				}
			}).start();
		} catch (Exception e) {
		}
	}
	
	public static void main(String[] args) {
		new Ex04_1();
	}
}
