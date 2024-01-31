/*	This Class represents Passengers in game
 * 	
 * 	Taşkın ÖKMEN 	150122019
 * 	Harun SUBAŞI	150121060
 * 	Berke BAYRAM	150122009
 * */

package application;

public class Passenger {
    private int size;
    private City startingCity;
    private City destinationCity;

    // Constructor
    public Passenger(int size, City startingCity, City destinationCity) {
        this.size = size;
        this.startingCity = startingCity;
        this.destinationCity = destinationCity;
    }

    // Get the size of the passenger
    public int getSize() {
        return size;
    }

    // Get the starting city of the passenger
    public City getStartingCity() {
        return startingCity;
    }

    // Set the starting city of the passenger
    public void setStartingCity(City startingCity) {
        this.startingCity = startingCity;
    }

    // Set the destination city of the passenger
    public void setDestinationCity(City destinationCity) {
        this.destinationCity = destinationCity;
    }

    // Get the destination city of the passenger
    public City getDestinationCity() {
        return destinationCity;
    }

    // Set the size of the passenger
    public void setSizeOfPassenger(int size){
        this.size = size;
    }

    // Decrease the size of the passenger by a given count
    public void decreaseSize(int count) {
        size -= count;
    }
}
