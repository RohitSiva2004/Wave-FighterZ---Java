/*NAME: Rohit Sivakumar
 * DATE: June 17th, 2022
 * COURSE CODE: ICS4U1-01
 * PROGRAM OVERVIEW: This program has 4 different scenes that user will be taken through, first scene is the title screen scene which
 * the user can either click PLAY, CONTROLS, HIGH SCORE, or EXIT, if the user clicks PLAY they will reach the map select screen if they click CONTROLS, they will be informed about the controls
 * and how to play and the goal of the game, the high score will show the current highest score recored, and exit will exit the user out of the program, in the map select screen,
 * the user can either click one of 3 maps, or click the random map label, which will randomly select one of 3 maps, each map is different width and height, so 
 * the smaller the map the more difficult, when the user clicks a map the game will begin, the user can click space bar to shoot, and enemies will spawn from the right side of the pane,
 * when enemies collide with player or collide with left side of the pane, the users health bar will decrease, and powerups will spawn on timers throughout the game, the heart powerup will
 * heal 25% of the users Hp, and the dragon Ball powerup will make the user shoot at a faster rate and regenerate the users health completely, and it will deactive after 20 seconds after 
 * picked up, after wave 5 the easy zombie will begin moving much faster, and new zombies will spawn which do much more damage on collison, and take more hits to defeat, the easy zombie
 *takes 2 hits to kill, and the hard zombies take 4 hits to kill, when the users HP reaches 0, the user will be brought to the gameOver scene which then the user will be asked if they
 *would like to exit the program or replay the game.
 */

package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;


public class waveFighterzMain extends Application {
	//globally initialize all 4 scenes that will be accessed through each scene
	private Scene titleScreen, mapSelect, gameActive, gameOver;
	//globally initialize the gameActive root
	private Pane root;
	//globally initialize booleans for textInputdialog and fired
	boolean isNumber, isEmpty, fired;
	//globally initialize variable goku called from the Goku class
	public Goku goku;
	//globally initialize variables goRight, left, down, up for movement, and blastCD to keep track of shootcooldown and attack and ssj
	private boolean goRight, goLeft, goUp, goDown, blastCD, attack, ssj;
	//globally initialize animation timer
	private AnimationTimer animation;
	//globally initialize counters and timers
	private int startTimer, waveNum, health, enemyCount, enemyCount2, waveCounter, enemy1hit,enemy2hit, blastCount, healPUCount, dBallCount,highScore, highestValueIndex;
	//globally initialize label for wave number and game start timer
	private Label lblstartTimer, lblWaveNum;
	//globally initialize timelines for each clock
	private Timeline clock1, clock2, clock3, clock4,clock5,clock6, clock7, clock8;
	//globally initialize highscoreName to call name entered when game is begun
	private String highScoreName;
	//globally initialize graphicscontext for creation of hpbar
	private GraphicsContext gc;
	//globally initialize rectange for hpbar
	private Rectangle hpBar;
	//globally initialize canvas for hpbar
	private Canvas canvas;
	//globally initialize arraylists for all powerups enemies and the blast projectile from their classes
	public ArrayList<Enemy> enemy1;
	public ArrayList<Enemy2> enemy2;
	private ArrayList<Blast> blast;
	private ArrayList<Heal> healPU;
	private ArrayList<DragonBall> dballPU;
	//globally initialize mplayer to play music throughout scenes
	private MediaPlayer mplayer1, mplayer2 ;
	



	@Override
	//method to launch the first scene
	public void start(Stage primaryStage) {
		titleScreen(primaryStage);


	}
	//launch scene
	public static void main(String[] args) {
		launch(args);
	}
	//method for first scene
	public void titleScreen(Stage primaryStage)
	{
		//initialize background music file and player and audio sound level
		File f1 = new File("backgroundmusic.mp3");
		Media media = new Media(f1.toURI().toString());
		mplayer1 = new MediaPlayer(media);
		mplayer1.setVolume(0.5);
		mplayer1.play();
		
		File f2 = new File("gameActiveMusic.mp3");
		Media media2 = new Media(f2.toURI().toString());
		mplayer2 = new MediaPlayer(media2);
		mplayer2.setVolume(0.5);
		

		//initialize audio clips for when user shoots, is hit or transforms
		


		//initialize image and imageview for scenes background and pane for scene
		Image imgTitleBackground = new Image("file:titleScreen_background.png");
		ImageView ivTitleBackground = new ImageView(imgTitleBackground);
		Pane titleRoot = new Pane();
		
		//make scene images size and image and globalvariable titleScreen
		titleScreen = new Scene(titleRoot, imgTitleBackground.getWidth(), imgTitleBackground.getHeight());
		titleRoot.getChildren().add(ivTitleBackground);

		//initialize playbutton, setting text font size and label and text colour
		Button btnPlay = new Button();
		btnPlay.setText("PLAY");
		btnPlay.setFont(Font.font("Britannic", FontWeight.BOLD, FontPosture.ITALIC, 35));
		btnPlay.setPrefSize(270, 30);
		btnPlay.setTextFill(Color.DARKORANGE);
		btnPlay.setStyle("-fx-background-color: linear-gradient(#4568DC, #B06AB3),repeating-image-pattern(\"https://edencoding.com/resources/wp-content/uploads/2021/02/Stars_128.png\"),\r\n"
				+ "            radial-gradient(center 50% 50%, radius 50%, #FFFFFF33, #00000033)");
		btnPlay.setBorder(new Border(new BorderStroke(Color.DARKORANGE, BorderStrokeStyle.SOLID, null, null)));
		btnPlay.setAlignment(Pos.CENTER);
		//when btnPlay is clicked change scene to mapselect
		btnPlay.setOnAction(e -> mapSelect(primaryStage));

		//initialize btnControls, setting text font size and label and text colour

		Button btnCONTROLS = new Button();
		btnCONTROLS.setText("CONTROLS");
		btnCONTROLS.setFont(Font.font("Britannic", FontWeight.BOLD, FontPosture.ITALIC, 35));
		btnCONTROLS.setPrefSize(270, 30);
		btnCONTROLS.setTextFill(Color.DARKORANGE);
		btnCONTROLS.setStyle("-fx-background-color: linear-gradient(#4568DC, #B06AB3),repeating-image-pattern(\"https://edencoding.com/resources/wp-content/uploads/2021/02/Stars_128.png\"),\r\n"
				+ "            radial-gradient(center 50% 50%, radius 50%, #FFFFFF33, #00000033)");
		btnCONTROLS.setBorder(new Border(new BorderStroke(Color.DARKORANGE, BorderStrokeStyle.SOLID, null, null)));
		btnCONTROLS.setAlignment(Pos.CENTER);
		
		//when controls button is clicked alert user how to play and controls of the game
		btnCONTROLS.setOnAction(e ->{
			Alert alert = new Alert(AlertType.INFORMATION);
			//set alert text
			alert.setContentText("CONTROLS:\nW to move up.\nA to move left.\nS to move down.\n"
					+ "D to move right.\nSPACE to shoot projectiles."
					+ "\n\nOBJECTIVE:\n"
					+ "The player will spawn in the center of the left side of the pane, and"
					+ " the player's goal is to survive through as many waves as possible as they "
					+ " defeat enemies before the enemies reach the left side of the pane or collide"
					+ " with player enough for player to sustain enough damage to then die, "
					+ " the achieved wave will be sent to the leaderboard, and player must try "
					+ " to beat the current highest waves completed.");

			alert.setHeaderText(null);
			alert.setTitle("CONTROLS");
			//clear alert buttons and set titles
			alert.getButtonTypes().clear();
			alert.getButtonTypes().add(ButtonType.OK);
			// add buttons and button action
			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == ButtonType.OK)
			{
				e.consume();
			}
		});
		//initialize btnHighscore, setting text font size and label and text colour

