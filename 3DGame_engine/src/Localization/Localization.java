package Localization;

import java.util.Locale;
import java.util.ResourceBundle;

/**
* <h1>Localization</h1>
* This class handles different locales available in the program.
* <p>

* @author  Tomi Lehto
* @version 1.0
*/

public class Localization {

	private static Locale currentLocale = new Locale.Builder().setLanguage("en").setRegion("EN").build();
	private static ResourceBundle messages;

	private Localization(){
	}

	/**
	   * This method is used to change the current locale and update
	   * ResourceBundle variable based on the new locale.
	   * @param language Language for the locale
	   * @param region Region for the locale
	   */
	public static void setNewLocale(String language, String region){
		currentLocale = new Locale(language, region);
		messages = ResourceBundle.getBundle("Localization.MessagesBundle", currentLocale);
	}

	/**
	   * This method is used to change the current locale.
	   * @param language Language for the locale
	   * @param region Region for the locale
	   * @param script Script for the locale
	   */
	public static void setNewLocale(String language, String region, String script){
		currentLocale = new Locale.Builder().setLanguage(language).setRegion(region).setScript(script).build();
	}

	/**
	 *
	 * @return value of currentLocale
	 */
	public static Locale getCurrentLocale(){
		return currentLocale;
	}

	/**
	 *
	 * @return value of the current ResourceBundle variable.
	 */
	public static ResourceBundle getBundle(){
		return messages;
	}

}
