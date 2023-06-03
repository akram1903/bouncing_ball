package entity;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import main.GamePanel;

public abstract class Entity {
	public int x,y,width,height;
	public int speedX,speedY;
	public Image image1,image2;
	public String direction;
	public Rectangle rectangle;
	//public abstract void setDefaultValues();
	public abstract void draw(Graphics2D g2d);
	public abstract void update();
	public abstract void getImage();
	GamePanel gp;

}
