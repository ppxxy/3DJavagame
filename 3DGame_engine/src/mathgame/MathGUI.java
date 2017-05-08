package mathgame;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import Localization.Localization;
import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.stage.Stage;


/**
* <h1>MathGame</h1>
* The MathGUI program implements an user interface for an application that
* can used to practice multiplication tables.
* <p>

* @author  Tomi Lehto
* @version 1.0
*/

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
		Localization.setNewLocale("ru", "RU");
		MathGame_IF game = new MathGame();
		controller = new MathController(this, game);
	}

	@Override
	public void start(final Stage stage) throws Exception {

		//main window
		paneMain = new BorderPane();
		HBox hboxMain = new HBox();
		Label labelMain = new Label(Localization.getBundle().getString("math_choose")+": ");
		Button buttonMain = new Button(Localization.getBundle().getString("math_begin"));
		ChoiceBox<String> cb = new ChoiceBox<String>();
		cb.getItems().addAll(Localization.getBundle().getString("random"), "2", "3", "4", "5", "6", "7", "8", "9");
		cb.setValue(Localization.getBundle().getString("random"));
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
		answerButton = new Button(Localization.getBundle().getString("math_answer"));
		nextButton = new Button(Localization.getBundle().getString("math_next"));
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
		Button newTestButton = new Button(Localization.getBundle().getString("math_new_test"));
		Button exitButton = new Button(Localization.getBundle().getString("math_exit"));
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

		//starts the exercise.
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

		//sends the users answer to MathGame and prints if it was correct or not.
		answerButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	if(!StringUtils.isNumeric(answerField.getText())){
		    		labelTestResult.setText(Localization.getBundle().getString("math_input_error"));
		    	} else {
		    		if(controller.checkAnswer(answerField.getText())){
			    		labelTestResult.setText(Localization.getBundle().getString("math_correct"));
			    		correctAnswers++;
			    	} else {
			    		labelTestResult.setText(Localization.getBundle().getString("math_wrong")+": "+controller.count());
			    	}

		    		switchButtons();
		    	}

		    	labelTestResult.setVisible(true);

		    }
		});

		//loads a new exercise after the previous has been answered.
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
		    		labelResults.setText(Localization.getBundle().getString("math_result1")+" "+correctAnswers+" / 5 "+Localization.getBundle().getString("math_result2"));
		    		stage.setScene(sceneResults);
		    	}

		    }
		});

		//starts a new test after the user is done with the old one.
		newTestButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	stage.setScene(sceneMain);
		    }
		});

		//exits the program.
		exitButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	Platform.exit();
		    }
		});

		//allows the use of enter key to submit (click answer button) textfield input.
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

	/**
	   * Updates the labels in the gui for the new task values.
	   */
	public void updateTestLabel(){
		labelTest.setText(value1+" x "+value2+" = ");
	}

	/**
	   * Switch the visibility of answer and next buttons.
	   */
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

	/**
	   * This method is used to print the task generated in MathGame to the GUI.
	   */
	@Override
	public void printTask(){
		value2 = list.get(apu);
		if(selectedValue.equals(Localization.getBundle().getString("random"))){
    		value1 = list.get(apu+4);
    		updateTestLabel();
    		apu++;
    	} else {
    		value1 = Integer.parseInt(selectedValue);
    		updateTestLabel();
    		apu++;
    	}
	}

	/**
	 *
	 * @return current selectedValue ("random" or a number between 1 and 9)
	 */
	@Override
	public String getSelectedValue(){
		return selectedValue;
	}

	/**
	 *
	 * @return value1 of the current multiplication exercise (value between 1 and 9)
	 */
	@Override
	public int getValue1(){
		return value1;
	}

	/**
	 *
	 * @return value2 of the current multiplication exercise (value between 1 and 9)
	 */
	@Override
	public int getValue2(){
		return value2;
	}



	public static void main(String[] args) {
        launch(args);
    }

}
