/*	This Class represents main Title Screen in game, it holds new game, continue and high score buttons
 * 	
 * 	Taşkın ÖKMEN 	150122019
 * 	Harun SUBAŞI	150121060
 * 	Berke BAYRAM	150122009
 * */

package application;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.Scanner;

class TitleScreen extends StackPane {

    public Button newGameButton;


    public TitleScreen(Stage stage) {
        Font titleFont = loadFont("title_font.ttf", 40);
        Font buttonFont = loadFont("button_font.otf", 36);
        Font highScoreFont = loadFont("title_font.ttf", 16);

        // Load menu image
        Image menuImage = new Image(new File("src/main/java/resources/title_screen_image.png").toURI().toString());
        ImageView menuImageView = new ImageView(menuImage);

        // Buttons
        newGameButton = new Button("New Game");
        Button continueGameButton = new Button("Continue Game");

        Button highScoreButton = new Button("High Scores");
        VBox highScoreVbox = new VBox(0, highScoreButton);

        // Set font for buttons
        newGameButton.setFont(buttonFont);
        continueGameButton.setFont(buttonFont);
        highScoreButton.setFont(buttonFont);

        // Button style
        newGameButton.setStyle("-fx-background-color: black; -fx-text-fill: aqua;");
        continueGameButton.setStyle("-fx-background-color: black; -fx-text-fill: aqua;");
        highScoreButton.setStyle("-fx-background-color: black; -fx-text-fill: aqua;");

        // Title label
        Label titleLabel = new Label("Vice City");
        titleLabel.setFont(titleFont);
        titleLabel.setTextFill(Color.VIOLET);
        StackPane.setAlignment(titleLabel, Pos.TOP_LEFT);
        Insets titleInsets = new Insets(20, 0, 0, 120);
        StackPane.setMargin(titleLabel, titleInsets);

        // Animations
        fadeAnimation(newGameButton);
        fadeAnimation(continueGameButton);
        fadeAnimation(highScoreButton);


        // Alignments
        VBox buttonBox = new VBox(70, newGameButton, continueGameButton, highScoreVbox);
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        Insets buttonInsets = new Insets(50, 0, 0, 100);
        StackPane.setMargin(buttonBox, buttonInsets);


        Label label = new Label("");
        label.setFont(highScoreFont);
        label.setStyle("-fx-text-fill: rgb(165,134,215);");

        highScoreVbox.getChildren().add(label);
        highScoreButton.setOnMouseClicked(e -> {
            label.setText("High Score: " + getHighScore());
            highScoreButton.setDisable(true);
        });

        continueGameButton.setOnMouseClicked(e-> {
            loadGame(stage);
        });

        File file = new File("save.txt");
        continueGameButton.setDisable(!file.exists());

        getChildren().addAll(menuImageView, buttonBox, titleLabel);
    }

    // Apply fade animation to a button
    private void fadeAnimation(Button button) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(200), button);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(.5);

        button.setOnMouseEntered(e -> {
            fadeTransition.playFromStart();
        });

        button.setOnMouseExited(e -> {
            fadeTransition.stop();
            button.setOpacity(1);
        });
    }

    // Load a font file given the file name and size
    private Font loadFont(String fileName, int size) {
        File fontFile = new File("src/main/java/fonts/" + fileName);
        try {
            InputStream targetStream = new FileInputStream(fontFile);
            return Font.loadFont(targetStream, size);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    
    //Returns high score from high score text file
    private int getHighScore() {
        try {
            File highScoreFile = FilePathHelper.getHighScoreFile();
            if (!highScoreFile.exists()) {
                highScoreFile.createNewFile();
                updateHighScore(0);
                return 0;
            }
            Scanner myReader = new Scanner(highScoreFile);
            if (myReader.hasNext()) {
                return myReader.nextInt();
            } else {
                return 0;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    //Writes the score to high score text file
    private void updateHighScore(int score) {
        try {
            FileWriter writer = new FileWriter(FilePathHelper.getHighScoreFile());
            writer.write(String.valueOf(score));
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    //Loads the game on save text file
    public void loadGame(Stage primaryStage) {
        LevelPane levelPane = new LevelPane("save.txt");

        Scene scene = new Scene(levelPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
