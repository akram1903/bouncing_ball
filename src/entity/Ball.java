package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import main.GamePanel;


public class Ball extends Entity{
	private final int constSpeed = 7;
	public final int diameter = 20;
	public final int coolingTime = 100/*millisecs*/;
	//public long startCoolingTime = 0;
	public Ball(GamePanel gp) {
		this.gp = gp;
		this.setDefaultValues();
		this.getImage();
	}
	
	public void setDefaultValues() {
		x = 300;
		y = 200;
		speedX = (int)(constSpeed*Math.cos(Math.PI/4));
		speedY = (int)(constSpeed*Math.cos(Math.PI/4));
		rectangle = new Rectangle(x,y,diameter,diameter);
	}
	@Override
	public void getImage() {

		image1 = new ImageIcon("D:\\Eclipse workspace\\bouncing_ball_game\\res\\ball20.png").getImage();

	}
	@Override
	public void update() {
		y = y + speedY;
		x = x +speedX;
		rectangle.x = x;
		rectangle.y = y;
		//if(startCoolingTime == 0 || System.currentTimeMillis()-startCoolingTime> coolingTime) {
			barCollisionHandle(gp.player);
		//}
			
		wallCollisionHandle();
	}

	private void barCollisionHandle(Player player) {
		
		//__________minor bug exists_________
		
		if(rectangle.intersects(player.rectangle)) {
			int playerCenterX = (int)player.rectangle.getCenterX();
			int ballCenterX = (int)rectangle.getCenterX();
			double speedAbs = Math.sqrt(speedX*speedX+speedY*speedY);
			int dx = playerCenterX- ballCenterX;
			double dy = (player.y+player.height/2.0) - (y+diameter/2.0);
			if(dx == 0) {
				speedX = 0;
				speedY = (int)speedAbs;
				return;
			}
			//startCoolingTime = System.currentTimeMillis();
			y = player.y-1-diameter;
			// for exception handling
			if(speedAbs ==0)
				System.out.println("speed x = "+speedX+"\nspeedY = "+speedY);
			
			
			
			double theta = Math.atan(Math.abs(dy/dx));
			
			System.out.println("Theta = "+theta);
			speedY = (int)(-1*speedAbs*Math.sin(theta));
			System.out.println("speed Y = "+speedY);
			
			if(ballCenterX > playerCenterX)
				speedX = (int)(speedAbs*Math.cos(theta));
			else
				speedX = (int)(-1*speedAbs*Math.cos(theta));
			//_____________talfi2a 5od balak__________________
			if (speedY ==0) {
				speedAbs = constSpeed;
				speedY = (int)Math.ceil(-0.2*speedAbs);
				speedX = (int)(0.8*speedAbs);
			}
		}
			
	}	
	

	    private void wallCollisionHandle() {
		if(y+diameter>gp.screenHeight) {
			//System.out.println(gp.screenHeight);
			gp.lose = true;
		}
		if(y<=0) {
			speedY = -speedY;
			y = 0;
		}
		if(x+diameter>gp.screenWidth) {
			//System.out.println(gp.screenWidth);
			speedX = -speedX;
			x = gp.screenWidth - diameter;
			
		}
		if(x<=0) {
			speedX = -speedX;
			x = 0;
		}
		
	}
	
	
	
	@Override
	public void draw(Graphics2D g2d) {
		//g2d.scale(0.1, 0.1);
//		g2d.scale(1, 1);
		g2d.drawImage(image1,x, y, null);
//		g2d.scale(0.5, 0.5);
//		g2d.fillOval(x, y,ballDiameter, ballDiameter);
	}
}
