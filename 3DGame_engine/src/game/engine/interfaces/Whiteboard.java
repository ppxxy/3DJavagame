package game.engine.interfaces;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Whiteboard extends Application{

	Canvas canvas = new Canvas(800, 500);
	GraphicsContext gc;
	ColorPicker cPicker = new ColorPicker();
	ChoiceBox<Integer> cb = new ChoiceBox<Integer>();
	Image penImage = new Image(getClass().getResourceAsStream("paintbrush.png"));
	Image eraserImage = new Image(getClass().getResourceAsStream("eraser.png"));
	ToggleGroup tGroup = new ToggleGroup();
	ToggleButton penButton = new ToggleButton();
	ToggleButton eraserButton = new ToggleButton();
	Boolean erase = false;


	StackPane pane = new StackPane();
	Scene scene = new Scene(pane, 800, 500);
	HBox hbox = new HBox();
	GridPane grid = new GridPane();
	GridPane grid2 = new GridPane();
	GridPane grid3 = new GridPane();
	Label label = new Label("Brush/Eraser Width: ");

	@Override
	public void start(Stage stage){
		try {
			pane.setStyle("-fx-background-color: #f2f2f2"); //set background and eraser the same colour.
			gc = canvas.getGraphicsContext2D();
			gc.setStroke(Color.BLACK);
			gc.setLineWidth(1);

			cPicker.setValue(Color.BLACK); //default value for the ColorPicker.
			cPicker.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					if(!erase){
						gc.setStroke(cPicker.getValue());
					}
				}
			});

			//add different integers to the choiceBox.
			cb.getItems().addAll(1,2,3,4,5,6,7,8,9,10);
			cb.setValue(1); //default value
			//adding listener to the ChoiceBox to change the width of the brush based on the selected value.
			cb.valueProperty().addListener(new InvalidationListener() {
				@Override
				public void invalidated(Observable e) {
					int width = cb.getValue();
					gc.setLineWidth(width);
				}
			});

			//handles what to do when mouse is pressed (starting to draw)
			scene.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					gc.beginPath(); //start a new line when mouse is pressed again, without the old line would always be connected to the new mouseclick location automatically.
					gc.lineTo(e.getSceneX(), e.getSceneY());
					gc.stroke();
				}
			});

			//handles what to do when mouse is dragged on the screen
			scene.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					gc.lineTo(e.getSceneX(), e.getSceneY());
					gc.stroke();
				}
			});

			eraserButton.setUserData(Color.rgb(242, 242, 242)); //set background and eraser the same colour.

			//checks when and which togglebutton is pressed and executes the lines if there are changes.
			tGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
			    public void changed(ObservableValue<? extends Toggle> ov,
			        Toggle toggle, Toggle new_toggle) {
			            if (new_toggle == eraserButton){
			            	erase = true;
			            	gc.setStroke((Color) tGroup.getSelectedToggle().getUserData());
			            } else {
			            	erase = false;
			            	gc.setStroke(cPicker.getValue());
			            }

			         }
			});

			//gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); TYHJENTÄÄ KOKO KANKAAN.

			//add image to the toggleButtons.
			ImageView penIV = new ImageView(penImage);
			ImageView eraserIV = new ImageView(eraserImage);
			penButton.setGraphic(penIV);
			eraserButton.setGraphic(eraserIV);

			penButton.setToggleGroup(tGroup); //make the togglebuttons work like radiobuttons.
			penButton.setSelected(true); //pen is selected when the program is opened.
			eraserButton.setToggleGroup(tGroup);

			//layout of the buttons and boxes.
			grid.addRow(0, cPicker);
			grid.setHgap(20);
			grid.setAlignment(Pos.TOP_LEFT);
			grid.setPadding(new Insets(20,0,0,20));

			grid2.addRow(0, label, cb);
			grid2.setAlignment(Pos.TOP_LEFT);
			grid2.setPadding(new Insets(20,0,0,20));

			grid3.addRow(0, penButton, eraserButton);
			grid3.setHgap(5);
			grid3.setAlignment(Pos.TOP_LEFT);
			grid3.setPadding(new Insets(20,0,0,20));



			hbox.getChildren().addAll(grid, grid2, grid3);


			pane.getChildren().addAll(canvas, hbox);

			stage.setMinHeight(500);
			stage.setMinWidth(500);
			stage.setScene(scene);
			stage.show();

		} catch(Exception e){
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);
	}


}
