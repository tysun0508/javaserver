package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PlayDAO {
	private Connection conn;
	private PreparedStatement ptmt;
	private ResultSet rs;

	// 접속하는 메소드
	
	public PlayDAO() { }
	
	public void connet() {
		try {
			// JDBC
			Class.forName("oracle.jdbc.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "hr";
			String password = "1111";
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
		}
	}
	
	// 회원가입
	public boolean Addmember(UserVO uservo) {
		int su = 0;
		try {
			connet();
			String sql = "insert into project2 values(project2_seq.nextval,?,?,?,?)";
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, uservo.getId());
			ptmt.setString(2, uservo.getPwd());
			ptmt.setString(3, uservo.getName());
			ptmt.setString(4, uservo.getContent());
			su = ptmt.executeUpdate();
		} catch (Exception e) {
		} finally {
			CloseAll();
		}
		if(su >0)
			return true;
		else
			return false;
	}

	// 회원가입도중 아이디중복체크
	public boolean idChk(String id){
		try {
			connet();
			String sql ="select * from project2 where id = ? ";
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, id);
			rs = ptmt.executeQuery();
			
			if(rs.next()){
				System.out.println(1);
				return true;
			}
		} catch (Exception e) {
			CloseAll();
		} finally {
			CloseAll();
		}
		return false;
	}
	
	public boolean loginSuccess(UserVO userVo) {
		try {
			connet();
			System.out.println(2);
			String sql = "select * from project2 where id = ? and pwd = ?";
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, userVo.getId());
			ptmt.setString(2, userVo.getPwd());
			rs = ptmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			CloseAll();
		} finally {
			CloseAll();
		}
		return false;
	}

	public void CloseAll() {
		try {
			if(rs != null)
				rs.close();
			if(ptmt != null)
				ptmt.close();
			if(conn != null)
				conn.close();
		} catch (Exception e2) {
		}
	}
	

}
