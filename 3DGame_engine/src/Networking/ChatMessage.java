package Networking;

import java.io.Serializable;

public class ChatMessage implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String message;

	public ChatMessage(String name,String message){
		this.name=name;
		this.message=message;
	}
	public ChatMessage(String message){
		this.message=message;
	}
	public void setMessage(String message){
		this.message=message;
	}
	public String getName(){
		return this.name;
	}
	public String getMessage(){
		return this.message;
	}
	@Override
	public String toString(){
		if(name!=null){
			return name+": "+message;
		}
		return message;
	}
}
