package com.tedu.day04;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

/*
 * Ӣ�ۻ���
 */
public class Hero {
	// ����
	int x = 200, y = 500;// ����
	Image[] heroImgs = new Image[10];// ͼƬ
	Image heroImg;
	int life = 5;// ����ֵ
	int fire = 1;// ��������
	int firespeed = 5;// �����ٶ�
	int score = 0;// ����
	int index = 0;// �±�

	// ����
	public Hero() {
		heroImg = new ImageIcon("plane/ws00.png").getImage();
		for (int i = 0; i < heroImgs.length; i++) {
			heroImgs[i] = new ImageIcon("plane/ws0" + i + ".png").getImage();
		}
	}

	// ͼƬ�л��ķ���
	public void step() {
		@SuppressWarnings("unused")
		int i = index++ / 10 % heroImgs.length;
		if (index == Integer.MAX_VALUE)
			index = 0;
		heroImg = heroImgs[index / 10 % 10];

	}

	// �����ӵ��ķ���
	public ArrayList<Bullet> shoot() {

		ArrayList<Bullet> list = new ArrayList<Bullet>();// ����洢�ӵ��ļ���
		switch (fire) {
		case 1: {
			Bullet b = new Bullet(x + heroImg.getWidth(null) / 2 - 5, y);
			list.add(b);
			break;
		}
		case 2: {
			Bullet b = new Bullet(x + heroImg.getWidth(null) / 3, y);
			list.add(b);
			Bullet b1 = new Bullet(x + heroImg.getWidth(null) * 2 / 3 - 10, y);
			list.add(b1);
			break;
		}
		case 3: {
			Bullet b = new Bullet(x + heroImg.getWidth(null) / 4 - 5, y);
			list.add(b);
			Bullet b1 = new Bullet(x + heroImg.getWidth(null) / 2 - 5, y);
			list.add(b1);
			Bullet b2 = new Bullet(x + heroImg.getWidth(null) / 4 * 3 - 5, y);
			list.add(b2);
			break;
		}
		}
		return list;
	}

}
