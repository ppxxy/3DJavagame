package mathgame;

import java.util.ArrayList;
import java.util.Random;

public class MathGame {
	Random random = new Random();
	ArrayList<Integer> list;

	public ArrayList<Integer> getTasks(String selectedValue) {
		list = new ArrayList<Integer>();
		if(selectedValue.equals("Random")){
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

	public int giveRandom(){
		return random.nextInt(9)+1;
	}

	public boolean giveAnswer(int a, int b, String answer){
		return a*b==Integer.parseInt(answer);
	}


}
