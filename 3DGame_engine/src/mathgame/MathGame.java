package mathgame;

import java.util.ArrayList;
import java.util.Random;

public class MathGame implements MathGame_IF{
	Random random = new Random();
	ArrayList<Integer> list;

	@Override
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

	@Override
	public int giveRandom(){
		return random.nextInt(9)+1;
	}

	@Override
	public boolean giveAnswer(int a, int b, String answer){
		return a*b==Integer.parseInt(answer);
	}


}
