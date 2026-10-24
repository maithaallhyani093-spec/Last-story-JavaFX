package view;

import java.io.File;

import controller.GameController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
 

public class Main extends Application { 
	//the variables that we used in our game.
	Stage st;
	GameController game;
	String NameOfPlayer;
	
   //عناصر الواجه
	Label question;
	Label Timer;

	Button Thefirstchoice;
	Button The2choice;
	ProgressBar progress ;
	
   //التايمر
	Timeline timer;
	int timeLeft=20;
	boolean useTimer;
	
   //تصميم الشاشه
	VBox Layout; 
	StackPane OurMainscreen;

   //صورة الانفجار والصوت 
	ImageView explosionBooo;
	MediaPlayer rain;
      
	
	
@Override
public void start(Stage stage) {
	this.st= stage;
     playRain();
     
        showScreen();}

   // لشاشه الاولى
    private void showScreen() {
    	
    Label title = new Label ("LAST STORY");
    title.setStyle("-fx-font-size: 30px;-fx-text-fill:white;");

    TextField nameField = new TextField();
    nameField.setPromptText("Who are you?");
   Button startBtn =new Button("Launch The Game");

       startBtn.setOnAction(e-> {
       NameOfPlayer = nameField.getText();
        	
        	if (NameOfPlayer.isEmpty())
        		NameOfPlayer = "Player";
        	showDifficultyScreen();       });
        
       
       VBox layout= new VBox (20,title,nameField, startBtn );
       layout.setAlignment(Pos.CENTER);
       
       OurMainscreen = createBackground(layout);
       
       st.setScene(new Scene (OurMainscreen,700,500));
       st.show();  }
    

   // الشاشه الثانيه(اختيار المستوى)
   private void showDifficultyScreen() {
   	Label label = new Label("Choose Difficulty");
    label.setStyle("-fx-font-size:24px;-fx-text-fill:white;");
        
    Button ez =new Button("Easy");
    Button hard =new Button("hard");
       
        ez.setOnAction(e -> startGame(false));
        hard.setOnAction(e -> startGame(true));

        VBox layout = new VBox(20, label, ez, hard);
        layout.setAlignment(Pos.CENTER);

        OurMainscreen = createBackground(layout);

        st.setScene(new Scene(OurMainscreen, 700, 500));
    }
//  بدء اللعبه
    private void startGame(boolean hardMode) {

        useTimer = hardMode;

        game = new GameController(NameOfPlayer, hardMode ? 2 : 1);

        question = new Label();
        question.setWrapText(true);
        question.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

        
        
        Timer = new Label("Time: 10");
        Timer.setStyle("-fx-text-fill: white;");

        progress = new ProgressBar(0);
        progress.setPrefWidth(300);

        Thefirstchoice = new Button();
        The2choice = new Button();

        Thefirstchoice.setPrefWidth(300);
        The2choice.setPrefWidth(300);

        Thefirstchoice.setOnAction(e -> handleChoice(1));

        The2choice.setOnAction(e -> {

            showExplosion();
            handleChoice(2);
        });

        explosionBooo = new ImageView(
                new Image(getClass().getResourceAsStream("/resources/explosion.png"))
        );

        explosionBooo.setFitWidth(150);
        explosionBooo.setPreserveRatio(true);
        explosionBooo.setVisible(false);
        explosionBooo.setMouseTransparent(true);

        Layout = new VBox(20,
                progress,
                question,
                Thefirstchoice,
                The2choice,
                Timer);

        Layout.setAlignment(Pos.CENTER);

        loadQuestion();

        OurMainscreen = createBackground(Layout);

        OurMainscreen.getChildren().add(explosionBooo);

        st.setScene(new Scene(OurMainscreen, 700, 500));
    }
// تحميل السؤال 
    private void loadQuestion() {

        if (game.hasMoreQuestions()) {

     question.setText(game.getCurrentQuestion().getText());
     Thefirstchoice.setText(game.getCurrentQuestion().getChoice1());
     The2choice.setText(game.getCurrentQuestion().getChoice2());

    double progresss =
   (double) game.getCurrentIndex() / 4;

    progress.setProgress(progresss);
    if (useTimer) { 
    Timer.setVisible(true);
    startTimer();
    }else {

    	Timer.setVisible(false);}
          
    } else {

            endGame();
        }
    }
// تشغيل التايمر 
    private void startTimer() {

        timeLeft = 10;

        Timer.setText("Time: " + timeLeft);

        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {

            timeLeft--;

            Timer.setText("Time: " + timeLeft);

            if (timeLeft <= 3) {

                Timer.setStyle("-fx-text-fill: red;");
            }

            if (timeLeft <= 0) {

                timer.stop();

                showExplosion();

                handleChoice(2);
            }

        }));

        timer.setCycleCount(10);

        timer.play();
    }
 //اختيار اللاعب 
    private void handleChoice(int choice) {

        if (timer != null)
            timer.stop();

        game.nextQuestion(choice);

        loadQuestion();
    }

    // حدوث الانفجار 
    private void showExplosion() {

        playBoom();

        explosionBooo.setVisible(true);

        Timeline hide = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                	explosionBooo.setVisible(false);
                })
        );

        hide.play();
    }
//  نهاية اللعبه
    private void endGame() {

        progress.setProgress(1);
        question.setText(
"THE CITY WILL REMEMBER U\n\n"+ NameOfPlayer +"\nYou are:\n" +  game.finalResult() );
  

        Thefirstchoice.setVisible(false);
        The2choice.setVisible(false);
        Timer.setVisible(false);

Button restartBtn = new Button("WANNA PLAY AGAIN?");

        restartBtn.setOnAction(e -> showScreen());

        Layout.getChildren().add(restartBtn);
        game.saveResult();
    }
    
// خلفية اللعبه
private StackPane createBackground(VBox layout) {

        ImageView bg = new ImageView(
                new Image(getClass().getResourceAsStream("/resources/city.jpg"))
        );

        bg.setFitWidth(700);
        bg.setFitHeight(500);

        StackPane pane = new StackPane();
        pane.getChildren().addAll(bg, layout);

        return pane;
    }
// صوت المطر 
    private void playRain() {

        Media sound = new Media(
        new File("src/resources/rain.wav").toURI().toString() );

    rain = new MediaPlayer(sound);
    rain.setCycleCount(MediaPlayer.INDEFINITE);
    rain.setVolume(0.2);
    rain.play();}
   
    
// صوت الانفجار
 private void playBoom() {

        Media sound = new Media(
        new File("src/resources/boom.mp3").toURI().toString() );

        MediaPlayer boom = new MediaPlayer(sound);
        boom.play();}
// تشغيل البرنامج
    public static void main(String[] args) {
  launch();
       
    }
}