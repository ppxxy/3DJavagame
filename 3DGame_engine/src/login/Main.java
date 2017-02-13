package login;

import game.engine.database.Connect;

public class Main {

	public static void main(String[] args) {

		Connect connect = new Connect();
		connect.connectDb();
		new Window("Log In");
	}

}
