package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import entity.Ball;
import entity.Block;
import entity.Player;

public class GamePanel extends JPanel implements Runnable, ActionListener{
	/**
	 * 
	 */
	private Clip clip;
	private FloatControl volume;
	private boolean win = false;
	public boolean lose = false;
	
	private static final long serialVersionUID = 1L;
	final int originalTileSize = 16;
	final int scale = 3;
	final int blockDimension = 60;
	final int tileSize = originalTileSize*scale;
	final int maxScreenCol = 16;
	final int maxScreenRow = 12;
	final public int screenWidth = tileSize * maxScreenCol;
	final public int screenHeight = tileSize * maxScreenRow;
	private File file;
	final int fps = 60;
	final double drawInterval = 1000000000/fps;
	Thread gameThread;
	KeyHandler keyH = new KeyHandler();
	
	int playerX,playerY  ,  ballX,ballY;
	int playerSpeed = 10;
	
	public Player player;
	public Ball ball;
	public JButton retry;
	public ArrayList<Block> blocks;
	Iterator<Block> bIterator;
	private Block b;
	
	Image backgroundImage,ballImage;
	
	
	GamePanel(){
		makeStartValues();
		retry = new JButton("Retry");
		retry.setBackground(Color.gray);
		retry.setLocation(10, 10);
		retry.setVisible(true);
		retry.setBounds(70,100,200,50);
		retry.addActionListener(this);
		retry.setFocusable(false);
		this.add(retry);
		ball = new Ball(this);
		player = new Player(this, keyH);
		blocks = new ArrayList<Block>();
		this.generateBlocks();

		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setBackground(Color.white);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		
		this.backgroundImage = new ImageIcon("D:\\Eclipse workspace\\bouncing_ball_game\\res\\background.png").getImage();

	}
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	@Override
	public void run() {
		this.performStartMusic();
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		while(gameThread != null && !win && !lose) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			timer += currentTime - lastTime;
			lastTime = currentTime;
			if(delta >= 1) {
			update();
			repaint();
			delta--;
			drawCount++;
			}
			if(timer > 1000000000) {
				System.out.println("FPS = "+ drawCount);
				drawCount = 0;
				timer = 0;
			}
		}
	
		if(win)
			this.performMusic("D:\\Eclipse workspace\\bouncing_ball_game\\res\\level-passed-143039.wav");
		else
			this.performMusic("D:\\Eclipse workspace\\bouncing_ball_game\\res\\game-over-arcade-6435.wav");
		repaint();
	}
	public void update() {
		player.update();
		ball.update();
		bIterator =blocks.iterator();
		Block previous = null;
		if(blocks.isEmpty())
			win = true;
		
		while(bIterator.hasNext()) {
			b = bIterator.next();
			if(b.checkColision(ball)) {
				if(previous == null ||(previous!= null && !previous.collided)) {
					b.colisionHandler(ball);	
				}
				bIterator.remove();
			}	
			previous = b;

		}
	}
    private void performStartMusic() { 
    	
        file = new File("D:\\Eclipse workspace\\bouncing_ball_game\\res\\gamePlayingSound.wav");
//        if(clip != null)
//			clip.stop();
        AudioInputStream audioIn;
        
            try {
				audioIn = AudioSystem.getAudioInputStream(file.toURI().toURL());
				
				Clip clip = AudioSystem.getClip();
				clip.open(audioIn);
				volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				//volume.setValue(-10f);
				clip.loop(-1);
			} catch (UnsupportedAudioFileException | IOException e) {
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}
            
        
    }
    private void performMusic(String pathName) {
    	volume.setValue(-80f);
//    	try {
//			Thread.sleep(500);
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
        file = new File(pathName);
//        if(clip != null)
//			clip.stop();
        AudioInputStream audioIn;
        
            try {
				audioIn = AudioSystem.getAudioInputStream(file.toURI().toURL());
				
				Clip clip = AudioSystem.getClip();
				clip.open(audioIn);
				volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				volume.setValue(0f);
				clip.start();
			} catch (UnsupportedAudioFileException | IOException e) {
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}
            
        
    }
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(backgroundImage, 0, 0, null);
		//g2d.setColor(Color.blue);
		
		ball.draw(g2d);
		
		bIterator =blocks.iterator();
		while(bIterator.hasNext())
			bIterator.next().draw(g2d);
		//g2d.fillRect(0, 0, 200, 300);
		player.draw(g2d);
		
		
		if(win) {
			g2d.setFont(new Font("MV Boli",Font.BOLD,70));
			char[] c = "You Win.. Good job!".toCharArray();
			g2d.drawChars(c, 0, c.length, 10, 350);
		}
		else if(lose) {
			g2d.setFont(new Font("MV Boli",Font.BOLD,70));
			char[] c = "GAME OVER!".toCharArray();
			g2d.drawChars(c, 0, c.length, 150, 350);
		}
		retry.repaint();
		
		g2d.dispose();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==retry) {
			System.out.println("retry button clicked");
			volume.setValue(-80f);
			win = false;lose = false;
			this.makeStartValues();
			this.generateBlocks();
			this.run();
		}
	}	
	private void makeStartValues() {
		ball = new Ball(this);
		player = new Player(this,keyH);
	}
	private void generateBlocks() {
		
		blocks.removeAll(blocks);
		for(int i=0;i<10;i++)
			for(int j=0;j<5;j++)
				blocks.add( new Block(this,60*i,60*j,blockDimension,blockDimension));
		}
}
