package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class Goku{
	
	private int xPos, yPos, width, height, direction;
	private Image imgGoku;
	private ImageView iviewGoku;
	private boolean dead, idle;
	public boolean attack, ssj;
	public final static int EAST=0;
	public final static int WEST=1;
	
	public Goku(){
		direction=EAST;
		ssj=false;
		dead=false;
		idle=false;
		attack=false;
		xPos=0;
		yPos=0;
		if(direction==EAST) {
			imgGoku=new Image("file:char_right.png", 100, 100, true, true);

		}
		else {
			imgGoku=new Image("file:char_left.png", 100, 100, true, true);

		}
		iviewGoku=new ImageView(imgGoku);
		iviewGoku.setX(xPos);
		iviewGoku.setY(yPos);
		width=(int) imgGoku.getWidth();
		height=(int) imgGoku.getHeight();
				
	}
	public void setX(int x) {
		xPos = x;
		iviewGoku.setX(xPos);
	}
	public void setY(int y) {
		yPos = y;
		iviewGoku.setY(yPos);
	}
	public int getX() {
		return xPos;
	}
	public int getY(){
		return yPos;
	}
	public void moveRight() {
		xPos +=2;
		direction = EAST;
		iviewGoku.setX(xPos);
		idle=false;
		
	}
	
	public void moveLeft() {
		xPos -=2;
		direction = WEST;
		iviewGoku.setX(xPos);
		idle=false;
		
	}
	
	public void moveUp() {
		yPos -=2;
		iviewGoku.setY(yPos);
		idle=false;
	}
	
	public void moveDown() {
		yPos +=2;
		iviewGoku.setY(yPos);
		idle=false;
	}
	
	public void kill() {
	        dead=true;
			imgGoku=new Image("file:char_dead.png", 100, 100, true, true);
			
			
		
	}
	public void ssj() {
        ssj=true;
        if(direction==EAST) {
        	imgGoku=new Image("file:ssj_right.png", 100, 100, true, true);
		}
		else {
			imgGoku=new Image("file:ssj_left.png", 100, 100, true, true);

		}
		
		
	
}
	public void idle() {
        idle=true;
		imgGoku=new Image("file:char_idle.png", 100, 100, true, true);
		if(ssj==true) {
			imgGoku=new Image("file:ssj_idle.png", 100, 100, true, true);
		}
		}
		
		public void attack() {
	        attack=true;
			imgGoku=new Image("file:char_attack.png", 100, 100, true, true);
			if(ssj==true) {
				imgGoku=new Image("file:ssj_attack.png", 100, 100, true, true);
			}
			
		
		
	
}
	public ImageView getImage() {
		 
		
		if (dead==false) {
		if(direction==EAST) {
			imgGoku=new Image("file:char_right.png", 100, 100, true, true);
			
		}
		
		else if(direction==WEST) {
			imgGoku=new Image("file:char_left.png", 100, 100, true, true);
			
		}
		
		
		else if(dead==true) {
			imgGoku=new Image("file:char_dead.png", 100, 100, true, true);
		}
		if(ssj==true) {
			if(direction==EAST) {
				imgGoku=new Image("file:ssj_right.png", 100, 100, true, true);
				
			}
			
			else if(direction==WEST) {
				imgGoku=new Image("file:ssj_left.png", 100, 100, true, true);
				
			}
		}
		if(idle==true) {
			imgGoku=new Image("file:char_idle.png", 100, 100, true, true);
		}
		if(idle && ssj ==true) {
			imgGoku=new Image("file:ssj_idle.png", 100, 100, true, true);
		}
		if(attack==true) {
			imgGoku=new Image("file:char_attack.png", 100, 100, true, true);
		}
		if(attack && ssj ==true) {
			imgGoku=new Image("file:ssj_attack.png", 100, 100, true, true);
		}
		}
		iviewGoku.setImage(imgGoku);
		return iviewGoku;
	}
	public int getHeight() {
		return height;
	}
	public int getWidth() {
		return width;
	}
	public void setDirection(int dir)
	{
		this.direction=dir; 
	}
	public int getDir() {
		return direction;
	}
}