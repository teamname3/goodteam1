package com.tedu.day04;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

/*
 * Ӣ�ۻ���
 */
public class Boss {
	// ����
	int x, y;// ����
	int speed;// �ٶ�
	int state; // ״̬
	static Image bossImg;
	static Image[] bossImgs = new Image[4];
	int life = 300;// ����ֵ
	int index = 0;// �±�

	static {
		bossImg = new ImageIcon("plane/boss0.png").getImage();
		for (int i = 0; i < 2; i++) {
			bossImgs[i] = new ImageIcon("plane/boss" + i + ".png").getImage();
		}
		for (int i = 2; i < 4; i++) {
			bossImgs[i] = new ImageIcon("plane/bosss" + (i - 2) + ".PNG").getImage();
		}
	}

	// ����
	public Boss() {
		x = (int) (Math.random() * (PlaneFrame.WIDTH - bossImg.getWidth(null)));
		y = PlaneFrame.HEIGHT + bossImg.getHeight(null);
		speed = (int) (Math.random() * 4) + 1;
		state = 0;
	}

	public void moveAndStep() {
		// ����״̬��������
		if (state == 0) {
			y -= speed;
			bossImg = bossImgs[index / 10 % 2 + 2];
		}
		if (state == 1) {
			y += 2;
			bossImg = bossImgs[index / 10 % 2];
		}
		// ����ǽ�Ĵ���
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

	//�����ӵ��ķ���
	public ArrayList<BossBu> shoot(){
		ArrayList<BossBu> list = new ArrayList<BossBu>();
		BossBu b1 = new BossBu(x,y+bossImg.getHeight(null));
		BossBu b2 = new BossBu(x+bossImg.getWidth(null)/2,y+bossImg.getHeight(null));
		BossBu b3 = new BossBu(x+bossImg.getWidth(null),y+bossImg.getHeight(null));
		list.add(b1);list.add(b2);list.add(b3);
		return list;
	}
	
}
