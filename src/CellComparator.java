/*	This Class compares cells based on f values of cells
 * 	
 * 	Taşkın ÖKMEN 	150122019
 * 	Harun SUBAŞI	150121060
 * 	Berke BAYRAM	150122009
 * */

package application;

import java.util.Comparator;

public class CellComparator implements Comparator<Cell> {
    @Override
    public int compare(Cell a, Cell b) {
        // Compare the 'f' values of the two cells and return the result
        return Double.compare(a.f, b.f);
    }
}
