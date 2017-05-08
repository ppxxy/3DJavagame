package game.engine.interfaces;

import Localization.Localization;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
* <h1>WBConfirmationBox</h1>
* Creates a confirmation box for when the user wants to
* clear the canvas in the Whiteboard class.
* <p>

* @author  Tomi Lehto
* @version 1.0
*/

public class WBConfirmationBox {
	private static Stage window;
	private static boolean clear = false;

	public static boolean Display(){
		window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL); //block events to other windows
		window.setTitle(Localization.getBundle().getString("confirm"));
		window.setMinWidth(250);
		window.setMinHeight(150);

		Label label = new Label();
		label.setText(Localization.getBundle().getString("clear_canvas_confirmation_message"));
		Button okButton = new Button(Localization.getBundle().getString("yes"));
		Button cancelButton = new Button(Localization.getBundle().getString("cancel"));


		cancelButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	clear = false;
		    	window.close();
		    }
		});

		okButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	clear = true;
		    	window.close();
		    }
		});

		VBox vbox = new VBox(10);
		HBox hbox = new HBox(10);
		hbox.setAlignment(Pos.CENTER);
		hbox.setPadding(new Insets(10,0,10,0));
		hbox.getChildren().addAll(okButton, cancelButton);
		vbox.getChildren().addAll(label, hbox);
		vbox.setAlignment(Pos.CENTER);

		Scene scene = new Scene(vbox);
		window.setScene(scene);
		window.showAndWait();
		return clear;

	}
}
