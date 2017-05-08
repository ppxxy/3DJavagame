package Dummies;

import java.util.ArrayList;
import Networking.ChatMessage;
import game.engine.interfaces.ChatControls;

public class ChatDummy {
	private ArrayList<ChatMessage> messages=new ArrayList<ChatMessage>();
	public static final int maxmessages=99;
	private ChatBoxDummy ifce;
	private MessageBoxDummy msgbox;
	private ChatControls kb;

	public ChatDummy(){
		ifce=new ChatBoxDummy(this);
		msgbox=new MessageBoxDummy(this);
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
	public void mute(String player){
		for(int i=0;i<messages.size();i++){
			if(messages.get(i).getName().equals(player)){
				messages.remove(i);
				i--;
			}
		}
	}
	/*public ChatBox getChatbox(){
		return ifce;
	}
	public MessageBox getMessageBox(){
		return this.msgbox;
	}*/
}