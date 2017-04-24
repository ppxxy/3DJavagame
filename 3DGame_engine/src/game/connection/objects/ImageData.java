package game.connection.objects;

import java.awt.image.RenderedImage;
import java.io.Serializable;

import Networking.WhiteboardConnection;

public class ImageData implements Serializable{
	private static final long serialVersionUID = 4923167136299302702L;
	
	private String data;
	
	public ImageData(String data){
		this.data = data;
	}
	
	public String getData(){
		return this.data;
	}
	
	public RenderedImage toRenderedImage(){
		return WhiteboardConnection.dataUrlToImage(data);
	}
}
