/*	This Class represents TopBar in game, it holds level number, score and next level button
 * 	
 * 	Taşkın ÖKMEN 	150122019
 * 	Harun SUBAŞI	150121060
 * 	Berke BAYRAM	150122009
 * */

package application;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class TopBar extends BorderPane {
    private Label scoreText;
    private Label nextLevelText;
    private Label levelText;
    int score;

    public TopBar(int level) {
        setPadding(new Insets(2, 2, 2, 2));
        this.setStyle("-fx-border-color: black");
        this.setPrefHeight(20);

        // Score label
        scoreText = new Label("Score: 0");
        setCenter(scoreText);

        // Next level label
        nextLevelText = new Label("Next Level>>");
        setRight(nextLevelText);

        // Level label
        levelText = new Label("Level#" + level);
        setLeft(levelText);

        disableNextLevelText();
    }

    // Add score and update the score label
    public void addScore(int score) {
        this.score += score;
        scoreText.setText("Score: " + this.score);
    }

    // Enable the next level text
    public void enableNextLevelText() {
        nextLevelText.setDisable(false);
    }

    // Disable the next level text
    public void disableNextLevelText() {
        nextLevelText.setDisable(true);
    }

    // Get the next level text label
    public Label getNextLevelText() {
        return nextLevelText;
    }

    // Set the level number
    private void setLevel(int level) {
        levelText.setText("Level#" + level);
    }

    // Update the level text when moving to the next level
    public void onNextLevel(int level) {
        setLevel(level);
    }
}
