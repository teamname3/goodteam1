package com.tedu.day04;

import java.awt.Image;

import javax.swing.ImageIcon;

/*
 * 敌机类
 */
public class Enemy {
	// 属性
	int x, y;// 坐标
	int speed;// 速度
	int state; // 状态
	static Image enemyImg;
	static Image[] enemyImgs = new Image[6];
	int index = 0;// 下标
	
	static {
		enemyImg = new ImageIcon("plane/flys.png").getImage();
		for(int i=0;i<enemyImgs.length;i++){
			enemyImgs[i]=new ImageIcon("plane/flys"+i+".png").getImage();
		}
	}

	// 构造
	public Enemy() {
		x=(int)(Math.random()*(PlaneFrame.WIDTH-enemyImg.getWidth(null)));
		y=-enemyImg.getHeight(null);
		speed=(int)(Math.random()*4)+1;
		state = (int) (Math.random() * 2);
	}
	
	public void moveAndStep() {
		// 根据状态更改坐标
		if (state == 0) {
			x += speed;
			y += speed;
		}
		if (state == 1) {
			x -= speed;
			y += speed;
		}
		// 碰到墙的处理
		if (x <= 0)
			state = 0;
		if (x >= PlaneFrame.WIDTH - enemyImg.getWidth(null))
			state = 1;
		
		@SuppressWarnings("unused")
		int i = index++ /10 % enemyImgs.length;
		if (index == Integer.MAX_VALUE)
			index = 0;
		enemyImg = enemyImgs[index / 10 % 6];

	}
}
