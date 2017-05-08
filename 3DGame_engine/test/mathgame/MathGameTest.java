package mathgame;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MathGameTest {
	MathGame mGame = new MathGame();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGiveAnswer() {
		assertEquals("Oikea vastaus",true, mGame.giveAnswer(2, 2, "4"));
		assertEquals("Väärä vastaus", false, mGame.giveAnswer(2, 2, "5"));
	}

	@Test
	public void countTest(){
		assertEquals("Kertolasku testi 0*2", 0*2, mGame.count(0, 2));
		assertEquals("Kertolasku testi 2*2", 2*2, mGame.count(2, 2));
	}

	@Test
	public void giveRandomTest(){
		int high = 9;
		int low = 1;
		assertTrue("Random below max", high >= mGame.giveRandom());
		assertTrue("Random below max", low <= mGame.giveRandom());
	}

	@Test
	public void getTasksTest(){
		assertEquals("Random parametri palauttaa 10 lukua", 10, mGame.getTasks("Random").size());
		assertEquals("Muu parametri palauttaa 5 lukua", 5, mGame.getTasks("1").size());
	}

}
