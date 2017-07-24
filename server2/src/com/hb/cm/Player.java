package com.hb.cm;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Player extends Thread{
	
	Socket s;
	Server server;
	String name;
	ObjectInputStream ois ;
	ObjectOutputStream oos;
	
	
	public Player() {}
	
	public Player(Socket s, Server server){
		this.s = s;
		this.server = server;
		try {
			ois = new ObjectInputStream(s.getInputStream());
			oos = new ObjectOutputStream(s.getOutputStream());
		} catch (Exception e) {
		}
	}
	@Override
	public void run() {
			try {
				while(true){
					Protocol p = (Protocol)ois.readObject();
					
					switch (p.getCmd()) {
					case 100:
						name = p.getMsg();
						Protocol p2 = new Protocol();
						p2.setCmd(200);
						p2.setMsg(name+"님 입장");
						server.sendMsg(p2);
						break;
					case 200:	
						server.sendMsg(p);
						break;
					case 300:
						server.del(this);
						break;
					}
				}
			} catch (Exception e) {
			} 
		
	}
	
}


















