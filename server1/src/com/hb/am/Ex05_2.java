package com.hb.am;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Ex05_2 {
	
	
	ServerSocket ss;
	Socket s;
	public Ex05_2() {
		try {
			ss = new ServerSocket(7777);
			System.out.println("서버대기중...");
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					while(true){
						try {
							s = ss.accept();
							// 클라이언트가 문자스트림으로 보냈다.
							// 서버도 문자스트림으로 대응하자
							InputStream in = s.getInputStream();
							InputStreamReader isr = new InputStreamReader(in);
							BufferedReader br = new BufferedReader(isr);
							
							// 한줄 읽기
							String msg = br.readLine();
							System.out.println("클라이언트에서 받은정보 : " + msg);
							
							OutputStream out = s.getOutputStream();
							OutputStreamWriter osw = new OutputStreamWriter(out);
							BufferedWriter bw = new BufferedWriter(osw);
							
							// Writer 할때는 라인 끝표시 반드시 해야 됨
							msg += System.getProperty("line.separator");
							bw.write(msg);
							bw.flush();
						} catch (Exception e) {
						}
					}
					
				}
			}).start();;
		} catch (Exception e) {
		}
	}
	
	public static void main(String[] args) {
		new Ex05_2();
	}
}
