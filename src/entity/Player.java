package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{
	KeyHandler keyH;
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		
		this.setDefaultValues();
		this.getImage();
	}
	
	public void setDefaultValues() {
		x = 300;
		y = 530;
		width = 300;
		height = 50;
		speedX = 7;
		speedY = 7;
		rectangle = new Rectangle(x,y,width,height);
	}
	@Override
	public void getImage() {

		image1 = new ImageIcon("D:\\Eclipse workspace\\bouncing_ball_game\\res\\space bars red resized.png").getImage();

	}
	@Override
	public void update() {
		if(keyH.upPressed)
			y -= speedY;
		else if(keyH.downPressed)
			y += speedY;
		else if(keyH.leftPressed)
			x -= speedX;
		else if(keyH.rightPressed)
			x += speedX;
		
		if(x+width/2 > gp.screenWidth)
			x = gp.screenWidth - width/2;
		else if(x+width/2<0)
			x = -width/2;
		if(y+height > gp.screenHeight)
			y = gp.screenHeight - height;
		else if(y<0)
			y = 0;
		
		rectangle.x = x;
		rectangle.y = y;
	}
	@Override
	public void draw(Graphics2D g2d) {
//		g2d.scale(0.5, 0.3);
		g2d.drawImage(image1,x, y, null);
	}
}
