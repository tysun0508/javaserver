package com.hb.am;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Ex06_Output implements Runnable {

	Socket s;
	Scanner scan = new Scanner(System.in);
	public Ex06_Output() {}
	public Ex06_Output(Socket s){
		this.s = s;
		
	}
	
	@Override
	public void run() {
		while(true){
			try {
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
				String msg = scan.nextLine();
				msg += System.getProperty("line.separate");
				bw.write(msg);
				bw.flush();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	}
	
}
