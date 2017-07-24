package project;

import java.awt.Color;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Player extends Thread{

	Socket s;
	Server server;
	ObjectInputStream ois;
	ObjectOutputStream oos;
	Room room;
	String nickName ;
	
	
	public Player() {}
	public Player(Socket s, Server server) {
		try {
			this.s = s;
			this.server = server;
			ois = new ObjectInputStream(s.getInputStream());
			oos = new ObjectOutputStream(s.getOutputStream());
		} catch (Exception e) {
		}
	}
	
	public String getNickName(){
		return nickName;
	}
	
	@Override
	public void run() {
		while(true)
			try {
				Protocol p = (Protocol) ois.readObject();
				System.out.println(server.list);
				System.out.println(p);
				switch (p.getCmd()) {
				
				case 0:
					break;
					
				// 중복검사부분
				case 10:
					Protocol pidck = new Protocol();
					if(server.useDao(p.getMsg(), 0))
						pidck.setCmd(5);
					else
						pidck.setCmd(15);
					
					this.oos.writeObject(pidck);
					this.oos.flush();
					break;
					
				case 50:
					Protocol paddid = new Protocol();
					if(!server.useDao(p.getUservo(), 1))
						paddid.setCmd(45);
					else
						paddid.setCmd(55);
						paddid.setMsg(p.getUservo().getId());
						
					this.oos.writeObject(paddid);
					this.oos.flush();
					break;
					
				case 100: // 로그인
					Protocol plogin = new Protocol();
					boolean logck = false;					
					if(!server.useDao(p.getUservo(), 2))
						plogin.setCmd(102);					
					else{
						logck = true;
						this.nickName = p.getUservo().getId();
						//server.addPlayer(this);
						plogin.setCmd(90);
					}
					this.oos.writeObject(plogin);
					this.oos.flush();
					
					if(logck){
						Protocol pval = new Protocol();
						pval.setCmd(100);
						pval.setMsg(p.getMsg());
						// 현재 대기실에 존재하는 모든유저 아이디받기
						pval.setUsers(server.getUsers());
						pval.setRooms(server.getRooms());
				
						server.sendMsg(pval);
					}
					break;
					
				case 200:  // 방 만들기
					String roomName = p.getMsg();
					room = new Room(roomName,server);
					// 자기자신이 방에 참여한다.
					room.joinRoom(this); 
					//생성된 방 객체를 서버에 등록 (방 추가)
					server.addRoom(room);
					// 자기자신은 대기실에서 삭제 되고
					server.delplayer(this);
					
					// 남은  인원들 보여주기
					// 방 제목은 남은 인원들에게 보여야 됨
					Protocol p2 = new Protocol();
					p2.setCmd(100);
					p2.setUsers(server.getUsers());
					p2.setRooms(server.getRooms());
					server.sendMsg(p2);
					break;
					
				case 300:  // 방 참여
					room = server.changeRoom(p.getIndex());
					
					boolean joinck = room.joinRoom(this);
					Protocol pclear = new Protocol();
					if(joinck==true){
						server.delplayer(this);
						// 대기실에게 보내기
						pclear.setCmd(100);
						pclear.setUsers(server.getUsers());
						pclear.setRooms(server.getRooms());
						server.sendMsg(pclear);
					}else{
						pclear.setCmd(510);
						this.oos.writeObject(pclear);
						this.oos.flush();
					}
					
					
					break;
					
				case 400:  // 쪽지 보내기
					Player player = server.list.get(p.getIndex());
					String msg = p.getMsg();
					p.setMsg(nickName+"님의 쪽지 : \r\n"+msg);
					player.oos.writeObject(p);
					player.oos.flush();
					break;
				case 500: 
				     p.setMsg(nickName+"   :   "+p.getMsg());
				     p.setChat(room.getJoinUsers());
				     room.sendMessageRoom(p);
					break;
				case 550:
					System.out.println(p);
					room.sendMessageRoom(p);
					break;
				case 600: 
					room.outRoom(this);
					// 방의 인원이 하나도 없으면 방 삭제
					if(room.getJoinCount()==0){
						server.delRoom(room);
					}
					server.list.add(this);
					
					Protocol p4=new Protocol();
					p4.setCmd(100);
					p4.setUsers(server.getUsers());
					p4.setRooms(server.getRooms());
					server.sendMsg(p4);
					break;
				case 700: //로그인한 유저가 완전종료
					Protocol pexit = new Protocol();
					pexit.setCmd(700);
					server.delplayer(this);		
					// 방에서나갈땐 위에꺼까지만
					Protocol newer = new Protocol();
					newer.setCmd(100);
					newer.setUsers(server.getUsers());
					newer.setRooms(server.getRooms());
					server.sendMsg(newer);					
					
					this.oos.writeObject(pexit);
					this.oos.flush();
					
					break;
				
				case 800: //게임 시작
					Protocol pstart = new Protocol();
					
					String k = server.showquiz();
					room.setQuiz(k);
					room.chk = true;
					
					pstart.setCmd(800);
					pstart.setMsg(room.getQuiz());
					
					
					this.oos.writeObject(pstart);
					this.oos.flush();
					
					Protocol pstart2 = new Protocol();
					pstart2.setCmd(850);
					pstart2.setMsg2("게임시작 "+nickName+"님 차례입니다.\n1번 문제입니다.\n");
					room.sendMessageRoom(pstart2);
					break;
	
				case 1000:
					//정답 맞추면
					if(room.getQuiz().equalsIgnoreCase(p.getAnswer())){
						int gamec = room.gc();
						Protocol p123 = new Protocol();
						p123.setCmd(1001);
						p123.setMsg(nickName);
						if(gamec<10){
							p123.setMsg2(String.valueOf((gamec+1)));
						}
						System.out.println(gamec);
						room.sendMessageRoom(p123);
						
						//문제냈던사람이 정답칸이 안보이게하는곳
						Protocol pclear2 = new Protocol();
						pclear2.setCmd(1010);
						room.sendMessageRoom(pclear2);
						
						//정답 맞춘사람이 이제 문제내는곳
						Protocol pstart3 = new Protocol();
						String k2 = server.showquiz();
						room.setQuiz(k2);
						pstart3.setCmd(801);
						pstart3.setMsg(room.getQuiz());
						pstart3.setMsg2("다음 문제입니다.");
						this.oos.writeObject(pstart3);
						this.oos.flush();
						
						// 그림그리는곳
						Protocol pstart4 = new Protocol();
						pstart4.setCmd(1020);
						this.oos.writeObject(pstart4);
						this.oos.flush();
						//게임문제
						if(gamec>9){
							Protocol gameset = new Protocol();
							gameset.setCmd(9999);
							gameset.setMsg(nickName);
							room.gcover();
							room.chk = false;
							System.out.println(gamec);
							room.sendMessageRoom(gameset);
						}
						
					}else{
						Protocol pOk = new Protocol();
						pOk.setCmd(1002);
						this.oos.writeObject(pOk);
						this.oos.flush();
					}
					break;
				}
			} catch (Exception e) {
			}

	}
}

