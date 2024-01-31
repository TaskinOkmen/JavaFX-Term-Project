/*	This Class represents LevelPane (the main class that holds everything) in game
 * 	
 * 	Taşkın ÖKMEN 	150122019
 * 	Harun SUBAŞI	150121060
 * 	Berke BAYRAM	150122009
 * */

package application;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.animation.PathTransition;
import javafx.scene.layout.BorderPane;

public class LevelPane extends BorderPane {
    private TopBar topBar;
    private GameBoardPane gameBoardPane;
    private BottomBar bottomBar;
    private GamePane gamePane;
    private int currentLevel;

    // Constructors based on level
    public LevelPane(int level) {
    	//Create all other panes
        topBar = new TopBar(level);
        gameBoardPane = new GameBoardPane(FilePathHelper.getLevelFile(level));
        bottomBar = new BottomBar();
        currentLevel = level;

        GamePane gamePane = new GamePane(gameBoardPane);
        this.gamePane = gamePane;
        
        //Adjust drive and next level button's event handlers
        bottomBar.getDriveText().setOnMouseClicked(e -> onDriveButtonClick());
        topBar.getNextLevelText().setOnMouseClicked(e -> onNextButtonClick());


        this.setTop(topBar);
        this.setCenter(gamePane);
        this.setBottom(bottomBar);
    }
    
    // Constructor for reading save text files
    public LevelPane(String fileName) {
        try {
            java.util.Scanner input = new java.util.Scanner(new File(fileName));
            int level = input.nextInt();

            //Create all other panes
            topBar = new TopBar(level);
            topBar.addScore(input.nextInt());

            gameBoardPane = new GameBoardPane(fileName);

            bottomBar = new BottomBar();
            currentLevel = level;

            GamePane gamePane = new GamePane(gameBoardPane);
            this.gamePane = gamePane;
            
            //Adjust drive and next level button's event handlers
            bottomBar.getDriveText().setOnMouseClicked(e -> onDriveButtonClick());
            topBar.getNextLevelText().setOnMouseClicked(e -> onNextButtonClick());


            this.setTop(topBar);
            this.setCenter(gamePane);
            this.setBottom(bottomBar);

            input.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Handle next level button click
    private void onNextButtonClick() {
        int nextLevel = currentLevel + 1;
        if (new File(FilePathHelper.getLevelFile(nextLevel)).exists()) {
            topBar.disableNextLevelText();
            topBar.onNextLevel(nextLevel);
            gameBoardPane = new GameBoardPane(FilePathHelper.getLevelFile(nextLevel));
            gamePane = new GamePane(gameBoardPane);
            this.setCenter(gamePane);
            currentLevel++;
            
            saveGame();
        }
    }

    // Handle drive button click
    private void onDriveButtonClick() {
        if (!gameBoardPane.isSelectedCityIsCurrentCity()) {
            PathTransition pt = createPathTransition();
            pt.play();
            onDriveFinish();
            updateHighScoreIfNecessary();
            
            saveGame();
        }
    }

    private int highScore;

    // Update high score if necessary
    private void updateHighScoreIfNecessary() {
        if (highScore == 0) {
            highScore = getHighScore();
        }
        int currentScore = topBar.score;
        if (currentScore > highScore) {
            updateHighScore(currentScore);
        }
    }

    // Update the high score in the file
    private void updateHighScore(int score) {
        try {
            FileWriter writer = new FileWriter(FilePathHelper.getHighScoreFile());
            writer.write(String.valueOf(score));
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Get the high score from the file
    private int getHighScore() {
        try {
            File highScoreFile = FilePathHelper.getHighScoreFile();
            if (!highScoreFile.exists()) {
                highScoreFile.createNewFile();
                updateHighScore(0);
                return  0;
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

    // Handle drive finish
    private void onDriveFinish() {
        gamePane.clearPath();
        Vehicle vehicle = gameBoardPane.getVehicle();

        vehicle.getTargetCity().cameFrom = null;
        vehicle.getCurrentCity().cameFrom = null;

        int transferPassengerSize = vehicle.dropOffPassengers();
        int score = calculateScore(vehicle.getCurrentCity(), vehicle.getTargetCity(), transferPassengerSize);
        topBar.addScore(score);
        vehicle.setCurrentCity(vehicle.getTargetCity());

        if (gameBoardPane.isLevelEnd()) {
            topBar.enableNextLevelText();
        }
        bottomBar.drawStatusPane();
    }

    // Calculate the score based on distance and passenger transfer
    private int calculateScore(City from, City to, int passengerSize) {
        int distance = (int) Math.ceil(Math.sqrt((from.col - to.col) * (from.col - to.col) + (from.row - to.row) * (from.row - to.row)));
        int income = (int) (passengerSize * (distance * 0.2));
        return income - distance;
    }

    // Create and configure a PathTransition for the vehicle animation
    PathTransition createPathTransition() {
        Vehicle vehicle = gameBoardPane.getVehicle();

        PathTransition pt = new PathTransition();
        pt.setPath(gameBoardPane.getPath());
        pt.setNode(vehicle);
        pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);

        return pt;
    }

    // Handle city click event
    public void onCityClick(City city) {
        Vehicle vehicle = gameBoardPane.getVehicle();
        vehicle.setTargetCity(city);
        gamePane.drawPath();
        bottomBar.setStatusPane(new StatusPane(vehicle));
        bottomBar.makeDriveButtonActive();
    }
    
    //Saves the game into a text file
    private void saveGame() {
        try (PrintWriter output = new PrintWriter("save.txt");) {
        	
        	//Add first line, last currentLevel and game score
            output.printf("%d %d\n", currentLevel, topBar.score);
            
            //Get all cities, passengers, fixed and vehicle from gameBoardPane
            ArrayList<City> cities = gameBoardPane.getCities();
            ArrayList<Passenger> passengers = gameBoardPane.getAllPassengers();
            ArrayList<Fixed> fixedCells = gameBoardPane.getFixedCells();
            Vehicle v = gameBoardPane.getVehicle();
            
            
            //Write on file with the same format as other level files
            //Later used for reading the save text file 
            for (City c: cities)
                output.printf("City,%s,%d,%d\n", c.getName(), c.getCellID(), c.getCityID());

            for (Passenger p: passengers)
                output.printf("Passenger,%d,%d,%d\n", p.getSize(), p.getStartingCity().getCityID(), p.getDestinationCity().getCityID());

            for (Fixed f: fixedCells)
                output.printf("Fixed,%d\n", f.getID());

            output.printf("Vehicle,%d,%d", v.getCurrentCity().getCityID(), v.getCapacity());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
