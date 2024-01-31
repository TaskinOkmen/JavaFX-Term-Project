/*	This Class represents StatusPane (it shows all the information of selected city) in game
 * 	
 * 	Taşkın ÖKMEN 	150122019
 * 	Harun SUBAŞI	150121060
 * 	Berke BAYRAM	150122009
 * */

package application;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StatusPane extends VBox {

    private Vehicle vehicle;
    
    //Construct statusPane
    public StatusPane(Vehicle vehicle) {
        this.vehicle = vehicle;

        drawStatusPane();
    }
    
    //Draw statusPane based on vehicles current and target City's
    public void drawStatusPane() {
        getChildren().clear();

        City currentCity = vehicle.getCurrentCity();
        City targetCity = vehicle.getTargetCity();

        setPadding(new Insets(5, 5, 5, 5));

        // Display the information about the target city, including its name, ID, distance from the current city,
        // and the vehicle's capacity
        getChildren().add(new Label(
                String.format("%s (City ID:%s, Distance:%d, Vehicle Capacity:%d)",
                        targetCity.getName(), targetCity.getCityID(),
                        distance(currentCity, targetCity), vehicle.getCapacity())));

        ArrayList<Passenger> passengers = targetCity.getPassengers();

        // Display information about each passenger in the target city
        for (Passenger p : passengers) {
            Label info = new Label(String.format("%s > %s (%d Passengers)",
                    p.getStartingCity().getName(), p.getDestinationCity().getName(), p.getSize()));

            // Indent the label to provide visual separation
            setMargin(info, new Insets(0, 0, 0, 20));
            getChildren().add(info);
        }
    }

    // Calculate the distance between two cities based on their cell IDs
    private int distance(City currentCity, City targetCity) {
        int cID = currentCity.getCellID(), tID = targetCity.getCellID();
        int rowDis = (cID - 1) / 10 - (tID - 1) / 10;
        int colDis = (cID - 1) % 10 - (tID - 1) % 10;

        return (int) Math.ceil(Math.sqrt(rowDis * rowDis + colDis * colDis));
    }
}