		Button btnHighScore = new Button();
		btnHighScore.setText("HIGH SCORE");
		btnHighScore.setFont(Font.font("Britannic", FontWeight.BOLD, FontPosture.ITALIC, 35));
		btnHighScore.setPrefSize(270, 30);
		btnHighScore.setTextFill(Color.DARKORANGE);
		btnHighScore.setStyle("-fx-background-color: linear-gradient(#4568DC, #B06AB3),repeating-image-pattern(\"https://edencoding.com/resources/wp-content/uploads/2021/02/Stars_128.png\"),\r\n"
				+ "            radial-gradient(center 50% 50%, radius 50%, #FFFFFF33, #00000033)");
		btnHighScore.setBorder(new Border(new BorderStroke(Color.DARKORANGE, BorderStrokeStyle.SOLID, null, null)));
		btnHighScore.setAlignment(Pos.CENTER);
		
		//when user clicks highscore button call getHighscore method and alert to user highest waves completed holder if exists
		btnHighScore.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent event) 
			{
				try 
				{
//call getHighscore method to alert if highscore exists
					getHighScore();
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("WAVES COMPLETED LEADERBOARD");
					alert.setContentText("The highest waves completed to date is held by " + highScoreName + " with a wave completion " + highScore + "!");
					alert.setHeaderText(null);
					alert.setResizable(false);
					alert.showAndWait();
				} 
				catch (Exception e) 
				{
					//if high score has not been recorded yet

					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("WAVES COMPLETED LEADERBOARD");
					alert.setContentText("There has not been any scores recorded yet!");
					alert.setHeaderText(null);
					alert.setResizable(false);
					alert.showAndWait();
				}
			}
		});

		//initialize btnHighscore, setting text font size and label and text colour

		Button btnEXIT = new Button();
		btnEXIT.setText("EXIT");
		btnEXIT.setFont(Font.font("Britannic", FontWeight.BOLD, FontPosture.ITALIC, 35));
		btnEXIT.setPrefSize(270, 30);
		btnEXIT.setTextFill(Color.DARKORANGE);
		btnEXIT.setStyle("-fx-background-color: linear-gradient(#4568DC, #B06AB3),repeating-image-pattern(\"https://edencoding.com/resources/wp-content/uploads/2021/02/Stars_128.png\"),\r\n"
				+ "            radial-gradient(center 50% 50%, radius 50%, #FFFFFF33, #00000033)");
		btnEXIT.setBorder(new Border(new BorderStroke(Color.DARKORANGE, BorderStrokeStyle.SOLID, null, null)));
		btnEXIT.setAlignment(Pos.CENTER);
		//if exit button is clicked user will be alerted whether they want to quit or not
		btnEXIT.setOnAction(e ->{
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setContentText("Are you sure you want to exit?");
			alert.setHeaderText(null);
			alert.setTitle("EXIT");

			Optional<ButtonType> result = alert.showAndWait();

			//if user clicks cancel they are brought back to the main screen 
			if (result.get() == ButtonType.CANCEL)
			{
				e.consume();
			}
			//if user clicks yes user will exit program
			else
			{
				System.exit(0);
			}
		});

