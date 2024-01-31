/*	This Class represents BottomBar in game, holds statusPane and drive button
 * 	
 * 	Taşkın ÖKMEN 	150122019
 * 	Harun SUBAŞI	150121060
 * 	Berke BAYRAM	150122009
 * */

package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class BottomBar extends BorderPane {
    Label driveText;
    private boolean isDriveTextActive = false;
    private StatusPane statusPane;


    public BottomBar() {
        // Set the border of the bottom bar
        this.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        // Set the preferred height of the bottom bar
        this.setPrefHeight(150);

        // Create and configure the drive text label
        driveText = new Label("DRIVE");
        driveText.setTextFill(Color.GRAY);
        driveText.setFont(Font.font("Arial-Bold", FontWeight.BOLD, FontPosture.REGULAR, 30));

        // Align the drive text label to the center and set the margin
        BorderPane.setAlignment(driveText, Pos.CENTER);
        BorderPane.setMargin(driveText, new Insets(12, 12, 12, 12));
        this.setRight(driveText);

        this.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        this.setPrefHeight(150);

        driveText = new Label("DRIVE");
        driveText.setTextFill(Color.GRAY);

        driveText.setFont(Font.font("Arial-Bold", FontWeight.BOLD, FontPosture.REGULAR, 30));

        BorderPane.setAlignment(driveText, Pos.CENTER);
        BorderPane.setMargin(driveText, new Insets(12, 12, 12, 12));
        this.setRight(driveText);
    }

    // Check if the drive button is active
    public boolean isDriveButtonActive() {
        return isDriveTextActive;
    }

    // Make the drive button active
    public void makeDriveButtonActive() {
        isDriveTextActive = true;
        driveText.setTextFill(Color.BLACK);
    }

    // Make the drive button inactive
    public void makeDriveButtonInactive() {
        isDriveTextActive = false;
        driveText.setTextFill(Color.GRAY);
    }

    // Draw the status pane
    public void drawStatusPane() {
        statusPane.drawStatusPane();
    }

    // Set the status pane for the bottom bar
    public void setStatusPane(StatusPane statusPane) {
        this.statusPane = statusPane;
        this.setLeft(statusPane);
    }

    // Get the drive text label
    public Label getDriveText() {
        return driveText;
    }
}
