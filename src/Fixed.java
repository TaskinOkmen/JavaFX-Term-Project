/*	This Class represents Fixed Cells in game
 * 	
 * 	Taşkın ÖKMEN 	150122019
 * 	Harun SUBAŞI	150121060
 * 	Berke BAYRAM	150122009
 * */

package application;

import java.io.File;

import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Fixed extends Cell {
	private int id;
	
	public Fixed(int id) {
		super((id - 1) / 10, (id - 1) % 10);
		this.id = id;
		
		//Set the image of fixed cell
		Image image = new Image(new File("src/main/java/resources/delete.png").toURI().toString());
        ImageView fixedImageView = new ImageView(image);
        fixedImageView.setFitWidth(40);
        fixedImageView.setFitHeight(40);
        
        
        setText(" ");
        setGraphic(fixedImageView);
        setContentDisplay(ContentDisplay.TOP);
        //Set the cell type to 'f'
        setType('f');
	}
	
	//Returns the ID of that fixed cell
	public int getID() {
		return id;
	}
	
}
