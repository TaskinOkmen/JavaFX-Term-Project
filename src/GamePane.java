/*	This Class represents GamePane (the pane in center) in game, it holds gameBoardPane, vehicle and drive path
 * 	
 * 	Taşkın ÖKMEN 	150122019
 * 	Harun SUBAŞI	150121060
 * 	Berke BAYRAM	150122009
 * */

package application;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Polyline;

public class GamePane extends Pane {
    private GameBoardPane gameBoardPane;
    private Polyline path;


    public GamePane(GameBoardPane gameBoardPane) {
        this.gameBoardPane = gameBoardPane;

        // Add the game board pane and the vehicle to the pane
        getChildren().addAll(gameBoardPane, gameBoardPane.getVehicle());
    }

    // Draw the path on the board
    public void drawPath() {
        // Clear any existing path
        clearPath();

        // Get the new drive path from the game board pane
        Polyline newPath = gameBoardPane.getDrivePath();

        // Add the new path to the pane at index 0
        getChildren().add(0, newPath);

        // Set the current path to the new path
        this.path = newPath;
    }

    // Clear the path from the board
    public void clearPath() {
        // Remove the current path from the pane
        getChildren().remove(this.path);
    }
}
