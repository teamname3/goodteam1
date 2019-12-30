package com.tedu.day04;

import java.awt.Image;

import javax.swing.ImageIcon;

/*
 * buff类
 */
public class Heart {
	// 属性
	int x, y;// 坐标
	int speed;// 速度
	int state; // 状态
	static Image heartImg;
	static Image[] heartImgs = new Image[9];
	int index = 0;// 下标
	
	static {
		heartImg = new ImageIcon("plane/qq05.png").getImage();
		for(int i=0;i<heartImgs.length;i++){
			heartImgs[i]=new ImageIcon("plane/qq0"+i+".png").getImage();
		}
	}
	// 构造
	public Heart() {
		x=(int)(Math.random()*(PlaneFrame.WIDTH-heartImg.getWidth(null)));
		y=-heartImg.getHeight(null);
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
		if (x >= PlaneFrame.WIDTH - heartImg.getWidth(null))
			state = 1;
		
		@SuppressWarnings("unused")
		int i = index++ /10 % heartImgs.length;
		if (index == Integer.MAX_VALUE)
			index = 0;
		heartImg = heartImgs[index / 10 % 9];

	}
}