package game.engine.interfaces;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;

public class ChatControls {
	private boolean active=false;
	private MessageBox msgbox;
	private ChatBox chatbox;
	private boolean[] keys = new boolean[65536];

	public ChatControls(MessageBox msgbox,ChatBox chatbox){
		this.msgbox=msgbox;
		this.chatbox=chatbox;
		try {
			Keyboard.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Keyboard.enableRepeatEvents(true);
	}

	public void poll(){
	while(Keyboard.next()) {
		checkActive();
		if(active){
		if(Keyboard.getEventKeyState()){
			keys[Keyboard.getEventKey()]=true;
				if(keys[Keyboard.KEY_BACK]){
					msgbox.backspace();
				}else if(keys[Keyboard.KEY_UP]){
					chatbox.scrollUp();
				}else if(keys[Keyboard.KEY_DOWN]){
					chatbox.scrollDown();
				}else{
					msgbox.drawChar(Keyboard.getEventCharacter());
				}
		}else {
			keys[Keyboard.getEventKey()]=false;
		 }

	}
	}

	}
	public void checkActive(){
		if(active){
			if(Keyboard.isKeyDown(Keyboard.KEY_RETURN)){
				msgbox.send();
				msgbox.deactivate();
				active=false;
			}else if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
				active=false;
				msgbox.deactivate();
			}
		}else{
			if(Keyboard.isKeyDown(Keyboard.KEY_RETURN)){
				active=true;
				msgbox.activate();
			}
		}
	}

	}
