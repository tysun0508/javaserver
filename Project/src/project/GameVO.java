package project;

import java.awt.Color;
import java.io.Serializable;

public class GameVO implements Serializable {
	public static final long serialVersionUID = 12345L;
	private int x, y, wh ;
	private Color color ;
	
	public GameVO() {}

	public GameVO(int x, int y, int wh, Color color) {
		super();
		this.x = x;
		this.y = y;
		this.wh = wh;
		this.color = color;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWh() {
		return wh;
	}

	public void setWh(int wh) {
		this.wh = wh;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	@Override
	public String toString() {
		return "GameVO [x=" + x + ", y=" + y + ", wh=" + wh + ", color=" + color + "]";
	}
	
}
