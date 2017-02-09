package login;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class Borders {

	public void addButtonBorders(ArrayList<JButton> buttons){

		for(int i = 0; i<buttons.size(); i++){
			buttons.get(i).setBorder(new LineBorder(Color.black));
		}

    }

	public void addTextFieldBorders(ArrayList<JTextField> textFields){

		for(int i = 0; i<textFields.size(); i++){
			textFields.get(i).setBorder(new LineBorder(Color.black));
		}
	}

	public void addPassFieldBorders(ArrayList<JPasswordField> passFields){

		for(int i = 0; i<passFields.size(); i++){
			passFields.get(i).setBorder(new LineBorder(Color.black));
		}
	}

	public void addTextAreaBorders(ArrayList<JTextArea> textAreas){

		for(int i = 0; i<textAreas.size(); i++){
			textAreas.get(i).setBorder(new LineBorder(Color.black));
			textAreas.get(i).setLineWrap(true);
			textAreas.get(i).setWrapStyleWord(true);
		}
	}

}
