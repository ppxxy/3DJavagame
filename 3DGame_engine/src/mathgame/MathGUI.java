package mathgame;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MathGUI extends Application implements MathGUI_IF{
	Scene sceneMain, sceneTest, sceneResults;
	BorderPane paneMain, paneTest, paneResults;
	String selectedValue;
	ArrayList<Integer> list = null;
	MathController_IF controller;
	int apu;
	int value1;
	int value2;
	Label labelTest;
	int correctAnswers;
	boolean answerVisible = true;
	Button answerButton;
	Button nextButton;

	public void init(){
		MathGame_IF game = new MathGame();
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
		labelTest = new Label();
		Label labelTestResult = new Label();
		TextField answerField = new TextField();
		answerButton = new Button("Vastaa");
		nextButton = new Button("Next");
		sceneTest = new Scene(paneTest, 300, 200);
		hboxTest.setPadding(new Insets(10,10,10,10));
		hboxTest.setAlignment(Pos.CENTER);
		hboxTest.getChildren().addAll(labelTest, answerField);
		paneTest.setTop(hboxTest);
		paneTest.setCenter(labelTestResult);
		labelTestResult.setVisible(false);
		hboxTest2.setAlignment(Pos.CENTER);
		hboxTest2.setPadding(new Insets(10,10,10,10));
		hboxTest2.getChildren().addAll(answerButton, nextButton);
		nextButton.setVisible(false);
		paneTest.setBottom(hboxTest2);

		//result window
		paneResults = new BorderPane();
		HBox hboxResults = new HBox();
		Label labelResults = new Label();
		Button newTestButton = new Button("New test");
		Button exitButton = new Button("Exit");
		//hboxResults.setPadding(new Insets(10,10,10,10));
		hboxResults.setSpacing(15);
		hboxResults.setAlignment(Pos.CENTER);
		hboxResults.getChildren().addAll(newTestButton, exitButton);
		paneResults.setTop(labelResults);
		paneResults.setPadding(new Insets(10,10,10,10));
		paneResults.setAlignment(labelResults, Pos.CENTER);
		paneResults.setCenter(hboxResults);
		sceneResults = new Scene(paneResults, 300, 200);


		stage.setScene(sceneMain);
		stage.show();

		buttonMain.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	correctAnswers = 0;
		    	apu = 0;
		    	selectedValue = cb.getValue();
		    	list = controller.startTest();
		    	stage.setScene(sceneTest);
		    	printTask();

		    }
		});

		answerButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	if(!StringUtils.isNumeric(answerField.getText())){
		    		labelTestResult.setText("Syötä kokonaisluku vastauskenttään");
		    	} else {
		    		if(controller.checkAnswer(answerField.getText())){
			    		labelTestResult.setText("Oikea vastaus");
			    		correctAnswers++;
			    	} else {
			    		labelTestResult.setText("Väärä vastaus, oikea vastaus on: "+controller.count());
			    	}

		    		switchButtons();
		    	}

		    	labelTestResult.setVisible(true);

		    }
		});

		nextButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	if(apu < 5){
		    		switchButtons();
			    	answerField.clear();
			    	labelTestResult.setVisible(false);
			    	printTask();
		    	} else {
		    		switchButtons();
		    		answerField.clear();
			    	labelTestResult.setVisible(false);
		    		labelResults.setText("Vastasit oikein "+correctAnswers+" / 5 kysymykseen");
		    		stage.setScene(sceneResults);
		    	}

		    }
		});

		newTestButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	stage.setScene(sceneMain);
		    }
		});

		exitButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	Platform.exit();
		    }
		});

		answerField.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	            	if(answerVisible){
	            		answerButton.fire();
	            	}
	            }
	        }
	    });

	}

	public void updateTestLabel(){
		labelTest.setText(value1+" x "+value2+" = ");
	}

	public void switchButtons(){
		if(answerVisible){
			answerButton.setVisible(false);
			answerVisible = false;
	    	nextButton.setVisible(true);
		} else {
			answerButton.setVisible(true);
			answerVisible = true;
	    	nextButton.setVisible(false);
		}

	}

	@Override
	public void printTask(){
		value2 = list.get(apu);
		if(selectedValue.equals("Random")){
    		value1 = list.get(apu+4);
    		updateTestLabel();
    		apu++;
    	} else {
    		value1 = Integer.parseInt(selectedValue);
    		updateTestLabel();
    		apu++;
    	}
	}

	@Override
	public String getSelectedValue(){
		return selectedValue;
	}

	@Override
	public int getValue1(){
		return value1;
	}

	@Override
	public int getValue2(){
		return value2;
	}



	public static void main(String[] args) {
        launch(args);
    }

}
