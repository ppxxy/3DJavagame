package game.engine.interfaces;

import static org.junit.Assert.*;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import Dummies.ChatBoxDummy;
import Dummies.ChatDummy;
import Networking.Chat;
import Networking.ChatMessage;

public class ChatBoxTest {
	private static ChatMessage message;
	private static ChatMessage longmessage;
	private static ChatBoxDummy chatbox;
	private static Graphics g;
	private static BufferedImage image;
	private static FontMetrics fm;

	@BeforeClass
	public static void beforeClass(){
		chatbox = new ChatBoxDummy(new ChatDummy());
		message = new ChatMessage("player","message");
		longmessage= new ChatMessage("player","message1234 56789 0123456789 123 05432949 98798798798 009809867567 87987867576978 23445435246456");
		image = new BufferedImage(1000,300,BufferedImage.TYPE_INT_ARGB);
		g=image.getGraphics();
		g.setFont(new Font("TAHOMA",Font.PLAIN,40));
		fm=g.getFontMetrics();
	}

	@org.junit.Before
	public void Before(){
	}

	@Test
	public void testCutting() {
		assertTrue("Message should be cut into an array of two or more Strings.",chatbox.cutString(longmessage.toString(), fm).size()>1);
	}
	/*
	@Test
	public void testCreateNameless(){
		assertEquals("Message should equal 'message'","message",namelessmessage.getMessage());
		assertEquals("Sender name should be null",null,namelessmessage.getName());
		assertEquals("Message.tostring should equal 'message'","message",namelessmessage.toString());
	}*/
}
