package application;

import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Enemy2 {
		private Image imgEnemy;
		private ImageView iviewEnemy;
		private int xPos, yPos, width, height;
		private Random rand;
		private int zombie;
		
		
		
		public Enemy2() {
			
			if(zombie==0) {
			imgEnemy=new Image("file:zombie2_walk1.png");
			}
			else {
				imgEnemy=new Image("file:zombie3_walk2.png");
			}
			
			iviewEnemy=new ImageView(imgEnemy);
			xPos=0;
			yPos=0;
			width=(int) imgEnemy.getWidth();
			height= (int) imgEnemy.getHeight();
			rand=new Random();
			zombie=rand.nextInt(2);

			
			
		}
		public ImageView getImage() {
		
			if(zombie==0) {
				imgEnemy=new Image("file:zombie2_walk1.png");
				}
				else {
					imgEnemy=new Image("file:zombie3_walk2.png");
				}
			
			iviewEnemy.setImage(imgEnemy);
			return iviewEnemy;
		
		
	}
		public void setX(int x) {
			xPos = x;
			iviewEnemy.setX(xPos);
		}
		public void setY(int y) {
			yPos = y;
			iviewEnemy.setY(yPos);
		}
		public int getX() {
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
		
		public void setLocation(int frameWidth, int frameHeight) {
			xPos=(frameWidth +rand.nextInt(frameWidth+100-frameWidth)+1);
			iviewEnemy.setX(xPos);
			yPos=(rand.nextInt(frameHeight-height));
			iviewEnemy.setY(yPos);
			
		}


		
		
	}


