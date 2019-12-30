package com.tedu.day04;

import java.awt.Image;

import javax.swing.ImageIcon;

/*
 * �л���
 */
public class Enemy {
	// ����
	int x, y;// ����
	int speed;// �ٶ�
	int state; // ״̬
	static Image enemyImg;
	static Image[] enemyImgs = new Image[6];
	int index = 0;// �±�
	
	static {
		enemyImg = new ImageIcon("plane/flys.png").getImage();
		for(int i=0;i<enemyImgs.length;i++){
			enemyImgs[i]=new ImageIcon("plane/flys"+i+".png").getImage();
		}
	}

	// ����
	public Enemy() {
		x=(int)(Math.random()*(PlaneFrame.WIDTH-enemyImg.getWidth(null)));
		y=-enemyImg.getHeight(null);
		speed=(int)(Math.random()*4)+1;
		state = (int) (Math.random() * 2);
	}
	
	public void moveAndStep() {
		// ����״̬��������
		if (state == 0) {
			x += speed;
			y += speed;
		}
		if (state == 1) {
			x -= speed;
			y += speed;
		}
		// ����ǽ�Ĵ���
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
