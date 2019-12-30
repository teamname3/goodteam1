package com.tedu.day04;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Bullet {
	// 属性
	int x, y;// 坐标
	static Image bulletImg = new ImageIcon("plane/bullets.png").getImage();
	int index = 0;// 下标

	// 构造
	public Bullet(int x,int y) {
		this.x=x;
		this.y=y;
	}

	public void move(int firespeed) {
		y-=firespeed;
	}
}