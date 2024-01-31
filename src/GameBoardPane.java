/*	This Class represents GameBoardPane in game, it holds all cells, vehicle and drive path
 * 	
 * 	Taşkın ÖKMEN 	150122019
 * 	Harun SUBAŞI	150121060
 * 	Berke BAYRAM	150122009
 * */

package application;

import java.io.File;
import java.util.*;

import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;

public class GameBoardPane extends GridPane {

    private final ArrayList<City> cities = new ArrayList<>();
    private Vehicle vehicle;
    private final int rows = 10;
    private final int cols = 10;
    
    //Cells array holds every cell on board
    private final Cell[][] cells = new Cell[rows][cols];

    private Polyline path;

    private City selectedCity = null;

    private ArrayList<Passenger> allPassengers = new ArrayList<>();
    private ArrayList<Fixed> fixedCells = new ArrayList<>();
    
    //Constructor, reads level text file and creates the game board
    public GameBoardPane(String fileName) {
    	
    	//First fill every cell with empty cells
        for (int row = 0; row < rows; ++row) {
            for (int column = 0; column < cols; ++column) {
                Cell cell = new Cell(row, column);


                cells[row][column] = cell;
                this.add(cell, column, row);
            }
        }
        
        //After create and set cells on level text file
        createCells(fileName);
        
        //Set all city cells event handlers
        for (City city : cities) {
            city.setOnMouseClicked(e -> {
                getLevelPane().onCityClick(city);
                onCityClick(city);
            });
        }

    }

    // Event handler for city click
    private void onCityClick(City city) {
        if (vehicle.getCurrentCity() != city) {
            getChildren().remove(this.path);
            this.path = getDrivePath();
        }
        this.selectedCity = city;
    }

