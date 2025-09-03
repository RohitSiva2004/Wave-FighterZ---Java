package application;

import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DragonBall {
	private double xPos, yPos, width, height;
	private Image imgHeal;
	private ImageView ivHeal;
	private Random rand;
	
	public DragonBall()
	{
		rand = new Random();
		
		imgHeal = new Image("file:dball.png", 50, 50, true, true);
		ivHeal = new ImageView(imgHeal);
		
		xPos = 0;
		yPos = 0;
		
		width = imgHeal.getWidth();
		height = imgHeal.getHeight();
		
		ivHeal.setX(xPos);
		ivHeal.setY(yPos);
	}
	
	public void setX(double x)
	{
		xPos = x;
		ivHeal.setX(xPos);
	}
	
	public void setY(double y)
	{
		yPos = y;
		ivHeal.setY(yPos);
	}
	
	public void setLocation(int frameWidth, int frameHeight)
	{
		xPos=(rand.nextInt((int) (frameWidth-width)));
		ivHeal.setX(xPos);
		yPos=(rand.nextInt((int) (frameHeight-height)));
		ivHeal.setY(yPos);
		
		ivHeal.setX(xPos);
		ivHeal.setY(yPos);
		
		
	}
	
	public double getX()
	{
		return xPos;
	}

	public double getY()
	{
		return yPos;
	}

	public double getWidth()
	{
		return width;
	}
	
	public double getHeight()
	{
		return height;
	}
	
	public ImageView getImage()
	{
		return ivHeal;
	}
	
	


}
