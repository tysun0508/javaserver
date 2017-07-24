package com.hb.bm;

import java.io.BufferedWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{
	
	ServerSocket ss;
	Socket s;
	// 객체 직렬화를 이용한 통신
	ObjectInputStream ois;
	BufferedWriter bw;
	
	public Server() {
		try {
			ss = new ServerSocket(7777);
			System.out.println("서버대기중...");
			new Thread(this).start();
		} catch (Exception e) {
			
		}
	}

	@Override
	public void run() {
		while(true){
			try {
				s = ss.accept();
				ois = new ObjectInputStream(s.getInputStream());
				VO vo = (VO) ois.readObject();
				int num1 = vo.getNum1();
				int num2 = vo.getNum2();
				String op = vo.getOp();
				int res = 0;
				if(op.equals("+")){
					res = num1+num2;
				}else if(op.equals("-")){
					res = num1-num2;
				}else if(op.equals("*")){
					res = num1*num2;
				}else if(op.equals("/")){
					res = num1/num2;
				}
				String msg = num1 + op + num2 + "=" + res ;
				msg = msg + System.getProperty("line.separator");
				bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
				bw.write(msg);
				bw.flush();
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	public static void main(String[] args) {
		new Server();
	}
}
























