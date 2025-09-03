package application;

import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Blast 
{
	// Declare global variables
	private double xPos, yPos, width, height;
	public boolean ssj;
	private Image imgBlast;
	private ImageView ivBlast;
	
	// Constructor for bullet class
	public Blast()
	{
		
		
	ssj=false;
	  imgBlast = new Image("file:char_projectile.png", 75, 75, true, true);
		
		
		ivBlast = new ImageView(imgBlast);
		
		// Assign default value for x and y position
		xPos = 0;
		yPos = 0;
		
		// Set width and height
		width = imgBlast.getWidth();
		height = imgBlast.getHeight();
		
		ivBlast.setX(xPos);
		ivBlast.setY(yPos);
	}
	
	
	// Method that sets x position
	public void setX(double x)
	{
		xPos = x;
		ivBlast.setX(xPos);
	}
	
	// Method that sets y position
	public void setY(double y)
	{
		yPos = y;
		ivBlast.setY(yPos);
	}
	
	public void setPosition(double playerX, double playerY, double playerWidth, double playerHeight)
	{
		
			xPos = playerX + 75;
			yPos = playerY + 10;
			
		
			ivBlast.setX(xPos);
			ivBlast.setY(yPos);
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
	
	public void ssj() {
        ssj=true;
 imgBlast = new Image("file:ssj_projectile.png", 75, 75, true, true);
		
		
	
	}
	
	public ImageView getImage()
	{
		if(ssj==false) {
			  imgBlast = new Image("file:char_projectile.png", 75, 75, true, true);

		}
		else if(ssj=true) {
			  imgBlast = new Image("file:ssj_projectile.png", 75, 75, true, true);

		}
		ivBlast.setImage(imgBlast);
		return ivBlast;
	}
	
	public void move()
	{
		
			xPos += 5;
		
		
			ivBlast.setX(xPos);
	}
}

	