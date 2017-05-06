package mathgame;

import java.util.ArrayList;
import java.util.Random;
import Localization.Localization;


/**
* <h1>MathGame</h1>
* The MathGame program implements an application that
* can used to practice multiplication tables
* <p>

* @author  Tomi Lehto
* @version 1.0
*/

public class MathGame implements MathGame_IF{
	Random random = new Random();
	ArrayList<Integer> list;

	
	/**
	   * This method is used to get multiplication questions.
	   * @param selectedValue This is the parameter that tells which multiplication table the user wants to practice
	   * @return ArrayList<Integer> This returns a list of the exercises.
	   */
	@Override
	public ArrayList<Integer> getTasks(String selectedValue) {
		list = new ArrayList<Integer>();
		if(selectedValue.equals(Localization.getBundle().getString("random"))){
			for(int i=0; i<10; i++){
				list.add(giveRandom());
			}
		} else {
			for(int i=0; i<5; i++){
				list.add(giveRandom());
			}
		}
		return list;
	}

	/**
	   * This method is used to get a random number.
	   * @return int This returns a random number between 1 and 9.
	   */
	@Override
	public int giveRandom(){
		return random.nextInt(9)+1;
	}

	/**
	   * This method is used to get the multiple of 2 numbers.
	   * @param a A number between 1 and 9
	   * @param b A number between 1 and 9
	   * @return int This returns the multiple of parameters a and b.
	   */
	@Override
	public int count(int a, int b){
		return a*b;
	}

	/**
	   * This method is used to check if the answer the user gave is a correct one.
	   * @param a Number used in the multiplication exercise
	   * @param b Number used in the multiplication exercise
	   * @param answer The answer given by the user
	   * @return boolean This returns true if the given answer was correct.
	   */
	@Override
	public boolean giveAnswer(int a, int b, String answer){
		return a*b==Integer.parseInt(answer);
	}


}
