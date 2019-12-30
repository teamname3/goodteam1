package com.tedu.day04;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Boom {
	int x,y;
	static Image boomImg = new ImageIcon("plane/0.png").getImage();

	public Boom(int x, int y) {
		this.x = x;
		this.y = y;
		
	}

	public void draw(Graphics g) {
		g.drawImage(boomImg, x, y, null);
	}
}
