package com.tedu.day04;

import javax.swing.JFrame;

public class PlaneFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 属性
	public static final int WIDTH = 512;
	public static final int HEIGHT = 768;
	
	// 构造
	public PlaneFrame() {
		setTitle("飞机大战");
		setBounds(500, 100, WIDTH, HEIGHT);// 窗口位置和大小
		PlanePanel boli = new PlanePanel();
		add(boli);

		boli.action();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

	public static void main(String[] args) {
		
		@SuppressWarnings("unused")
		PlaneFrame chuangti = new PlaneFrame();
	}

}
