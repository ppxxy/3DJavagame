package Localization;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Localization {

	private static Locale currentLocale;
	private static ResourceBundle messages;

	private Localization(){
	}

	public static void setNewLocale(String language, String region){
		currentLocale = new Locale(language, region);
		messages = ResourceBundle.getBundle("Localization.MessagesBundle", currentLocale);
	}

	public static void setNewLocale(String language, String region, String script){
		currentLocale = new Locale.Builder().setLanguage(language).setRegion(region).setScript(script).build();
	}

	public static Locale getCurrentLocale(){
		return currentLocale;
	}

	public static ResourceBundle getBundle(){
		return messages;
	}

	public static void main(String[] args) {
		Localization.setNewLocale("ru", "RU");
		System.out.println("Current Locale: " + getCurrentLocale());

		// read MyLabels_en_US.properties
		System.out.println("Say how are you in US English: " + getBundle().getString("how_are_you"));

		Localization.setNewLocale("fi", "FI");

		// read MyLabels_ms_MY.properties
		System.out.println("Current Locale: " + getCurrentLocale());
		System.out.println("Say how are you in Finnish: " + getBundle().getString("how_are_you"));
	}
}
