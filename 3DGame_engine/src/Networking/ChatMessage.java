package Networking;

import java.io.Serializable;

public class ChatMessage implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String message;
	//private String secondline;
	//private int rows;

	public ChatMessage(String name,String message){
		this.name=name;
		this.message=message;
		/*if(message.length()>30){
			this.rows=2;
			this.message=message.substring(0, 30);
			this.secondline=message.substring(30,message.length()-1);
		}else{
			this.message=message;
			this.rows=1;
		}*/
		//System.out.println(this.message);
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
	public String geMessage(){
		return this.message;
	}
	/*public int getRows(){
		return rows;
	}
	/*public String getSecondline(){
		return this.secondline;
	}*/
	@Override
	public String toString(){
		if(name!=null){
			return name+": "+message;
		}
		return message;
	}
}