//if user hovers over play button change colours to brighter colours
		btnPlay.setOnMouseEntered(e->{

			btnPlay.setTextFill(Color.WHITE);
			btnPlay.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, null, null)));

		});
		//if user hovers over controls button change colours to brighter colours
		btnCONTROLS.setOnMouseEntered(e->{

			btnCONTROLS.setTextFill(Color.WHITE);

			btnCONTROLS.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, null, null)));
		});
		//if user hovers over highscore button change colours to brighter colours
		btnHighScore.setOnMouseEntered(e->{

			btnHighScore.setTextFill(Color.WHITE);

			btnHighScore.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, null, null)));

		});
		//if user hovers over exit button change colours to brighter colours
		btnEXIT.setOnMouseEntered(e->{

			btnEXIT.setTextFill(Color.WHITE);

			btnEXIT.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, null, null)));


		});
		//if user hovers over play button change colours to default
		btnPlay.setOnMouseExited(e->{

			btnPlay.setTextFill(Color.DARKORANGE);

			btnPlay.setBorder(new Border(new BorderStroke(Color.DARKORANGE, BorderStrokeStyle.SOLID, null, null)));

		});
		//if user hovers over controls button change colours to default
		btnCONTROLS.setOnMouseExited(e->{

			btnCONTROLS.setTextFill(Color.DARKORANGE);

			btnCONTROLS.setBorder(new Border(new BorderStroke(Color.DARKORANGE, BorderStrokeStyle.SOLID, null, null)));


		});
		//if user hovers over highscore button change colours to default
		btnHighScore.setOnMouseExited(e->{

			btnHighScore.setTextFill(Color.DARKORANGE);

			btnHighScore.setBorder(new Border(new BorderStroke(Color.DARKORANGE, BorderStrokeStyle.SOLID, null, null)));


		});
		//if user hovers over exit button change colours to default
		btnEXIT.setOnMouseExited(e->{

			btnEXIT.setTextFill(Color.DARKORANGE);

			btnEXIT.setBorder(new Border(new BorderStroke(Color.DARKORANGE, BorderStrokeStyle.SOLID, null, null)));


		});


		//if user ever clicks on program X button
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>()
		{
			public void handle(WindowEvent e) 
			{
				//alert to user whether they would like to quit or not
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setContentText("Are you sure you would like to exit?");
				alert.setHeaderText(null);
				alert.setTitle("EXIT");
				Optional<ButtonType> result = alert.showAndWait();

				//if user clicks yes exit program, no return back to program
				if (result.get() == ButtonType.CANCEL)
				{
					e.consume();
				}
				else
				{
					System.exit(0);
				}
			}
		});

		//add all buttons to a VBOX layout, then add to the pane
		VBox vbox1 = new VBox();
		VBox.setMargin(btnPlay, new Insets(180,0,20,70));
		VBox.setMargin(btnCONTROLS,new Insets(0,0,20,70) );
		VBox.setMargin(btnHighScore,new Insets(0,0,20,70) );
		VBox.setMargin(btnEXIT,new Insets(0,0,20,70) );
		vbox1.getChildren().addAll(btnPlay,btnCONTROLS,btnHighScore,btnEXIT );

		titleRoot.getChildren().add(vbox1);

		//show stage , center stage, add title and setScene to global variable
		primaryStage.centerOnScreen();
		primaryStage.setTitle("Wave FighterZ");
		primaryStage.setScene(titleScreen);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	//method for mapSelect scene
	public void mapSelect(Stage primaryStage) {
		//initialize image and imageview for scenes background and pane for scene
		Image imgmapSelectbackground = new Image("file:mapSelect.png");
		ImageView ivmapSelectBackground = new ImageView(imgmapSelectbackground);
		Pane mapRoot = new Pane();
		//initialize random variable
		Random rnd = new Random();

		//initialize a 2D array for all 3 maps to randomly be selected
		Image[] map = 
			{	new Image("file:map1.jpg"), new Image("file:map2.jpg"), 
					new Image("file:map3.jpg")};

		//set scene to size and image of mapselect image and add to pane
		mapSelect = new Scene(mapRoot, imgmapSelectbackground.getWidth(), imgmapSelectbackground.getHeight());
		mapRoot.getChildren().add(ivmapSelectBackground);

		//initialize image and view for map1 preview and hover
		Image imgMap1preview=new Image("file:map1_preview.jpg", 150, 150, true, true);
		ImageView ivpvMap1 = new ImageView(imgMap1preview);

	
		Image imgMap1previewhover=new Image("file:map1_previewhover.jpg", 150, 150, true, true);
		ImageView ivpvhoverMap1 = new ImageView(imgMap1previewhover);


		//initialize label for map1, and set size and graphic to map1 image, and add border
		Label map1 = new Label();
		map1.setPrefSize(150,50);
		map1.setGraphic(ivpvMap1);
		map1.setBorder(new Border(new BorderStroke(Color.DARKORANGE,
				BorderStrokeStyle.SOLID,null, new
				BorderWidths(5))));
		//if map1 is clicked begin game and map1 will the image selected from 2D array
		map1.setOnMouseClicked(e->{
			gameActive(primaryStage, map[0]);

		});
		
       //if user hovers over map1 label change the label to a brighter map1 image
		map1.setOnMouseEntered(e->{
			map1.setGraphic(ivpvhoverMap1);
		});
		//if user stops hovering change label back to default image
		map1.setOnMouseExited(e->{
			map1.setGraphic(ivpvMap1);
		});
		//set x and y of label
		map1.setLayoutX(15);
		map1.setLayoutY(140);

		//initialize image and view for map2 preview and hover

		Image imgMap2preview=new Image("file:map2_preview.jpg", 150, 150, true, true);
		ImageView ivpvMap2 = new ImageView(imgMap2preview);
		
		//initialize image and view for map2 preview and hover


		Image imgMap2previewhover=new Image("file:map2_previewhover.jpg", 150, 150, true, true);
		ImageView ivpvhoverMap2 = new ImageView(imgMap2previewhover);

		//initialize label for map2, and set size and graphic to map2 image, and add border
		Label map2 = new Label();
		map2.setPrefSize(150,50);
		map2.setGraphic(ivpvMap2);
		map2.setBorder(new Border(new BorderStroke(Color.DARKORANGE,
				BorderStrokeStyle.SOLID,null, new
				BorderWidths(5))));
		//if map2 is clicked begin game and map2 will the image selected from 2D array

		map2.setOnMouseClicked(e->{
			gameActive(primaryStage, map[1]);

		});
	       //if user hovers over map2 label change the label to a brighter map2 image

		map2.setOnMouseEntered(e->{
			map2.setGraphic(ivpvhoverMap2);
		});
		//if user stops hovering change label back to default image

		map2.setOnMouseExited(e->{
			map2.setGraphic(ivpvMap2);
		});
		//set x and y of map2 label
		map2.setLayoutX(200);
		map2.setLayoutY(140);

		//initialize image and view for map3 preview and hover

		Image imgMap3preview=new Image("file:map3_preview.jpg", 150, 150, true, true);
		ImageView ivpvMap3 = new ImageView(imgMap3preview);

		Image imgMap3previewhover=new Image("file:map3_previewhover.jpg", 150, 150, true, true);
		ImageView ivpvhoverMap3 = new ImageView(imgMap3previewhover);

		//initialize label for map3, and set size and graphic to map3 image, and add border

		Label map3 = new Label();
		map3.setPrefSize(150,50);
		map3.setGraphic(ivpvMap3);
		map3.setBorder(new Border(new BorderStroke(Color.DARKORANGE,
				BorderStrokeStyle.SOLID,null, new
				BorderWidths(5))));
		//if map1 is clicked begin game and map3 will the image selected from 2D array

		map3.setOnMouseClicked(e->{
			gameActive(primaryStage, map[2]);

		});
	       //if user hovers over map3 label change the label to a brighter map3 image

		map3.setOnMouseEntered(e->{
			map3.setGraphic(ivpvhoverMap3);
		});
		//if user stops hovering change label back to default image

		map3.setOnMouseExited(e->{
			map3.setGraphic(ivpvMap3);
		});
		//set x and y of map3 label

		map3.setLayoutX(15);
		map3.setLayoutY(320);
		
		//initialize image and view for random map preview and hover


		Image imgMap4=new Image("file:rndmap.png", 150, 150, true, true);
		ImageView ivMap4 = new ImageView(imgMap4);

		Image imgMap4previewhover=new Image("file:rndmap_previewhover.png", 150, 150, true, true);
		ImageView ivpvhoverMap4 = new ImageView(imgMap4previewhover);

		//initialize label for random map, and set size and graphic to random map image, and add border

		Label map4 = new Label();
		map4.setPrefSize(100,50);
		map4.setGraphic(ivMap4);
		map4.setBorder(new Border(new BorderStroke(Color.DARKORANGE,
				BorderStrokeStyle.SOLID,null, new
				BorderWidths(5))));
		//if random map is clicked begin game and map will be randomly selected from 2D array

		map4.setOnMouseClicked(e->{
			gameActive(primaryStage, map[rnd.nextInt(3)]);

		});
	       //if user hovers over map4 label change the label to a brighter map3 image

		map4.setOnMouseEntered(e->{
			map4.setGraphic(ivpvhoverMap4);
		});
		//if user stops hovering change label back to default image

		map4.setOnMouseExited(e->{
			map4.setGraphic(ivMap4);
		});
		//set x and y of map3 label

		map4.setLayoutX(200);
		map4.setLayoutY(320);


        //add all map labels to pane
		mapRoot.getChildren().addAll(map1, map2, map3, map4);


		//center scene, add title, set scene to global variable and show scene
		primaryStage.centerOnScreen();
		primaryStage.setTitle("Map Select");
		primaryStage.setScene(mapSelect);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	//method for gameActive scene
	public void gameActive (Stage primaryStage, Image map)
	{

mplayer1.stop();
mplayer2.play();

//initialize textInput dialog to be output to user when scene is changed to gameActive
		TextInputDialog highscoreName = new TextInputDialog();
		//initialize dball image and view
		Image dball = new Image("file:dball.png", 100, 100, true, true);
		ImageView ivdball = new ImageView(dball);
		//set title of input dialog, and content text, and image to dball
		highscoreName.setTitle("NAME ENTRY");
		highscoreName.setContentText("Please enter your name:   ");
		highscoreName.setHeaderText(null);
		highscoreName.setGraphic(ivdball);
		//initialize fonts for Wave and WaveNum
		Font f = Font.loadFont("file:GreatLakesShadowNF.ttf", 300);
		Font f2 = Font.loadFont("file:GreatLakesShadowNF.ttf", 50);

		//initialize boolean variables for if user enters number or nothing to true, and initialize arraylists for each of the classes used ingame
		isNumber = true;
		isEmpty = true;
		enemy1=new ArrayList<Enemy>();
		enemy2=new ArrayList<Enemy2>();
		blast=new ArrayList<Blast>();
		healPU = new ArrayList<Heal>();
		dballPU = new ArrayList<DragonBall>();
		
		AudioClip[] clips = new AudioClip[] {new
				AudioClip("file:blast.mp3"),
				new AudioClip("file:hit.mp3"), 
				new AudioClip("file:ssj.mp3")};
		
		clips[0].setVolume(0.2);

		//initialize blastCD fired attack and ssj to false
		blastCD=false;
		fired=false;
		attack=false;
		ssj=false;

        //initialize new alert for if what user enters is blank or a  number
		Alert alert = new Alert(AlertType.ERROR);
		alert.setHeaderText(null);
		alert.setTitle("ERROR!");

		//show alert to user waiting for input
		Optional<String> result = highscoreName.showAndWait();

		//if user enters input
		if(result.isPresent()) {
			//loop to check each character of string user entered to see if there is a digit or not
			for (int i = 0; i < highscoreName.getResult().length(); i++)
			{
				if (Character.isDigit(highscoreName.getResult().charAt(i)))
				{
					//if user entered a number break, and re ask for input

					alert.setContentText("Do not enter digits!");
					alert.showAndWait();
					//set isNumber to true
					isNumber = true;
					break;
				}
				else
				{
					//set number to false and check for blank
					isNumber = false;
				}
			}

//if user enters nothing into the dialog
			if (highscoreName.getResult().equals(""))
			{
				//empty is true and alert to the user and re ask for input
				alert.setContentText("Enter a name!");
				alert.showAndWait();
				isEmpty = true;
			}
			else
			{
				//if empty and number are false then we can begin game
				isEmpty = false;
			}
		}

		//if user did not enter a number and input is not blank
		if (!isNumber && !isEmpty)
		{
			//if valid input exists
			if (result.isPresent()) {
				//set imageView to the map selected and initialize pane and add the map to the pane and scene
				ImageView ivBack = new ImageView(map);
				root = new Pane();
				root.getChildren().add(ivBack);
				gameActive = new Scene(root, map.getWidth(), map.getHeight());


//center scene on screen, set X of the scene, set title, set scene, and show scene to user
				primaryStage.centerOnScreen();
				primaryStage.setX(200);
				primaryStage.setTitle("Wave FighterZ");
				primaryStage.setScene(gameActive);
				primaryStage.setResizable(false);
				primaryStage.show();
			}
			
//initialize goku variable from the goku Class
			goku = new Goku();
			//set gokus Y to the center of the left side of the pane
			goku.setY((int) (map.getHeight()/2 - goku.getHeight()));

//add gokus image to the pane
			root.getChildren().add(goku.getImage());


			//begin animation timer 
			animation = new AnimationTimer() {
				public void handle(long val) {
					
//if the user clicks RIGHT, LEFT, UP, DOWN, OR SPACE, set variables to true
					gameActive.setOnKeyPressed(new EventHandler<KeyEvent>() {
						public void handle (KeyEvent e)
						{
							//if right, left, up or down arrow key is pressed, set boolean variables
							// to true
							if (e.getCode() == KeyCode.RIGHT)
							{
								//

								goRight = true;
							}
							else if (e.getCode() == KeyCode.LEFT)
							{
								goLeft = true;
							}
							else if (e.getCode() == KeyCode.UP)
							{
								goUp = true;
							}
							else if (e.getCode() == KeyCode.DOWN)
							{
								goDown = true;
							}

							//if user clicks space
							else if(e.getCode() == KeyCode.SPACE) {
								//change gokus image to attack image and check if blastCD is false, and if it is not add new blast index, image to the pane, and set the position
								// of the blast projectile in front of goku and set blastCD to true, to call to clock to activate shoot cooldown
								goku.attack=true;
								if (blastCD == false)
								{
									blast.add(new Blast());
									blast.get(blast.size()-1).setPosition(goku.getX(), goku.getY(), goku.getWidth(), goku.getHeight());
									root.getChildren().add(blast.get(blast.size()-1).getImage());
									blastCD = true;

									clips[0].play();
									clock3.play();

								}



							}

						}
					});

//if user releases keys set variables to false for smooth movement
					gameActive.setOnKeyReleased(new EventHandler<KeyEvent>() {
						public void handle (KeyEvent e)
						{

							if (e.getCode() == KeyCode.RIGHT)
							{
								goRight = false;

							}
							else if (e.getCode() == KeyCode.LEFT)
							{
								goLeft = false;

							}
							else if (e.getCode() == KeyCode.UP)
							{
								goUp = false;

							}
							else if (e.getCode() == KeyCode.DOWN)
							{
								goDown = false;

							}


						}
					});



                  //loop for enemy1 array list
					for (int i = 0; i < enemy1.size(); i++)
					{
						//if wave not 5 yet, than enemy1 will move at normal pace
						if(waveNum<=4) {
							enemy1.get(i).move();
						}
						//if wave is 5 and above enemy1 will begin moving fast
						if(waveNum>=5) {
							enemy1.get(i).move2();
						}
						//if goku collides with enemy 1 play hit sound, and update hpBars width and remove enemy1 from the pane
						if(goku.getImage().getBoundsInParent().intersects(enemy1.get(i).getImage().getBoundsInParent()))
						{
							clips[1].play();
							hpBar.setWidth(hpBar.getWidth()-10);
							root.getChildren().remove(enemy1.get(i).getImage());
							enemy1.remove(i);
							enemyCount--;
							//if user has collided to many times and Hp reaches 0, change scene to gameOver scene
							if (hpBar.getWidth()<=0) {
								animation.stop();
								clock1.stop();
								clock2.stop();
								clock3.stop();
								clock4.stop();
								clock5.stop();
								clock6.stop();
								clock7.stop();
								gameOver(primaryStage,highscoreName.getResult(), waveNum );
							}
						}
						//get image of enemy1
						enemy1.get(i).getImage();

						//if enemy1 hits the left side of the pane
						if (enemy1.get(i).getX() + enemy1.get(i).getWidth() <= 0)
						{
							//remove enemy1 from the pane and arraylist and update width of health and play hit sound
							root.getChildren().remove(enemy1.get(i).getImage());
							enemy1.remove(i);
							enemyCount--;
							hpBar.setWidth(hpBar.getWidth()-10);
							clips[1].play();
						}


					}
	                  //loop for enemy1 array list

					for (int i = 0; i < enemy2.size(); i++)
					{
                     //call to class to move enemy2
						enemy2.get(i).move();

						//if enemy2 collides with goku, then play hit sound and update health bar and remove enemy2 from pane and arraylist
						if(goku.getImage().getBoundsInParent().intersects(enemy2.get(i).getImage().getBoundsInParent()))
						{
							clips[1].play();
							hpBar.setWidth(hpBar.getWidth()-30);
							root.getChildren().remove(enemy2.get(i).getImage());
							enemy2.remove(i);
							enemyCount2--;
							//if hp reaches 0, change scene to gameOver scene
							if (hpBar.getWidth()<=0) {
								animation.stop();
								clock1.stop();
								clock2.stop();
								clock3.stop();
								clock4.stop();
								clock5.stop();
								clock6.stop();
								clock7.stop();
								gameOver(primaryStage,highscoreName.getResult(), waveNum );
							}
						}
						//get image of enemy2
						enemy2.get(i).getImage();

						//if enemy2 reaches the left side of the pane, update health bar of user and remove from pane and list and play sound
						if (enemy2.get(i).getX() + enemy2.get(i).getWidth() <= 0)
						{
							root.getChildren().remove(enemy2.get(i).getImage());
							enemy2.remove(i);
							enemyCount2--;
							hpBar.setWidth(hpBar.getWidth()-30);
							clips[1].play();
						}



					}



//if variable is true call to class to moveUp and do not let user to go past Y=0;
					if(goUp==true) {
						goku.moveUp();
						if (goku.getY() < 0)
						{
							goku.setY(0);
						}
					}
					//if variable is true call to class to moveDown and do not let user to go past scenes height;

					if(goDown==true) {
						goku.moveDown();
						if (goku.getY() + goku.getHeight() > gameActive.getHeight())
						{
							goku.setY((int) (gameActive.getHeight() -goku.getHeight()));
						}
					}
					//if variable is true call to class to moveRight and do not let user to go past scenes width;

					if(goRight==true) {
						goku.moveRight();
						if (goku.getX() + goku.getWidth() > gameActive.getWidth())
						{
							goku.setDirection(Goku.EAST);
							goku.setX((int) (gameActive.getWidth() - goku.getWidth()));
						}
					}
					//if variable is true call to class to moveLeft and do not let user to go past X=0;
					if(goLeft==true) {
						goku.moveLeft();
						if (goku.getX() < 0)
						{
							goku.setDirection(Goku.WEST);
							goku.setX(0);
						}

					}
					//if all variables are false set goku to idle state
					if(goUp==false && goDown==false && goRight==false && goLeft==false && attack==false) {
						goku.idle();
					}

					//get image of goku
					goku.getImage();
					
					//loop for projectiles
					for (int i = 0; i < blast.size(); i++)
					{
						//call blast and move 
						blast.get(i).move();
						//update image of moving blast
						blast.get(i).getImage();
						//if blast reaches the right side of the pane remove from pane and list index
						if (blast.get(i).getX() > map.getWidth())
						{
							root.getChildren().remove(blast.get(i).getImage());
							blast.remove(i);
						}


					}
					//loop for enemy1 and blast collison
					for (int i = 0; i < enemy1.size(); i++)
					{
						for (int j = 0; j < blast.size(); j++)
						{
							//if blast hits enemy1 update enemy1hit counter and remove blast image and if counter reaches 2 remove enemy1 image 
							if(blast.get(j).getImage().getBoundsInParent().intersects(enemy1.get(i).getImage().getBoundsInParent()))
							{

								enemy1hit++;
								root.getChildren().remove(blast.get(j).getImage());
								blast.remove(j);


								if(enemy1hit==2) {

									enemyCount--;
									root.getChildren().remove(enemy1.get(i).getImage());
									enemy1.remove(i);
									enemy1hit=0;		}

							}
						}
					}

					//loop for enemy1 and blast collison

					for (int i = 0; i < enemy2.size(); i++)
					{
						for (int j = 0; j < blast.size(); j++)
						{
							//if blast hits enemy2 update enemy2hit counter and remove blast image and if counter reaches 4 remove enemy2 image 

							if(blast.get(j).getImage().getBoundsInParent().intersects(enemy2.get(i).getImage().getBoundsInParent()))
							{

								enemy2hit++;
								root.getChildren().remove(blast.get(j).getImage());
								blast.remove(j);


								if(enemy2hit==4) {
									enemyCount2--;
									root.getChildren().remove(enemy2.get(i).getImage());
									enemy2.remove(i);
									enemy2hit=0;		}

							}
						}
					}
					//loop for heal powerup
					for(int i = 0; i < healPU.size(); i++)
					{

						//if goku collides with heal powerup remove from pane and update health bar
						if(goku.getImage().getBoundsInParent().intersects(healPU.get(i).getImage().getBoundsInParent())) {
							root.getChildren().remove(healPU.get(i).getImage());
							healPU.remove(i);
							healPUCount--;
							if(hpBar.getWidth()<=150) {
								hpBar.setWidth(hpBar.getWidth()+50);
							}
							else {
								hpBar.setWidth(200);
							}
						}

					}
					//loop for dball powerup
					for(int i = 0; i < dballPU.size(); i++)
					{
						//if goku collides with dball powerup remove from pane and transform goku to ssj and set ssj variables to true and call to clocks, and play ssj sound effect, also update
						//health to max HP

						if(goku.getImage().getBoundsInParent().intersects(dballPU.get(i).getImage().getBoundsInParent())) {
							root.getChildren().remove(dballPU.get(i).getImage());
							dballPU.remove(i);
							dBallCount--;
							hpBar.setWidth(200);
							goku.ssj=true;
							ssj=true;
							clock7.play();
							clock8.play();
							clips[2].play();


						}

					}







				}};

				
                //variable for game start counter
				startTimer=3;
				//label for game start timer 3, 2, 1 GO!
				lblstartTimer = new Label();
				//set to starttimers value
				lblstartTimer.setText(Integer.toString(startTimer));
				lblstartTimer.setPrefSize(map.getWidth(), map.getHeight());
				lblstartTimer.setFont(f);
				lblstartTimer.setAlignment(Pos.CENTER);
				lblstartTimer.setTextFill(Color.DARKORANGE);

				//variable to keep track of wave number
				waveNum=1;
				lblWaveNum = new Label();
				//set label to wave + waveNumber when game begin timer ends
				lblWaveNum.setText("WAVE: " + waveNum);
				lblWaveNum.setPrefSize(map.getWidth(), 0);
				lblWaveNum.setFont(f2);
				lblWaveNum.setAlignment(Pos.CENTER);
				lblWaveNum.setTextFill(Color.YELLOW);

				//set health to 200 for hpBar track
				health=200;

				//create red rectangle to go inside border of HPBAR
				hpBar = new Rectangle(health, 35, Color.RED);
				hpBar.setLayoutX(gameActive.getWidth()/2-350);
				hpBar.setLayoutY(25);

				//create border for red rectangle to go inside
				canvas = new Canvas(gameActive.getWidth(), gameActive.getHeight());
				gc = canvas.getGraphicsContext2D();
				gc.setStroke(Color.PURPLE);
				gc.setLineWidth(3);
				gc.strokeRect(gameActive.getWidth()/2-350, 25, 200, 35);

				//initialize image and view for goku icon to set beside HP bar
				Image imgGokuIcon=new Image("file:char_icon.png", 75, 75, true, true);
				ImageView ivGokuIcon=new ImageView(imgGokuIcon);

				//set icon beside health bar
				ivGokuIcon.setX(gameActive.getWidth()/2-405);
				ivGokuIcon.setY(4);

				//clock to start game, every 1000 millis timer will decrease and when timer reaches 0 animation timer will begin and other labels will be added removing starttimer label
				KeyFrame kfClock1 = new KeyFrame(Duration.millis(1000), new
						EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						startTimer--;
						goku.idle();
						if (startTimer == 0) {
							//begin game and change text to go
							lblstartTimer.setText("GO!");
							animation.start();
						}
						else if(startTimer==-1) {
							clock1.stop();
							//remove labels and add wave Labels and health bar and icon
							root.getChildren().remove(lblstartTimer);
							root.getChildren().add(lblWaveNum);
							root.getChildren().addAll(hpBar, canvas, ivGokuIcon);

						}
						else {
							//update startTimer value to label
							lblstartTimer.setText(Integer.toString(startTimer));
						}

					}
				});
				//initialize new timline and play clock1
				clock1 = new Timeline(kfClock1);
				clock1.setCycleCount(Timeline.INDEFINITE);
				clock1.play();


				//initialize counting and hit tracking variables
				enemyCount=-1;
				enemyCount2=-1;
				waveCounter=0;
				enemy1hit=0;
				blastCount=-1;
				healPUCount=-1;
				dBallCount=-1;


				//clock to add enemies to pane and keep track of waves, after 10 zombie spawns a new wave begins, and wave label will be updated, 
				KeyFrame kfClock2 = new KeyFrame(Duration.seconds(1.5), new
						EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						enemyCount++;
						waveCounter++;
						if(waveCounter==10) {
							waveNum++;
							lblWaveNum.setText("WAVE: " + waveNum);
							waveCounter=0;
						}
						if(waveNum==5) {
							//if wave reaches 5 play clock5
							clock4.play();
						}

						enemy1.add(enemyCount, new Enemy());
						enemy1.get(enemyCount).setLocation((int) map.getWidth(),
								(int) map.getHeight());
						root.getChildren().add(enemy1.get(enemyCount).getImage());
					}
				});
				clock2 = new Timeline(kfClock2);
				clock2.setCycleCount(Timeline.INDEFINITE);
				//play clock2
				clock2.play();



				//add starttimer to pane
				root.getChildren().addAll(lblstartTimer);



				//clock to keep track of when blasts are shoot and cooldowns and play blast audio
				KeyFrame kfblastCD = new KeyFrame(Duration.millis(400), new
						EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {

						
						goku.attack=false;
						blastCD=false;


					}
				});
				clock3 = new Timeline(kfblastCD);
				clock3.setCycleCount(Timeline.INDEFINITE);

				//clock to add enemy2 to the pane, with its own spawn cooldown

				KeyFrame kfclock4 = new KeyFrame(Duration.seconds(3), new
						EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {

						enemyCount2++;
						enemy2.add(enemyCount2, new Enemy2());
						enemy2.get(enemyCount2).setLocation((int) map.getWidth(),
								(int) map.getHeight());
						root.getChildren().add(enemy2.get(enemyCount2).getImage());

					}
				});
				clock4 = new Timeline(kfclock4);
				clock4.setCycleCount(Timeline.INDEFINITE);

				//clock to add heal powerups to the pane every 25 seconds, when game is begun setting the powerup randomly upon the pane
				KeyFrame kfclock5 = new KeyFrame(Duration.seconds(25), new
						EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						healPUCount++;
						healPU.add(healPUCount, new Heal());
						healPU.get(healPUCount).setLocation((int)map.getWidth(), (int)map.getHeight());
						root.getChildren().add(healPU.get(healPUCount).getImage());	
					}
				});
				clock5 = new Timeline(kfclock5);
				clock5.setCycleCount(Timeline.INDEFINITE);
				//play clock5
				clock5.play();

				//clock to add dball powerups to the pane every 40 seconds, when game is begun setting the powerup randomly upon the pane

				KeyFrame kfclock6 = new KeyFrame(Duration.seconds(40), new
						EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						dBallCount++;
						dballPU.add(dBallCount, new DragonBall());
						dballPU.get(dBallCount).setLocation((int)map.getWidth(), (int)map.getHeight());
						root.getChildren().add(dballPU.get(dBallCount).getImage());	
					}
				});
				clock6 = new Timeline(kfclock6);
				clock6.setCycleCount(Timeline.INDEFINITE);
				//play clock 6
				clock6.play();

				//clock to keep track of ssj, when ssj is activated, keep ssj activated for 20 seconds then revert to normal and stop the ssj shoot timer clock
				KeyFrame kfclock7 = new KeyFrame(Duration.seconds(20), new
						EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						if(ssj=true) {
							goku.ssj=false;
							ssj=false;
							clock8.stop();
						}
					}
				});
				clock7 = new Timeline(kfclock7);
				clock7.setCycleCount(Timeline.INDEFINITE);

				//clock for when user collides with dball they will be able to attack 2x faster against enemies
				KeyFrame kfssjblastCD = new KeyFrame(Duration.millis(200), new
						EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {

						goku.attack=false;
						blastCD=false;


					}
				});
				clock8 = new Timeline(kfssjblastCD);
				clock8.setCycleCount(Timeline.INDEFINITE);

		}}








	//method for gameOver scene
	public void gameOver(Stage primaryStage, String name, int score)
	{

		//stop music when game is over
		mplayer2.stop();
	

		//add image and imageview background for gameOver to gameOver pane
		Image imggameOverBackground = new Image("file:gameOver_screen.png");
		ImageView ivgameOverBackground = new ImageView(imggameOverBackground);
		Pane gameOverRoot = new Pane();

		//change scene to gameover image width and height and image
		gameOver = new Scene(gameOverRoot, imggameOverBackground.getWidth(), imggameOverBackground.getHeight());
		gameOverRoot.getChildren().add(ivgameOverBackground);

		//try catch to catch addScore and highScores if exception occurs when calling to methods
		try 
		{
			addScore(name, score);
		} 
		catch (Exception e1) 
		{
			e1.printStackTrace();
		}

		try 
		{
			getHighScore();
		} 
		catch (Exception e2) 
		{
			e2.printStackTrace();
		}
		
		
        //initliaze btnPlayagain and set text, font, size and label and text colour and x and y on the pane
		Button btnPlayAGAIN = new Button();
		btnPlayAGAIN.setText("REPLAY");
		btnPlayAGAIN.setFont(Font.font("Britannic", FontWeight.BOLD, FontPosture.ITALIC, 35));
		btnPlayAGAIN.setPrefSize(270, 30);
		btnPlayAGAIN.setTextFill(Color.DARKORANGE);
		btnPlayAGAIN.setStyle("-fx-background-color: linear-gradient(#4568DC, #B06AB3),repeating-image-pattern(\"https://edencoding.com/resources/wp-content/uploads/2021/02/Stars_128.png\"),\r\n"
				+ "            radial-gradient(center 50% 50%, radius 50%, #FFFFFF33, #00000033)");
		btnPlayAGAIN.setBorder(new Border(new BorderStroke(Color.DARKORANGE, BorderStrokeStyle.SOLID, null, null)));
		btnPlayAGAIN.setAlignment(Pos.CENTER);
		btnPlayAGAIN.setLayoutX(80);
		btnPlayAGAIN.setLayoutY(350);
		//if user clicks replay button user will be sent back to the titleScreen
		btnPlayAGAIN.setOnAction(e -> titleScreen(primaryStage));
		
		

		//initliaze btnEXIT and set text, font, size and label and text colour and x and y on the pane
		Button btnEXIT = new Button();
		btnEXIT.setText("EXIT");
		btnEXIT.setFont(Font.font("Britannic", FontWeight.BOLD, FontPosture.ITALIC, 35));
		btnEXIT.setPrefSize(270, 30);
		btnEXIT.setTextFill(Color.DARKORANGE);
		btnEXIT.setStyle("-fx-background-color: linear-gradient(#4568DC, #B06AB3),repeating-image-pattern(\"https://edencoding.com/resources/wp-content/uploads/2021/02/Stars_128.png\"),\r\n"
				+ "            radial-gradient(center 50% 50%, radius 50%, #FFFFFF33, #00000033)");
		btnEXIT.setBorder(new Border(new BorderStroke(Color.DARKORANGE, BorderStrokeStyle.SOLID, null, null)));
		btnEXIT.setAlignment(Pos.CENTER);
		btnEXIT.setLayoutX(80);
		btnEXIT.setLayoutY(450);
		//if user clicks exit button user can exit program or return back to gameOver scene
		btnEXIT.setOnAction(e ->{
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setContentText("Are you sure you want to exit?");
			alert.setHeaderText(null);
			alert.setTitle("EXIT");

			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == ButtonType.CANCEL)
			{
				e.consume();
			}
			else
			{
				System.exit(0);
			}
		});
		//change border and text colour when hovered
		btnPlayAGAIN.setOnMouseEntered(e->{

			btnPlayAGAIN.setTextFill(Color.WHITE);
			btnPlayAGAIN.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, null, null)));

		});
		//change border and text colour when hovered

		btnEXIT.setOnMouseEntered(e->{

			btnEXIT.setTextFill(Color.WHITE);
			btnEXIT.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, null, null)));

		});
