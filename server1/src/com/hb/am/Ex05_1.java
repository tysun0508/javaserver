package com.hb.am;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Ex05_1 {
	
	
	ServerSocket ss;
	Socket s;
	public Ex05_1() {
		try {
			ss = new ServerSocket(7777);
			System.out.println("서버대기중...");
			
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					while(true){
						try {
							s = ss.accept();
							InputStream in = s.getInputStream();
							BufferedInputStream bis = new BufferedInputStream(in);
							
							
							byte[] b = new byte[1024];
							bis.read(b);
							//byte[]를 String으로 변경하자
							String msg = new String(b).trim();
							System.out.println("받은 정보 : "+ msg);
							
							OutputStream out = s.getOutputStream();
							BufferedOutputStream bos = new BufferedOutputStream(out);
							
							// Writer 할때는 라인 끝표시 반드시 해야 됨
							msg += System.getProperty("line.separator");
							bos.write(msg.getBytes());
							bos.flush();
							
						} catch (Exception e) {
						}
					}
					
				}
			}).start();;
		} catch (Exception e) {
		}
	}
	
	public static void main(String[] args) {
		new Ex05_1();
	}
}
