package com.hb.cm;

import java.io.Serializable;

public class Protocol implements Serializable{
	public static final long serialVersionUID = 2L ;
	// 약속된 번호 : 100(대화명 받기=로그인), 200(채팅), 300(종료하기).
	private int cmd;
	// 채팅내용
	private String msg;
	public Protocol() {}
	public Protocol(int cmd, String msg) {
		super();
		this.cmd = cmd;
		this.msg = msg;
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
	
}
