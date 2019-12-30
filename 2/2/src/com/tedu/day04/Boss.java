package com.tedu.day04;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

/*
 * 英雄机类
 */
public class Boss {
	// 属性
	int x, y;// 坐标
	int speed;// 速度
	int state; // 状态
	static Image bossImg;
	static Image[] bossImgs = new Image[4];
	int life = 300;// 生命值
	int index = 0;// 下标

	static {
		bossImg = new ImageIcon("plane/boss0.png").getImage();
		for (int i = 0; i < 2; i++) {
			bossImgs[i] = new ImageIcon("plane/boss" + i + ".png").getImage();
		}
		for (int i = 2; i < 4; i++) {
			bossImgs[i] = new ImageIcon("plane/bosss" + (i - 2) + ".PNG").getImage();
		}
	}

	// 构造
	public Boss() {
		x = (int) (Math.random() * (PlaneFrame.WIDTH - bossImg.getWidth(null)));
		y = PlaneFrame.HEIGHT + bossImg.getHeight(null);
		speed = (int) (Math.random() * 4) + 1;
		state = 0;
	}

	public void moveAndStep() {
		// 根据状态更改坐标
		if (state == 0) {
			y -= speed;
			bossImg = bossImgs[index / 10 % 2 + 2];
		}
		if (state == 1) {
			y += 2;
			bossImg = bossImgs[index / 10 % 2];
		}
		// 碰到墙的处理
		if (y < -bossImg.getHeight(null)) {
			state = 1;
		}
		if (y > 100 && state == 1) {
			x += speed;
			y = 100;
		}
		if (x<0||x > PlaneFrame.WIDTH - bossImg.getWidth(null)) {
			speed=-speed;
		}

		if (index == Integer.MAX_VALUE)
			index = 0;
	}

	//发射子弹的方法
	public ArrayList<BossBu> shoot(){
		ArrayList<BossBu> list = new ArrayList<BossBu>();
		BossBu b1 = new BossBu(x,y+bossImg.getHeight(null));
		BossBu b2 = new BossBu(x+bossImg.getWidth(null)/2,y+bossImg.getHeight(null));
		BossBu b3 = new BossBu(x+bossImg.getWidth(null),y+bossImg.getHeight(null));
		list.add(b1);list.add(b2);list.add(b3);
		return list;
	}
	
}
