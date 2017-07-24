package project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Protocol implements Serializable{
	public static final long serivalVersionUID = 9879;
	
	private int cmd;
	private String msg ,msg2, msg3 ,answer;
	int index ; // 대기실에서 접속할 방의 인텍스의 값
	
	
	
	String[] users, rooms, chat  ; // 대기실 인원, 방 인원, 채팅인원
	private GameVO gamevo;
	private UserVO uservo;
	
	
	
	
	public String getMsg3() {
		return msg3;
	}
	public void setMsg3(String msg3) {
		this.msg3 = msg3;
	}
	public String getMsg2() {
		return msg2;
	}
	public void setMsg2(String msg2) {
		this.msg2 = msg2;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public GameVO getGamevo() {
		return gamevo;
	}
	public void setGamevo(GameVO gamevo) {
		this.gamevo = gamevo;
	}
	public UserVO getUservo() {
		return uservo;
	}
	public void setUservo(UserVO uservo) {
		this.uservo = uservo;
	}
	
	
	public String[] getChat() {
		return chat;
	}
	public void setChat(String[] chat) {
		this.chat = chat;
	}
	public Protocol() {}
	public Protocol(int cmd, String msg, int index, String[] users, String[] rooms) {
		super();
		this.cmd = cmd;
		this.msg = msg;
		this.index = index;
		this.users = users;
		this.rooms = rooms;
	}
	public int getCmd() {
		return cmd;
	}
	public void setCmd(int cmd) {
		this.cmd = cmd;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String[] getUsers() {
		return users;
	}
	public void setUsers(String[] users) {
		this.users = users;
	}
	public String[] getRooms() {
		return rooms;
	}
	public void setRooms(String[] rooms) {
		this.rooms = rooms;
	}
	@Override
	public String toString() {
		return "Protocol [cmd=" + cmd + ", msg=" + msg + ", index=" + index + ", gamevo=" + gamevo + ", uservo="
				+ uservo + "]";
	}
	
	
}