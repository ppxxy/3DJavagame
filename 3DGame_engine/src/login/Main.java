package login;

import Localization.Localization;
import Networking.Connect;

public class Main {

	public static void main(String[] args) {

		Connect connect = new Connect();
		new Window(Localization.getBundle().getString("login_main_login"));
	}

}
