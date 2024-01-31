/*	This Program executes the game
 * 	
 * 	Taşkın ÖKMEN 	150122019
 * 	Harun SUBAŞI	150121060
 * 	Berke BAYRAM	150122009
 * */

package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Test extends Application {


    @Override
    public void start(Stage primaryStage) {
    	//Create the title screen
        TitleScreen titleScreen = new TitleScreen(primaryStage);
        Scene scene = new Scene(titleScreen);
        
        //Adjust the event handler of new game button
        titleScreen.newGameButton.setOnMouseClicked(e -> onNextGameButtonClick(primaryStage));
        
        //Set the scene
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("GameProject");
        primaryStage.show();
    }
    
    //Start the game from level 1
    private void onNextGameButtonClick(Stage stage) {
        LevelPane levelPane = new LevelPane(1);
        Scene scene = new Scene(levelPane);
        stage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
