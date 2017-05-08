package game.connection.objects;

import java.io.Serializable;

public class RequestData implements Serializable{
	private static final long serialVersionUID = 8608139073406925968L;

	public static final RequestData REQUEST_PLAYERS = new RequestData(1), REQUEST_IMAGE = new RequestData(2);

	private int id;

	private RequestData(int id){
		this.id = id;
	}

	public int getId(){
		return this.id;
	}

}
