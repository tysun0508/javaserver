package project;

import java.io.Serializable;

public class UserVO implements Serializable{
	public static final long serialVersionUID = 3333L;
	private String id, pwd, name, content;
	private int idx;
	
	public UserVO() {}

	public UserVO(String id, String pwd, String name, String content, int idx) {
		super();
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.content = content;
		this.idx = idx;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}
	
	@Override
	public String toString() {
		return "userVO [id=" + id + ", pwd=" + pwd + ", name=" + name + ", content=" + content +", idx=" + idx + "]";
	}
}