//revert back to default when mouse exits button
		btnPlayAGAIN.setOnMouseExited(e->{

			btnPlayAGAIN.setTextFill(Color.DARKORANGE);

			btnPlayAGAIN.setBorder(new Border(new BorderStroke(Color.DARKORANGE, BorderStrokeStyle.SOLID, null, null)));

		});
		//revert back to default when mouse exits button

		btnEXIT.setOnMouseExited(e->{

			btnEXIT.setTextFill(Color.DARKORANGE);

			btnEXIT.setBorder(new Border(new BorderStroke(Color.DARKORANGE, BorderStrokeStyle.SOLID, null, null)));

		});
//add btns to pane
		gameOverRoot.getChildren().addAll(btnEXIT,btnPlayAGAIN);





//center scene, set scene title, set scene to global variable and show scene
		primaryStage.centerOnScreen();
		primaryStage.setTitle("GAME OVER");
		primaryStage.setScene(gameOver);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	//method to getHighscore
	public void getHighScore() throws Exception
	{
		try 
		{
			// Initialize bufferedreader object to read HighScores.txt document
			BufferedReader readFile = new BufferedReader(new FileReader("WaveLeaderboard.txt"));

			// Initialize lineOfText string and scores/names arraylists 
			// (scores is integer arraylist, names is string arraylist)
			String lineOfText;
			ArrayList<Integer> scores = new ArrayList<Integer>();
			ArrayList<String> names = new ArrayList<String>();

			// Check if line has characters in it
			while ((lineOfText = readFile.readLine()) != null)
			{
				// Try parsing current line to integer and adding to score arraylist
				try 
				{
					scores.add(Integer.parseInt(lineOfText));
				} 
				// If line does not have a number, add to names arraylist
				catch (NumberFormatException e) 
				{
					names.add(lineOfText);
				}
			}
			// Close the buffered reader
			readFile.close();

			// Find maximum values by using method from the collections class to search arraylist
			highScore = Collections.max(scores);

			// Search index of highest value, initialize name at that index in the names arraylist
			highestValueIndex = linearSearch(scores, highScore);
			highScoreName = names.get(highestValueIndex);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	//method to linear search WAVELeaderboard file for highest score on file
	public int linearSearch(ArrayList<Integer> data, int value)
	{
		// Declare and initialize default value for the index of the search value
		int indexOfValue = -1;

		// Loop through each index in the arraylist
		for (int i = 0; i < data.size(); i++)
		{
			// If integer at current index matches search value, initialize indexOfValue to current index
			if (data.get(i) == value)
			{
				indexOfValue = i;
			}
		}
		// Return index of the search value
		return indexOfValue;
	}
	//method to addScore to waveLeaderboard txt when score is sorted, to check for highest waves completed to be added to highscore button clicked
	public void addScore(String name, int score) throws Exception
	{
		// Declare and initialize buffered writer object to write to HighScores.txt file, append to end of document
		BufferedWriter writeFile = new BufferedWriter(new FileWriter("WaveLeaderboard.txt", true));

		// Write the user's name (taken from parameter) to the current line, add new line
		writeFile.write(name);
		writeFile.newLine();

		// Write the user's score (taken from parameter) to the current line, add new line
		writeFile.write(Integer.toString(score));
		writeFile.newLine();

		// Close the buffered writer
		writeFile.close();
	}


}
