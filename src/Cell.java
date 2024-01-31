/*	This Class represents Cells in game, it's extender are City and Fixed classes
 * 	
 * 	Taşkın ÖKMEN 	150122019
 * 	Harun SUBAŞI	150121060
 * 	Berke BAYRAM	150122009
 * */

package application;

import javafx.scene.control.Label;

class Cell extends Label {
    public int row;
    public int col;
    
    //g, h and f costs
    double g;
    double f;
    double h;
    
    //By default it is set to empty cell ('e')
    private char type = 'e';

    Cell cameFrom;

    public Cell(int row, int column) {
        // Initialize the Cell with its row and column positions
        this.row = row;
        this.col = column;
        int size = 50;
        this.setPrefWidth(size);
        this.setPrefHeight(size);
        this.setMaxHeight(size);
        this.setMaxWidth(size);
    }

    // Get the type of the Cell
    public char getType() {
        return type;
    }

    // Set the type of the Cell
    public void setType(char type) {
        this.type = type;
    }

    // Check if the Cell is traversable
    public boolean isTraversable() {
        return type == 'e';
    }

    // Override the equals method to compare Cells based on their row and column positions
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Cell) {
            return ((Cell) obj).row == this.row && ((Cell) obj).col == this.col;
        }
        return false;
    }
}
