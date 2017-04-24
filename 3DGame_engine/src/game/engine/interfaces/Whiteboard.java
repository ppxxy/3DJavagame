package game.engine.interfaces;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Localization.Localization;
import Networking.WhiteboardConnection;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Whiteboard extends Application{

	Image img;
	Canvas canvas = new Canvas(800, 500);
	GraphicsContext gc;
	ColorPicker cPicker = new ColorPicker();
	ChoiceBox<Integer> cb = new ChoiceBox<Integer>();
	Image penImage = new Image(getClass().getResourceAsStream("/res/paintbrush.png"));
	Image eraserImage = new Image(getClass().getResourceAsStream("/res/eraser.png"));
	ToggleGroup tGroup = new ToggleGroup();
	ToggleButton penButton = new ToggleButton();
	ToggleButton eraserButton = new ToggleButton();
	Boolean erase = false; //tells if eraser is selected
	Button clearButton = new Button(Localization.getBundle().getString("clear_canvas"));



	StackPane pane = new StackPane();
	Scene scene = new Scene(pane, 800, 500);
	HBox hbox = new HBox();
	GridPane grid = new GridPane();
	GridPane grid2 = new GridPane();
	GridPane grid3 = new GridPane();
	GridPane grid4 = new GridPane();
	Label label = new Label(Localization.getBundle().getString("brush_eraser"));


	@Override
	public void start(final Stage stage){
		try {
			pane.setStyle("-fx-background-color: white"); //set background and eraser the same colour.
			gc = canvas.getGraphicsContext2D();
			gc.setStroke(Color.BLACK);
			gc.setLineWidth(1);
			img = new Image(getClass().getResourceAsStream("whiteboardImg.png"));
			gc.drawImage(img, 0, 0);

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

			eraserButton.setUserData(Color.WHITE); //set background and eraser the same colour.

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

			clearButton.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			    	if(WBConfirmationBox.Display()){
			    		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); //Clears the whole canvas
			    	}
			    }
			});

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

			grid4.addRow(0, clearButton);
			grid4.setAlignment(Pos.TOP_RIGHT);
			grid4.setPadding(new Insets(20,0,0,40));




			hbox.getChildren().addAll(grid, grid2, grid3, grid4);


			pane.getChildren().addAll(canvas, hbox);

			stage.setMinHeight(500);
			stage.setMinWidth(500);
			stage.setScene(scene);
			stage.show();

			//canvas is saved as a picture when the window is closed.
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		          public void handle(WindowEvent we) {
		        	  captureAndSaveDisplay();
		              stage.close();
		          }
		      });

		} catch(Exception e){
			e.printStackTrace();
		}

	}


	public void captureAndSaveDisplay(){
		/*FileChooser fileChooser = new FileChooser();

		//Set extension filter
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("png files (*.png)", "*.png"));

		//Prompt user to select a file
		File file = fileChooser.showSaveDialog(null);*/
		File file = new File("C:/Users/Tomi/git/3DJavaGame/3DGame_engine/src/game/engine/interfaces/whiteboardImg.png");

		if(file != null){
			try {
				//Pad the capture area
				WritableImage writableImage = new WritableImage((int)canvas.getWidth(),
						(int)canvas.getHeight());
				writableImage = canvas.snapshot(null, writableImage);
				RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);

				String dataurl = WhiteboardConnection.imageToDataUrl(renderedImage);
				RenderedImage REimage = WhiteboardConnection.dataUrlToImage(dataurl);

				//Write the snapshot to the chosen file
				ImageIO.write(REimage, "png", file);
			} catch (IOException ex) {
				ex.printStackTrace(); }
		}
	}


	public static void main(String[] args) {
		Localization.setNewLocale("ru", "RU");
		launch(args);
	}


}
