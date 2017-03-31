package game.engine.interfaces;

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

public class WBConfirmationBox {
	private static Stage window;
	private static boolean clear = false;

	public static boolean Display(String title, String message){
		window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL); //block events to other windows
		window.setTitle(title);
		window.setMinWidth(250);
		window.setMinHeight(150);

		Label label = new Label();
		label.setText(message);
		Button okButton = new Button("Yes");
		Button cancelButton = new Button("Cancel");


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
		hbox.getChildren().addAll(cancelButton, okButton);
		vbox.getChildren().addAll(label, hbox);
		vbox.setAlignment(Pos.CENTER);

		Scene scene = new Scene(vbox);
		window.setScene(scene);
		window.showAndWait();
		return clear;

	}
}
