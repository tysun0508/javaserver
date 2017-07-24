package com.hb.cm;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Player extends Thread{
	
	Socket s;
	Server server;
	ObjectInputStream ois;
	ObjectOutputStream oos;
	
	public Player() {}
	
	public Player(Socket s, Server server){
		this.s = s;
		this.server = server;
		try {
			ois = new ObjectInputStream(s.getInputStream());
			oos = new ObjectOutputStream(s.getOutputStream());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	@Override
	public void run() {
		while(true){
			try {
				VO vo = (VO)ois.readObject();
				switch (vo.getCmd()) {
				case 200:
					server.sendMsg(vo);
					break;

				case 300:
					server.delMsg(this);
					break;
				}
				
			} catch (Exception e) {
			}
		}
	}
}

































