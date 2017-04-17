package Networking;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import Dummies.ChatDummy;
import Networking.ChatMessage;

public class ChatTest {
	private static ChatDummy chat;
	private ChatMessage message;

	@BeforeClass
	public static void beforeClass(){
	}

	@org.junit.Before
	public void Before(){
		chat=new ChatDummy();
		message = new ChatMessage("testplayer","123test");
	}

	@Test
	public void testRecieve() {
		chat.recieveMessage(message);
		assertEquals("Message should be recieved",1,chat.getMessages().size());
		for(int i=0;i<chat.maxmessages+1;i++){
			chat.recieveMessage(message);
		}
		assertEquals("Recieved messages should not exceed max messages",chat.maxmessages,chat.getMessages().size());
	}
	@Test
	public void testMute() {
		chat.recieveMessage(message);
		chat.mute("testplayer");
		assertEquals("Muted players message should be deleted",0,chat.getMessages().size());
		chat.recieveMessage(new ChatMessage("player2","test"));
		chat.recieveMessage(message);
		chat.mute("testplayer");
		assertEquals("Other players messages should not be deleted when someone gets muted",1,chat.getMessages().size());
	}

}
