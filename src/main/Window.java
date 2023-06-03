package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class Window extends JFrame implements KeyListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	GamePanel panel;
	//double scale;
	Image backgroundImage,barImage,ballImage;
	//ImageIcon barIcon;
	JLabel bar;
	static int leftArrow = 37;
	static int rightArrow = 39;
	int position;
	int ballX,ballY,dx,dy,ballDiameter;
	
	Window(){
		this.getContentPane().setBackground(Color.black);
		//this.setOpacity(1);
		this.ballImage = new ImageIcon("D:\\Eclipse workspace\\bouncing_ball_game\\media\\Championship Soccer.png").getImage();
		this.backgroundImage = new ImageIcon("D:\\Eclipse workspace\\bouncing_ball_game\\media\\Cool Gaming Background resized.jpg").getImage();
		//this.barIcon = new ImageIcon("D:\\Eclipse workspace\\bouncing_ball_game\\media\\space bars with gimp.png");
		//this.bar = new JLabel();
		this.barImage = new ImageIcon("D:\\Eclipse workspace\\bouncing_ball_game\\media\\space bars with gimp.png").getImage();
		//this.bar.setIcon(barIcon);
		this.panel = new GamePanel();
		this.position = panel.getWidth()/2;
		//this.bar.setBounds(position,700,200,100);
		this.setTitle("bouncing ball");
		this.setDefaultCloseOperation(Window.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.addKeyListener(this);
		this.add(panel);

		//this.add(bar);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		ballX = 500;
		ballY=ballX;
		ballDiameter = 360;
		dx = 5;
		dy = 10;
		
		/*
		 * note: there is a glitch in the graphics when repainting the window
		 */
	}
	
	public void paint(Graphics g) {
		
		//scale = 1;
		Graphics2D g2d = (Graphics2D)g;
		ballX = ballX + dx;
		ballY = ballY + dy;
//		new Thread(() -> {
//		g2d.setPaint(Color.gray);
		g2d.drawImage(backgroundImage, 0, 0, null);
//		g2d.fillRect(0, 0, panel.getWidth(), panel.getHeight());
//		}).start();
//		if(ballY+ballDiameter>panel.getHeight()) {
//			dy = -dy;
//			ballY = panel.getHeight();
//		}
//		if(ballY<=0) {
//			dy = -dy;
//			ballY = ballDiameter;
//		}
//		if(ballX+ballDiameter>panel.getWidth()) {
//			dx = -dx;
//			ballX = panel.getWidth();
//			
//		}
//		if(ballX<=0) {
//			dx = -dx;
//			ballX = 0;
//		}
		g2d.drawImage(ballImage, ballX, ballY, null);
//		new Thread(() -> {
		g2d.drawImage(barImage, position, panel.getHeight()-100, null);
//		}).start();
		//g2d.scale(scale, scale);
		//g2d.setStroke(new BasicStroke(5));
		
		//g2d.fillOval(500, 360, 100,100);
		
	
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()== leftArrow) {
			position-=50;
			repaint();
//			try {
//				Thread.sleep(25);
//			} catch (InterruptedException e1) {
//				e1.printStackTrace();
//			}
		}
		else if(e.getKeyCode()== rightArrow) {
			position+=50;
			repaint();
//			try {
//				Thread.sleep(25);
//			} catch (InterruptedException e1) {
//				e1.printStackTrace();
//			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
//		System.out.println("key released " + e.getKeyChar()+"\t"+e.getKeyCode());
		
		
	}

}
