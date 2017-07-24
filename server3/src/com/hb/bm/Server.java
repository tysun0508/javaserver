package com.hb.bm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Server implements Runnable{
	
	ServerSocket ss;
	Socket s;
	ObjectInputStream ois;
	ObjectOutputStream oos;
	
	Connection conn;
	PreparedStatement ptmt;
	ResultSet rs;
	
	public Server() {
		try {
			ss = new ServerSocket(7979);
			System.out.println("서버 대기중...");
			new Thread(this).start();
			conn = dBConn();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	@Override
	public void run() {
		while(true){
			try {
				s = ss.accept();
				ois = new ObjectInputStream(s.getInputStream());
				VO vo = (VO)ois.readObject();
				oos = new ObjectOutputStream(s.getOutputStream());
					switch (vo.getCmd()) {
					case 100:	
						String sql = "select * from testmember2 where id =? and pw=?";
						ptmt = conn.prepareStatement(sql);
						ptmt.setString(1, vo.getId());
						ptmt.setString(2, vo.getPw());
						rs = ptmt.executeQuery();
						if(rs.next()){
							VO vo2 = new VO();
							vo2.setCmd(101);
							vo2.setId(rs.getString("id"));
							vo2.setPw(rs.getString("pw"));
							vo2.setName(rs.getString("name"));
							vo2.setSubject(rs.getString("subject"));
							vo2.setContent(rs.getString("content"));
							
							oos.writeObject(vo2);
							oos.flush();
						}else{
							VO vo2 = new VO();
							vo2.setCmd(102);
							
							oos.writeObject(vo2);
							oos.flush();
						}
						break;
					case 200:
						String sql2 = "select * from testmember2 where id =? ";
						ptmt = conn.prepareStatement(sql2);
						ptmt.setString(1, vo.getId());
						rs = ptmt.executeQuery();
						if(rs.next()){
							VO vo3 = new VO();
							vo3.setCmd(202);
							oos.writeObject(vo3);
							oos.flush();
						}else{
							VO vo3 = new VO();
							vo3.setCmd(201);
							oos.writeObject(vo3);
							oos.flush();
						}
						break;
					}
			} catch (Exception e) {
			}
		}
	}
	
	public Connection dBConn(){
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "hr";
			String password = "1111";
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
		}
		return conn;
		
	}
	
	public static void main(String[] args) {
		new Server();
	}
}
