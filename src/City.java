/*	This Class represents City Cells in game, it holds all data related to cities
 * 	
 * 	Taşkın ÖKMEN 	150122019
 * 	Harun SUBAŞI	150121060
 * 	Berke BAYRAM	150122009
 * */

package application;

import java.io.File;
import java.util.ArrayList;

import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class City extends Cell {
    private String name;
    private int cellID;
    private int cityID;
    
    //passengers ArrayList holds every passenger object that has
    //either starting city or destination equal to this object
    private ArrayList<Passenger> passengers = new ArrayList<>();


    public City(String[] attrs) {
        // Construct a City object using the given attributes
        super(0, 0);
        this.name = attrs[1];
        this.cellID = Integer.parseInt(attrs[2]);
        this.cityID = Integer.parseInt(attrs[3]);
        setType('c');
        super.setMaxWidth(50);

        // Set the text and graphic for the City label
        super.setText(name);
        super.setGraphic(getCityImageView());
        super.setContentDisplay(ContentDisplay.TOP);

        // Calculate the row and column positions of the City based on its cellID
        this.row = (cellID - 1) / 10;
        this.col = (cellID - 1) % 10;
    }

    // Add a passenger to the City
    public void addPassenger(Passenger p) {
        passengers.add(p);
    }

    // Add a list of passengers to the City
    public void addPassengerList(ArrayList<Passenger> p) {
        passengers.addAll(p);
    }

    // Get a randomly selected city image (one of 6 city images) for the City label
    private static ImageView getCityImageView() {
        int cityNumber = (int) (Math.random() * 6 + 1);
        // Pick a random city number and add that to imageView
        Image image = new Image(new File("src/main/java/resources/city_" + cityNumber + ".png").toURI().toString(), 40, 40, false, false);

        ImageView cityImageView = new ImageView(image);
        cityImageView.prefHeight(40);
        cityImageView.prefWidth(40);

        return cityImageView;
    }

    // Get the name of the City
    public String getName() {
        return name;
    }

    // Get the cellID of the City
    public int getCellID() {
        return cellID;
    }

    // Get the cityID of the City
    public int getCityID() {
        return cityID;
    }

    // Get the row position of the City
    public int getRow() {
        return row;
    }

    // Get the column position of the City
    public int getCol() {
        return col;
    }

    // Get the list of passengers in the City
    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    // Remove a passenger from the City
    public void removePassenger(Passenger passenger) {
        passengers.remove(passenger);
    }
}
