package com.tedu.day04;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * Boss���ӵ���
 */
public class BossBu {
	//����
	int x,y;
	int speed=2;
	static Image bossBuImg;
	static Image[] bossBuImgs = new Image[2];
	//��̬�����
	static{
		bossBuImg = new ImageIcon("plane/bossbu.png").getImage();
		bossBuImgs[0] =  new ImageIcon("plane/bossbu0.png").getImage();
		bossBuImgs[1] =  new ImageIcon("plane/bossbu1.png").getImage();
	}
	//����
	public BossBu(int x,int y){
		this.x=x;
		this.y=y;
	}
	//�ƶ��Լ�ͼƬ���л�
	int index=0;
	public void moveAndStep(){
		int i= index++/10%bossBuImgs.length;
		bossBuImg = bossBuImgs[i];
		y+=speed;		
	}
	
}
