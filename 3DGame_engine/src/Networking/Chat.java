package Networking;

import java.util.ArrayList;

import org.lwjgl.LWJGLException;

import game.engine.interfaces.ChatBox;
import game.engine.interfaces.ChatControls;
import game.engine.interfaces.MessageBox;

public class Chat {
	private ArrayList<ChatMessage> messages=new ArrayList<ChatMessage>();
	private final int maxmessages=99;
	private ChatBox ifce;
	private MessageBox msgbox;
	private ChatControls kb;

	public Chat(){
		ifce=new ChatBox(this);
		msgbox=new MessageBox(this);
	}

	public void recieveMessage(ChatMessage message){
		if(messages.size()<maxmessages){
			messages.add(0,message);
		}else{
			messages.remove(maxmessages-1);
			messages.add(0,message);
		}
		drawMessages();
	}

	public void sendMessage(String message){
		ChatMessage msg=new ChatMessage("testplayer", message);
		game.engine.main.Main.connection.send(msg);
	}
	public ArrayList<ChatMessage> getMessages(){
		return messages;
	}
	public void drawMessages(){
		ifce.drawMessages(messages);
	}
	public ChatBox getChatbox(){
		return ifce;
	}
	public MessageBox getMessageBox(){
		return this.msgbox;
	}
	public void mute(String player){
		for(int i=0;i<messages.size();i++){
			if(messages.get(i).getName().equals(player)){
				messages.remove(i);
				i--;
			}
		}
	}
}