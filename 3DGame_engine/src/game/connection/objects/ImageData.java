package game.connection.objects;

import java.io.Serializable;

import Networking.WhiteboardConnection;
import javafx.scene.image.Image;

public class ImageData implements Serializable{
	private static final long serialVersionUID = 4923167136299302702L;
	
	private String data;
	
	public ImageData(String data){
		this.data = data;
	}
	
	public String getData(){
		return this.data;
	}
	
	public Image toRenderedImage(){
		return WhiteboardConnection.dataUrlToImage(data);
	}
}
