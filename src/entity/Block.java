package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import main.GamePanel;

public class Block extends Entity{

	
	public boolean collided = false;
	
	public Block(GamePanel gp,int x,int y,int width,int height){
		this.gp = gp;
		rectangle = new Rectangle(x,y,width,height);
		this.getImage();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	

	

	@Override
	public void update() {
		colisionHandler(gp.ball);
		
	}
	
	public boolean checkColision(Ball ball) {
		if(rectangle.intersects(ball.rectangle))
			collided = true;
		return collided;
	}
	public void colisionHandler(Ball ball) {
			collided = true;
//			
//			if((ball.x+ball.diameter>=x && ball.x <= x+width) /*|| (ball.x+ball.diameter>x && ball.x+ball.diameter < x+width)*/) {
//				ball.speedY = -1*ball.speedY;
//				System.out.println("Block collision A!");
//			}
//			else {
//				ball.speedX = -1*ball.speedX;
//				System.out.println("Block collision B!");
//			}
	
//			if((x>=ball.x+ball.diameter || x+width<=ball.y)&&
//					((y+10>ball.y+ball.diameter&&y<ball.y+ball.diameter)||(y+height<ball.y&&y+height-10<ball.y))) {
			if(y+height/12>=ball.y+ball.diameter || y+height-height/12 <= ball.y){
				ball.speedY = -1*ball.speedY;
				System.out.println("Block collision Y!");
			}
			else {
				ball.speedX = -1*ball.speedX;
				System.out.println("Block collision X!");
			}
//			ball.speedX = -ball.speedX;
//			ball.speedY = -ball.speedY;
				
				
	}

	@Override
	public void getImage() {
		image1 = new ImageIcon("D:\\Eclipse workspace\\bouncing_ball_game\\res\\theBlock.png").getImage();
		
	}
	@Override
	public void draw(Graphics2D g2d) {
		if(!collided)
			g2d.drawImage(image1,x, y, null);
		
	}
}
