package com.tedu.day04;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PlanePanel extends JPanel implements MouseMotionListener, MouseListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 属性
	Image bkImg;
	Image proImg;
	Image startImg;
	Image zanImg;
	Image runImg;
	Image overImg;

	int backX = 0, backY = 0;// 背景图片的坐标
	boolean yes = true, start = false, over = false;
	Hero hero = new Hero();// 英雄机的对象(产生英雄机)
	ArrayList<Enemy> enemys = new ArrayList<Enemy>();// 敌机的集合
	ArrayList<Heart> hearts = new ArrayList<Heart>();// buff的集合
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();// 子弹的集合
	ArrayList<Boss> bosss = new ArrayList<Boss>();// boss的集合
	ArrayList<BossBu> bossbues = new ArrayList<BossBu>(); // boss子弹的集合

	// 构造
	public PlanePanel() {

		runImg = new ImageIcon("plane/background.jpg").getImage();
		proImg = new ImageIcon("plane/gg.png").getImage();
		startImg = new ImageIcon("plane/start.jpg").getImage();
		zanImg = new ImageIcon("plane/zan.jpg").getImage();
		overImg = new ImageIcon("plane/over.jpg").getImage();
		bkImg = startImg;
		// 鼠标事件的绑定
		addMouseMotionListener(this);
		addMouseListener(this);
		}

		


	// paint方法
	public void paint(Graphics g) {
		super.paint(g);
		if (start) {// 单击开始后
			// 画背景图片
			g.drawImage(bkImg, backX, backY, null);
			g.drawImage(bkImg, backX, backY - 768, null);

			g.drawImage(proImg, 30, 550, null);
			// 画英雄机
			g.drawImage(hero.heroImg, hero.x, hero.y, null);

			// 画敌机、buff、子弹、boss、boss子弹
			enemyPaint(g);
			heartPaint(g);
			bulletPaint(g);
			bossPaint(g);
			bossBuPaint(g);

			Font f = new Font("宋体", Font.HANGING_BASELINE, 30);
			g.setColor(Color.ORANGE);
			g.setFont(f);
			for (int i = 0; i < bosss.size(); i++) {
				Boss bo = bosss.get(i);
				g.drawString("BOSS" + (i + 1) + "生命值：" + (int) bo.life / 3 + "%", 30, 40 + i * 100);
			}
			g.drawString("关卡：" + (hero.score / 200 + 1), 360, 40);

			Font ff = new Font("楷体", Font.HANGING_BASELINE, 30);
			g.setColor(Color.pink);
			g.setFont(ff);
			g.drawString("SCORE= " + hero.score, 30, 650);
			g.drawString("LIFE:", 30, 690);
			for (int i = 1; i <= hero.life; i++) {
				Font fff = new Font("楷体", Font.HANGING_BASELINE, 50);
				g.setFont(fff);
				g.setColor(Color.cyan);
				g.drawString("*", 80 + i * 30, 700);
			}
			if (!yes) {
				g.drawImage(zanImg, 0, 0, null);
			}
			if (over) {
				g.drawImage(overImg, 0, 0, null);
			}
		} else {
			// 开始界面
			Font s = new Font("宋体", Font.BOLD, 27);
			g.setColor(Color.RED);
			g.setFont(s);
			g.drawImage(startImg, 0, 0, null);
			g.drawString("单击鼠标左键开始！ ", 30, 220);
			g.drawString("鼠标控制移动", 30, 400);
			g.drawString("红心buff增加生命、火力", 30, 440);
			g.drawString("与boss相撞会降低火力", 30, 480);

		}
	}

	// 定义一个方法，用于控制游戏流程
	public void action() {

		Timer timer = new Timer();

		timer.schedule(new TimerTask() {

			public void run() {
				if (start && !over) {
					// 已单击开始且英雄机生命不为0
					if (yes) {
						// 鼠标没有移出边界暂停游戏
						bkImg = runImg;
						backY++;// 背景图片自增循环
						if (backY > PlaneFrame.HEIGHT) {
							backY = 0;
						}

						hero.step();// 英雄机图片切换（扇翅膀）
						enemyAction();// 产生敌机
						enemyMove();// 移动并切换敌机图片
						heartAction();// 产生buff
						heartMove();// 移动并切换buff图片
						bulletAction();// 产生子弹
						bulletMove();// 移动子弹
						bossAction();// 产生boss
						bossMove();// 移动并切换boss图片
						bulletBuAction();// 产生boss子弹
						bulletBuMove();// 移动boss子弹

						hit();// 碰撞事件
						outOfBounds();// 越界处理（敌机 、buff、子弹）
					}
				}
				repaint();// 重绘
			}
		}, 0, 10);

	}

	// 鼠标点击开始
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			start = true;
		}
	}

	// 鼠标移动控制英雄机移动
	public void mouseMoved(MouseEvent e) {
		if (e.getX() < 0 || e.getX() > bkImg.getWidth(null) || e.getY() < 0 || e.getY() > bkImg.getHeight(null)) {
			yes = false;
		} else {
			yes = true;
			hero.x = e.getX() - hero.heroImg.getHeight(null) / 2;
			hero.y = e.getY() - hero.heroImg.getHeight(null) / 2;
		}
		if (hero.life <= 0) {
			over = true;
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	// 产生敌机
	int enemyIndex = 0;

	public void enemyAction() {
		if (enemyIndex++ % (30 + (int) (300 / (hero.score + 1))) == 0) {
			Enemy e = new Enemy();
			enemys.add(e);
		}

		if (enemyIndex == Integer.MAX_VALUE)
			enemyIndex = 0;
		// if(enemys.size()>10){
		// for(int i=10;i<enemys.size();i++)
		// enemys.remove(i);
		// }
	}

	// 移动敌机
	public void enemyMove() {
		for (int i = 0; i < enemys.size(); i++) {
			enemys.get(i).moveAndStep();// 移动并切换敌机图片
		}
	}

	// 画敌机
	public void enemyPaint(Graphics g) {
		for (int i = 0; i < enemys.size(); i++) {
			Enemy e = enemys.get(i);
			g.drawImage(Enemy.enemyImg, e.x, e.y, null);
		}
	}

	// 产生buff
	int heartIndex = 0;

	public void heartAction() {
		if (heartIndex++ % ((hero.fire + hero.firespeed) * 100) == 0) {
			Heart h = new Heart();
			hearts.add(h);
			// buff消退
			if (heartIndex / 300 % 5 == 0)
				hero.fire--;
			if (hero.fire == 0)
				hero.fire = 1;
			if (heartIndex / 300 % 7 == 0)
				hero.firespeed--;
			if (hero.firespeed == 0)
				hero.firespeed = 1;
		}
		if (heartIndex == Integer.MAX_VALUE)
			heartIndex = 0;
	}

	// 移动buff
	public void heartMove() {
		for (int i = 0; i < hearts.size(); i++) {
			Heart h = hearts.get(i);
			h.moveAndStep();// 移动并切换图片

		}
	}

	// 画buff
	public void heartPaint(Graphics g) {
		for (int i = 0; i < hearts.size(); i++) {
			Heart h = hearts.get(i);
			g.drawImage(Heart.heartImg, h.x, h.y, null);
			if (h.y > PlaneFrame.HEIGHT)
				hearts.remove(i);
		}
	}

	// 产生（发射）子弹
	int bulletIndex = 0;

	public void bulletAction() {
		if (bulletIndex++ % (int) (100 / (hero.fire + hero.firespeed) + 1) == 0) {
			bullets.addAll(hero.shoot());
		}
		if (bulletIndex == Integer.MAX_VALUE)
			bulletIndex = 0;
	}

	// 移动子弹
	public void bulletMove() {
		for (int i = 0; i < bullets.size(); i++) {
			Bullet b = bullets.get(i);
			b.move(hero.firespeed);
		}
	}

	// 画子弹
	@SuppressWarnings("static-access")
	public void bulletPaint(Graphics g) {
		for (int i = 0; i < bullets.size(); i++) {
			Bullet b = bullets.get(i);
			g.drawImage(b.bulletImg, b.x, b.y, null);
		}
	}

	// 产生boss
	int bossIndex = 0;

	public void bossAction() {
		if (bossIndex++ % (int) (30000 / (hero.fire + hero.firespeed)) == 0) {
			Boss bo = new Boss();
			bosss.add(bo);
		}
		if (bossIndex == Integer.MAX_VALUE)
			bossIndex = 0;
	}

	// 移动boss
	public void bossMove() {
		for (int i = 0; i < bosss.size(); i++) {


		}
	}

	// 画boss
	@SuppressWarnings("static-access")
	public void bossPaint(Graphics g) {
		for (int i = 0; i < bosss.size(); i++) {
			Boss bo = bosss.get(i);
			g.drawImage(bo.bossImg, bo.x, bo.y, null);
		}
	}

	// 产生boss的子弹
	int bulletBuIndex = 0;

	public void bulletBuAction() {
		if (bulletBuIndex++ % 100 == 0) {
			for (int i = 0; i < bosss.size(); i++) {
				Boss b = bosss.get(i);
				if (b.state == 1) {
					bossbues.addAll(b.shoot());
				}
			}
		}
		if (bulletBuIndex == Integer.MAX_VALUE) {
			bulletBuIndex = 0;
		}
	}

	// boss子弹的移动以及图片切换
	public void bulletBuMove() {
		for (int i = 0; i < bossbues.size(); i++) {
			BossBu bb = bossbues.get(i);
			bb.moveAndStep();
		}
	}

	// 画boss的子弹
	@SuppressWarnings("static-access")
	public void bossBuPaint(Graphics g) {
		for (int i = 0; i < bossbues.size(); i++) {
			BossBu bb = bossbues.get(i);
			g.drawImage(bb.bossBuImg, bb.x, bb.y, null);
		}
	}

	// 判断碰撞
	@SuppressWarnings("static-access")
	public void hit() {
		// 英雄机子弹消灭敌机
		int ex1, ey1, ex2, ey2;// 表示敌机的左上，右下两个点
		int bx1, by1, bx2, by2;// 表示子弹的左上，右下两个点
		for (int i = 0; i < bullets.size(); i++) {
			Bullet b = bullets.get(i);
			bx1 = b.x;
			by1 = b.y;
			bx2 = b.x + b.bulletImg.getWidth(null);
			by2 = b.y + b.bulletImg.getHeight(null);
			for (int j = 0; j < enemys.size(); j++) {
				Enemy e = enemys.get(j);
				ex1 = e.x;
				ey1 = e.y;
				ex2 = e.x + e.enemyImg.getWidth(null);
				ey2 = e.y + e.enemyImg.getHeight(null);
				if (bx1 > ex1 && bx2 < ex2 && by1 > ey1 && by2 < ey2) {
					hero.score++;
					enemys.remove(j);
					bullets.remove(i);
				}
			}
		}

		// 英雄机获得buff
		// 存储英雄机的中心点，小红心的左上和右下坐标
		int hx, hy;
		int hx1, hy1, hx2, hy2;
		for (int i = 0; i < hearts.size(); i++) {
			Heart h = hearts.get(i);
			hx = hero.x + hero.heroImg.getWidth(null) / 2;
			hy = hero.y + hero.heroImg.getHeight(null) / 2;
			hx1 = h.x;
			hy1 = h.y;
			hx2 = h.x + h.heartImg.getWidth(null);
			hy2 = h.y + h.heartImg.getHeight(null);
			if (hx > hx1 && hx < hx2 && hy > hy1 && hy < hy2) {
				hero.fire++;
				hero.life++;
				hero.firespeed++;
				hearts.remove(i);
				if (hero.fire > 3)
					hero.fire = 3;
				if (hero.firespeed > 7)
					hero.firespeed = 7;
				if (hero.life > 7) {
					hero.life = 7;
				}
				if (hero.life < 0) {
					hero.life = 0;
				}
			}
		}

		// 子弹打中Boss
		// Boss的左上角坐标和右下角坐标
		int bossX1, bossY1, bossX2, bossY2;
		for (int i = 0; i < bullets.size(); i++) {
			Bullet b = bullets.get(i);
			bx1 = b.x;
			by1 = b.y;
			bx2 = b.x + b.bulletImg.getWidth(null);
			by2 = b.y + b.bulletImg.getHeight(null);
			for (int j = 0; j < bosss.size(); j++) {
				Boss boss = bosss.get(j);
				bossX1 = boss.x;
				bossY1 = boss.y;
				bossX2 = boss.x + boss.bossImg.getWidth(null);
				bossY2 = boss.y + boss.bossImg.getHeight(null);
				if (boss.life < 0) {
					bosss.remove(j);
					hero.score += 300;
				}
				if (bx1 > bossX1 && by1 > bossY1 && bx2 < bossX2 && by2 < bossY2 && boss.state == 1) {
					bullets.remove(i);
					boss.life--;
				}
			}
		}

		// 英雄机撞到boss

		for (int i = 0; i < bosss.size(); i++) {
			Boss bo = bosss.get(i);
			if (hero.x + hero.heroImg.getWidth(null) / 2 > bo.x
					&& hero.x + hero.heroImg.getWidth(null) / 2 < bo.x + bo.bossImg.getWidth(null)
					&& hero.y + hero.heroImg.getHeight(null) / 2 > bo.y
					&& hero.y + hero.heroImg.getHeight(null) / 2 < bo.y + bo.bossImg.getHeight(null)) {
				hero.firespeed = 1;
			}
		}
		
		// 英雄机与敌机的碰撞
		for (int i = 0; i < enemys.size(); i++) {
			Enemy e = enemys.get(i);
			if (hero.x + hero.heroImg.getWidth(null) / 2 > e.x
					&& hero.x + hero.heroImg.getWidth(null) / 2 < e.x + e.enemyImg.getWidth(null)
					&& hero.y + hero.heroImg.getHeight(null) / 2 > e.y
					&& hero.y + hero.heroImg.getHeight(null) / 2 < e.y + e.enemyImg.getHeight(null)) {
				hero.life--;
				enemys.remove(i);
			}
		}

		// 英雄机和boss子弹的碰撞
		// 变量存储英雄机的左上角，右下角坐标，boss子弹的中心点坐标
		int hx_1, hy_1, hx_2, hy_2;
		int bossbuX, bossbuY;
		for (int i = 0; i < bossbues.size(); i++) {
			BossBu bb = bossbues.get(i);
			hx_1 = hero.x+10;
			hy_1 = hero.y+10;
			hx_2 = hero.x-10 + hero.heroImg.getWidth(null);
			hy_2 = hero.y-10 + hero.heroImg.getHeight(null);
			bossbuX = bb.x + bb.bossBuImg.getWidth(null) / 2;
			bossbuY = bb.y + bb.bossBuImg.getHeight(null) / 2;
			if (bossbuX > hx_1 && bossbuX < hx_2 && bossbuY > hy_1 && bossbuY < hy_2) {
				hero.life--;
				bossbues.remove(i);
			}
		}

	}

	// 越界的处理
	@SuppressWarnings("static-access")
	public void outOfBounds() {
		// 英雄机越界
		// 上边界
		if (hero.y < 0) {
			hero.y = 0;
		}
		// 下边界
		if (hero.y + hero.heroImg.getHeight(null) > PlaneFrame.HEIGHT - 35) {
			hero.y = PlaneFrame.HEIGHT - hero.heroImg.getHeight(null) - 35;
		}
		// 左边界
		if (hero.x < -5) {
			hero.x = -5;
		}
		// 右边界
		if (hero.x + hero.heroImg.getWidth(null) > PlaneFrame.WIDTH - 10) {
			hero.x = PlaneFrame.WIDTH - hero.heroImg.getWidth(null) - 10;
		}
		// 敌机越界
		for (int i = 0; i < enemys.size(); i++) {
			Enemy e = enemys.get(i);
			if (e.y + e.enemyImg.getHeight(null) > PlaneFrame.HEIGHT)
				enemys.remove(i);
		}
		// buff越界
		for (int i = 0; i < hearts.size(); i++) {
			Heart e = hearts.get(i);
			if (e.y + e.heartImg.getHeight(null) > PlaneFrame.HEIGHT)
				hearts.remove(i);
		}
		// 子弹越界
		for (int i = 0; i < bullets.size(); i++) {
			Bullet b = bullets.get(i);
			if (b.y < 0)
				bullets.remove(i);
		}
		// boss子弹越界
		for (int i = 0; i < bossbues.size(); i++) {
			BossBu bb = bossbues.get(i);
			if (bb.y > PlaneFrame.HEIGHT) {
				bossbues.remove(i);
			}
		}
	}

}
