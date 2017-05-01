package mathgame;

import java.util.ArrayList;

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
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MathGUI extends Application{
	Scene sceneMain, sceneTest, sceneResults;
	BorderPane paneMain, paneTest, paneResults;
	String selectedValue;
	ArrayList<Integer> list = null;
	MathController controller;
	int apu;
	int value1;
	int value2;
	int correctAnswers;

	public void init(){
		MathGame game = new MathGame();
		controller = new MathController(this, game);
	}

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

		//exercise window
		paneTest = new BorderPane();
		HBox hboxTest = new HBox();
		HBox hboxTest2 = new HBox();
		Label labelTest1 = new Label();
		Label labelTestX = new Label("x");
		Label labelTest2 = new Label();
		Label labelTestResult = new Label();
		Label labelEquals = new Label("=");
		TextField answerField = new TextField();
		Button answerButton = new Button("Vastaa");
		Button nextButton = new Button("Next");
		sceneTest = new Scene(paneTest, 300, 200);
		hboxTest.setPadding(new Insets(10,10,10,10));
		hboxTest.setAlignment(Pos.CENTER);
		hboxTest.getChildren().addAll(labelTest1, labelTestX, labelTest2, labelEquals, answerField);
		paneTest.setTop(hboxTest);
		paneTest.setCenter(labelTestResult);
		labelTestResult.setVisible(false);
		hboxTest2.setAlignment(Pos.CENTER);
		hboxTest2.setPadding(new Insets(10,10,10,10));
		hboxTest2.getChildren().addAll(answerButton, nextButton);
		nextButton.setVisible(false);
		paneTest.setBottom(hboxTest2);

		stage.setScene(sceneMain);
		stage.show();

		buttonMain.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	correctAnswers = 0;
		    	apu = 0;
		    	selectedValue = cb.getValue();
		    	list = controller.startTest();
		    	stage.setScene(sceneTest);
		    	if(selectedValue.equals("Random")){
		    		value1 = list.get(apu);
		    		value2 = list.get(apu+4);
		    		labelTest1.setText(""+value1);
		    		labelTest2.setText(""+value2);
		    		apu++;
		    	} else {
		    		value1 = Integer.parseInt(selectedValue);
		    		labelTest1.setText(""+value1);
		    		labelTest2.setText(""+list.get(apu));
		    		apu++;
		    	}
		    }
		});

		answerButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	if(controller.checkAnswer(answerField.getText())){
		    		labelTestResult.setText("Oikea vastaus");
		    	} else {
		    		labelTestResult.setText("V‰‰r‰ vastaus, oikea vastaus on: "+count());
		    	}
		    }
		});

		nextButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {

		    }
		});

	}


	public String getSelectedValue(){
		return selectedValue;
	}

	public int getValue1(){
		return value1;
	}

	public int getValue2(){
		return value2;
	}

	public int count(){
		return value1*value2;
	}



	public static void main(String[] args) {
        launch(args);
    }

}
