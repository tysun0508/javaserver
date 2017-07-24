package com.hb.am;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class Ex06_Input implements Runnable {
	
	Socket s ;
	String msg;
	public Ex06_Input() {}
	public Ex06_Input(Socket s){
		this.s = s;
	}
	
	@Override
	public void run() {
		while(true){
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				msg = br.readLine();
				System.out.println("받는 말 :" + msg);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}
}