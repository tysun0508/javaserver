package com.hb.am;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
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
	BufferedReader br;
	BufferedWriter bw;
	
	Connection conn;
	PreparedStatement ptmt;
	ResultSet rs;
	
	public Server() {
		try {
			ss = new ServerSocket(9999);
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
				br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
				
				String[]msg = br.readLine().split("#");
					switch (msg[0]) {
					case "100":	
						String sql = "select * from testmember2 where id =? and pw=?";
						ptmt = conn.prepareStatement(sql);
						ptmt.setString(1, msg[1]);
						ptmt.setString(2, msg[2]);
						rs = ptmt.executeQuery();
						if(rs.next()){
							String msg2 = "ok" + System.getProperty("line.separator");
							bw.write(msg2);
							bw.flush();
						}else{
							String msg2 = "no" + System.getProperty("line.separator");
							bw.write(msg2);
							bw.flush();
						}
						break;
					case "200":
						String sql2 = "select * from testmember2 where id =?";
						ptmt = conn.prepareStatement(sql2);
						ptmt.setString(1, msg[1]);
						rs = ptmt.executeQuery();
						String msg3 = "";
						if(rs.next()){
							msg3 += rs.getString("pw")+"#";
							msg3 += rs.getString("name")+"#";
							msg3 += rs.getString("subject")+"#";
							msg3 += rs.getString("content")+System.getProperty("line.separator");
							bw.write(msg3);
							bw.flush();
						}else{
							msg3 = "no" + System.getProperty("line.separator");
							bw.write(msg3);
							bw.flush();
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
