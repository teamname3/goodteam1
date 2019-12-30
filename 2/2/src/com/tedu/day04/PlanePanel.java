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
	// ����
	Image bkImg;
	Image proImg;
	Image startImg;
	Image zanImg;
	Image runImg;
	Image overImg;

	int backX = 0, backY = 0;// ����ͼƬ������
	boolean yes = true, start = false, over = false;
	Hero hero = new Hero();// Ӣ�ۻ��Ķ���(����Ӣ�ۻ�)
	ArrayList<Enemy> enemys = new ArrayList<Enemy>();// �л��ļ���
	ArrayList<Heart> hearts = new ArrayList<Heart>();// buff�ļ���
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();// �ӵ��ļ���
	ArrayList<Boss> bosss = new ArrayList<Boss>();// boss�ļ���
	ArrayList<BossBu> bossbues = new ArrayList<BossBu>(); // boss�ӵ��ļ���

	// ����
	@SuppressWarnings("deprecation")
	public PlanePanel() {

		runImg = new ImageIcon("plane/background.jpg").getImage();
		proImg = new ImageIcon("plane/gg.png").getImage();
		startImg = new ImageIcon("plane/start.jpg").getImage();
		zanImg = new ImageIcon("plane/zan.jpg").getImage();
		overImg = new ImageIcon("plane/over.jpg").getImage();
		bkImg = startImg;
		// ����¼��İ�
		addMouseMotionListener(this);
		addMouseListener(this);

		// ��������
		Music.bg1.loop();

	}

	// paint����
	public void paint(@SuppressWarnings("exports") Graphics g) {
		super.paint(g);
		if (start) {// ������ʼ��
			// ������ͼƬ
			g.drawImage(bkImg, backX, backY, null);
			g.drawImage(bkImg, backX, backY - 768, null);

			g.drawImage(proImg, 30, 550, null);
			// ��Ӣ�ۻ�
			g.drawImage(hero.heroImg, hero.x, hero.y, null);

			// ���л���buff���ӵ���boss��boss�ӵ�
			enemyPaint(g);
			heartPaint(g);
			bulletPaint(g);
			bossPaint(g);
			bossBuPaint(g);

			Font f = new Font("����", Font.HANGING_BASELINE, 30);
			g.setColor(Color.ORANGE);
			g.setFont(f);
			for (int i = 0; i < bosss.size(); i++) {
				Boss bo = bosss.get(i);
				g.drawString("BOSS" + (i + 1) + "����ֵ��" + (int) bo.life / 3 + "%", 30, 40 + i * 100);
			}
			g.drawString("�ؿ���" + (hero.score / 1000 + 1), 360, 40);

			Font ff = new Font("����", Font.HANGING_BASELINE, 30);
			g.setColor(Color.pink);
			g.setFont(ff);
			g.drawString("SCORE= " + hero.score, 30, 650);
			g.drawString("LIFE:", 30, 690);
			for (int i = 1; i <= hero.life; i++) {
				Font fff = new Font("����", Font.HANGING_BASELINE, 50);
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
			// ��ʼ����
			Font s = new Font("����", Font.BOLD, 27);
			g.setColor(Color.RED);
			g.setFont(s);
			g.drawImage(startImg, 0, 0, null);
			g.drawString("������������ʼ�� ", 30, 220);
			g.drawString("�������ƶ�", 30, 400);
			g.drawString("����buff��������������", 30, 440);
			g.drawString("��boss��ײ�ή�ͻ���", 30, 480);

		}
	}

	// ����һ�����������ڿ�����Ϸ����
	public void action() {

		Timer timer = new Timer();

		timer.schedule(new TimerTask() {

			public void run() {
				if (start && !over) {
					// �ѵ�����ʼ��Ӣ�ۻ�������Ϊ0
					if (yes) {
						// ���û���Ƴ��߽���ͣ��Ϸ
						bkImg = runImg;
						backY++;// ����ͼƬ����ѭ��
						if (backY > PlaneFrame.HEIGHT) {
							backY = 0;
						}

						hero.step();// Ӣ�ۻ�ͼƬ�л����ȳ��
						enemyAction();// �����л�
						enemyMove();// �ƶ����л��л�ͼƬ
						heartAction();// ����buff
						heartMove();// �ƶ����л�buffͼƬ
						bulletAction();// �����ӵ�
						bulletMove();// �ƶ��ӵ�
						bossAction();// ����boss
						bossMove();// �ƶ����л�bossͼƬ
						bulletBuAction();// ����boss�ӵ�
						bulletBuMove();// �ƶ�boss�ӵ�

						hit();// ��ײ�¼�
						outOfBounds();// Խ�紦���л� ��buff���ӵ���
					}
				}
				repaint();// �ػ�
			}
		}, 0, 10);

	}

	// �������ʼ
	@SuppressWarnings("static-access")
	public void mouseClicked(@SuppressWarnings("exports") MouseEvent e) {
		if (e.getButton() == e.BUTTON1) {
			start = true;
		}
	}

	// ����ƶ�����Ӣ�ۻ��ƶ�
	public void mouseMoved(@SuppressWarnings("exports") MouseEvent e) {
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
	public void mouseDragged(@SuppressWarnings("exports") MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(@SuppressWarnings("exports") MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(@SuppressWarnings("exports") MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(@SuppressWarnings("exports") MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(@SuppressWarnings("exports") MouseEvent e) {
		// TODO Auto-generated method stub

	}

	// �����л�
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

	// �ƶ��л�
	public void enemyMove() {
		for (int i = 0; i < enemys.size(); i++) {
			enemys.get(i).moveAndStep();// �ƶ����л��л�ͼƬ
		}
	}

	// ���л�
	@SuppressWarnings("static-access")
	public void enemyPaint(@SuppressWarnings("exports") Graphics g) {
		for (int i = 0; i < enemys.size(); i++) {
			Enemy e = enemys.get(i);
			g.drawImage(e.enemyImg, e.x, e.y, null);
		}
	}

	// ����buff
	int heartIndex = 0;

	public void heartAction() {
		if (heartIndex++ % ((hero.fire + hero.firespeed) * 100) == 0) {
			Heart h = new Heart();
			hearts.add(h);
			// buff����
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

	// �ƶ�buff
	public void heartMove() {
		for (int i = 0; i < hearts.size(); i++) {
			Heart h = hearts.get(i);
			h.moveAndStep();// �ƶ����л�ͼƬ

		}
	}

	// ��buff
	@SuppressWarnings("static-access")
	public void heartPaint(@SuppressWarnings("exports") Graphics g) {
		for (int i = 0; i < hearts.size(); i++) {
			Heart h = hearts.get(i);
			g.drawImage(h.heartImg, h.x, h.y, null);
			if (h.y > PlaneFrame.HEIGHT)
				hearts.remove(i);
		}
	}

	// ���������䣩�ӵ�
	int bulletIndex = 0;

	public void bulletAction() {
		if (bulletIndex++ % (int) (100 / (hero.fire + hero.firespeed) + 1) == 0) {
			bullets.addAll(hero.shoot());
		}
		if (bulletIndex == Integer.MAX_VALUE)
			bulletIndex = 0;
	}

	// �ƶ��ӵ�
	public void bulletMove() {
		for (int i = 0; i < bullets.size(); i++) {
			Bullet b = bullets.get(i);
			b.move(hero.firespeed);
		}
	}

	// ���ӵ�
	@SuppressWarnings("static-access")
	public void bulletPaint(@SuppressWarnings("exports") Graphics g) {
		for (int i = 0; i < bullets.size(); i++) {
			Bullet b = bullets.get(i);
			g.drawImage(b.bulletImg, b.x, b.y, null);
		}
	}

	// ����boss
	int bossIndex = 0;

	public void bossAction() {
		if (bossIndex++ % (int) (30000 / (hero.fire + hero.firespeed)) == 0) {
			Boss bo = new Boss();
			bosss.add(bo);
		}
		if (bossIndex == Integer.MAX_VALUE)
			bossIndex = 0;
	}

	// �ƶ�boss
	public void bossMove() {
		for (int i = 0; i < bosss.size(); i++) {
			bosss.get(i).moveAndStep();// �ƶ����л�bossͼƬ
		}
	}

	// ��boss
	@SuppressWarnings("static-access")
	public void bossPaint(@SuppressWarnings("exports") Graphics g) {
		for (int i = 0; i < bosss.size(); i++) {
			Boss bo = bosss.get(i);
			g.drawImage(bo.bossImg, bo.x, bo.y, null);
		}
	}

	// ����boss���ӵ�
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

	// boss�ӵ����ƶ��Լ�ͼƬ�л�
	public void bulletBuMove() {
		for (int i = 0; i < bossbues.size(); i++) {
			BossBu bb = bossbues.get(i);
			bb.moveAndStep();
		}
	}

	// ��boss���ӵ�
	@SuppressWarnings("static-access")
	public void bossBuPaint(@SuppressWarnings("exports") Graphics g) {
		for (int i = 0; i < bossbues.size(); i++) {
			BossBu bb = bossbues.get(i);
			g.drawImage(bb.bossBuImg, bb.x, bb.y, null);
		}
	}

	// �ж���ײ
	@SuppressWarnings("static-access")
	public void hit() {
		// Ӣ�ۻ��ӵ�����л�
		int ex1, ey1, ex2, ey2;// ��ʾ�л������ϣ�����������
		int bx1, by1, bx2, by2;// ��ʾ�ӵ������ϣ�����������
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

		// Ӣ�ۻ����buff
		// �洢Ӣ�ۻ������ĵ㣬С���ĵ����Ϻ���������
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

		// �ӵ�����Boss
		// Boss�����Ͻ���������½�����
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

		// Ӣ�ۻ�ײ��boss

		for (int i = 0; i < bosss.size(); i++) {
			Boss bo = bosss.get(i);
			if (hero.x + hero.heroImg.getWidth(null) / 2 > bo.x
					&& hero.x + hero.heroImg.getWidth(null) / 2 < bo.x + bo.bossImg.getWidth(null)
					&& hero.y + hero.heroImg.getHeight(null) / 2 > bo.y
					&& hero.y + hero.heroImg.getHeight(null) / 2 < bo.y + bo.bossImg.getHeight(null)) {
				hero.firespeed = 1;
			}
		}
		
		// Ӣ�ۻ���л�����ײ
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

		// Ӣ�ۻ���boss�ӵ�����ײ
		// �����洢Ӣ�ۻ������Ͻǣ����½����꣬boss�ӵ������ĵ�����
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

	// Խ��Ĵ���
	@SuppressWarnings("static-access")
	public void outOfBounds() {
		// Ӣ�ۻ�Խ��
		// �ϱ߽�
		if (hero.y < 0) {
			hero.y = 0;
		}
		// �±߽�
		if (hero.y + hero.heroImg.getHeight(null) > PlaneFrame.HEIGHT - 35) {
			hero.y = PlaneFrame.HEIGHT - hero.heroImg.getHeight(null) - 35;
		}
		// ��߽�
		if (hero.x < -5) {
			hero.x = -5;
		}
		// �ұ߽�
		if (hero.x + hero.heroImg.getWidth(null) > PlaneFrame.WIDTH - 10) {
			hero.x = PlaneFrame.WIDTH - hero.heroImg.getWidth(null) - 10;
		}
		// �л�Խ��
		for (int i = 0; i < enemys.size(); i++) {
			Enemy e = enemys.get(i);
			if (e.y + e.enemyImg.getHeight(null) > PlaneFrame.HEIGHT)
				enemys.remove(i);
		}
		// buffԽ��
		for (int i = 0; i < hearts.size(); i++) {
			Heart e = hearts.get(i);
			if (e.y + e.heartImg.getHeight(null) > PlaneFrame.HEIGHT)
				hearts.remove(i);
		}
		// �ӵ�Խ��
		for (int i = 0; i < bullets.size(); i++) {
			Bullet b = bullets.get(i);
			if (b.y < 0)
				bullets.remove(i);
		}
		// boss�ӵ�Խ��
		for (int i = 0; i < bossbues.size(); i++) {
			BossBu bb = bossbues.get(i);
			if (bb.y > PlaneFrame.HEIGHT) {
				bossbues.remove(i);
			}
		}
	}

}
