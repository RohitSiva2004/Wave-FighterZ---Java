package application;
import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Enemy {
	//create image object for bullets img
	private Image imgEnemy;
	//create imageView object for bullet img
	private ImageView iviewEnemy;
	//create variables for bullet imgs xPos, yPos, width and height
	private int xPos, yPos, width, height;
	//create random variable to randomize spawning
	private Random rand;
	
	
	
	public Enemy() {
		
		//declare image file for img object
		imgEnemy=new Image("file:zombie1_walk1.png");
		//declare imageView for img object
		iviewEnemy=new ImageView(imgEnemy);
		//declare xPos, and yPos to a default of 0
		xPos=0;
		yPos=0;
		//declare width and height to imgBullets width and height
		width=(int) imgEnemy.getWidth();
		height= (int) imgEnemy.getHeight();
		//declare new Random for random variable
		rand=new Random();

		
		
	}
	//method to getImage of bullet
	public ImageView getImage() {
	
		//declare imgBullet default image to "bulletWest.png"
		imgEnemy=new Image("file:zombie1_walk1.png");
		
		//set imageview to imgBullet
		iviewEnemy.setImage(imgEnemy);
		//return ImageView of bullet
		return iviewEnemy;
	
	
}
	//method to setX of img
	public void setX(int x) {
		//set xPos to what is passed in
		xPos = x;
		//set imageViews x to xPos
		iviewEnemy.setX(xPos);
	}
	//method to set Y of img
	public void setY(int y) {
		//set yPos to what is passed in
		yPos = y;
		//set imageViews y to yPos
		iviewEnemy.setY(yPos);
	}
	//method to getX of img
	public int getX() {
		//return xPos
		return xPos;
	}
	public int getY(){
		
		return yPos;
	}
	public double getWidth(){
		return width;
	}
	public double getHeight(){
		return height;
	}
	public void move() {
		xPos-=1;
		iviewEnemy.setX(xPos);
	}
	public void move2() {
		xPos-=1.5;
		iviewEnemy.setX(xPos);
	}
	public void setLocation(int frameWidth, int frameHeight) {
		xPos=(frameWidth +rand.nextInt(frameWidth+100-frameWidth)+1);
		iviewEnemy.setX(xPos);
		yPos=(rand.nextInt(frameHeight-height));
		iviewEnemy.setY(yPos);
		
	}


	
	
}
