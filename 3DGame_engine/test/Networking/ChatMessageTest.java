package Networking;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import Dummies.ChatDummy;
import Networking.ChatMessage;

public class ChatMessageTest {
	private ChatMessage message;
	private ChatMessage namelessmessage;

	@BeforeClass
	public static void beforeClass(){
	}

	@org.junit.Before
	public void Before(){
		message = new ChatMessage("player","message");
		namelessmessage= new ChatMessage("message");

	}

	@Test
	public void testCreate() {
		assertEquals("Message should equal 'message'","message",message.getMessage());
		assertEquals("Sender name should equal 'player'","player",message.getName());
		assertEquals("Message.tostring should equal 'player: message'","player: message",message.toString());
	}
	@Test
	public void testCreateNameless(){
		assertEquals("Message should equal 'message'","message",namelessmessage.getMessage());
		assertEquals("Sender name should be null",null,namelessmessage.getName());
		assertEquals("Message.tostring should equal 'message'","message",namelessmessage.toString());
	}
}
