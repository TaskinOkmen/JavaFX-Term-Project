/*	This Class represents Vehicle in game
 * 	
 * 	Taşkın ÖKMEN 	150122019
 * 	Harun SUBAŞI	150121060
 * 	Berke BAYRAM	150122009
 * */

package application;

import java.io.File;
import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Vehicle extends ImageView {
	private City currentCity;  // The current city where the vehicle is located
	private City targetCity;  // The target city the vehicle is heading towards
	private int capacity;  // The capacity of the vehicle

	private ArrayList<Passenger> allPassengers;


	public Vehicle(City currentCity, int capacity, ArrayList<Passenger> allPassengers) {
		this.currentCity = currentCity;
		this.capacity = capacity;
		this.allPassengers = allPassengers;
		
		//Set an image based on capacity of vehicle (car, minibus, bus)
		if (capacity <= 6)
			super.setImage(new Image(new File("src/main/java/resources/car.png").toURI().toString()));
		else if (capacity <= 14)
			super.setImage(new Image(new File("src/main/java/resources/minibus.png").toURI().toString()));
		else
			super.setImage(new Image(new File("src/main/java/resources/bus.png").toURI().toString()));


		super.setFitHeight(60);
		super.setFitWidth(60);
		
		//Set the position of vehicle based on current City
		setX(currentCity.getCol() * 50 + 25);
		setY(currentCity.getRow() * 60 + 25);
	}
	
	//Getter and Setter Methods
	public City getCurrentCity() {
		return currentCity;
	}

	public void setCurrentCity(City currentCity) {
		this.currentCity = currentCity;
	}

	public int getCapacity() {
		return capacity;
	}

	public City getTargetCity() {
		return targetCity;
	}

	public void setTargetCity(City targetCity) {
		this.targetCity = targetCity;
	}

	//Returns the number of passengers that transported from current City to target City of vehicle
	public int dropOffPassengers() {
		java.util.ArrayList<Passenger> passengers = targetCity.getPassengers();
		int i = 0, emptySeats = capacity;

        	while (i < passengers.size()) {
            		Passenger p = passengers.get(i);

            		if (p.getStartingCity() == currentCity) {

                		if (p.getSize() <= emptySeats) {
                    			emptySeats -= p.getSize();
				 	currentCity.removePassenger(p);
				    	targetCity.removePassenger(p);
				    	allPassengers.remove(p);
                		}
                		else {
                    			p.setSizeOfPassenger(p.getSize() - emptySeats);
                    			return capacity;
                		}
            		}
            		else ++i;
        	}

        	return capacity - emptySeats;
	}
	
}
