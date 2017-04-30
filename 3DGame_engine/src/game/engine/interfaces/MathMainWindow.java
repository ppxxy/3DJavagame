package game.engine.interfaces;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MathMainWindow extends Application{
	Scene sceneMain, sceneTest, sceneResults;
	BorderPane paneMain, paneTest, paneResults;

	@Override
	public void start(final Stage stage) throws Exception {

		//main window
		paneMain = new BorderPane();
		HBox hboxMain = new HBox();
		Label labelMain = new Label("Valitse luku: ");
		Button buttonMain = new Button("Aloita harjoitus");
		ChoiceBox<String> cb = new ChoiceBox<String>();
		cb.getItems().addAll("Random", "2", "3", "4", "5", "6", "7", "8", "9");
		cb.setValue("Random");
		buttonMain.setPadding(new Insets(10,10,10,10));
		hboxMain.setPadding(new Insets(10,10,10,10));
		hboxMain.setAlignment(Pos.CENTER);
		hboxMain.setSpacing(15);

		hboxMain.getChildren().addAll(labelMain, cb);
		paneMain.setTop(hboxMain);
		paneMain.setCenter(buttonMain);
		sceneMain = new Scene(paneMain, 300, 200);

		stage.setScene(sceneMain);
		stage.show();

		buttonMain.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	stage.setScene(sceneTest);
		    }
		});



	}

	public static void main(String[] args) {
        launch(args);
    }

}
