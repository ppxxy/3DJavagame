package Localization;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LocalizationTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Localization.setNewLocale("en", "EN"); //default
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testRU() {
		Localization.setNewLocale("ru", "RU");
		assertEquals("RUTest ", "контрольная работа", Localization.getBundle().getString("for_test"));

	}

	@Test
	public void testFI() {
		Localization.setNewLocale("fi", "FI");
		assertEquals("FITest ", "TestiSana", Localization.getBundle().getString("for_test"));
	}

	@Test
	public void TestDefault() {
		assertEquals("ENTest ", "TestWord", Localization.getBundle().getString("for_test"));
		Localization.setNewLocale("no", "NO");
		assertEquals("DefaultTest ", "TestWord", Localization.getBundle().getString("for_test"));
	}

}