    // Create cells based on the level file
    private void createCells(String fileName) {
        try {
            Scanner input = new Scanner(new File(fileName));
            
            //Skip the first line (it has level and score values), if it is a save text file
            if (fileName.equals("save.txt")) input.nextLine();

            while (input.hasNext()) {
                String line = input.nextLine();
                String[] attrs = line.split(",");
                String objectName = attrs[0];
                
                
                //Create corresponding object
                if (objectName.equals("City"))
					createNewCityCell(attrs);
				
				else if (objectName.equals("Fixed"))
					createFixedCell(attrs);
				
				else if (objectName.equals("Passenger"))
					createNewPassenger(attrs);
				
				else if (objectName.equals("Vehicle"))
					createVehicle(attrs);
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Create a new city cell
    private void createNewCityCell(String[] attrs) {
        int id = Integer.parseInt(attrs[2]) - 1;
        int row = id / 10, col = id % 10;
        
        //Create city based on attributes
        City city = new City(attrs);
        cities.add(city);

        cells[row][col] = city;
        this.add(city, col, row);
    }

    // Create a new passenger
    private void createNewPassenger(String[] attrs) {
        City startingCity = null, destinationCity = null;

        int startingCityID = Integer.parseInt(attrs[2]);
        int destinationCityID = Integer.parseInt(attrs[3]);
        
        
        //Find the starting and destination city of passenger
        for (City city : cities) {
            int cityID = city.getCityID();

            if (cityID == startingCityID)
                startingCity = city;
            else if (cityID == destinationCityID)
                destinationCity = city;
        }
        
        //Create Passenger object
        Passenger p = new Passenger(Integer.parseInt(attrs[1]), startingCity, destinationCity);
        
        //Add both passengers array list of starting and destination city
        startingCity.addPassenger(p);
        destinationCity.addPassenger(p);

        allPassengers.add(p);
    }

    // Create a fixed cell
    private void createFixedCell(String[] attrs) {
        int id = Integer.parseInt(attrs[1]) - 1;
        int row = id / 10, col = id % 10;
        
        Fixed fixed = new Fixed(Integer.parseInt(attrs[1]));

        fixedCells.add(fixed);
        cells[row][col] = fixed;
        this.add(fixed, col, row);
    }

    // Create the vehicle
    private void createVehicle(String[] attrs) {
        int currentCityID = Integer.parseInt(attrs[1]);
        
        //Find the current city of vehicle
        for (City city : cities)
            if (city.getCityID() == currentCityID) {
                vehicle = new Vehicle(city, Integer.parseInt(attrs[2]), allPassengers);
                break;
            }
    }

    // Get the drive path as a Polyline
    public Polyline getDrivePath() {
        Polyline drivePath = new Polyline();
        drivePath.setStroke(Color.GREEN);
        drivePath.setStrokeWidth(10);

        ObservableList<Double> path = drivePath.getPoints();

        List<Cell> cellList = getDriveCellList();
        for (Cell cell : cellList) {
            double x = cell.getLayoutX() + cell.getWidth() / 2;
            double y = cell.getLayoutY() + cell.getHeight() / 2;
            path.add(x);
            path.add(y);
        }
        cellList.clear();
        return drivePath;
    }

    // Get the cells in the drive path
    public List<Cell> getDriveCellList() {
        int totalCellCount = rows * cols;
        City start = vehicle.getCurrentCity();
        City goal = vehicle.getTargetCity();

        boolean containsNeighbor;

        ArrayList<Cell> closedList = new ArrayList<>(totalCellCount);
        ArrayList<Cell> openSet = new ArrayList<>();
        openSet.add(start);

        start.g = 0d;
        start.f = start.g + heuristicCostEstimate(start, goal);

        Cell current;

        while (!openSet.isEmpty()) {
            current = openSet.get(0);
            openSet.remove(0);

            if (current.equals(goal)) {
                List<Cell> cellList = reconstructPath(goal);
                revlist(cellList);
                return cellList;
            }

            if (!closedList.contains(current)){
                closedList.add(current);
            }

            for (Cell neighbor : getNeighbors(current, goal)) {
                if (neighbor == null) {
                    continue;
                }

                if (closedList.contains(neighbor)) {
                    continue;
                }

                double tentativeScoreG = current.g + distBetween(current, neighbor);

                if (!(containsNeighbor = openSet.contains(neighbor)) || Double.compare(tentativeScoreG, neighbor.g) < 0) {
                    neighbor.cameFrom = current;
                    neighbor.g = tentativeScoreG;
                    neighbor.h = heuristicCostEstimate(neighbor, goal);
                    neighbor.f = neighbor.g + neighbor.h;

                    if (!containsNeighbor) {
                        openSet.add(neighbor);
                    }
                }
            }
        }
        // No path found
        return new ArrayList<>();
    }

    // Calculate the distance between cells
    private double distBetween(Cell current, Cell neighbor) {
        return heuristicCostEstimate(current, neighbor);
    }

    // Reconstruct the path based on the current cell
    public List<Cell> reconstructPath(Cell current) {
        List<Cell> totalPath = new ArrayList<>();
        totalPath.add(current);

        while ((current = current.cameFrom) != null) {
            totalPath.add(current);
        }

        return totalPath;
    }

    // Estimate the heuristic cost between two cells
    private double heuristicCostEstimate(Cell from, Cell to) {
        return Math.sqrt((from.col - to.col) * (from.col - to.col) + (from.row - to.row) * (from.row - to.row));
    }

    // Get the neighboring cells of a given cell
    public Cell[] getNeighbors(Cell cell, Cell target) {
        Cell[] neighbors = new Cell[4];

        int currentColumn = cell.col;
        int currentRow = cell.row;

        int neighborColumn;
        int neighborRow;

        // Top
        neighborColumn = currentColumn;
        neighborRow = currentRow - 1;

        if (neighborRow >= 0) {
            if (cells[neighborRow][neighborColumn].isTraversable() || (target.row == neighborRow && target.col == neighborColumn)) {
                neighbors[0] = cells[neighborRow][neighborColumn];
            }
        }

        // Bottom
        neighborColumn = currentColumn;
        neighborRow = currentRow + 1;

        if (neighborRow <= rows - 1) {
            if (cells[neighborRow][neighborColumn].isTraversable() || (target.row == neighborRow && target.col == neighborColumn)) {
                neighbors[1] = cells[neighborRow][neighborColumn];
            }
        }

        // Left
        neighborColumn = currentColumn - 1;
        neighborRow = currentRow;

        if (neighborColumn >= 0) {
            if (cells[neighborRow][neighborColumn].isTraversable() || (target.row == neighborRow && target.col == neighborColumn)) {
                neighbors[2] = cells[neighborRow][neighborColumn];
            }
        }

        // Right
        neighborColumn = currentColumn + 1;
        neighborRow = currentRow;

        if (neighborColumn <= cols - 1) {
            if (cells[neighborRow][neighborColumn].isTraversable() || (target.row == neighborRow && target.col == neighborColumn)) {
                neighbors[3] = cells[neighborRow][neighborColumn];
            }
        }

        return neighbors;
    }

    // Reverse the order of a list recursively
    public static void revlist(List<Cell> list) {
        if (list.size() <= 1 || list == null)
            return;

        Cell value = list.remove(0);
        revlist(list);
        list.add(value);
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Polyline getPath() {
        return path;
    }

    private LevelPane getLevelPane() {
        return (LevelPane) getParent().getParent();
    }
    
    //Check if every passengers array list of City Object is empty
    public boolean isLevelEnd() {
        for (City city : cities) {
            for (Passenger p : city.getPassengers()) {
                if (p.getSize() != 0)
                    return false;
            }
        }
        return true;
    }

    public ArrayList<Passenger> getAllPassengers() {
        return allPassengers;
    }

    public ArrayList<Fixed> getFixedCells() {
        return fixedCells;
    }

    public boolean isSelectedCityIsCurrentCity() {
        return selectedCity == getVehicle().getCurrentCity();
    }
}