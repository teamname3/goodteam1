package com.tedu.day04;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Bullet {
	// ����
	int x, y;// ����
	static Image bulletImg = new ImageIcon("plane/bullets.png").getImage();
	int index = 0;// �±�

	// ����
	public Bullet(int x,int y) {
		this.x=x;
		this.y=y;
	}

	public void move(int firespeed) {
		y-=firespeed;
	}
}